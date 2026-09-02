package korlibs.image.text

import korlibs.image.font.readBitmapFont
import korlibs.io.file.std.resourcesVfs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class TextRendererTest {
    @Test
    fun test() = runTest {
        val font = resourcesVfs["font/segment7.fnt"].readBitmapFont()
        val actions = Text2TextRendererActions()
        DefaultStringTextRenderer.invoke(actions, "42:10", 92.0, font)
        assertEquals(
            """
                Entry('glyph-4', 5, 2, 45, 70)
                Entry('glyph-2', 57, 1, 47, 74)
                Entry('glyph-:', 115, 31, 38, 11)
                Entry('glyph-1', 198, 3, 14, 69)
                Entry('glyph-0', 219, 1, 47, 74)
            """.trimIndent(),
            actions.readAll().joinToString("\n")
        )
    }
}
