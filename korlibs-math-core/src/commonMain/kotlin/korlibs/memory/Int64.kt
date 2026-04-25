package korlibs.memory

import kotlin.contracts.*

inline class Int64Array(val raw: DoubleArray) : Iterable<Int64> {
    inline val indices: IntRange get() = raw.indices

    constructor(size: Int, value: Int64 = Int64.ZERO) : this(DoubleArray(size) { value.raw })
    companion object {
        inline operator fun invoke(size: Int, gen: (Int) -> Int64): Int64Array = Int64Array(DoubleArray(size) { gen(it).raw })
    }

    inline val size: Int get() = raw.size
    inline operator fun get(index: Int): Int64 = Int64.fromRaw(raw[index])
    inline operator fun set(index: Int, value: Int64) { raw[index] = value.raw }
    override fun iterator(): Iterator<Int64> = object : Iterator<Int64> {
        var index = 0
        override fun hasNext(): Boolean = index < raw.size
        override fun next(): Int64 = this@Int64Array[index].also { index++ }
    }

    override fun toString(): String = "IntArray64($size)"
}

inline fun <T : Int64> int64ArrayOf(vararg values: T): Int64Array = Int64Array(values.size) { values[it] }
inline fun int64ArrayOf(vararg values: Int): Int64Array = Int64Array(values.size) { values[it].toInt64() }
inline fun int64ArrayOf(vararg values: Long): Int64Array = Int64Array(values.size) { values[it].toInt64() }

fun Int64Array.copyOf(newSize: Int = this.size): Int64Array = Int64Array(raw.copyOf(newSize))
fun Int64Array.copyOfRange(fromIndex: Int, toIndex: Int): Int64Array = Int64Array(raw.copyOfRange(fromIndex, toIndex))
public fun Int64Array.getOrNull(index: Int): Int64? = if (index in indices) get(index) else null
//@kotlin.internal.InlineOnly
@OptIn(ExperimentalContracts::class)
public inline fun Int64Array.getOrElse(index: Int, defaultValue: (Int) -> Int64): Int64 {
    contract { callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE) }
    return if (index in indices) get(index) else defaultValue(index)
}

infix fun Int64Array?.contentEquals(other: Int64Array?): Boolean = this?.raw.contentEquals(other?.raw)
fun Int64Array?.contentHashCode(): Int = this?.raw.contentHashCode()
fun Int64Array?.contentToString(): String = if (this == null) "null" else "[" + this.raw.joinToString(", ") { it.toString() } + "]"

/**
 * Allocation-less Long implementation that uses a Double with reinterpreted values
 *
 * IMPORTANT:
 *
 * Due to Kotlin not supporting [equals] in inline classes,
 * Equality fails in some cases where Int64 represents a NaN or an Infinity.
 * For comparing Int64, use [Int64.equalsSafe] instead.
 */
