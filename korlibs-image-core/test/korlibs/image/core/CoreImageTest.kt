package korlibs.image.core

import kotlinx.coroutines.test.*
import kotlin.io.encoding.*
import kotlin.test.*

@OptIn(ExperimentalEncodingApi::class)
class CoreImageTest {
    val pngData = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=")

    // 2x1 image: #FF8000FF, #8040FFFF
    val png2x1Data = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAIAAAABCAIAAAB7QOjdAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAD0lEQVQImWP+38DAyPAfAAmYAoMPmI8gAAAAAElFTkSuQmCC")

    val png2Data = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR4XmP4X2LoAgAGAwHpGKW+VwAAAABJRU5ErkJggg==")

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
        val c = CoreImage32Color(red = 255, green = 128, blue = 64, alpha = 255)
        assertEquals(255, c.red)
        assertEquals(128, c.green)
        assertEquals(64, c.blue)
        assertEquals(255, c.alpha)
    }

    @Test
    fun testEncodeDecode() = runTest {
        val c1 = CoreImage32Color(red = 255, green = 128, blue = 64, alpha = 255)
        val c2 = CoreImage32Color(red = 64, green = 255, blue = 90, alpha = 255)
        val bytes = CoreImage32(2, 1, intArrayOf(c1.value, c2.value)).encodeBytes(CoreImageFormat.PNG)
        val image = CoreImage.decodeBytes(bytes).to32()
        assertEquals(c1, CoreImage32Color(image.data[0]))
        assertEquals(c2, CoreImage32Color(image.data[1]))
    }

    @Test
    fun testDecodeCheckColors() = runTest {
        val colors = CoreImage.decodeBytes(png2x1Data).to32()
        assertEquals("2x1", "${colors.width}x${colors.height}")
        val c1 = CoreImage32Color(colors.data[0])
        val c2 = CoreImage32Color(colors.data[1])
        assertEquals("#FF8000FF,#8040FFFF", "${c1.toHexString()},${c2.toHexString()}")
    }

    @Test
    fun testEncodeDecodeCheckColors() = runTest {
        val colors = CoreImage.decodeBytes(CoreImage.decodeBytes(png2x1Data).encodeBytes(CoreImageFormat.PNG)).to32()
        assertEquals("2x1", "${colors.width}x${colors.height}")
        val c1 = CoreImage32Color(colors.data[0])
        val c2 = CoreImage32Color(colors.data[1])
        assertEquals("#FF8000FF,#8040FFFF", "${c1.toHexString()},${c2.toHexString()}")
    }

    @Test
    fun testPremultipliedEncodedecode() = runTest {
        val image = CoreImage32(1, 1, intArrayOf(CoreImage32Color(0xFF, 0x77, 0x33, 0x44).value), premultiplied = false).premultiplied()
        val image2 = CoreImage.decodeBytes(CoreImage.encode(image, CoreImageFormat.PNG, 1.0)).to32()
        assertEquals(true, image2.premultiplied)
        assertEquals("#441F0D44", CoreImage32Color(image2.data[0]).toHexString())
    }

    @Test
    fun testPremultipliedDecode() = runTest {
        val img = CoreImage.decodeBytes(png2Data).to32()
        assertEquals(true, img.premultiplied)
        assertEquals("#441F0D44", CoreImage32Color(img.data[0]).toHexString())
    }

    fun CoreImage32Color.toHexString(): String = buildString(9) {
        val HEX = "0123456789ABCDEF"
        append("#")
        append(HEX[(red ushr 4) and 0xF])
        append(HEX[(red ushr 0) and 0xF])
        append(HEX[(green ushr 4) and 0xF])
        append(HEX[(green ushr 0) and 0xF])
        append(HEX[(blue ushr 4) and 0xF])
        append(HEX[(blue ushr 0) and 0xF])
        append(HEX[(alpha ushr 4) and 0xF])
        append(HEX[(alpha ushr 0) and 0xF])
    }
}
