package korlibs.time

import kotlin.test.*

class DateTimeSpanFormatTest {
    @Test
    fun test() {
        DateTimeSpanFormat("MM-dd-HH-mm-ss").also { format ->
            val span = 3.months + 13.days + 99.hours + 73.minutes + 15.seconds
            assertEquals(span, format.parse("03-13-99-73-15"))
            assertEquals(span, format.parse("03-17-04-13-15"))
            assertEquals("03-17-04-13-15", format.format(span))
        }
    }
}