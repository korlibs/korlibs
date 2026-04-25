package korlibs.time

import kotlin.test.*
import kotlin.time.*

class TimeSpanTest {
    val nanoSeconds: Double = 1/1000000000.0
    val microSeconds: Double = 1/1000000.0
    val milliSeconds: Double = 1/1000.0
    val seconds: Double = 1.0
    val minutes: Double = 60.0
    val hours: Double = 3600.0
    val days: Double = 3600.0 * 24.0

    @Test
    fun testIntNanoSeconds() {
        assertEquals(1.nanoseconds, nanoSeconds.toDuration(DurationUnit.SECONDS))
    }
    @Test
    fun testIntMicroSeconds() {
        assertEquals(1.microseconds, microSeconds.toDuration(DurationUnit.SECONDS))
    }
    @Test
    fun testIntMilliSeconds() {
        assertEquals(1.milliseconds, milliSeconds.toDuration(DurationUnit.SECONDS))
    }
    @Test
    fun testIntSeconds() {
        assertEquals(1.seconds, seconds.toDuration(DurationUnit.SECONDS))
    }
    @Test
    fun testIntMinutes() {
        assertEquals(1.minutes, minutes.toDuration(DurationUnit.SECONDS))
    }
    @Test
    fun testIntHours() {
        assertEquals(1.hours, hours.toDuration(DurationUnit.SECONDS))
    }
    @Test
    fun testIntDays() {
        assertEquals(1.days, days.toDuration(DurationUnit.SECONDS))
    }
}
