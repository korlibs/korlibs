@file:Suppress("PackageDirectoryMismatch")

package korlibs.math.range

data class DoubleRangeExclusive(val start: Double, val endExclusive: Double) {
    val length: Double get() = endExclusive - start
    operator fun contains(value: Double): Boolean = value >= start && value < endExclusive
    override fun toString(): String = "${start.toString().removeSuffix(".0")} until ${endExclusive.toString().removeSuffix(".0")}"
}

inline infix fun Double.until(endExclusive: Double): DoubleRangeExclusive = DoubleRangeExclusive(this, endExclusive)

data class FloatInRange(val value: Float, val min: Float, val max: Float, val inclusive: Boolean = true)

data class FloatRangeExclusive(val start: Float, val endExclusive: Float) {
    val length: Float get() = endExclusive - start
    operator fun contains(value: Double): Boolean = value >= start && value < endExclusive
    override fun toString(): String = "${start.toString().removeSuffix(".0")} until ${endExclusive.toString().removeSuffix(".0")}"
}

inline infix fun Float.until(endExclusive: Float): FloatRangeExclusive = FloatRangeExclusive(this, endExclusive)
