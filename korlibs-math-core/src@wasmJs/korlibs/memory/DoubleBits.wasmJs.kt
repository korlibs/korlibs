package korlibs.memory

actual fun Double.Companion.fromLowHigh(low: Int, high: Int): Double = fromLowHighBitsSlow(low, high)
actual inline fun <T> Double.getLowHighBits(block: (low: Int, high: Int) -> T): T = getLowHighBitsSlow<T>(block)
actual val Double.low: Int get() = lowSlow
actual val Double.high: Int get() = highSlow
