package korlibs.image.vector

import korlibs.image.bitmap.Bitmap
import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.NativeImageOrBitmap32
import korlibs.image.bitmap.context2d
import korlibs.image.color.Colors
import korlibs.image.format.PNG
import korlibs.image.format.writeTo
import korlibs.io.file.fullPathNormalized
import korlibs.io.file.std.tempVfs
import korlibs.math.geom.Point
import korlibs.math.geom.degrees
import korlibs.platform.Platform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class NativeRenderTest {
    @Test fun testNativeFill() = doTest(native = true, drawBitmap = false)
    @Test fun testNativeBitmap() = doTest(native = true, drawBitmap = true)
    @Test fun testNonNativeFill() = doTest(native = false, drawBitmap = false)
    @Test fun testNonNativeBitmap() = doTest(native = false, drawBitmap = true)

    fun doTest(native: Boolean, drawBitmap: Boolean) = runTest {
        val bmp = createBitmap(native, drawBitmap)
        checks(bmp, "${Platform.rawPlatformName.lowercase()}.native_$native.bmp_$drawBitmap")
    }

    fun createBitmap(native: Boolean, drawBitmap: Boolean): Bitmap32 {
        val bmp: Bitmap = NativeImageOrBitmap32(100, 100, native = native)
        return bmp.context2d {
            rotate(30.degrees)
            translate(20, 20)
            fillStyle = createColor(Colors.RED)
            if (drawBitmap) {
                drawImage(Bitmap32(20, 20, Colors.RED), Point(10, 10))
            } else {
                fillRect(10, 10, 20, 20)
            }
        }.toBMP32()
    }

    suspend fun checks(image: Bitmap32, name: String) {
        try {
            assertEquals(Colors.RED, image[14, 54])
            assertEquals(0, image[4, 45].a)
            assertEquals(0, image[19, 42].a)
        } catch (e: Throwable) {
            val file = tempVfs["output.$name.png"]
            try {
                image.writeTo(file, PNG)
                println("Failed image saved to: " + file.fullPathNormalized)
            } catch (e: Throwable) {
                println("Couldn't save failing image")
            }
            throw e
        }
    }
}
