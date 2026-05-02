package korlibs.image.bitmap.effect

import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.image.font.*
import korlibs.io.async.*
import korlibs.math.geom.*
import kotlinx.coroutines.test.*
import kotlin.test.*

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
