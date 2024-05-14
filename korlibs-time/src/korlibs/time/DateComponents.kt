package korlibs.time

import korlibs.time.internal.*

data class DateComponents(
    val year: Int, val month1: Int, val dayOfMonth: Int,
    val hours: Int, val minutes: Int, val seconds: Int, val milliseconds: Int,
    val offset: TimezoneOffset? = null, val dayOfWeek: DayOfWeek? = null,
    val clampHours: Boolean = true,
)

fun DateTimeTz.toComponents(): DateComponents = DateComponents(yearInt, month1, dayOfMonth, hours, minutes, seconds, milliseconds, offset, dayOfWeek, clampHours = true)
fun DateTimeSpan.toComponents(): DateComponents = DateComponents(years, months, daysIncludingWeeks, hours, minutes, seconds, milliseconds.toInt(), null, null, clampHours = true)

fun DateComponents.toDateTimeTz(doAdjust: Boolean, doThrow: Boolean): DateTimeTz? {
    if (!doAdjust) {
        if (month1 !in 1..12) if (doThrow) error("Invalid month $month1") else return null
        if (dayOfMonth !in 1..32) if (doThrow) error("Invalid day $dayOfMonth") else return null
        if (hours !in 0..24) if (doThrow) error("Invalid hour $hours") else return null
        if (minutes !in 0..59) if (doThrow) error("Invalid minute $minutes") else return null
        if (seconds !in 0..59) if (doThrow) error("Invalid second $seconds") else return null
        if (milliseconds !in 0..999) if (doThrow) error("Invalid millisecond $milliseconds") else return null
    }
    val dateTime = DateTime.createAdjusted(year, month1, dayOfMonth, hours umod 24, minutes, seconds, milliseconds)
    return dateTime.toOffsetUnadjusted(offset?.time ?: 0.hours)
}

fun DateComponents.toDateTimeSpan(): DateTimeSpan {
    return year.years + month1.months + dayOfMonth.days + hours.hours + minutes.minutes + seconds.seconds + milliseconds.milliseconds
}