package korlibs.image.font

import korlibs.image.bitmap.Bitmap32
import korlibs.io.async.suspendTest
import kotlin.test.Test

class NativeFontTest {
    @Test
    fun name() = suspendTest{
        val bmpFont = BitmapFont(SystemFont("Arial"), 64.0, CharacterSet("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
        // This check possible issues on native
        bmpFont.registerTemporarily {
            val bmp = Bitmap32(200, 200, premultiplied = true)
        }
        //bmp.drawText(bmpFont, "HELLO")
        //awtShowImage(bmp); Thread.sleep(10000)
    }
}
