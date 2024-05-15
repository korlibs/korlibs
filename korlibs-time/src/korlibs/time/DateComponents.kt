package korlibs.time

import korlibs.time.internal.*
import kotlin.time.*

data class DateComponents(
    /** How properties must be interpreted */
    val mode: Mode = Mode.DATE,
    /** For dates, years is the full year. For example 1970 or 2024 */
    val years: Int = 0,
    /** For dates, months is [1..12] */
    val months: Int = 0,
    /** For dates, days is the day in the month []1..31] */
    val days: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
    val nanoseconds: Int = 0,
    val offset: Duration? = null,
    val sign: Int = +1,
    val isTime: Boolean = false,
    ///** Used only for dates */
    //val dayOfWeek: DayOfWeek? = null,
) {
    enum class Mode { DATE, TIME, DATE_TIME_SPAN }

    /** When parsing time without dates, hours shouldn't be clamped */
    val clampHours: Boolean get() = mode == Mode.DATE

    val millisecondsDouble: Double get() = nanoseconds / 1_000_000.0
    val milliseconds: Int get() = nanoseconds / 1_000_000

    val timezoneOffset = TimezoneOffset(offset)
    val date get() = Date(years, months, days)
    val dayOfWeek get() = date.dayOfWeek
    val dayOfYear get() = date.dayOfYear
    //val dayOfWeekSure get() = dayOfWeek ?: DayOfWeek.Monday
}

fun Duration.toDateComponents(): DateComponents {
    val sign = if (this.isNegative()) -1 else +1
    val time = ComputedTime(this.absoluteValue)
    return DateComponents(mode = DateComponents.Mode.TIME, hours = time.hoursIncludingDaysAndWeeks, minutes = time.minutes, seconds = time.seconds, nanoseconds = time.nanoseconds, sign = sign)
}

fun DateComponents.toDuration(): Duration {
    return ((years * 365 + months * 30 + days).days + hours.hours + minutes.minutes + seconds.seconds + nanoseconds.nanoseconds) * sign
}

fun DateComponents.toDateTimeTz(doThrow: Boolean = false, doAdjust: Boolean = true): DateTimeTz? {
    val info = this
    if (!doAdjust) {
        if (info.months !in 1..12) if (doThrow) error("Invalid month ${info.months}") else return null
        if (info.days !in 1..32) if (doThrow) error("Invalid day ${info.days}") else return null
        if (info.hours !in 0..24) if (doThrow) error("Invalid hour ${info.hours}") else return null
        if (info.minutes !in 0..59) if (doThrow) error("Invalid minute ${info.minutes}") else return null
        if (info.seconds !in 0..59) if (doThrow) error("Invalid second ${info.seconds}") else return null
        if (info.milliseconds !in 0 .. 999) if (doThrow) error("Invalid millisecond ${info.milliseconds}") else return null
    }
    val dateTime = DateTime.createAdjusted(info.years, info.months, info.days, info.hours umod 24, info.minutes, info.seconds, info.milliseconds)
    return dateTime.toOffsetUnadjusted(info.offset ?: 0.hours)
}

fun DateTimeTz.toDateComponents(): DateComponents = DateComponents(
    mode = DateComponents.Mode.DATE,
    years = this.yearInt,
    months = this.month1,
    days = this.dayOfMonth,
    hours = this.hours,
    minutes = this.minutes,
    seconds = this.seconds,
    nanoseconds = this.milliseconds * 1_000_000,
    offset = this.offset.time,
    //dayOfWeek = this.dayOfWeek,
)

fun DateComponents.toDateTimeSpan(): DateTimeSpan = DateTimeSpan(
    years, months, 0, days, hours, minutes, seconds, (nanoseconds / 1_000_000).toDouble()
)

fun DateTimeSpan.toDateComponents(): DateComponents = DateComponents(
    mode = DateComponents.Mode.DATE_TIME_SPAN,
    years = this.years,
    months = this.months,
    days = this.daysIncludingWeeks,
    hours = this.hours,
    minutes = this.minutes,
    seconds = this.seconds,
    nanoseconds = this.nanoseconds,
)
