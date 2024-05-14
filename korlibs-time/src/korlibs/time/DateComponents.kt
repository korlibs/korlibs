package korlibs.time

import korlibs.time.internal.*
import kotlin.time.*

data class DateComponents(
    val years: Int = 0, val months: Int = 0, val days: Int = 0,
    val hours: Int = 0, val minutes: Int = 0, val seconds: Int = 0, val milliseconds: Int = 0,
    val offset: TimezoneOffset? = null, val dayOfWeek: DayOfWeek? = null,
    val dayOfYear: Int = -1, val weekOfYear: Int = -1,
    val clampHours: Boolean = true,
)

fun DateTimeTz.toComponents(clampHours: Boolean = true): DateComponents = DateComponents(yearInt, month1, dayOfMonth, hours, minutes, seconds, milliseconds, offset, dayOfWeek, dayOfYear, weekOfYear0, clampHours = clampHours)
fun DateTimeSpan.toComponents(clampHours: Boolean = true): DateComponents = DateComponents(years, months, daysIncludingWeeks, hours, minutes, seconds, milliseconds.toInt(), null, null, clampHours = clampHours)
fun Duration.toComponents(clampHours: Boolean = false): DateComponents {
    val span = DateTimeSpan(MonthSpan(0), this)
    return DateComponents(0, 0, 0, span.hoursIncludingDaysAndWeeks, span.minutes, span.seconds, span.milliseconds.toInt(), null, null, clampHours = clampHours)
}

fun DateComponents.toDateTimeTz(doAdjust: Boolean, doThrow: Boolean): DateTimeTz? {
    if (!doAdjust) {
        if (months !in 1..12) if (doThrow) error("Invalid month $months") else return null
        if (days !in 1..32) if (doThrow) error("Invalid day $days") else return null
        if (hours !in 0..24) if (doThrow) error("Invalid hour $hours") else return null
        if (minutes !in 0..59) if (doThrow) error("Invalid minute $minutes") else return null
        if (seconds !in 0..59) if (doThrow) error("Invalid second $seconds") else return null
        if (milliseconds !in 0..999) if (doThrow) error("Invalid millisecond $milliseconds") else return null
    }
    val dateTime = DateTime.createAdjusted(years, months, days, hours umod 24, minutes, seconds, milliseconds)
    return dateTime.toOffsetUnadjusted(offset?.time ?: 0.hours)
}

fun DateComponents.toDateTimeSpan(doAdjust: Boolean, doThrow: Boolean): DateTimeSpan? {
    if (!doAdjust) {
        if (minutes !in 0..59) if (doThrow) error("Invalid minute $minutes") else return null
        if (seconds !in 0..59) if (doThrow) error("Invalid second $seconds") else return null
        if (milliseconds !in 0..999) if (doThrow) error("Invalid millisecond $milliseconds") else return null
    }
    return DateTimeSpan(years.years + months.months, days.days + hours.hours + minutes.minutes + seconds.seconds + milliseconds.milliseconds)
}