package korlibs.image.format

import korlibs.image.bitmap.Bitmap32
import korlibs.image.color.Colors
import korlibs.image.doTest
import korlibs.io.stream.openSync
import korlibs.math.geom.Size
import korlibs.platform.Platform
import kotlin.test.Test
import kotlin.test.assertEquals

class NativeEncodingTest {
    @Test
    fun test() = doTest {
        if (Platform.isJsNodeJs) RegisteredImageFormats.register(PNG)
        val bytes = nativeImageFormatProvider.encodeSuspend(Bitmap32(10, 10, Colors.RED), ImageEncodingProps("image.png"))
        assertEquals(Size(10, 10), PNG.decodeHeader(bytes.openSync())!!.size)

        val image = nativeImageFormatProvider.decodeSuspend(bytes)
        assertEquals(Colors.RED, image.toBMP32()[0, 0])
    }
}
