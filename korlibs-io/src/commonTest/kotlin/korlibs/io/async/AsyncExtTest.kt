package korlibs.io.async

import korlibs.time.seconds
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.delay

class AsyncExtTest {
    @Test
    fun test() = suspendTest {
        delay(0.05.seconds)
        assertEquals(1, 1)
    }
}
