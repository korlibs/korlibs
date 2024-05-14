package korlibs.time

import korlibs.Serializable
import kotlin.jvm.JvmInline
import kotlin.math.*
import kotlin.time.*

/**
 * Creates a [MonthSpan] representing these years.
 */
inline val Int.years get() = MonthSpan(12.0 * this)
inline val Double.years get() = MonthSpan(12.0 * this)
inline val Number.years get() = MonthSpan(12.0 * this.toDouble())

/**
 * Creates a [MonthSpan] representing these months.
 */
inline val Int.months get() = MonthSpan(this.toDouble())
inline val Double.months get() = MonthSpan(this)
inline val Number.months get() = MonthSpan(this.toDouble())

/**
 * Represents a number of years and months temporal distance.
 */
@JvmInline
value class MonthSpan(
    /** Total months of this [MonthSpan] as integer */
    val totalMonthsDouble: Double
) : Comparable<MonthSpan>, Serializable {
    constructor(totalMonths: Int) : this(totalMonths.toDouble())

    val totalMonthsInt: Int get() = totalMonthsDouble.roundToInt()

    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    operator fun unaryMinus() = MonthSpan(-totalMonthsDouble)
    operator fun unaryPlus() = MonthSpan(+totalMonthsDouble)

    operator fun plus(other: Duration) = DateTimeSpan(this, other)
    operator fun plus(other: MonthSpan) = MonthSpan(totalMonthsDouble + other.totalMonthsDouble)
    operator fun plus(other: DateTimeSpan) = DateTimeSpan(other.monthSpan + this, other.timeSpan)

    operator fun minus(other: Duration) = this + -other
    operator fun minus(other: MonthSpan) = this + -other
    operator fun minus(other: DateTimeSpan) = this + -other

    operator fun times(times: Double) = MonthSpan(totalMonthsDouble * times)
    operator fun times(times: Int) = this * times.toDouble()
    operator fun times(times: Float) = this * times.toDouble()

    operator fun div(times: Double) = MonthSpan(totalMonthsDouble / times)
    operator fun div(times: Int) = this / times.toDouble()
    operator fun div(times: Float) = this / times.toDouble()

    override fun compareTo(other: MonthSpan): Int = this.totalMonthsDouble.compareTo(other.totalMonthsDouble)

    /** Converts this time to String formatting it like "20Y", "20Y 1M", "1M" or "0M". */
    override fun toString(): String {
        val list = arrayListOf<String>()
        if (years != 0) list.add("${years}Y")
        if (months != 0 || years == 0) list.add("${months}M")
        return list.joinToString(" ")
    }
}

/** Total years of this [MonthSpan] as double (might contain decimals) */
val MonthSpan.totalYears: Double get() = totalMonthsDouble / 12.0

/** Years part of this [MonthSpan] as integer */
val MonthSpan.years: Int get() = (totalMonthsDouble / 12).toInt()

/** Months part of this [MonthSpan] as integer */
val MonthSpan.months: Int get() = (totalMonthsDouble % 12).toInt()

/** Months part of this [MonthSpan] as double */
val MonthSpan.monthsDouble: Double get() = (totalMonthsDouble % 12)
