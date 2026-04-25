package korlibs.memory

actual fun Double.Companion.fromLowHigh(low: Int, high: Int): Double = fromLowHighBitsSlow(low, high)
actual inline fun <T> Double.getLowHighBits(block: (low: Int, high: Int) -> T): T = getLowHighBitsSlow<T>(block)
actual fun Double.equalsRaw(other: Double): Boolean = equalsRawSlow(other)
actual val Double.lowBits: Int get() = lowSlow
actual val Double.highBits: Int get() = highSlow
