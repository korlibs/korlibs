package korlibs.image.format

import korlibs.image.bitmap.*
import korlibs.io.file.std.*
import kotlinx.coroutines.test.*
import kotlin.io.encoding.*
import kotlin.test.*

@ExperimentalEncodingApi
class ReadNativeImageNativeFormat {
    val pngData = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=")
    val png2Data = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR4XmP4X2LoAgAGAwHpGKW+VwAAAABJRU5ErkJggg==")

    @Test
    fun testNativeImageShouldBeConsistentPerPlatform() = runTest {
        val bmp0 = nativeImageFormatProvider.create(1, 1, null)
        val bmp1 = resourcesVfs["bubble-chat.9.png"].readNativeImage()
        val bmp2 = resourcesVfs["bubble-chat.9.png"].readNativeImage(ImageDecodingProps(asumePremultiplied = true))
        val bmp3a = PNG.read(pngData).ensureNative()
        val bmp3b = PNG.read(png2Data).ensureNative()
        assertEquals(bmp0::class, bmp1::class, message = "Normal read")
        assertEquals(bmp0::class, bmp2::class, message = "asumePremultiplied read")
        assertEquals(bmp0::class, bmp3a::class, message = "PNG read to native A")
        assertEquals(bmp0::class, bmp3b::class, message = "PNG read to native B")
    }
}