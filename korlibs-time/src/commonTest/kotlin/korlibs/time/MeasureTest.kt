package korlibs.time

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.measureTimedValue

class MeasureTest {
    @Test
    fun test() {
        val result = measureTimedValue {
            val start = DateTime.now()
            do {
                val current = DateTime.now()
            } while (current - start < 40.milliseconds)
            "hello"
        }
        assertEquals("hello", result.value)
        assertTrue("Near 40.milliseconds != ${result.duration}") { result.duration >= 20.milliseconds && result.duration <= 1.seconds }
    }
}
