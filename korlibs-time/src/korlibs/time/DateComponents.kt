package korlibs.time

import korlibs.time.DateComponents.Companion.UNSET
import korlibs.time.internal.*
import kotlin.time.*

data class DateComponents(
    val years: Int = 0, val months: Int = 0, val days: Int = 0,
    val hours: Int = 0, val minutes: Int = 0, val seconds: Int = 0, val milliseconds: Int = 0,
    val offset: Duration = Duration.NIL, val dayOfWeek: Int = UNSET,
    val dayOfYear: Int = UNSET, val weekOfYear: Int = UNSET,
    val clampHours: Boolean = true,
) {
    companion object {
        val UNSET = Int.MIN_VALUE

        operator fun invoke(
            years: Double, months: Double, days: Double, hours: Double, minutes: Double, seconds: Double, milliseconds: Double, offset: Duration = Duration.NIL,
            dayOfWeek: Int = UNSET, dayOfYear: Int = UNSET, weekOfYear: Int = UNSET, clampHours: Boolean = true,
        ): DateComponents {
            val span = DateTimeSpan(years = years, months = months, days = days, hours = hours, minutes = minutes, seconds = seconds, milliseconds = milliseconds)
            return DateComponents(span.years, span.months, span.daysIncludingWeeks, span.hours, span.minutes, span.seconds, span.milliseconds.toInt(), offset, dayOfWeek, dayOfYear, weekOfYear, clampHours)
        }
    }
    val time get() = hours.hours + minutes.minutes + seconds.seconds + milliseconds.milliseconds
    val offsetSure: TimezoneOffset get() = TimezoneOffset(if (offset == Duration.NIL) 0.seconds else offset)
}

fun DateTimeTz.toComponents(clampHours: Boolean = true): DateComponents = DateComponents(yearInt, month1, dayOfMonth, hours, minutes, seconds, milliseconds, offset.time, dayOfWeek.index0, dayOfYear, weekOfYear1, clampHours = clampHours)
fun DateTimeSpan.toComponents(clampHours: Boolean = true): DateComponents = DateComponents(years, months, daysIncludingWeeks, hours, minutes, seconds, milliseconds.toInt(), clampHours = clampHours)
fun Duration.toComponents(clampHours: Boolean = false): DateComponents {
    val span = DateTimeSpan(MonthSpan(0), this)
    return DateComponents(0, 0, 0, span.hoursIncludingDaysAndWeeks, span.minutes, span.seconds, span.milliseconds.toInt(), clampHours = clampHours)
}

fun DateComponents.toDateTimeTz(doAdjust: Boolean, doThrow: Boolean): DateTimeTz? {
    when {
        dayOfYear != UNSET -> DateTime(years, 1, 1) + (dayOfYear - 1).days
        weekOfYear != UNSET -> {
            val reference = Year(years).first(DayOfWeek.Thursday) - 3.days
            val days = ((weekOfYear - 1) * 7 + (dayOfWeek % 7))
            reference + days.days
        }
        else -> DateTime(years, months, days)
    }

    if (!doAdjust) {
        if (months !in 1..12) if (doThrow) error("Invalid month $months") else return null
        if (days !in 1..32) if (doThrow) error("Invalid day $days") else return null
        if (hours !in 0..24) if (doThrow) error("Invalid hour $hours") else return null
        if (minutes !in 0..59) if (doThrow) error("Invalid minute $minutes") else return null
        if (seconds !in 0..59) if (doThrow) error("Invalid second $seconds") else return null
        if (milliseconds !in 0..999) if (doThrow) error("Invalid millisecond $milliseconds") else return null
    }
    val dateTime = DateTime.createAdjusted(years, months, days, (hours umod 24), minutes, seconds, milliseconds)
    return dateTime.toOffsetUnadjusted(offsetSure)
}

fun DateComponents.toDateTimeSpan(doAdjust: Boolean, doThrow: Boolean): DateTimeSpan? {
    if (!doAdjust) {
        if (minutes !in 0..59) if (doThrow) error("Invalid minute $minutes") else return null
        if (seconds !in 0..59) if (doThrow) error("Invalid second $seconds") else return null
        if (milliseconds !in 0..999) if (doThrow) error("Invalid millisecond $milliseconds") else return null
    }
    return DateTimeSpan(years.orElse(0).years + months.orElse(0).months, days.orElse(0).days + time)
}

private fun Int.orElse(default: Int): Int = if (this == UNSET) default else this
