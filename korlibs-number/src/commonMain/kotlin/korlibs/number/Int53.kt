package korlibs.number

import korlibs.number.internal.*
import kotlin.contracts.*
import kotlin.math.*

inline class Int53Array(val raw: DoubleArray) : Iterable<Int53> {
    inline val indices: IntRange get() = raw.indices

    constructor(size: Int, value: Int53 = Int53.ZERO) : this(DoubleArray(size) { value.value })
    companion object {
        inline operator fun invoke(size: Int, gen: (Int) -> Int53): Int53Array = Int53Array(DoubleArray(size) { gen(it).value })
    }

    inline val size: Int get() = raw.size
    inline operator fun get(index: Int): Int53 = Int53(raw[index])
    inline operator fun set(index: Int, value: Int53) { raw[index] = value.value }
    override fun iterator(): Iterator<Int53> = object : Iterator<Int53> {
        var index = 0
        override fun hasNext(): Boolean = index < raw.size
        override fun next(): Int53 = this@Int53Array[index].also { index++ }
    }

    override fun toString(): String = "IntArray64($size)"
}

inline fun <T : Int53> int53ArrayOf(vararg values: T): Int53Array = Int53Array(values.size) { values[it] }
inline fun int53ArrayOf(vararg values: Int): Int53Array = Int53Array(values.size) { values[it].toInt53() }
inline fun int53ArrayOf(vararg values: Long): Int53Array = Int53Array(values.size) { values[it].toInt53() }

fun Int53Array.copyOf(newSize: Int = this.size): Int53Array = Int53Array(raw.copyOf(newSize))
fun Int53Array.copyOfRange(fromIndex: Int, toIndex: Int): Int53Array = Int53Array(raw.copyOfRange(fromIndex, toIndex))
public fun Int53Array.getOrNull(index: Int): Int53? = if (index in indices) get(index) else null
//@kotlin.internal.InlineOnly
@OptIn(ExperimentalContracts::class)
public inline fun Int53Array.getOrElse(index: Int, defaultValue: (Int) -> Int53): Int53 {
    contract { callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE) }
    return if (index in indices) get(index) else defaultValue(index)
}

infix fun Int53Array?.contentEquals(other: Int53Array?): Boolean = this?.raw.contentEquals(other?.raw)
fun Int53Array?.contentHashCode(): Int = this?.raw.contentHashCode()
fun Int53Array?.contentToString(): String = if (this == null) "null" else "[" + this.raw.joinToString(", ") { it.toString() } + "]"

/**
 * Represents and integral value of 52-bits + sign using a Double as internal representation.
 * Trying to avoid allocations on the JS target by not using [Long] when 53 bits is enough.
 */
