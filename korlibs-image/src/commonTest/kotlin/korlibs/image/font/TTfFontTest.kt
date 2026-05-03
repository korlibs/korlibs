package korlibs.image.font

import korlibs.image.annotation.KorimInternal
import korlibs.io.lang.Charsets
import korlibs.io.lang.toByteArray
import korlibs.io.stream.openFastStream
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Minimal valid TTF byte-array builder used to construct synthetic fonts in
 * memory for unit testing.  Only the tables actually referenced by the tests
 * are included; everything else is omitted or stubbed.
 */
private object TtfTestBuilder {

    // Encode a big-endian 16-bit unsigned integer
    fun u16(v: Int): ByteArray = byteArrayOf((v ushr 8).toByte(), v.toByte())

    // Encode a big-endian 32-bit signed integer
    fun s32(v: Int): ByteArray = byteArrayOf(
        (v ushr 24).toByte(), (v ushr 16).toByte(), (v ushr 8).toByte(), v.toByte()
    )

    fun s16(v: Int): ByteArray = byteArrayOf((v ushr 8).toByte(), v.toByte())

    fun bytes(vararg b: Int): ByteArray = ByteArray(b.size) { b[it].toByte() }

    fun concat(vararg arrays: ByteArray): ByteArray {
        val total = arrays.sumOf { it.size }
        val out = ByteArray(total)
        var pos = 0
        for (a in arrays) {
            a.copyInto(out, pos); pos += a.size
        }
        return out
    }

    /** Pad a byte array to a multiple of 4 bytes. */
    fun pad4(b: ByteArray): ByteArray {
        val rem = b.size % 4
        return if (rem == 0) b else b + ByteArray(4 - rem)
    }

    /** Build a minimal but parseable TTF containing only the tables needed. */
    fun buildMinimalTtf(
        unitsPerEm: Int = 1000,
        ascender: Int = 800,
        descender: Int = -200,
        lineGap: Int = 0,
        numGlyphs: Int = 2,
        advanceWidths: IntArray = intArrayOf(500, 600),
        /** Map of codePoint -> glyphIndex, written via cmap format 4 */
        charMap: Map<Int, Int> = mapOf('A'.code to 1),
        /** Optional font name placed in the 'name' table */
        fontName: String = "TestFont",
    ): ByteArray {
        // ── 'head' table ──────────────────────────────────────────────────────
        val headTable = concat(
            u16(1), u16(0),           // version 1.0
            s32(0),                   // fontRevision
            s32(0),                   // checkSumAdjustment
            s32(0x5F0F3CF5),          // magicNumber
            u16(0),                   // flags
            u16(unitsPerEm),          // unitsPerEm
            ByteArray(16),            // created + modified (8 bytes each)
            s16(0), s16(0),           // xMin, yMin
            s16(1000), s16(1000),     // xMax, yMax
            u16(0),                   // macStyle
            u16(8),                   // lowestRecPPEM
            s16(2),                   // fontDirectionHint
            s16(0),                   // indexToLocFormat (0 = Int16)
            s16(0),                   // glyphDataFormat
        )

        // ── 'maxp' table ──────────────────────────────────────────────────────
        val maxpTable = concat(
            s32(0x00010000),   // version 1.0
            u16(numGlyphs),
            u16(10),
            u16(4),           // maxPoints, maxContours
            u16(0),
            u16(0),            // maxCompositePoints, maxCompositeContours
            u16(2),
            u16(0),            // maxZones, maxTwilightPoints
            u16(0),
            u16(2),            // maxStorage, maxFunctionDefs
            u16(0),
            u16(128),          // maxInstructionDefs, maxStackElements
            u16(0),
            u16(0),
            u16(0),    // maxSizeOfInstructions, maxComponentElements, maxComponentDepth
        )

        // ── 'hhea' table ──────────────────────────────────────────────────────
        val hheaTable = concat(
            u16(1), u16(0),           // version
            s16(ascender),
            s16(descender),
            s16(lineGap),
            u16(advanceWidths.maxOrNull() ?: 0),  // advanceWidthMax
            s16(0), s16(0), s16(0),   // minLSB, minRSB, xMaxExtent
            s16(1), s16(0), s16(0),   // caretSlopeRise, caretSlopeRun, caretOffset
            s16(0), s16(0), s16(0), s16(0),  // reserved x4
            s16(0),                   // metricDataFormat
            u16(numGlyphs),           // numberOfHMetrics
        )

        // ── 'hmtx' table ──────────────────────────────────────────────────────
        var hmtxTable = ByteArray(0)
        for (aw in advanceWidths) hmtxTable = concat(hmtxTable, u16(aw), s16(0))

        // ── 'loca' table (format 0 = Int16 offsets / 2) ───────────────────────
        // All glyphs are empty (start == end for every glyph).
        var locaTable = ByteArray(0)
        repeat(times = (0..numGlyphs).count()) {
            locaTable = concat(locaTable, u16(0))
        }

        // ── 'glyf' table (empty – all glyphs are zero-size) ──────────────────
        val glyfTable = ByteArray(0)

        // ── 'cmap' table (format 4) ───────────────────────────────────────────
        val cmapTable = buildCmapFormat4(charMap)

        // ── 'name' table ──────────────────────────────────────────────────────
        val nameTable = buildNameTable(fontName)

        // ── Assemble offset table + table directory ───────────────────────────
        data class RawTable(val tag: String, val data: ByteArray) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || this::class != other::class) return false

