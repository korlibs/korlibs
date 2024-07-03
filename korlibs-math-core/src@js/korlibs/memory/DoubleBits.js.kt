package korlibs.memory

import korlibs.platform.*
import org.khronos.webgl.*

@PublishedApi internal val buffer = ArrayBuffer(8)
@PublishedApi internal val f64 = Float64Array(buffer)
@PublishedApi internal val i32 = Int32Array(buffer)

//@PublishedApi internal val IDX_LOW = if (Platform.isLittleEndian) 0 else 1
//@PublishedApi internal val IDX_HIGH = if (Platform.isLittleEndian) 1 else 0
@PublishedApi internal const val IDX_LOW = 0
@PublishedApi internal const val IDX_HIGH = 1

actual fun Double.Companion.fromLowHigh(low: Int, high: Int): Double {
    i32[IDX_LOW] = low
    i32[IDX_HIGH] = high
    return f64[0]
}
actual inline fun <T> Double.getLowHighBits(block: (low: Int, high: Int) -> T): T {
    f64[0] = this
    return block(i32[IDX_LOW], i32[IDX_HIGH])
}
actual fun Double.equalsRaw(other: Double): Boolean {
    f64[0] = this
    val low = i32[IDX_LOW]
    val high = i32[IDX_HIGH]
    f64[0] = other
    return low == i32[IDX_LOW] && high == i32[IDX_HIGH]
}
actual val Double.lowBits: Int get() {
    f64[0] = this
    return i32[IDX_LOW]
}
actual val Double.highBits: Int get()  {
    f64[0] = this
    return i32[IDX_HIGH]
}

/*
@PublishedApi internal val dataTemp = DataView(ArrayBuffer(8))

actual fun Double.Companion.fromLowHigh(low: Int, high: Int): Double {
    dataTemp.setInt32(0, low, true)
    dataTemp.setInt32(4, high, true)
    return dataTemp.getFloat64(0, true)
}
actual inline fun <T> Double.getLowHighBits(block: (low: Int, high: Int) -> T): T {
    dataTemp.setFloat64(0, this, true)
    return block(dataTemp.getInt32(0, true), dataTemp.getInt32(4, true))
}
actual val Double.low: Int get() {
    dataTemp.setFloat64(0, this, true)
    return dataTemp.getInt32(0, true)
}
actual val Double.high: Int get()  {
    dataTemp.setFloat64(0, this, true)
    return dataTemp.getInt32(4, true)
}
*/