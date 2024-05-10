package korlibs.util

import kotlin.test.*

class SimpleIndenterTest {
    @Test
    fun testIndents() {
        assertEquals("\t".repeat(10), SimpleIndenter.INDENTS[10])
        assertEquals("\t".repeat(1), SimpleIndenter.INDENTS[1])
        assertEquals("\t".repeat(0), SimpleIndenter.INDENTS[0])
        assertEquals("\t".repeat(100), SimpleIndenter.INDENTS[100])
    }

    @Test
    fun testIndenter() {
        val indenter = SimpleIndenter()
        indenter.inline("hello")
        indenter.line(" world {")
        indenter.indent {
            indenter.line("hi")
        }
        indenter.line("}")
        assertEquals("hello world {\n\thi\n}", indenter.toString())
    }
}
