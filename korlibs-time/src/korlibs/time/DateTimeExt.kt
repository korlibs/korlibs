package korlibs.time

import korlibs.time.internal.*
import kotlin.time.*

/**
 * Constructs a new [DateTime] from date and time information.
 *
 * This might throw a [DateException] on invalid dates.
 */
operator fun DateTime.Companion.invoke(
    year: Year,
    month: Month,
    day: Int,
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
    milliseconds: Int = 0
): DateTime = DateTime(
    DateTime.dateToMillis(year.year, month.index1, day) + DateTime.timeToMillis(
        hour,
        minute,
        second
    ) + milliseconds
)

/**
 * Constructs a new [DateTime] from date and time information.
 *
 * This might throw a [DateException] on invalid dates.
 */
operator fun DateTime.Companion.invoke(
    date: Date,
    time: Time = Time(0.milliseconds)
): DateTime = DateTime(
    date.year, date.month1, date.day,
    time.hour, time.minute, time.second, time.millisecond
)

/**
 * Constructs a new [DateTime] from date and time information.
 *
 * This might throw a [DateException] on invalid dates.
 */
operator fun DateTime.Companion.invoke(
    year: Int,
    month: Month,
    day: Int,
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
    milliseconds: Int = 0
): DateTime = DateTime(
    DateTime.dateToMillis(year, month.index1, day) + DateTime.timeToMillis(
        hour,
        minute,
        second
    ) + milliseconds
)

/** Constructs a new [DateTime] by parsing the [str] using standard date formats. */
fun DateTime.Companion.fromString(str: String) = DateFormat.parse(str)
/** Constructs a new [DateTime] by parsing the [str] using standard date formats. */
fun DateTime.Companion.parse(str: String) = DateFormat.parse(str)
/** Returns the current local time as [DateTimeTz]. */
fun DateTime.Companion.nowLocal(): DateTimeTz = DateTimeTz.nowLocal()


/** Converts this date to String using [format] for representing it */
fun DateTime.format(format: DateFormat): String = format.format(this)
/** Converts this date to String using [format] for representing it */
fun DateTime.format(format: String): String = DateFormat(format).format(this)

/** Converts this date to String using [format] for representing it */
fun DateTime.toString(format: String): String = DateFormat(format).format(this)
/** Converts this date to String using [format] for representing it */
fun DateTime.toString(format: DateFormat): String = format.format(this)

/** Converts this date to String using the [DateFormat.DEFAULT_FORMAT] for representing it */
fun DateTime.toStringDefault(): String = DateFormat.DEFAULT_FORMAT.format(this)


/** Constructs a new [DateTime] after adding [deltaMonths] and [deltaMilliseconds] */
fun DateTime.add(deltaMonths: Int, deltaMilliseconds: Double): DateTime = when {
    deltaMonths == 0 && deltaMilliseconds == 0.0 -> this
    deltaMonths == 0 -> DateTime(this.unixMillis + deltaMilliseconds)
    else -> {
        var year = this.year
        var month = this.month.index1
        var day = this.dayOfMonth
        val i = month - 1 + deltaMonths

        if (i >= 0) {
            month = i % Month.Count + 1
            year += i / Month.Count
        } else {
            month = Month.Count + (i + 1) % Month.Count
            year += (i - (Month.Count - 1)) / Month.Count
        }
        //Year.checked(year)
        val days = Month(month).days(year)
        if (day > days) day = days

        DateTime(DateTime.dateToMillisUnchecked(year.year, month, day) + (yearOneMillis % MILLIS_PER_DAY) + deltaMilliseconds)
    }
}

/** Constructs a new [DateTime] after adding [dateSpan] and [timeSpan] */
fun DateTime.add(dateSpan: MonthSpan, timeSpan: TimeSpan): DateTime = add(dateSpan.totalMonths, timeSpan.milliseconds)

fun DateTime.copyDayOfMonth(
    year: Year = this.year,
    month: Month = this.month,
    dayOfMonth: Int = this.dayOfMonth,
    hours: Int = this.hours,
    minutes: Int = this.minutes,
    seconds: Int = this.seconds,
    milliseconds: Int = this.milliseconds
): DateTime = DateTime(year, month, dayOfMonth, hours, minutes, seconds, milliseconds)

val DateTime.date: Date get() = Date(yearInt, month1, dayOfMonth)
val DateTime.time: Time get() = Time(hours, minutes, seconds, milliseconds)

operator fun DateTime.plus(delta: MonthSpan): DateTime = this.add(delta.totalMonths, 0.0)
operator fun DateTime.plus(delta: DateTimeSpan): DateTime = this.add(delta.totalMonths, delta.totalMilliseconds)
operator fun DateTime.minus(delta: MonthSpan): DateTime = this + -delta
operator fun DateTime.minus(delta: DateTimeSpan): DateTime = this + -delta