//@Deprecated("")
public inline class Int53(public val value: Double) : Comparable<Int53> {
    public companion object {
        // Double.fromBits(0x000FFFFFFFFFFFFFL)
        //val MAX_VALUE = Int53(Double.fromBits(0x000FFFFFFFFFFFFFL)) // 2**52 - 1
        public val ZERO: Int53 = Int53(0.0)
        public val MAX_VALUE: Int53 = Int53(4503599627370495.0) // 2**52 - 1
        public val MIN_VALUE: Int53 = Int53(-4503599627370495.0) // -(2**52 - 1)
        private val MAX_UINT32 = 4294967295.0
        public fun fromDoubleClamped(value: Double): Int53 = when {
            value > MAX_VALUE.value -> MAX_VALUE
            value < MIN_VALUE.value -> MIN_VALUE
            else -> Int53(if (value < 0.0) ceil(value) else floor(value))
        }
        // @TODO: Do not use Long to do this
        public fun fromLowHigh(low: Int, high: Int): Int53 {
            return Int53(low.toUInt().toDouble() + (high.toUInt().toDouble() * pot(32)))

            //return (low.toLong() or ((high and 0x1FFFFF).signExtend(21).toLong() shl 32)).toInt53()

            //if ((high and 0x80000) != 0) {
            //    // Negative
            //    val uhigh = ((high).inv() and 0xFFFFF).toDouble()
            //    val ulow = low.inv().toUInt().toDouble()
            //    val svalue = -(ulow + (uhigh * 4294967296)) - 1
            //    return fromDoubleClamped(svalue)
            //} else {
            //    return fromDoubleClamped(low.toUInt().toDouble() + ((high and 0xFFFFF) * 4294967296))
            //}
        }

        @PublishedApi internal val POWS = DoubleArray(64) { 2.0.pow(it) }

        inline fun pot(index: Int): Double = POWS[index and 0x3F]
        //inline fun pot(index: Int): Double = 2.0.pow(index)
    }

    // @TODO: Do not use Long to do this
    //val high: Int get() = ((double.toRawBits() ushr 32) and 20.mask().toLong()).toInt()
    public val high: Int get() = ((long ushr 32) and 21.mask().toLong()).toInt()
    public val low: Int get() {
        return long.toInt()
        //return floor(value.absoluteValue % pot(32)).toInt()
        //var v = this
        //val llow = (v % 0x10000).toInt()
        //v /= 0x10000
        //val lhigh = (v % 0x10000).toInt()
        //return llow or (lhigh shl 16)
    }

    public operator fun unaryMinus(): Int53 = Int53(-value)
    public operator fun unaryPlus(): Int53 = Int53(+value)

    // @TODO: handle overflows
    public operator fun plus(other: Double): Int53 = fromDoubleClamped(this.value + other)
    public operator fun plus(other: Int): Int53 = this + other.toDouble()
    public operator fun plus(other: Int53): Int53 = this + other.toDouble()
    public operator fun plus(other: Float): Int53 = this + other.toDouble()

    public operator fun minus(other: Double): Int53 = fromDoubleClamped(this.value - other)
    public operator fun minus(other: Int): Int53 = this - other.toDouble()
    public operator fun minus(other: Int53): Int53 = this - other.toDouble()
    public operator fun minus(other: Float): Int53 = this - other.toDouble()

    public operator fun times(other: Double): Int53 = fromDoubleClamped(this.value * other)
    public operator fun times(other: Int): Int53 = this * other.toDouble()
    public operator fun times(other: Int53): Int53 = this * other.toDouble()
    public operator fun times(other: Float): Int53 = this * other.toDouble()

    public operator fun div(other: Double): Int53 = fromDoubleClamped(this.value / other)
    public operator fun div(other: Int): Int53 = this / other.toDouble()
    public operator fun div(other: Int53): Int53 = this / other.toDouble()
    public operator fun div(other: Float): Int53 = this / other.toDouble()

    public operator fun rem(other: Double): Int53 = fromDoubleClamped(this.value % other.toDouble())
    public operator fun rem(other: Int): Int53 = this % other.toDouble()
    public operator fun rem(other: Int53): Int53 = this % other.toDouble()
    public operator fun rem(other: Float): Int53 = this % other.toDouble()

    public infix fun and(other: Int53): Int53 = Int53.fromLowHigh(this.low and other.low, this.high and other.high)
    public infix fun or(other: Int53): Int53 = Int53.fromLowHigh(this.low or other.low, this.high or other.high)
    public infix fun xor(other: Int53): Int53 = Int53.fromLowHigh(this.low xor other.low, this.high xor other.high)
    public fun inv(): Int53 = Int53.fromLowHigh(this.low.inv(), this.high.inv())
    public infix fun shl(shift: Int): Int53 = floor(value * pot(shift)).toInt53()
    public infix fun ushr(shift: Int): Int53 = floor(value / pot(shift)).toInt53()

    public fun insert(value: Int, offset: Int, count: Int): Int53 {
        //val mask = count.mask().toInt53() shl offset
        //val ovalue = (value.toInt53() shl offset) and mask
        //return (this and mask.inv()) or ovalue
        return clear(offset, count).insertNoClear(value, offset, count)
    }

    public fun insert(value: Boolean, offset: Int): Int53 = insert(if (value) 1 else 0, offset, 1)

    //return this and (count.mask().toInt53() shl offset).inv()
    public fun clear(offset: Int, count: Int): Int53 {
        val offsetMul = pot(offset)
        return this - ((floor(value / offsetMul) % pot(count)) * offsetMul)
    }

    public fun insertNoClear(value: Int, offset: Int, count: Int): Int53 = Int53(this.value + ((value and count.mask()).toDouble() * pot(offset)))
    public fun insertNoClear(value: Boolean, offset: Int): Int53 = insertNoClear(if (value) 1 else 0, offset, 1)
    //return (value.toLong() ushr offset).toInt() and bits.mask()
    fun extract(offset: Int, bits: Int): Int = (floor(value / pot(offset)) % pot(bits)).toInt()
    //fun extract(offset: Int, bits: Int): Int = (value.toLong() ushr offset).toInt() and bits.mask()
    fun extract(offset: Int): Boolean = extract(offset, 1) != 0
    fun extractSigned(offset: Int, bits: Int): Int {
        val shift = (32 - bits)
        return (extract(offset, bits) shl shift) shr shift
    }

    public val int: Int get() = value.toInt()
    public val long: Long get() = value.toLong()
    public val double: Double get() = value

    public fun toInt(): Int = int
    public fun toLong(): Long = long
    public fun toDouble(): Double = double

    public fun compareTo(other: Double): Int = this.value.compareTo(other)
    public fun compareTo(other: Int): Int = this.compareTo(other.toDouble())
    override fun compareTo(other: Int53): Int = this.compareTo(other.toDouble())
    public fun compareTo(other: Float): Int = this.compareTo(other.toDouble())

    // @TODO: We are casting to Long that might allocate on JS
    override fun toString(): String = value.toLong().toString().removeSuffix(".0")
}

public inline fun String.toInt53(): Int53 = this.toDouble().toInt53()
public inline fun String.toInt53OrNull(): Int53? = this.toDoubleOrNull()?.toInt53()
public inline fun Int.toInt53(): Int53 = Int53.fromDoubleClamped(this.toDouble())
public inline fun Double.toInt53(): Int53 = Int53.fromDoubleClamped(this)
public inline fun Long.toInt53(): Int53 = Int53.fromDoubleClamped(this.toDouble())
public inline fun Number.toInt53(): Int53 = Int53.fromDoubleClamped(this.toDouble())
