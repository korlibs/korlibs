package korlibs.image.format

import korlibs.image.bitmap.Bitmap32
import korlibs.image.color.RGBA
import korlibs.math.clamp
import kotlin.math.abs
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Test class that cover JPEGEncoder and JPEGDecoder.
 *
 * The tests are combined to also cover round-trips.
 */
class JPEGEncoderDecoderTest {

    private fun createTestBitmap(width: Int = 16, height: Int = 16): Bitmap32 {
        return Bitmap32(width, height).apply {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    this[x, y] = RGBA(
                        r = (x * 16).clamp(0, 255),
                        g = (y * 16).clamp(0, 255),
                        b = 128,
                        a = 255
                    )
                }
            }
        }
    }

    @Test
    fun `encode produces non-empty bytes`() {
        val bitmap = createTestBitmap()
        val bytes = JPEGEncoder.encode(bitmap, quality = 80)
        assertTrue(bytes.isNotEmpty())
    }

    @Test
    fun `encode output starts with JPEG SOI marker`() {
        val bytes = JPEGEncoder.encode(createTestBitmap(), quality = 80)
        assertEquals(0xFF.toByte(), bytes[0])
        assertEquals(0xD8.toByte(), bytes[1])
    }

    @Test
    fun `encode output ends with JPEG EOI marker`() {
        val bytes = JPEGEncoder.encode(createTestBitmap(), quality = 80)
        assertEquals(0xFF.toByte(), bytes[bytes.size - 2])
        assertEquals(0xD9.toByte(), bytes[bytes.size - 1])
    }

    @Test
    fun `encode higher quality produces larger output than lower quality`() {
        val bitmap = createTestBitmap(64, 64)
        val lowQ  = JPEGEncoder.encode(bitmap, quality = 0)
        val highQ = JPEGEncoder.encode(bitmap, quality = 100)
        assertTrue(highQ.size > lowQ.size, "quality=100 should be larger than quality=0")
    }

    @Test
    fun `encode handles 1x1 bitmap`() {
        val tiny = Bitmap32(1, 1).apply { this[0, 0] = RGBA(255, 0, 0, 255) }
        val bytes = JPEGEncoder.encode(tiny, quality = 80)
        assertTrue(bytes.isNotEmpty())
    }

    @Test
    fun `encode handles large bitmap without throwing`() {
        JPEGEncoder.encode(createTestBitmap(512, 512), quality = 80)
    }

    @Test
    fun `encode quality 0 still produces a valid JPEG structure`() {
        val bytes = JPEGEncoder.encode(createTestBitmap(), quality = 0)
        assertEquals(0xFF.toByte(), bytes[0])
        assertEquals(0xD8.toByte(), bytes[1])
        assertEquals(0xFF.toByte(), bytes[bytes.size - 2])
        assertEquals(0xD9.toByte(), bytes[bytes.size - 1])
    }

    @Test
    fun `decodeInfo returns correct dimensions`() {
        val bitmap = createTestBitmap(32, 16)
        val bytes = JPEGEncoder.encode(bitmap, quality = 80)
        val info = JPEGDecoder.decodeInfo(bytes)
        assertEquals(32, info.width)
        assertEquals(16, info.height)
    }

    @Test
    @Ignore // Fails on wasmJS with uncaught runtime error
    fun `decodeInfo throws on empty input`() {
        assertFailsWith<Throwable> {
            JPEGDecoder.decodeInfo(ByteArray(0))
        }
    }

    @Test
    fun `decodeInfo throws on garbage input`() {
        assertFailsWith<Throwable> {
            JPEGDecoder.decodeInfo(ByteArray(64) { it.toByte() })
        }
    }

    @Test
    fun `decode returns bitmap with correct dimensions`() {
        val original = createTestBitmap(32, 16)
        val bytes = JPEGEncoder.encode(original, quality = 80)
        val decoded = JPEGDecoder.decode(bytes)
        assertEquals(32, decoded.width)
        assertEquals(16, decoded.height)
    }

    @Test
    @Ignore // Fails on web wasmJS with uncaught runtime error
    fun `decode throws on empty input`() {
        assertFailsWith<Throwable> {
            JPEGDecoder.decode(ByteArray(0))
        }
    }

    @Test
    fun `decode throws on garbage input`() {
        assertFailsWith<Throwable> {
            JPEGDecoder.decode(ByteArray(64) { it.toByte() })
        }
    }

    @Test
    fun `decode output is a non-null bitmap`() {
        val bytes = JPEGEncoder.encode(createTestBitmap(), quality = 80)
        val bitmap = JPEGDecoder.decode(bytes)
        assertNotNull(bitmap)
    }

    @Test
    fun `encode then decode preserves dimensions`() {
        val original = createTestBitmap(32, 32)
        val bytes = JPEGEncoder.encode(original, quality = 90)
        val decoded = JPEGDecoder.decode(bytes)
        assertEquals(original.width, decoded.width)
        assertEquals(original.height, decoded.height)
    }

    @Test
    fun `encode then decode at high quality preserves pixels approximately`() {
        // JPEG is lossy, so we allow a small per-channel tolerance
        val original = createTestBitmap(16, 16)
        val bytes = JPEGEncoder.encode(original, quality = 100)
        val decoded = JPEGDecoder.decode(bytes).toBMP32()

        var maxDelta = 0
        for (y in 0 until original.height) {
            for (x in 0 until original.width) {
                val src = original[x, y]
                val dst = decoded[x, y]
                maxDelta = maxOf(
                    maxDelta,
                    abs(src.r - dst.r),
                    abs(src.g - dst.g),
                    abs(src.b - dst.b)
                )
            }
        }
        assertTrue(maxDelta <= 10, "Max per-channel delta was $maxDelta, expected <= 10 at quality 100")
    }

    @Test
    fun `encode then decodeInfo is consistent with decode dimensions`() {
        val bytes = JPEGEncoder.encode(createTestBitmap(24, 48), quality = 80)
        val info   = JPEGDecoder.decodeInfo(bytes)
        val bitmap = JPEGDecoder.decode(bytes)
        assertEquals(info.width,  bitmap.width)
        assertEquals(info.height, bitmap.height)
    }
}
