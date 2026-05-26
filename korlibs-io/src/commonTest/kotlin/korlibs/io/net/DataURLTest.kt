package korlibs.io.net

import korlibs.encoding.hex
import korlibs.io.async.suspendTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DataURLTest {
    @Test
    fun test() = suspendTest {
        DataURL.temporal(byteArrayOf(1, 2, 3), "text/plain") {
            assertEquals("010203", it.readBytes().hex)
        }
    }
}