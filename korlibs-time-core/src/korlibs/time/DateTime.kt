package korlibs.time

import korlibs.time.DateTime.Companion.EPOCH
import korlibs.time.core.*
import korlibs.time.core.internal.*
import korlibs.time.core.internal.CoreTimeInternal.MILLIS_PER_DAY
import korlibs.time.core.internal.CoreTimeInternal.MILLIS_PER_HOUR
import korlibs.time.core.internal.CoreTimeInternal.MILLIS_PER_MINUTE
import korlibs.time.core.internal.CoreTimeInternal.MILLIS_PER_SECOND
import korlibs.time.core.internal.CoreTimeInternal.Month_check
import korlibs.time.core.internal.CoreTimeInternal.Month_days
import korlibs.time.core.internal.CoreTimeInternal.Month_daysToStart
import korlibs.time.core.internal.CoreTimeInternal.Month_fromDayOfYear
import korlibs.time.core.internal.CoreTimeInternal.Year_days
import korlibs.time.core.internal.CoreTimeInternal.Year_daysSinceOne
import korlibs.time.core.internal.CoreTimeInternal.Year_fromDays
import korlibs.time.core.internal.CoreTimeInternal.Year_isLeap
import kotlin.jvm.*
import kotlin.time.*

/**
 * Represents a Date in UTC (GMT+00) with millisecond precision.
 *
 * It is internally represented as an inlined double, thus doesn't allocate in any target including JS.
 * It can represent without loss dates between (-(2 ** 52) and (2 ** 52)):
 * - Thu Aug 10 -140744 07:15:45 GMT-0014 (Central European Summer Time)
 * - Wed May 23 144683 18:29:30 GMT+0200 (Central European Summer Time)
 */
