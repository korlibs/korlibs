package korlibs.image.bitmap.effect

import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.context2d
import korlibs.image.color.Colors
import korlibs.image.font.DefaultTtfFont
import korlibs.math.geom.Point
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class BorderTest {
    @Test
    fun test() = runTest {
        val bmp = Bitmap32(100, 100, premultiplied = false).context2d {
            drawText("Hello", pos = Point(20, 20), font = DefaultTtfFont, paint = Colors.RED)
        }
        val bmpBorder = bmp.border(4, Colors.GREEN)
        //bmpBorder.showImageAndWait()
    }
}