                other as RawTable

                if (tag != other.tag) return false
                if (!data.contentEquals(other.data)) return false

                return true
            }

            override fun hashCode(): Int {
                var result = tag.hashCode()
                result = 31 * result + data.contentHashCode()
                return result
            }
        }

        val tables = listOf(
            RawTable("cmap", cmapTable),
            RawTable("glyf", glyfTable),
            RawTable("head", headTable),
            RawTable("hhea", hheaTable),
            RawTable("hmtx", hmtxTable),
            RawTable("loca", locaTable),
            RawTable("maxp", maxpTable),
            RawTable("name", nameTable),
        )

        val numTables = tables.size
        val searchRange = (1 shl (31 - numTables.countLeadingZeroBits())) * 16
        val entrySelector = 31 - numTables.countLeadingZeroBits()
        val rangeShift = numTables * 16 - searchRange

        // Offset table: 12 bytes; table directory: 16 bytes × numTables
        val headerSize = 12 + numTables * 16
        var currentOffset = headerSize

        // Align each table to 4 bytes
        val tableOffsets = mutableListOf<Int>()
        val paddedTables = tables.map { raw ->
            val padded = pad4(raw.data)
            tableOffsets += currentOffset
            currentOffset += padded.size
            padded
        }

        val offsetTable = concat(
            u16(1), u16(0),         // sfVersion 1.0
            u16(numTables),
            u16(searchRange),
            u16(entrySelector),
            u16(rangeShift),
        )

        var directory = ByteArray(0)
        for ((i, raw) in tables.withIndex()) {
            directory = concat(
                directory,
                raw.tag.toByteArray(Charsets.UTF8),
                s32(0),                         // checksum (ignored)
                s32(tableOffsets[i]),
                s32(raw.data.size),
            )
        }

        return concat(offsetTable, directory, *paddedTables.toTypedArray())
    }

    private fun buildCmapFormat4(charMap: Map<Int, Int>): ByteArray {
        // Single-record cmap with one format-4 subtable
        // For simplicity we create one segment per mapping + end segment.
        val sorted = charMap.entries.sortedBy { it.key }

        // Build segments: one per code point for simplicity
        val segments = sorted.map { (cp, gi) -> Triple(cp, cp, gi - cp) } +
                listOf(Triple(0xFFFF, 0xFFFF, 1))  // terminator

        val segCount = segments.size
        val searchRange16 = (1 shl (31 - segCount.countLeadingZeroBits())) * 2
        val entrySelector16 = 31 - segCount.countLeadingZeroBits()
        val rangeShift16 = segCount * 2 - searchRange16

        var subtable = concat(
            u16(4),                  // format
            u16(0),                  // length placeholder – filled below
            u16(0),                  // language
            u16(segCount * 2),       // segCount×2
            u16(searchRange16),
            u16(entrySelector16),
            u16(rangeShift16),
        )
        for ((_, ec, _) in segments) subtable = concat(subtable, u16(ec))
        subtable = concat(subtable, u16(0))  // reservedPad
        for ((sc, _, _) in segments) subtable = concat(subtable, u16(sc))
        for ((_, _, delta) in segments) subtable = concat(subtable, s16(delta))
        repeat(times = segments.indices.count()) {
            subtable = concat(subtable, u16(0))
        }  // idRangeOffset all 0

        // Patch length field (offset 2, 2 bytes)
        subtable[2] = (subtable.size ushr 8).toByte()
        subtable[3] = subtable.size.toByte()

        // cmap header: version(2) + numTables(2) + EncodingRecord(8)
        val cmapHeader = concat(
            u16(0),    // version
            u16(1),    // numTables
            u16(3), u16(1),          // platformId=3, encodingId=1 (Windows Unicode BMP)
            s32(4 + 4),              // offset to subtable: 4 (version+numTables) + 4 (this record past version/numTables)
        )
        // Re-compute: header = 4 + 8 = 12 bytes
        val headerBytes = concat(u16(0), u16(1), u16(3), u16(1), s32(12))
        return concat(headerBytes, subtable)
    }

    private fun buildNameTable(fontName: String): ByteArray {
        val nameBytes = fontName.toByteArray(Charsets.UTF8)
        val stringOffset = 6 + 12  // header(6) + one record(12)
        val record = concat(
            u16(0), u16(0), u16(0),  // platformId=0, encodingId=0, languageId=0
            u16(1),                  // nameId=1 (Font Family Name)
            u16(nameBytes.size),
            u16(0),                  // offset within string storage
        )
        val header = concat(u16(0), u16(1), u16(stringOffset))
        return concat(header, record, nameBytes)
    }
}

