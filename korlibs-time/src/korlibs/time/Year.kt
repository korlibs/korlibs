package korlibs.time

import korlibs.time.core.*
import korlibs.Serializable
import korlibs.time.core.internal.*
import korlibs.time.core.internal.CoreTimeInternal.Year_leapCountSinceOne
import kotlin.jvm.*

/**
 * Represents a Year in a typed way.
 *
 * A year is a set of 365 days or 366 for leap years.
 * It is the time it takes the earth to fully orbit the sun.
 *
 * The integrated model is capable of determine if a year is leap for years 1 until 9999 inclusive.
 */
@JvmInline
@OptIn(CoreTimeInternalApi::class)
value class Year(val year: Int) : Comparable<Year>, Serializable {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L

        /**
         * Creates a Year instance checking that the year is between 1 and 9999 inclusive.
         *
         * It throws a [DateException] if the year is not in the 1..9999 range.
         */
        fun checked(year: Int) = year.apply { if (year !in 1..9999) throw DateException("Year $year not in 1..9999") }

        /**
         * Determines if a year is leap checking that the year is between 1..9999 or throwing a [DateException] when outside that range.
         */
        fun isLeapChecked(year: Int): Boolean = isLeap(checked(year))

        /**
         * Determines if a year is leap. The model works for years between 1..9999. Outside this range, the result might be invalid.
         */
        fun isLeap(year: Int): Boolean = CoreTimeInternal.Year_isLeap(year)

        /**
         * Computes the year from the number of days since 0001-01-01.
         */
        fun fromDays(days: Int): Year = Year(CoreTimeInternal.Year_fromDays(days))

        /**
         * Get the number of days of a year depending on being leap or not.
         * Normal, non leap years contain 365 days, while leap ones 366.
         */
        fun days(isLeap: Boolean) = if (isLeap) DAYS_LEAP else DAYS_COMMON

        /**
         * Return the number of leap years that happened between 1 and the specified [year].
         */
        fun leapCountSinceOne(year: Int): Int = Year_leapCountSinceOne(year)

        /**
         * Number of days since 1 and the beginning of the specified [year].
         */
        fun daysSinceOne(year: Int): Int = DAYS_COMMON * (year - 1) + leapCountSinceOne(year)

        /**
         * Number of days in a normal year.
         */
        const val DAYS_COMMON = CoreTimeInternal.DAYS_COMMON

        /**
         * Number of days in a leap year.
         */
        const val DAYS_LEAP = CoreTimeInternal.DAYS_LEAP
    }

    /**
     * Determines if this year is leap checking that the year is between 1..9999 or throwing a [DateException] when outside that range.
     */
    val isLeapChecked get() = Year.isLeapChecked(year)

    /**
     * Determines if this year is leap. The model works for years between 1..9999. Outside this range, the result might be invalid.
     */
    val isLeap get() = Year.isLeap(year)

    /**
     * Total days of this year, 365 (non leap) [DAYS_COMMON] or 366 (leap) [DAYS_LEAP].
     */
    val days: Int get() = Year.days(isLeap)

    /**
     * Number of leap years since the year 1 (without including this one)
     */
    val leapCountSinceOne: Int get() = leapCountSinceOne(year)

    /**
     * Number of days since year 1 to reach this year
     */
    val daysSinceOne: Int get() = daysSinceOne(year)

    /**
     * Compares two years.
     */
    override fun compareTo(other: Year): Int = this.year.compareTo(other.year)

    operator fun plus(delta: Int): Year = Year(year + delta)
    operator fun minus(delta: Int): Year = Year(year - delta)
    operator fun minus(other: Year): Int = this.year - other.year
}
