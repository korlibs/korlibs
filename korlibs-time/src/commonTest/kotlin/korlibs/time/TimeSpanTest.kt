package korlibs.time

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TimeSpanTest {
    val nanoSeconds: Double = 1/1000000000.0

    val microSeconds: Double = 1/1000000.0

    val milliSeconds: Double = 1/1000.0

    val seconds: Double = 1.0

    val minutes: Double = 60.0

    val hours: Double = 3600.0

    val days: Double = 3600.0 * 24.0

    @Test
    fun testToTimeString() {
        assertEquals("00:00:01", 1.seconds.toTimeString(components = 3))
        assertEquals("00:01:02", 62.seconds.toTimeString(components = 3))
        assertEquals("01:01:00", 3660.seconds.toTimeString(components = 3))

        assertEquals("00:01", 1.seconds.toTimeString(components = 2))
        assertEquals("01:02", 62.seconds.toTimeString(components = 2))
        assertEquals("61:00", 3660.seconds.toTimeString(components = 2))

        assertEquals("01", 1.seconds.toTimeString(components = 1))
        assertEquals("62", 62.seconds.toTimeString(components = 1))
        assertEquals("3660", 3660.seconds.toTimeString(components = 1))

        assertEquals("01:01:02.500", 3662.5.seconds.toTimeString(components = 3, addMilliseconds = true))
        assertEquals("61:02.500", 3662.5.seconds.toTimeString(components = 2, addMilliseconds = true))
        assertEquals("3662.500", 3662.5.seconds.toTimeString(components = 1, addMilliseconds = true))
    }

    @Test
    fun testOperators() {
        assertEquals((-1).seconds, -(1.seconds))
    }

    @Test
    fun testTimes() {
        assertEquals(1000.nanoseconds, 1.microseconds)
        assertEquals(1000.microseconds, 1.milliseconds)
        assertEquals(1000.milliseconds, 1.seconds)
        assertEquals(60.seconds, 1.minutes)
        assertEquals(60.minutes, 1.hours)
        assertEquals(24.hours, 1.days)
        assertEquals(7.days, 1.weeks)
    }

    @Test
    fun testTimes2() {
        val second = 1.seconds
        val hour = 1.hours
        val week = 1.weeks

        assertEquals(second.nanoseconds, 1_000_000_000.0)
        assertEquals(second.microseconds, 1_000_000.0)
        assertEquals(second.milliseconds, 1_000.0)
        assertEquals(second.seconds, 1.0)

        assertEquals(hour.minutes, 60.0)
        assertEquals(hour.hours, 1.0)

        assertEquals(week.days, 7.0)
        assertEquals(week.weeks, 1.0)
    }

    @Test
    fun testNull() {
        assertTrue { Duration.ZERO != Duration.NIL }
        assertTrue { Duration.NIL == Duration.NIL }
        assertTrue { Duration.NIL.isNil }
    }

    @Test
    fun ratio() {
        assertEquals(0.5, 0.5.seconds / 1.seconds)
        assertEquals(5.0, 10.seconds / 2000.milliseconds)
    }

    @Test
    fun remaining() {
        if (KotlinVersion.CURRENT < KotlinVersion(1, 9, 20)) return // @TODO: This should be only for WASM

        assertEquals(0.5.seconds, 0.5.seconds % 1.seconds)
        assertEquals(0.seconds, 1.seconds % 1.seconds)
        assertEquals(0.5.seconds, 1.5.seconds % 1.seconds)
        assertEquals((-0.5).seconds, ((-1.5).seconds) % 1.seconds)
    }

    @Test
    fun umod() {
        assertEquals(0.5.seconds, 0.5.seconds umod 1.seconds)
        assertEquals(0.seconds, 1.seconds umod 1.seconds)
        assertEquals(0.5.seconds, 1.5.seconds umod 1.seconds)
        assertEquals(0.5.seconds, (-1.5).seconds umod 1.seconds)
    }

    @Test
    fun clamp() {
        assertEquals(1.seconds, min(1.seconds, 2.seconds))
        assertEquals(2.seconds, max(1.seconds, 2.seconds))

        assertEquals(1.seconds, min(2.seconds, 1.seconds))
        assertEquals(2.seconds, max(2.seconds, 1.seconds))

        assertEquals(1.seconds, 1.seconds.clamp(0.seconds, 2.seconds))
        assertEquals(0.seconds, (-1).seconds.clamp(0.seconds, 2.seconds))
        assertEquals(2.seconds, 2.1.seconds.clamp(0.seconds, 2.seconds))
    }

    @Test
    fun testToString() {
        assertEquals("1s", 1.seconds.toString())
        assertEquals("500us", 0.5.milliseconds.toString())
    }

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
