package korlibs.image.core

import kotlinx.coroutines.test.*
import kotlin.io.encoding.*
import kotlin.test.*

@OptIn(ExperimentalEncodingApi::class)
class CoreImageTest {
    val pngData = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=")

    @Test
    fun testInfo() = runTest {
        assertEquals(CoreImageInfo(1, 1), CoreImage.info(pngData).copy(format = null))
    }

    @Test
    fun testDecode() = runTest {
        assertEquals(CoreImageInfo(1, 1), CoreImage.decodeBytes(pngData).info())
    }

    @Test
    fun testEncode() = runTest {
        assertEquals(
            CoreImageInfo(2, 1),
            CoreImage.decodeBytes(CoreImage32(2, 1, intArrayOf(-1, -1)).encodeBytes(CoreImageFormat.PNG)).info()
        )
    }

    @Test
    fun testRGBA() {
        val c = CoreImageRGBA(red = 255, green = 128, blue = 64, alpha = 255)
        assertEquals(255, c.red)
        assertEquals(128, c.green)
        assertEquals(64, c.blue)
        assertEquals(255, c.alpha)
    }

    @Test
    fun testEncodeDecode() = runTest {
        val c1 = CoreImageRGBA(red = 255, green = 128, blue = 64, alpha = 255)
        val c2 = CoreImageRGBA(red = 64, green = 255, blue = 90, alpha = 255)
        val bytes = CoreImage32(2, 1, intArrayOf(c1.value, c2.value)).encodeBytes(CoreImageFormat.PNG)
        val image = CoreImage.decodeBytes(bytes).to32()
        assertEquals(c1, CoreImageRGBA(image.data[0]))
        assertEquals(c2, CoreImageRGBA(image.data[1]))
    }
}