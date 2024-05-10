package korlibs.math.interpolation

import kotlin.math.*

//inline class Ratio(val valueD: Double) : Comparable<Ratio> {
//    constructor(ratio: Float) : this(ratio.toDouble())
//    val value: Double get() = valueD
//    val valueF: Float get() = value.toFloat()
inline class Ratio(val value: Double) : Comparable<Ratio> {
    constructor(ratio: Float) : this(ratio.toDouble())

    fun toFloat(): Float = value.toFloat()
    fun toDouble(): Double = value.toDouble()

    constructor(value: Int, maximum: Int) : this(value.toFloat() / maximum.toFloat())
    constructor(value: Float, maximum: Float) : this(value / maximum)
    constructor(value: Double, maximum: Double) : this(value / maximum)

    operator fun unaryPlus(): Ratio = Ratio(+this.value)
    operator fun unaryMinus(): Ratio = Ratio(-this.value)
    operator fun plus(that: Ratio): Ratio = Ratio(this.value + that.value)
    operator fun minus(that: Ratio): Ratio = Ratio(this.value - that.value)

    operator fun times(that: Ratio): Ratio = Ratio(this.value * that.value)
    operator fun div(that: Ratio): Ratio = Ratio(this.value / that.value)
    operator fun times(that: Double): Double = (this.value * that)
    operator fun div(that: Double): Double = (this.value / that)

    val absoluteValue: Ratio get() = Ratio(value.absoluteValue)
    val clamped: Ratio get() = Ratio(value.clamp01())

    fun convertToRange(min: Float, max: Float): Float = this.toFloat().convertRange(0f, 1f, min, max)
    fun convertToRange(min: Double, max: Double): Double = this.toDouble().convertRange(0.0, 1.0, min, max)
    fun convertToRange(min: Ratio, max: Ratio): Ratio = Ratio(this.toDouble().convertRange(0.0, 1.0, min.toDouble(), max.toDouble()))

    override fun compareTo(other: Ratio): Int = value.compareTo(other.value)

    fun isNaN(): Boolean = value.isNaN()

    override fun toString(): String = "$value"

    companion object {
        val ZERO = Ratio(0.0)
        val QUARTER = Ratio(.25)
        val HALF = Ratio(.5)
        val ONE = Ratio(1.0)
        val NaN = Ratio(Float.NaN)

        inline fun fromValueInRange(value: Number, min: Number, max: Number): Ratio =
            value.toDouble().convertRange(min.toDouble(), max.toDouble(), 0.0, 1.0).toRatio()

        inline fun fromValueInRangeClamped(value: Number, min: Number, max: Number): Ratio =
            value.toDouble().convertRangeClamped(min.toDouble(), max.toDouble(), 0.0, 1.0).toRatio()

        inline fun forEachRatio(steps: Int, include0: Boolean = true, include1: Boolean = true, block: (ratio: Ratio) -> Unit) {
            val NS = steps - 1
            val NSd = NS.toDouble()
            val start = if (include0) 0 else 1
            val end = if (include1) NS else NS - 1
            for (n in start..end) {
                val ratio = n.toFloat() / NSd
                block(ratio.toRatio())
            }
        }
    }
}

inline operator fun Float.times(ratio: Ratio): Float = (this * ratio.value).toFloat()
inline operator fun Double.times(ratio: Ratio): Double = this * ratio.value
inline operator fun Int.times(ratio: Ratio): Double = this.toDouble() * ratio.value
inline operator fun Float.div(ratio: Ratio): Float = (this / ratio.value).toFloat()
inline operator fun Double.div(ratio: Ratio): Double = this / ratio.value
inline operator fun Int.div(ratio: Ratio): Double = this.toDouble() / ratio.value

inline operator fun Ratio.times(value: Ratio): Ratio = Ratio(this.value * value.value)

inline operator fun Ratio.times(value: Float): Float = (this.value * value).toFloat()
inline operator fun Ratio.times(value: Double): Double = this.value * value
inline operator fun Ratio.div(value: Float): Float = (this.value / value).toFloat()
inline operator fun Ratio.div(value: Double): Double = this.value / value

@Deprecated("", ReplaceWith("this")) fun Ratio.toRatio(): Ratio = this

inline fun Number.toRatio(): Ratio = Ratio(this.toDouble())
fun Float.toRatio(): Ratio = Ratio(this)
fun Double.toRatio(): Ratio = Ratio(this)

