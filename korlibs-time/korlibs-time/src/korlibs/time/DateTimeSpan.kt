package korlibs.time

import korlibs.Serializable
import korlibs.time.core.*
import korlibs.time.core.internal.*
import korlibs.time.internal.Moduler
import kotlin.time.*

/**
 * Immutable structure representing a set of a [monthSpan] and a [duration].
 * This structure loses information about which months are included, that makes it impossible to generate a real [Duration] including months.
 * You can use [DateTimeRange.duration] to get this information from two real [DateTime].
 */
@OptIn(CoreTimeInternalApi::class)
data class DateTimeSpan(
    /** The [MonthSpan] part */
    val monthSpan: MonthSpan,
    /** The [Duration] part */
    val duration: Duration
) : Comparable<DateTimeSpan>, Serializable {

    @Deprecated("", ReplaceWith("duration"))
    val timeSpan: Duration get() = duration

    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    constructor(
        years: Int = 0,
        months: Int = 0,
        weeks: Int = 0,
        days: Int = 0,
        hours: Int = 0,
        minutes: Int = 0,
        seconds: Int = 0,
        milliseconds: Double = 0.0
    ) : this(
        years.years + months.months,
        weeks.weeks + days.days + hours.hours + minutes.minutes + seconds.seconds + milliseconds.milliseconds
    )

    operator fun unaryMinus() = DateTimeSpan(-monthSpan, -duration)
    operator fun unaryPlus() = DateTimeSpan(+monthSpan, +duration)

    operator fun plus(other: Duration) = DateTimeSpan(monthSpan, duration + other)
    operator fun plus(other: MonthSpan) = DateTimeSpan(monthSpan + other, duration)
    operator fun plus(other: DateTimeSpan) = DateTimeSpan(monthSpan + other.monthSpan, duration + other.duration)

    operator fun minus(other: Duration) = this + -other
    operator fun minus(other: MonthSpan) = this + -other
    operator fun minus(other: DateTimeSpan) = this + -other

    operator fun times(times: Double) = DateTimeSpan((monthSpan * times), (duration * times))
    operator fun times(times: Int) = this * times.toDouble()
    operator fun times(times: Float) = this * times.toDouble()

    operator fun div(times: Double) = times(1.0 / times)
    operator fun div(times: Int) = this / times.toDouble()
    operator fun div(times: Float) = this / times.toDouble()

    /** From the date part, all months represented as a [totalYears] [Double] */
    val totalYears: Double get() = monthSpan.totalYears

    /** From the date part, all months including months and years */
    val totalMonths: Int get() = monthSpan.totalMonths

    /** From the time part, all the milliseconds including milliseconds, seconds, minutes, hours, days and weeks */
    val totalMilliseconds: Double get() = duration.milliseconds

    /** The [years] part as an integer. */
    val years: Int get() = monthSpan.years
    /** The [months] part as an integer. */
    val months: Int get() = monthSpan.months

    /** The [weeks] part as an integer. */
    val weeks: Int get() = computed.weeks

    /** The [days] part as an integer. */
    val daysNotIncludingWeeks: Int get() = computed.days

    /** The [daysIncludingWeeks] part as an integer including days and weeks. */
    val daysIncludingWeeks: Int get() = computed.days + (computed.weeks * DayOfWeek.Count)

    /** The [days] part as an integer. */
    @Deprecated("", ReplaceWith("daysNotIncludingWeeks"))
    val days: Int get() = daysNotIncludingWeeks

    /** The [hours] part as an integer including days and weeks. */
    val hoursIncludingDaysAndWeeks: Int get() = computed.hoursIncludingDaysAndWeeks

    /** The [hours] part as an integer. */
    val hours: Int get() = computed.hours

    /** The [minutes] part as an integer. */
    val minutes: Int get() = computed.minutes

    /** The [seconds] part as an integer. */
    val seconds: Int get() = computed.seconds

    /** The [milliseconds] part as a double. */
    val milliseconds: Double get() = computed.milliseconds

    /** The [nanoseconds] part as an int. */
    val nanoseconds: Int get() = computed.nanoseconds

    /** The [secondsIncludingMilliseconds] part as a doble including seconds and milliseconds. */
    val secondsIncludingMilliseconds: Double get() = computed.secondsIncludingMilliseconds

    /**
     * Note that if milliseconds overflow months this could not be exactly true. But probably will work in most cases.
     * This structure doesn't have information about which months are counted. So some months could have 28-31 days and thus can't be done.
     * You can use [DateTimeRange.duration] to compare this with real precision using a range between two [DateTime].
     */
    override fun compareTo(other: DateTimeSpan): Int {
        if (this.totalMonths != other.totalMonths) return this.monthSpan.compareTo(other.monthSpan)
        return this.duration.compareTo(other.duration)
    }

    /**
     * Represents this [DateTimeSpan] as a string like `50Y 10M 3W 6DH 30m 15s`.
     * Parts that are zero, won't be included. You can omit weeks and represent them
     * as days by adjusting the [includeWeeks] parameter.
     */
    fun toString(includeWeeks: Boolean): String = arrayListOf<String>().apply {
        if (years != 0) add("${years}Y")
        if (months != 0) add("${months}M")
        if (includeWeeks && weeks != 0) add("${weeks}W")
        if (days != 0 || (!includeWeeks && weeks != 0)) add("${if (includeWeeks) days else daysIncludingWeeks}D")
        if (hours != 0) add("${hours}H")
        if (minutes != 0) add("${minutes}m")
        if (seconds != 0 || milliseconds != 0.0) add("${secondsIncludingMilliseconds}s")
        if (monthSpan == 0.years && ((duration == 0.seconds) || (duration == (-0).seconds))) add("0s")
    }.joinToString(" ")

    override fun toString(): String = toString(includeWeeks = true)

    private val computed by lazy { ComputedTime(duration) }
}
