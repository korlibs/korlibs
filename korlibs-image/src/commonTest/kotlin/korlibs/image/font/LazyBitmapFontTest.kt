package korlibs.image.font

import kotlin.test.Test
import kotlin.test.assertEquals

class LazyBitmapFontTest {
    @Test
    fun testEnsureSpaceIsEmpty() {
        val glyph = DefaultTtfFontAsBitmap.getOrNull(' '.code)
        val texture = glyph!!.texture
        assertEquals(0, texture.width)
        assertEquals(0, texture.height)
    }
}
