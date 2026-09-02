package korlibs.image.text

import korlibs.image.bitmap.NativeImage
import korlibs.image.bitmap.context2d
import korlibs.image.color.Colors
import korlibs.math.geom.Rectangle
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class RichTextDataRendererText {
    @Test
    fun test() = runTest {
        val nativeImage = NativeImage(512, 512)
        nativeImage.context2d {
            val textBounds = Rectangle(50, 50, 150, 100)
            stroke(Colors.BLUE, lineWidth = 2.0) {
                rect(textBounds)
            }
            drawRichText(
                RichTextData.fromHTML("hello world<br /><br /> this is a long test", style = RichTextData.Style.DEFAULT.copy(textSize = 24.0)),
                bounds = textBounds,
                ellipsis = "...",
                fill = Colors.RED,
                //align = TextAlignment.RIGHT,
                //align = TextAlignment.CENTER,
                align = TextAlignment.MIDDLE_CENTER,
            )
        }
        //nativeImage.showImageAndWait()
    }
}
