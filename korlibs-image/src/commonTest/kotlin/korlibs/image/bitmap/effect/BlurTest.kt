package korlibs.image.bitmap.effect

import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.context2d
import korlibs.image.color.Colors
import korlibs.math.geom.Point
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class BlurTest {
    @Test
    fun test() = runTest {
        val bmpWithDropShadow = Bitmap32(100, 100, premultiplied = true).context2d {
            fill(Colors.RED) {
                circle(Point(50, 50), 40.0)
            }
        }.dropShadowInplace(0, 0, 5, Colors.BLUE)
        //bmpWithDropShadow.showImageAndWait()
    }
}
