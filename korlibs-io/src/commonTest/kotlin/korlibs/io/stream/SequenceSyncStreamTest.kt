package korlibs.io.stream

import korlibs.io.lang.UTF8
import korlibs.io.lang.toByteArray
import korlibs.io.lang.toString
import kotlin.test.Test
import kotlin.test.assertEquals

class SequenceSyncStreamTest {
    @Test
    fun test() {
        val stream = sequenceSyncStream {
            repeat(10) {
                yield("á".toByteArray(UTF8))
            }
        }
        assertEquals("áááááááááá", stream.readBytes(100).toString(UTF8))
        assertEquals("", stream.readBytes(100).toString(UTF8))
    }
}
