package korlibs.memory

expect fun Double.Companion.fromLowHigh(low: Int, high: Int): Double
expect inline fun <T> Double.getLowHighBits(block: (low: Int, high: Int) -> T): T
expect val Double.low: Int
expect val Double.high: Int

@PublishedApi internal fun Double.Companion.fromLowHighBitsSlow(low: Int, high: Int): Double = Double.fromBits(Long.fromLowHigh(low, high))
@PublishedApi internal inline fun <T> Double.getLowHighBitsSlow(block: (low: Int, high: Int) -> T): T = block(lowSlow, highSlow)
@PublishedApi internal val Double.lowSlow: Int get() = this.reinterpretAsLong().low
@PublishedApi internal val Double.highSlow: Int get() = this.reinterpretAsLong().high