/** The local offset for this date for the timezone of the device */
val DateTime.localOffset: TimezoneOffset get() = TimezoneOffset.local(DateTime(unixMillisDouble))
/** The [Year] part */
val DateTime.year: Year get() = Year(yearInt)
/** The [Month] part */
val DateTime.month: Month get() = Month[month1]
/** Represents a couple of [Year] and [Month] that has leap information and thus allows to get the number of days of that month */
val DateTime.yearMonth: YearMonth get() = YearMonth(year, month)
/** The [dayOfWeek] part */
val DateTime.dayOfWeek: DayOfWeek get() = DayOfWeek[dayOfWeekInt]

/** Returns a new local date that will match these components. */
val DateTime.localUnadjusted: DateTimeTz get() = DateTimeTz.local(this, localOffset)
/** Returns a new local date that will match these components but with a different [offset]. */
fun DateTime.toOffsetUnadjusted(offset: Duration): DateTimeTz = toOffsetUnadjusted(offset.offset)
/** Returns a new local date that will match these components but with a different [offset]. */
fun DateTime.toOffsetUnadjusted(offset: TimezoneOffset) = DateTimeTz.local(this, offset)

/** Returns this date with the local offset of this device. Components might change because of the offset. */
val DateTime.local: DateTimeTz get() = DateTimeTz.utc(this, localOffset)
/** Returns this date with a local offset. Components might change because of the [offset]. */
fun DateTime.toOffset(offset: Duration): DateTimeTz = toOffset(offset.offset)
/** Returns this date with a local offset. Components might change because of the [offset]. */
fun DateTime.toOffset(offset: TimezoneOffset): DateTimeTz = DateTimeTz.utc(this, offset)
/** Returns this date with a local offset. Components might change because of the [timeZone]. */
fun DateTime.toTimezone(timeZone: Timezone): DateTimeTz = toOffset(timeZone.offset)
/** Returns this date with a 0 offset. Components are equal. */
val DateTime.utc: DateTimeTz get() = DateTimeTz.utc(this, TimezoneOffset(0.minutes))


// startOf

val DateTime.startOfYear get() = DateTime(year, Month.January, 1)
val DateTime.startOfMonth get() = DateTime(year, month, 1)
val DateTime.startOfQuarter get() = DateTime(year, Month[(quarter - 1) * 3 + 1], 1)
fun DateTime.startOfDayOfWeek(day: DayOfWeek): DateTime {
    for (n in 0 until 7) {
        val date = (this - n.days)
        if (date.dayOfWeek == day) return date.startOfDay
    }
    error("Shouldn't happen")
}
val DateTime.startOfWeek: DateTime get() = startOfDayOfWeek(DayOfWeek.Sunday)
val DateTime.startOfIsoWeek: DateTime get() = startOfDayOfWeek(DayOfWeek.Monday)
val DateTime.startOfDay get() = DateTime(year, month, dayOfMonth)
val DateTime.startOfHour get() = DateTime(year, month, dayOfMonth, hours)
val DateTime.startOfMinute get() = DateTime(year, month, dayOfMonth, hours, minutes)
val DateTime.startOfSecond get() = DateTime(year, month, dayOfMonth, hours, minutes, seconds)

// endOf

val DateTime.endOfYear get() = DateTime(year, Month.December, 31, 23, 59, 59, 999)
val DateTime.endOfMonth get() = DateTime(year, month, month.days(year), 23, 59, 59, 999)
val DateTime.endOfQuarter get() = DateTime(year, Month[(quarter - 1) * 3 + 3], month.days(year), 23, 59, 59, 999)
fun DateTime.endOfDayOfWeek(day: DayOfWeek): DateTime {
    for (n in 0 until 7) {
        val date = (this + n.days)
        if (date.dayOfWeek == day) return date.endOfDay
    }
    error("Shouldn't happen")
}
val DateTime.endOfWeek: DateTime get() = endOfDayOfWeek(DayOfWeek.Monday)
val DateTime.endOfIsoWeek: DateTime get() = endOfDayOfWeek(DayOfWeek.Sunday)
val DateTime.endOfDay get() = DateTime(year, month, dayOfMonth, 23, 59, 59, 999)
val DateTime.endOfHour get() = DateTime(year, month, dayOfMonth, hours, 59, 59, 999)
val DateTime.endOfMinute get() = DateTime(year, month, dayOfMonth, hours, minutes, 59, 999)
val DateTime.endOfSecond get() = DateTime(year, month, dayOfMonth, hours, minutes, seconds, 999)

/** Returns a [DateTime] of [this] day with the hour at 00:00:00 */
val DateTime.dateDayStart get() = DateTime(year, month, dayOfMonth, 0, 0, 0, 0)
/** Returns a [DateTime] of [this] day with the hour at 23:59:59.999 */
val DateTime.dateDayEnd get() = DateTime(year, month, dayOfMonth, 23, 59, 59, 999)

