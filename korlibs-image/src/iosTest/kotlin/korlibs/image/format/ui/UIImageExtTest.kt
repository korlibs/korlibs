package korlibs.image.format.ui

import korlibs.encoding.hex
import korlibs.image.bitmap.Bitmap32
import korlibs.image.color.Colors
import korlibs.image.color.RgbaArray
import korlibs.image.format.PNG
import korlibs.platform.toByteArray
import kotlin.test.Test
import kotlin.test.assertTrue
import platform.UIKit.UIImagePNGRepresentation

class UIImageExtTest {
    @Test
    fun testUIImageConversions() {
        val bmp = Bitmap32(2, 2, RgbaArray(Colors.RED, Colors.GREEN, Colors.BLUE, Colors.WHITE))
        val bmpC = bmp.clone()
        val uiImage = bmp.toUIImage()
        val bmp3 = PNG.read(UIImagePNGRepresentation(uiImage)!!.toByteArray()).toBMP32()
        val bmp2 = uiImage.toBitmap32()
        println(bmp.ints.map { it.hex })
        println(bmp2.ints.map { it.hex })
        println(bmp3.ints.map { it.hex })
        assertTrue("Original bitmap shouldn't have been modified") { bmp.contentEquals(bmpC) }
        assertTrue("Conversion back and forth should work") { bmp.contentEquals(bmp2) }
        assertTrue("PNG generation should match") { bmp.contentEquals(bmp3) }
    }
}
