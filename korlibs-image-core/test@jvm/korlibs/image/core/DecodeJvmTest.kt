package korlibs.image.core

import junit.framework.TestCase.*
import kotlinx.coroutines.test.*
import kotlin.io.encoding.*
import kotlin.test.Test

@OptIn(ExperimentalEncodingApi::class)
class DecodeJvmTest {
    val pngData = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=")

    @Test
    fun testInfo() = runTest {
        assertEquals(CoreImageInfo(1, 1, format = CoreImageFormat("png")), CoreImage.info(pngData))
    }

    @Test
    fun testAwt() = runTest {
        assertEquals(CoreImageInfo(1, 1), AwtCoreImageFormatProvider.decode(pngData).info())
    }

    @Test
    fun testMac() = runTest {
        if (!CoreImageIsMAC) return@runTest
        assertEquals(CoreImageInfo(1, 1), CoreGraphicsCoreImageFormatProvider.decode(pngData).info())
    }
}