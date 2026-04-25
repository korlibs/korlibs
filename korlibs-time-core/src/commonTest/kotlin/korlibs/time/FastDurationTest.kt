package korlibs.time

import kotlin.test.*
import kotlin.time.*

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