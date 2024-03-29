package korlibs.time.core.internal

import korlibs.time.*
import korlibs.time.core.*
import kotlin.math.*

internal infix fun Int.umod(other: Int): Int {
    val rm = this % other
    val remainder = if (rm == -0) 0 else rm
    return when {
        remainder < 0 -> remainder + other
        else -> remainder
    }
}

@PublishedApi
internal infix fun Double.umod(other: Double): Double {
    val rm = this % other
    val remainder = if (rm == -0.0) 0.0 else rm
    return when {
        remainder < 0.0 -> remainder + other
        else -> remainder
    }
}

internal infix fun Int.div2(other: Int): Int = when {
    this < 0 || this % other == 0 -> this / other
    else -> (this / other) - 1
}

internal fun Int.cycle(min: Int, max: Int): Int = ((this - min) umod (max - min + 1)) + min
internal fun Int.cycleSteps(min: Int, max: Int): Int = (this - min) / (max - min + 1)
internal fun Double.toInt2(): Int = if (this < 0.0) floor(this).toInt() else this.toInt()
internal fun Double.toIntMod(mod: Int): Int = (this umod mod.toDouble()).toInt2()

@CoreTimeInternalApi
object CoreTimeInternal {
    const val MILLIS_PER_SECOND = 1000
    const val MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60 // 60_000
    const val MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60 // 3600_000
    const val MILLIS_PER_DAY = MILLIS_PER_HOUR * 24 // 86400_000
    const val MILLIS_PER_WEEK = MILLIS_PER_DAY * 7 // 604800_000
    const val MILLIS_PER_MICROSECOND = 1.0 / 1000.0
    const val MILLIS_PER_NANOSECOND = MILLIS_PER_MICROSECOND / 1000.0

    val MONTH_START_DAYS: Array<IntArray> by lazy {
        Array(2) { leap ->
            var acc = 0
            IntArray(13) { month ->
                acc += if (month == 0) 0 else Month_days(month, leap != 0)
                acc
            }
        }
    }

    fun Month_daysToStart(month: Int, year: Int): Int = Month_daysToStart(month, Year_isLeap(year))
    fun Month_daysToStart(month: Int, leap: Boolean): Int = MONTH_START_DAYS[if (leap) 1 else 0][Month_adjust(month) - 1]

    fun Month_daysToEnd(month: Int, year: Int): Int = Month_daysToEnd(month, Year_isLeap(year))
    fun Month_daysToEnd(month: Int, leap: Boolean): Int = MONTH_START_DAYS[if (leap) 1 else 0][Month_adjust(month)]

    fun Month_adjust(month: Int): Int = ((month - 1) % 12) + 1
    fun Month_days(month: Int, year: Int): Int = Month_days(month, Year_isLeap(year))
    fun Month_days(month: Int, leap: Boolean): Int = when (Month_adjust(month)) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        2 -> if (leap) 29 else 28
        else -> 30
    }

    fun Month_fromDayOfYear(dayOfYear: Int, leap: Boolean): Int {
        val days = MONTH_START_DAYS[if (leap) 1 else 0]
        val day0 = dayOfYear - 1
        val guess = day0 / 32
        if (guess in 0..11 && day0 in days[guess] until days[guess + 1]) return guess + 1
        if (guess in 0..10 && day0 in days[guess + 1] until days[guess + 2]) return guess + 2
        return -1
    }

    fun Month_check(month: Int) {
        if (month !in 1..12) throw DateException("Month $month not in 1..12")
    }

    fun Year_isLeap(year: Int): Boolean = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)
    fun Year_daysSinceOne(year: Int): Int = DAYS_COMMON * (year - 1) + Year_leapCountSinceOne(year)
    fun Year_days(year: Int): Int = Year_days(Year_isLeap(year))
    fun Year_days(leap: Boolean): Int = if (leap) DAYS_LEAP else DAYS_COMMON
    const val DAYS_COMMON = 365
    const val DAYS_LEAP = 366
    private const val LEAP_PER_4_YEARS = 1
    private const val LEAP_PER_100_YEARS = 24 // 24 or 25 (25 the first chunk)
    private const val LEAP_PER_400_YEARS = 97
    private const val DAYS_PER_4_YEARS = 4 * DAYS_COMMON + LEAP_PER_4_YEARS
    private const val DAYS_PER_100_YEARS = 100 * DAYS_COMMON + LEAP_PER_100_YEARS
    private const val DAYS_PER_400_YEARS = 400 * DAYS_COMMON + LEAP_PER_400_YEARS
    fun Year_leapCountSinceOne(year: Int): Int {
        if (year < 1) {
            // @TODO: Hack for negative numbers. Maybe we can do some kind of offset and subtract.
            var leapCount = 0
            var y = 1
            while (y >= year) {
                if (Year_isLeap(y)) leapCount--
                y--
            }
            return leapCount
        }
        val y1 = (year - 1)
        val res = (y1 / 4) - (y1 / 100) + (y1 / 400)
        return res
    }

    fun Year_fromDays(days: Int): Int {
        // https://en.wikipedia.org/wiki/Leap_year#Algorithm
        // Each 400 years the modular cycle is completed

        val v400 = days / DAYS_PER_400_YEARS
        val r400 = days - (v400 * DAYS_PER_400_YEARS)

        val v100 = min(r400 / DAYS_PER_100_YEARS, 3)
        val r100 = r400 - (v100 * DAYS_PER_100_YEARS)

        val v4 = r100 / DAYS_PER_4_YEARS
        val r4 = r100 - (v4 * DAYS_PER_4_YEARS)

        val v1 = min(r4 / DAYS_COMMON, 3)

        val extra = if (days < 0) 0 else 1
        //val extra = 1

        return extra + v1 + (v4 * 4) + (v100 * 100) + (v400 * 400)
    }
}