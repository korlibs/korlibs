package korlibs.io.net

import korlibs.encoding.*
import korlibs.io.async.*
import kotlin.test.*

class DataURLTest {
    @Test
    fun test() = suspendTest {
        DataURL.temporal(byteArrayOf(1, 2, 3), "text/plain") {
            assertEquals("010203", it.readBytes().hex)
        }
    }
}