@JvmInline
@OptIn(CoreTimeInternalApi::class)
value class DateTime(
    /** Number of milliseconds since UNIX [EPOCH] */
    val unixMillis: Double
) : Comparable<DateTime>, korlibs.Serializable {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L

        /** It is a [DateTime] instance representing 00:00:00 UTC, Thursday, 1 January 1970. */
        val EPOCH = DateTime(0.0)

        /**
         * Constructs a new [DateTime] from date and time information.
         *
         * This might throw a [DateException] on invalid dates.
         */
        operator fun invoke(
            year: Int,
            month: Int,
            day: Int,
            hour: Int = 0,
            minute: Int = 0,
            second: Int = 0,
            milliseconds: Int = 0
        ): DateTime = DateTime(
            DateTime.dateToMillis(year, month, day) + DateTime.timeToMillis(
                hour,
                minute,
                second
            ) + milliseconds
        )

        /**
         * Constructs a new [DateTime] from date and time information.
         *
         * On invalid dates, this function will try to adjust the specified invalid date to a valid one by clamping components.
         */
        fun createClamped(
            year: Int,
            month: Int,
            day: Int,
            hour: Int = 0,
            minute: Int = 0,
            second: Int = 0,
            milliseconds: Int = 0
        ): DateTime {
            val clampedMonth = month.coerceIn(1, 12)
            return createUnchecked(
                year = year,
                month = clampedMonth,
                day = day.coerceIn(1, Month_days(month, year)),
                hour = hour.coerceIn(0, 23),
                minute = minute.coerceIn(0, 59),
                second = second.coerceIn(0, 59),
                milliseconds = milliseconds
            )
        }

        /**
         * Constructs a new [DateTime] from date and time information.
         *
         * On invalid dates, this function will try to adjust the specified invalid date to a valid one by adjusting other components.
         */
        fun createAdjusted(
            year: Int,
            month: Int,
            day: Int,
            hour: Int = 0,
            minute: Int = 0,
            second: Int = 0,
            milliseconds: Int = 0
        ): DateTime {
            var dy = year
            var dm = month
            var dd = day
            var th = hour
            var tm = minute
            var ts = second

            tm += ts.cycleSteps(0, 59); ts = ts.cycle(0, 59) // Adjust seconds, adding minutes
            th += tm.cycleSteps(0, 59); tm = tm.cycle(0, 59) // Adjust minutes, adding hours
            dd += th.cycleSteps(0, 23); th = th.cycle(0, 23) // Adjust hours, adding days

            while (true) {
                val dup = Month_days(dm, dy)

                dm += dd.cycleSteps(1, dup); dd = dd.cycle(1, dup) // Adjust days, adding months
                dy += dm.cycleSteps(1, 12); dm = dm.cycle(1, 12) // Adjust months, adding years

                // We have already found a day that is valid for the adjusted month!
                if (dd.cycle(1, Month_days(dm, dy)) == dd) {
                    break
                }
            }

            return createUnchecked(dy, dm, dd, th, tm, ts, milliseconds)
        }

        /**
         * Constructs a new [DateTime] from date and time information.
         *
         * On invalid dates, this function will have an undefined behaviour.
         */
        fun createUnchecked(
            year: Int,
            month: Int,
            day: Int,
            hour: Int = 0,
            minute: Int = 0,
            second: Int = 0,
            milliseconds: Int = 0
        ): DateTime {
            return DateTime(
                DateTime.dateToMillisUnchecked(year, month, day) + DateTime.timeToMillisUnchecked(
                    hour,
                    minute,
                    second
                ) + milliseconds
            )
        }

        /** Constructs a new [DateTime] from a [unix] timestamp in milliseconds. */
        operator fun invoke(unix: Long) = fromUnixMillis(unix)
        /** Constructs a new [DateTime] from a [unix] timestamp in milliseconds. */
        operator fun invoke(unix: Double) = fromUnixMillis(unix)

        /** Constructs a new [DateTime] from a [unix] timestamp in milliseconds. */
        fun fromUnixMillis(unix: Double): DateTime = DateTime(unix)
        /** Constructs a new [DateTime] from a [unix] timestamp in milliseconds. */
        fun fromUnixMillis(unix: Long): DateTime = fromUnixMillis(unix.toDouble())

        /** Returns the current time as [DateTime]. Note that since [DateTime] is inline, this property doesn't allocate on JavaScript. */
        fun now(): DateTime = DateTime(CoreTime.currentTimeMillisDouble())

        /** Returns the total milliseconds since unix epoch. The same as [nowUnixMillisLong] but as double. To prevent allocation on targets without Long support. */
        fun nowUnixMillis(): Double = CoreTime.currentTimeMillisDouble()
        /** Returns the total milliseconds since unix epoch. */
        fun nowUnixMillisLong(): Long = CoreTime.currentTimeMillisDouble().toLong()

        internal const val EPOCH_INTERNAL_MILLIS =
            62135596800000.0 // Millis since 00-00-0000 00:00 UTC to UNIX EPOCH

        internal enum class DatePart { Year, DayOfYear, Month, Day }

        fun dateToMillisUnchecked(year: Int, month: Int, day: Int): Double =
            (Year_daysSinceOne(year) + Month_daysToStart(month, year) + day - 1) * MILLIS_PER_DAY.toDouble() - EPOCH_INTERNAL_MILLIS

        fun timeToMillisUnchecked(hour: Int, minute: Int, second: Int): Double =
            hour.toDouble() * MILLIS_PER_HOUR + minute.toDouble() * MILLIS_PER_MINUTE + second.toDouble() * MILLIS_PER_SECOND

        fun dateToMillis(year: Int, month: Int, day: Int): Double {
            //Year.checked(year)
            Month_check(month)
            if (day !in 1..Month_days(month, year)) throw DateException("Day $day not valid for year=$year and month=$month")
            return dateToMillisUnchecked(year, month, day)
        }

        fun timeToMillis(hour: Int, minute: Int, second: Int): Double {
            if (hour !in 0..23) throw DateException("Hour $hour not in 0..23")
            if (minute !in 0..59) throw DateException("Minute $minute not in 0..59")
            if (second !in 0..59) throw DateException("Second $second not in 0..59")
            return timeToMillisUnchecked(hour, minute, second)
        }

        // millis are 00-00-0000 based.
        internal fun getDatePart(millis: Double, part: DatePart): Int {
            val totalDays = (millis / MILLIS_PER_DAY).toInt2()

            // Year
            val year = Year_fromDays(totalDays)
            if (part == DatePart.Year) return year

            // Day of Year
            val isLeap = Year_isLeap(year)
            val startYearDays = Year_daysSinceOne(year)
            val dayOfYear = 1 + ((totalDays - startYearDays) umod Year_days(year))
            if (part == DatePart.DayOfYear) return dayOfYear

            // Month
            val month = Month_fromDayOfYear(dayOfYear, isLeap).takeIf { it >= 0 }
                ?: error("Invalid dayOfYear=$dayOfYear, isLeap=$isLeap")
            if (part == DatePart.Month) return month

            // Day
            val dayOfMonth = dayOfYear - Month_daysToStart(month, isLeap)
            if (part == DatePart.Day) return dayOfMonth

            error("Invalid DATE_PART")
        }
    }

    /** Number of milliseconds since the 00:00:00 UTC, Monday, 1 January 1 */
    val yearOneMillis: Double get() = EPOCH_INTERNAL_MILLIS + unixMillis

    /** Number of milliseconds since UNIX [EPOCH] as [Double] */
    val unixMillisDouble: Double get() = unixMillis

    /** Number of milliseconds since UNIX [EPOCH] as [Long] */
    val unixMillisLong: Long get() = unixMillisDouble.toLong()

    /** The year part as [Int] */
    val yearInt: Int get() = getDatePart(yearOneMillis, DatePart.Year)

    /** The month part as [Int] where January is represented as 0 */
    val month0: Int get() = month1 - 1
    /** The month part as [Int] where January is represented as 1 */
    val month1: Int get() = getDatePart(yearOneMillis, DatePart.Month)

    /** The [dayOfMonth] part */
    val dayOfMonth: Int get() = getDatePart(yearOneMillis, DatePart.Day)

    /** The day of week part as [Int] : 0: Sunday, 1: Monday, 2: Tuesday, 3: Wednesday, 4: Thursday, 5: Friday, 6: Saturday */
    val dayOfWeekInt: Int get() = (yearOneMillis / MILLIS_PER_DAY + 1).toIntMod(7)

    /** The [dayOfYear] part */
    val dayOfYear: Int get() = getDatePart(yearOneMillis, DatePart.DayOfYear)

    /** The [hours] part */
    val hours: Int get() = (yearOneMillis / MILLIS_PER_HOUR).toIntMod(24)
    /** The [minutes] part */
    val minutes: Int get() = (yearOneMillis / MILLIS_PER_MINUTE).toIntMod(60)
    /** The [seconds] part */
    val seconds: Int get() = (yearOneMillis / MILLIS_PER_SECOND).toIntMod(60)
    /** The [milliseconds] part */
    val milliseconds: Int get() = (yearOneMillis).toIntMod(1000)

    /** Returns the quarter 1, 2, 3 or 4 */
    val quarter get() = (month0 / 3) + 1

    operator fun plus(delta: Duration): DateTime = DateTime(unixMillis + delta.milliseconds)
    operator fun minus(delta: Duration): DateTime = DateTime(unixMillis - delta.milliseconds)

    operator fun plus(delta: FastDuration): DateTime = DateTime(unixMillis + delta.milliseconds)
    operator fun minus(delta: FastDuration): DateTime = DateTime(unixMillis - delta.milliseconds)

    operator fun minus(other: DateTime): Duration = (this.unixMillisDouble - other.unixMillisDouble).milliseconds
    fun fastMinus(other: DateTime): FastDuration = (this.unixMillisDouble - other.unixMillisDouble).fastMilliseconds

    override fun compareTo(other: DateTime): Int = this.unixMillis.compareTo(other.unixMillis)

    //override fun toString(): String = DateFormat.DEFAULT_FORMAT.format(this)
    override fun toString(): String = "DateTime($unixMillisLong)"
}

fun max(a: DateTime, b: DateTime): DateTime =
    DateTime.fromUnixMillis(kotlin.math.max(a.unixMillis, b.unixMillis))
fun min(a: DateTime, b: DateTime): DateTime =
    DateTime.fromUnixMillis(kotlin.math.min(a.unixMillis, b.unixMillis))
fun DateTime.clamp(min: DateTime, max: DateTime): DateTime = when {
    this < min -> min
    this > max -> max
    else -> this
}