inline class Int64(val raw: Double) : Comparable<Int64> {
    companion object {
        val ZERO = Int64(0, 0)

        fun equals(a: Int64, b: Int64): Boolean = a.raw.equalsRaw(b.raw)

        inline operator fun invoke(value: Long): Int64 = Int64(value.reinterpretAsDouble())
        inline operator fun invoke(low: Int, high: Int): Int64 = Int64(Double.fromLowHigh(low, high))
        inline operator fun invoke(value: Int64): Int64 = Int64(value.raw)
        inline operator fun invoke(value: UInt): Int64 = Int64(Double.fromLowHigh(value.toInt(), 0))
        inline operator fun invoke(value: Int): Int64 = when {
            value < 0 -> Int64(Double.fromLowHigh(value and (1 shl 31), 1 shl 31))
            else -> Int64(Double.fromLowHigh(value, 0))
        }

        inline fun fromRaw(value: Double) = Int64(value)
        inline fun fromInt52(values: Double) = Int64(Double.fromParts(0, 0, values))

        fun add(low1: UInt, high1: Int, low2: UInt, high2: Int): Int64 {
            val low = low1 + low2
            val carry = if (low < low1) 1 else 0
            val high = high1 + high2 + carry
            return Int64(low.toInt(), high)
        }
        fun sub(low1: UInt, high1: Int, low2: UInt, high2: Int): Int64 {
            val lowDiff = low1 - low2
            val borrow = if (low1 < low2) 1 else 0
            val highDiff = high1 - high2 - borrow
            return Int64(lowDiff.toInt(), highDiff)
        }
        // @TODO: Fix this
        fun imul(low1: UInt, high1: Int, low2: UInt, high2: Int): Int64 {
            if (low1 == 0u && high1 == 0) return Int64.ZERO
            if (low2 == 0u && high2 == 0) return Int64.ZERO

            /*
            if (equalsLong(_this__u8e3s4, get_MIN_VALUE())) {
                return if (isOdd(other)) get_MIN_VALUE() else get_ZERO()
            } else if (equalsLong(other, get_MIN_VALUE())) {
                return if (isOdd(_this__u8e3s4)) get_MIN_VALUE() else get_ZERO()
            }
            if (isNegative(_this__u8e3s4)) {
                val tmp: Unit
                if (isNegative(other)) {
                    tmp = multiply(negate(_this__u8e3s4), negate(other))
                } else {
                    tmp = negate(multiply(negate(_this__u8e3s4), other))
                }
                return tmp
            } else if (isNegative(other)) {
                return negate(multiply(_this__u8e3s4, negate(other)))
            }
            if (lessThan(_this__u8e3s4, get_TWO_PWR_24_()) && lessThan(other, get_TWO_PWR_24_())) {
                return fromNumber(toNumber(_this__u8e3s4) * toNumber(other))
            }
            val a48: Unit = _this__u8e3s4.high_1 ushr 16 or 0
            val a32: Unit = _this__u8e3s4.high_1 and 65535
            val a16: Unit = _this__u8e3s4.low_1 ushr 16 or 0
            val a00: Unit = _this__u8e3s4.low_1 and 65535
            val b48: Unit = other.high_1 ushr 16 or 0
            val b32: Unit = other.high_1 and 65535
            val b16: Unit = other.low_1 ushr 16 or 0
            val b00: Unit = other.low_1 and 65535
            var c48 = 0
            var c32 = 0
            var c16 = 0
            var c00 = 0
            c00 = c00 + imul(a00, b00) or 0
            c16 = c16 + (c00 ushr 16 or 0) or 0
            c00 = c00 and 65535
            c16 = c16 + imul(a16, b00) or 0
            c32 = c32 + (c16 ushr 16 or 0) or 0
            c16 = c16 and 65535
            c16 = c16 + imul(a00, b16) or 0
            c32 = c32 + (c16 ushr 16 or 0) or 0
            c16 = c16 and 65535
            c32 = c32 + imul(a32, b00) or 0
            c48 = c48 + (c32 ushr 16 or 0) or 0
            c32 = c32 and 65535
            c32 = c32 + imul(a16, b16) or 0
            c48 = c48 + (c32 ushr 16 or 0) or 0
            c32 = c32 and 65535
            c32 = c32 + imul(a00, b32) or 0
            c48 = c48 + (c32 ushr 16 or 0) or 0
            c32 = c32 and 65535
            c48 = c48 + (((imul(a48, b00) + imul(a32, b16) or 0) + imul(a16, b32) or 0) + imul(a00, b48) or 0) or 0
            c48 = c48 and 65535
            return Long(c16 shl 16 or c00, c48 shl 16 or c32)
            */
            TODO()
        }
    }

    inline val isNegative get() = high.extract1(31) != 0
    inline val isPositive get() = !isNegative
    inline val isZero get() = low == 0 && high == 0

    operator fun unaryPlus(): Int64 = this
    operator fun unaryMinus(): Int64 = Int64(low, -high)
    fun inv(): Int64 = Int64(low.inv(), high.inv())

    operator fun plus(other: Int64): Int64 = add(ulow, high, other.ulow, other.high)
    operator fun minus(other: Int64): Int64 = sub(ulow, high, other.ulow, other.high)
    infix fun xor(other: Int64): Int64 = Int64(low xor other.low, high xor other.high)
    infix fun and(other: Int64): Int64 = Int64(low and other.low, high and other.high)
    infix fun or(other: Int64): Int64 = Int64(low or other.low, high or other.high)

    //infix fun shl(other: Int): Int64 = Int64(low shl other, high shl other) // @TODO: Fix this
    //infix fun shr(other: Int): Int64 = Int64(low shr other, high shr other) // @TODO: Fix this
    //infix fun ushr(other: Int): Int64 = Int64(low ushr other, high ushr other) // @TODO: Fix this

    // @TODO: SLOW (USE INTERMEDIARY LONGS)
    infix fun shl(other: Int): Int64 = Int64(toLong() shl other)
    infix fun shr(other: Int): Int64 = Int64(toLong() shr other)
    infix fun ushr(other: Int): Int64 = Int64(toLong() ushr other)
    operator fun times(other: Int64): Int64 {
        if (this.isZero || other.isZero) return Int64.ZERO
        return Int64(toLong() * other.toLong())
    }
    //operator fun times(other: Int64): Int64 = imul(ulow, high, other.ulow, other.high) // @TODO: Fix this
    operator fun div(other: Int64): Int64 = Int64(toLong() / other.toLong())
    operator fun rem(other: Int64): Int64 = Int64(toLong() % other.toLong())
    override fun compareTo(other: Int64): Int = this.toLong().compareTo(other.toLong())
    // @TODO /END SLOW (USE INTERMEDIARY LONGS)

    //val int52: Double get() = raw.bitsMantissaDouble
    inline val ulow: UInt get() = raw.lowBits.toUInt()
    inline val low: Int get() = raw.lowBits
    inline val high: Int get() = raw.highBits

    fun equalsSafe(other: Int64): Boolean = equals(this, other)

    fun toInt(): Int = if (isPositive) low and 0x7FFFFFFF else -(low and 0x7FFFFFFF)
    inline fun toLong(): Long = raw.reinterpretAsLong()

    override fun toString(): String = "${toLong()}"
}

fun Byte.toInt64(): Int64 = Int64(this.toInt())
fun Int.toInt64(): Int64 = Int64(this)
fun Long.toInt64(): Int64 = Int64(this)
fun Double.toInt64(): Int64 = Int64.fromInt52(this)
fun Number.toInt64(): Int64 = Int64(this.toLong())