inline fun Number.toRatio(max: Number): Ratio = Ratio(this.toDouble(), max.toDouble())
fun Float.toRatio(max: Float): Ratio = Ratio(this, max)
fun Double.toRatio(max: Double): Ratio = Ratio(this, max)

fun Number.toRatioClamped(): Ratio = Ratio(this.toDouble().clamp01())
fun Float.toRatioClamped(): Ratio = Ratio(this.clamp01())
fun Double.toRatioClamped(): Ratio = Ratio(this.clamp01())

fun Ratio.convertRange(srcMin: Ratio, srcMax: Ratio, dstMin: Ratio, dstMax: Ratio): Ratio = Ratio(this.toDouble().convertRange(srcMin.toDouble(), srcMax.toDouble(), dstMin.toDouble(), dstMax.toDouble()))
fun Ratio.isAlmostEquals(that: Ratio, epsilon: Ratio = Ratio(0.000001)): Boolean = this.toDouble().isAlmostEquals(that.toDouble(), epsilon.toDouble())
fun Ratio.isAlmostZero(epsilon: Ratio = Ratio(0.000001)): Boolean = this.isAlmostEquals(Ratio.ZERO, epsilon)
fun Ratio.roundDecimalPlaces(places: Int): Ratio = Ratio(value.roundDecimalPlaces(places))

fun abs(a: Ratio): Ratio = Ratio(a.value.absoluteValue)
fun min(a: Ratio, b: Ratio): Ratio = Ratio(kotlin.math.min(a.value, b.value))
fun max(a: Ratio, b: Ratio): Ratio = Ratio(kotlin.math.max(a.value, b.value))
fun Ratio.clamp(min: Ratio, max: Ratio): Ratio = when {
    this < min -> min
    this > max -> max
    else -> this
}

@PublishedApi internal fun Float.convertRange(srcMin: Float, srcMax: Float, dstMin: Float, dstMax: Float): Float = (dstMin + (dstMax - dstMin) * ((this - srcMin) / (srcMax - srcMin)))
/** Converts this value considering it was in the range [srcMin]..[srcMax] into [dstMin]..[dstMax], if the value is not inside the range the output value will be outside the destination range */
@PublishedApi internal fun Double.convertRange(srcMin: Double, srcMax: Double, dstMin: Double, dstMax: Double): Double = (dstMin + (dstMax - dstMin) * ((this - srcMin) / (srcMax - srcMin)))

@PublishedApi internal fun Float.convertRangeClamped(srcMin: Float, srcMax: Float, dstMin: Float, dstMax: Float): Float = convertRange(srcMin, srcMax, dstMin, dstMax).clamp(dstMin, dstMax)
/** Converts this value considering it was in the range [srcMin]..[srcMax] into [dstMin]..[dstMax], if the value is not inside the range the output value will be clamped to the nearest bound */
@PublishedApi internal fun Double.convertRangeClamped(srcMin: Double, srcMax: Double, dstMin: Double, dstMax: Double): Double = convertRange(srcMin, srcMax, dstMin, dstMax).clamp(dstMin, dstMax)

/** Clamps [this] value into the range [min] and [max] */
internal fun Double.clamp(min: Double, max: Double): Double = if (this < min) min else if (this > max) max else this
/** Clamps [this] value into the range [min] and [max] */
internal fun Float.clamp(min: Float, max: Float): Float = if ((this < min)) min else if ((this > max)) max else this

/** Clamps [this] value into the range 0 and 1 */
internal fun Double.clamp01(): Double = clamp(0.0, 1.0)
/** Clamps [this] value into the range 0 and 1 */
internal fun Float.clamp01(): Float = clamp(0f, 1f)

internal fun Float.isAlmostEquals(other: Float, epsilon: Float = 0.000001f): Boolean = (this - other).absoluteValue < epsilon
internal fun Double.isAlmostEquals(other: Double, epsilon: Double = 0.000001): Boolean = (this - other).absoluteValue < epsilon

internal fun Float.roundDecimalPlaces(places: Int): Float {
    if (places < 0) return this
    val placesFactor: Float = 10f.pow(places.toFloat())
    return round(this * placesFactor) / placesFactor
}

internal fun Double.roundDecimalPlaces(places: Int): Double {
    if (places < 0) return this
    val placesFactor: Double = 10.0.pow(places.toDouble())
    return round(this * placesFactor) / placesFactor
}

