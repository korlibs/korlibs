package korlibs.image.font

import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LazyBitmapFontTest {

    private val atlasSizes =
        listOf(16.0, 24.0, 32.0, 40.0, 48.0, 64.0, 72.0, 96.0, 112.0, 128.0, 144.0, 192.0)

    @Test
    fun testEnsureSpaceIsEmpty() {
        val glyph = DefaultTtfFontAsBitmap.getOrNull(' '.code)
        val texture = glyph!!.texture
        assertEquals(0, texture.width)
        assertEquals(0, texture.height)
    }

    /**
     * A renderer draws a glyph's bottom edge at `yoffset + texHeight`.
     *
     * The glyphs tested here report `top == 0` - their outline already sits on the baseline. For those,
     * that sum is the gap between the baseline and their own ink (i.e. it should be zero). This should
     * be the same value for all of them, at any atlas size.
     *
     * Which glyphs qualify is read from the metrics rather than hardcoded, so this makes no assumption
     * about the default font's geometry.
     *
     * Several [atlasSizes] are scanned because the failure is size-dependent. A glyph's height can land
     * above or below by a half pixel, so any single size can agree by luck. Sizes 32, 72, 112 and 144
     * all happen to be lucky for this font and picking one of those would have made this test worthless.
     */
    @Test
    fun `Glyphs that share a baseline with each other should agree on that baseline`() {
        val distanceFields = listOf(null, "sdf")
        atlasSizes.forEach { atlasSize ->
            distanceFields.forEach { distanceField ->
                val offsets = baselineOffsets(atlasSize, distanceField)

                assertTrue(offsets.isNotEmpty(), "no flat-bottomed glyphs available to test")
                assertEquals(
                    expected = 1,
                    actual = offsets.values.toSet().size,
                    message = "glyphs sharing one baseline were placed on different rows " +
                        "(atlasSize=$atlasSize, distanceField=$distanceField): $offsets",
                )
            }
        }
    }

    /**
     * Asserts that no borders/padding/etc shift fonts from their baseline.
     */
    @Test
    fun `Glyphs should sit on their baseline regardless of atlasSize`() {
        val distanceFields = listOf(null, "sdf")
        atlasSizes.forEach { atlasSize ->
            distanceFields.forEach { distanceField ->
                val misplaced = baselineOffsets(atlasSize, distanceField).filterValues { it != 0 }

                assertEquals(
                    expected = emptyMap(),
                    actual = misplaced,
                    message = "glyphs were placed off their own baseline " +
                        "(atlasSize=$atlasSize, distanceField=$distanceField)",
                )
            }
        }
    }

    /** Distance from the baseline to the drawn bottom edge, per flat-bottomed glyph. */
    private fun baselineOffsets(atlasSize: Double, distanceField: String?): Map<Char, Int> {
        val font = DefaultTtfFont.toLazyBitmapFont(atlasSize, distanceField)
        return ('a'..'z')
            .filter { DefaultTtfFont.getGlyphMetrics(atlasSize, it.code).top.absoluteValue < 1e-6 }
            .associateWith { char ->
                val glyph = font.getOrNull(char.code)!!
                glyph.yoffset + glyph.texHeight
            }
    }
}
