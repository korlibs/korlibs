package korlibs.io.lang

import kotlin.test.*

class PropertiesTest {
    @Test
    fun testParse() {
        val properties = Properties.parseString("""
            hello=world
            hi=there
            #foo=bar
        """.trimIndent())
        assertEquals("world", properties["hello"])
        assertEquals("there", properties["hi"])
        assertEquals(null, properties["foo"])
    }

    @Test
    fun testBuild() {
        val properties = Properties(mapOf("hello" to "world", "hi" to "there"))
        assertEquals("world", properties["hello"])
        assertEquals("there", properties["hi"])
        assertEquals(null, properties["foo"])
    }

    @Test
    fun testToString() {
        assertEquals(
            """
                hello=world
                hi=there
                
            """.trimIndent(),
            Properties(mapOf("hello" to "world", "hi" to "there")).toString()
        )
    }
}
