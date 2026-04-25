package korlibs.memory

// S | EEEEEEEEEEE | FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
// S=1
// E=11
// F=52

fun Double.toStringInfo() = buildString(128) {
    append(this@toStringInfo)
    append(" = Double.fromParts(")
    append("sign=")
    append(this@toStringInfo.bitsSign)
    append(", exponent=0b")
    append(this@toStringInfo.bitsExponent.toString(2).padStart(11, '0'))
    append(", mantissa=0b")
    //append(this@toStringInfo.bitsMantissaLong.toString(2).padStart(52, '0'))
    append(this@toStringInfo.bitsMantissaHigh.toString(2).padStart(20, '0'))
    append(this@toStringInfo.bitsMantissaLow.toString(2).padStart(32, '0'))
    append(")")
}

private const val TWO_POW_32_DOUBLE = 4294967296.0
val Double.Companion.TWO_POW_32 get() = TWO_POW_32_DOUBLE

fun Double.Companion.fromParts(sign: Int, exponent: Int, mantissa: Double): Double = fromParts(sign, exponent, (mantissa % TWO_POW_32_DOUBLE).toInt(), (mantissa / TWO_POW_32_DOUBLE).toInt())
fun Double.Companion.fromParts(sign: Int, exponent: Int, mantissa: Long): Double = fromParts(sign, exponent, mantissa.low, mantissa.high)
fun Double.Companion.fromParts(sign: Int, exponent: Int, mantissaLow: Int, mantissaHigh: Int): Double = fromLowHigh(mantissaLow, mantissaHigh.insert12(exponent, 20).insert1(sign, 31))

expect fun Double.Companion.fromLowHigh(low: Int, high: Int): Double
expect inline fun <T> Double.getLowHighBits(block: (low: Int, high: Int) -> T): T
/** Bit-wise equals without considering NaNs */
expect fun Double.equalsRaw(other: Double): Boolean
expect val Double.lowBits: Int
expect val Double.highBits: Int

val Double.bitsSign: Int get() = highBits.extract1(31)
val Double.bitsExponent: Int get() = highBits.extract11(20)
val Double.bitsMantissaHigh: Int get() = highBits.extract20(0)
val Double.bitsMantissaLow: Int get() = lowBits
val Double.bitsMantissaDouble: Double get() = bitsMantissaLow.toDouble() + bitsMantissaHigh.toDouble() * TWO_POW_32_DOUBLE
val Double.bitsMantissaLong: Long get() = Long.fromLowHigh(bitsMantissaLow, bitsMantissaHigh)

@PublishedApi internal fun Double.Companion.fromLowHighBitsSlow(low: Int, high: Int): Double = Double.fromBits(Long.fromLowHigh(low, high))
@PublishedApi internal inline fun <T> Double.getLowHighBitsSlow(block: (low: Int, high: Int) -> T): T = block(lowSlow, highSlow)
@PublishedApi internal inline fun Double.equalsRawSlow(other: Double): Boolean = this.reinterpretAsLong().equals(other.reinterpretAsLong())
@PublishedApi internal val Double.lowSlow: Int get() = this.reinterpretAsLong().low
@PublishedApi internal val Double.highSlow: Int get() = this.reinterpretAsLong().high
