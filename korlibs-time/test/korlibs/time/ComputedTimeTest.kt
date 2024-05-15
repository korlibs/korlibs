package korlibs.time

import kotlin.test.*

class ComputedTimeTest {
    @Test
    fun test() {
        ComputedTime(48.hours).also { computed ->
            assertEquals(2, computed.days)
        }
        ComputedTime(14.days + 1.days + 16.hours).also { computed ->
            assertEquals(2, computed.weeks)
            assertEquals(1, computed.days)
            assertEquals(16, computed.hours)
            assertEquals(376, computed.hoursIncludingDaysAndWeeks)
        }
    }
}