package korlibs.io.lang

import kotlin.test.*

class StringCaseTest {
    @Test
    fun testToCase() {
        val case = StringCase("hi", "there")
        assertEquals("HiThere", case.pascalCase)
        assertEquals("hiThere", case.camelCase)
        assertEquals("hi there", case.spaceCase)
        assertEquals("hi-there", case.kebabCase)
        assertEquals("hi_there", case.snakeCase)
        assertEquals("HI_THERE", case.screamingSnakeCase)
    }

    @Test
    fun testDetect() {
        assertEquals(listOf("hello", "world"), "hello world".case.words)
        assertEquals(listOf("hello", "world"), "helloWorld".case.words)
        assertEquals(listOf("hello", "world"), "HELLO_WORLD".case.words)
        assertEquals(listOf("hello", "world"), "hello-world".case.words)
        assertEquals(listOf("foo", "1337"), "foo1337".case.words)
        assertEquals(listOf("1a", "2b", "test"), "1a2bTest".case.words)
    }
}
