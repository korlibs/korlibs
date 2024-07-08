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
}