package korlibs.time

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration

class FastDurationTest {
    @Test
    fun testFastToSlow() {
        assertEquals(Duration.NIL, FastDuration.NIL.slow)
        assertEquals(Duration.NIL, FastDuration.NIL.toDuration())
    }
    @Test
    fun testSlowToFast() {
        assertEquals(FastDuration.NIL, Duration.NIL.fast)
        assertEquals(FastDuration.NIL, Duration.NIL.toFastDuration())
    }
}