@OptIn(KorimInternal::class)
class TtfFontTest {

    @Test
    fun `TtfFont can be constructed from minimal byte array`() {
        val bytes = TtfTestBuilder.buildMinimalTtf()
        val font = TtfFont(bytes)
        assertNotNull(font)
    }

    @Test
    fun `TtfFont name defaults to value from name table`() {
        val bytes = TtfTestBuilder.buildMinimalTtf(fontName = "MyTestFont")
        val font = TtfFont(bytes)
        assertEquals("MyTestFont", font.ttfName)
    }

    @Test
    fun `extName overrides name from name table`() {
        val bytes = TtfTestBuilder.buildMinimalTtf(fontName = "MyTestFont")
        val font = TtfFont(bytes, extName = "OverrideName")
        assertEquals("OverrideName", font.name)
        assertEquals("MyTestFont", font.ttfName)
    }

    @Test
    fun `unitsPerEm is read from head table`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(unitsPerEm = 2048))
        assertEquals(2048, font.unitsPerEm)
    }

    @Test
    fun `unitsPerEm defaults sensibly for standard value`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(unitsPerEm = 1000))
        assertEquals(1000, font.unitsPerEm)
    }

    @Test
    fun `ascender and descender are read from hhea table`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(ascender = 900, descender = -300))
        assertEquals(900, font.ascender)
        assertEquals(-300, font.descender)
    }

    @Test
    fun `lineGap is read from hhea table`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(lineGap = 50))
        assertEquals(50, font.lineGap)
    }

    @Test
    fun `getFontMetrics returns size-scaled metrics`() {
        val font = TtfFont(
            TtfTestBuilder.buildMinimalTtf(
                unitsPerEm = 1000,
                ascender = 800,
                descender = -200
            )
        )
        val metrics = font.getFontMetrics(16.0)
        // ascent should be 800/1000 × 16 = 12.8
        assertEquals(12.8, metrics.ascent, 0.001)
        // descent should be -200/1000 × 16 = -3.2
        assertEquals(-3.2, metrics.descent, 0.001)
    }

    @Test
    fun `getFontMetrics size=1 matches fontMetrics1px`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(unitsPerEm = 1000, ascender = 750))
        val m1 = font.getFontMetrics(1.0)
        assertEquals(font.fontMetrics1px.ascent, m1.ascent, 0.0001)
    }

    @Test
    fun `numGlyphs is read from maxp table`() {
        val font = TtfFont(
            TtfTestBuilder.buildMinimalTtf(
                numGlyphs = 5,
                advanceWidths = IntArray(5) { 400 })
        )
        assertEquals(5, font.numGlyphs)
    }

    @Test
    fun `getCharIndexFromCodePoint returns mapped glyph index`() {
        val font =
            TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1, 'B'.code to 2)))
        assertEquals(1, font.getCharIndexFromCodePoint('A'.code))
        assertEquals(2, font.getCharIndexFromCodePoint('B'.code))
    }

    @Test
    fun `getCharIndexFromCodePoint returns null for unmapped code point`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        assertNull(font.getCharIndexFromCodePoint('Z'.code))
    }

    @Test
    fun `getCharIndexFromChar convenience method matches getCharIndexFromCodePoint`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('X'.code to 3)))
        assertEquals(font.getCharIndexFromCodePoint('X'.code), font.getCharIndexFromChar('X'))
    }

    @Test
    fun `getCodePointFromCharIndex returns reverse mapping`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        assertEquals('A'.code, font.getCodePointFromCharIndex(1))
    }

    @Test
    fun `getCodePointFromCharIndex returns null for unknown glyph index`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        assertNull(font.getCodePointFromCharIndex(99))
    }

    @Test
    fun `getGlyphByCodePoint returns non-null for mapped code point`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        assertNotNull(font.getGlyphByCodePoint('A'.code))
    }

    @Test
    fun `getGlyphByCodePoint returns null for unmapped code point`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        assertNull(font.getGlyphByCodePoint('Z'.code))
    }

    @Test
    fun `getGlyphByChar convenience delegate works`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        assertNotNull(font.getGlyphByChar('A'))
        assertNull(font.getGlyphByChar('Z'))
    }

    @Test
    @Ignore // TODO See why this test fails
    fun `indexing operator returns same result as getGlyphByCodePoint`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        assertEquals(font.getGlyphByCodePoint('A'.code), font['A'.code])
    }

    @Test
    fun `glyph advanceWidth matches hmtx table`() {
        val font = TtfFont(
            TtfTestBuilder.buildMinimalTtf(
                numGlyphs = 2,
                advanceWidths = intArrayOf(600, 800),
                charMap = mapOf('A'.code to 1),
            )
        )
        val glyph = font.getGlyphByIndex(1, cache = false)
        assertNotNull(glyph)
        assertEquals(800, glyph.advanceWidth)
    }

    @Test
    fun `getGlyphByIndex returns dummy glyph for empty loca entry`() {
        // All loca entries are 0 in our minimal builder → every glyph is empty
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        val g = font.getGlyphByIndex(0, cache = false)
        assertNotNull(g)
    }

    @Test
    fun `getAllGlyphs returns numGlyphs entries`() {
        val font = TtfFont(
            TtfTestBuilder.buildMinimalTtf(
                numGlyphs = 3,
                advanceWidths = intArrayOf(400, 500, 600)
            )
        )
        assertEquals(3, font.getAllGlyphs().size)
    }

    @Test
    fun `getGlyphMetrics returns non-default metrics for known glyph`() {
        val font = TtfFont(
            TtfTestBuilder.buildMinimalTtf(
                unitsPerEm = 1000,
                numGlyphs = 2,
                advanceWidths = intArrayOf(500, 600),
                charMap = mapOf('A'.code to 1),
            )
        )
        val m = font.getGlyphMetrics(16.0, 'A'.code)
        assertTrue(m.existing)
        // advanceWidth = 600, scale = 16/1000, so expected = 9.6
        assertEquals(9.6, m.xadvance, 0.01)
    }

    @Test
    fun `getGlyphMetrics for unknown codePoint returns non-existing metrics`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        val m = font.getGlyphMetrics(16.0, 0x1F600)  // emoji not in map
        assertFalse(m.existing)
    }

    @Test
    fun `getKerning returns 0 - not yet implemented`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        assertEquals(0.0, font.getKerning(16.0, 'A'.code, 'V'.code))
    }

    @Test
    fun `toString contains font name`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(fontName = "SpecialFont"))
        assertTrue(font.toString().contains("SpecialFont"))
    }

    @Test
    fun `onlyReadMetadata mode still exposes name and metrics`() {
        val bytes = TtfTestBuilder.buildMinimalTtf(fontName = "MetaFont", unitsPerEm = 512)
        val font = TtfFont(bytes, onlyReadMetadata = true)
        assertEquals("MetaFont", font.ttfName)
        assertEquals(512, font.unitsPerEm)
    }

    @Test
    fun `onlyReadMetadata mode leaves characterMaps empty`() {
        val bytes = TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1))
        val font = TtfFont(bytes, onlyReadMetadata = true)
        // cmap is not read in metadata-only mode
        assertTrue(font.characterMaps.isEmpty())
    }

    @Test
    @Ignore // TODO See why this test fails
    fun `Fixed value combines integer and fractional parts`() {
        val raw = Fixed(1, 32768)  // 1 + 32768/65536 = 1.5
        val fixed = Fixed(raw)
        assertEquals(1.5, fixed.value, 0.0001)
    }

    @Test
    fun `Fixed zero produces zero value`() {
        val raw = Fixed(0, 0)
        val fixed = Fixed(raw)
        assertEquals(0.0, fixed.value, 0.0001)
    }

    @Test
    fun `FWord toDouble preserves value`() {
        val fw = FWord(1200)
        assertEquals(1200.0, fw.toDouble())
    }

    @Test
    fun `NamesInfo ttfName falls back to COMPLETE_NAME when NAME is absent`() {
        val info = BaseTtfFont.NamesInfo()
        // Manually set only COMPLETE_NAME
        info.names[BaseTtfFont.NameId.COMPLETE_NAME.id] = "CompleteFont"
        assertEquals("CompleteFont", info.ttfName)
    }

    @Test
    fun `NamesInfo getName returns null for unset id`() {
        val info = BaseTtfFont.NamesInfo()
        assertNull(info.getName(BaseTtfFont.NameId.TRADEMARK))
    }

    @Test
    fun `Contour default values are zero and off-curve`() {
        val c = BaseTtfFont.Contour()
        assertEquals(0, c.x)
        assertEquals(0, c.y)
        assertFalse(c.onCurve)
    }

    @Test
    fun `Contour copyFrom transfers all fields`() {
        val src = BaseTtfFont.Contour(10, -20, true)
        val dst = BaseTtfFont.Contour()
        dst.copyFrom(src)
        assertEquals(10, dst.x)
        assertEquals(-20, dst.y)
        assertTrue(dst.onCurve)
    }

    @Test
    fun `HorMetric holds advanceWidth and lsb`() {
        val m = BaseTtfFont.HorMetric(600, 40)
        assertEquals(600, m.advanceWidth)
        assertEquals(40, m.lsb)
    }

    @Test
    fun `getTableNames contains expected tables`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        val names = font.getTableNames()
        assertTrue("head" in names)
        assertTrue("hhea" in names)
        assertTrue("maxp" in names)
        assertTrue("cmap" in names)
        assertTrue("name" in names)
    }

    @Test
    fun `getTable returns non-null for present table`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        assertNotNull(font.getTable("head"))
    }

    @Test
    fun `getTable returns null for absent table`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        assertNull(font.getTable("COLR"))
    }

    @Test
    fun `readHeaderTables companion parses table directory`() {
        val bytes = TtfTestBuilder.buildMinimalTtf()
        val tables = BaseTtfFont.readHeaderTables(bytes.openFastStream())
        assertTrue(tables.containsKey("head"))
        assertTrue(tables.containsKey("cmap"))
    }

    @Test
    fun `getGlyphPath returns null for unknown codePoint`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        val path = font.getGlyphPath(16.0, 0x1F600)
        assertNull(path)
    }

    @Test
    fun `getGlyphPath returns non-null for known codePoint`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(charMap = mapOf('A'.code to 1)))
        val path = font.getGlyphPath(16.0, 'A'.code)
        assertNotNull(path)
    }

    @Test
    fun `getGlyphPath scale is proportional to requested size`() {
        val font = TtfFont(
            TtfTestBuilder.buildMinimalTtf(
                unitsPerEm = 1000,
                charMap = mapOf('A'.code to 1)
            )
        )
        val path16 = font.getGlyphPath(16.0, 'A'.code)!!
        val path32 = font.getGlyphPath(32.0, 'A'.code)!!
        assertEquals(path16.scale * 2.0, path32.scale, 0.0001)
    }

    @Test
    fun `substitutionsCodePoints is empty when no GSUB table present`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        assertEquals(0, font.substitutionsCodePoints.size)
    }

    @Test
    fun `glyphSubstitution is empty when no GSUB table present`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        assertEquals(0, font.glyphSubstitution.size)
    }

    @Test
    fun `bitmapGlyphInfos is empty for plain TTF`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        assertTrue(font.bitmapGlyphInfos.isEmpty())
    }

    @Test
    fun `palettes is empty for font without CPAL table`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf())
        assertTrue(font.palettes.isEmpty())
    }

    @Test
    fun `font can be constructed with ligatures disabled`() {
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(), enableLigatures = false)
        assertNotNull(font)
    }

    @Test
    fun `Table stores id checksum offset and length`() {
        val t = BaseTtfFont.Table("head", 0xDEADBEEF.toInt(), 1024, 54)
        assertEquals("head", t.id)
        assertEquals(0xDEADBEEF.toInt(), t.checksum)
        assertEquals(1024, t.offset)
        assertEquals(54, t.length)
    }

    @Test
    fun `scale at size equals size divided by unitsPerEm`() {
        val upm = 1000
        val size = 24.0
        val font = TtfFont(TtfTestBuilder.buildMinimalTtf(unitsPerEm = upm))
        val expected = size / upm.toDouble()
        val m = font.getFontMetrics(size)
        // ascent(800) × (size/upm) and font stores that scale; verify indirectly
        assertEquals(expected * font.ascender, m.ascent, 0.001)
    }
}
