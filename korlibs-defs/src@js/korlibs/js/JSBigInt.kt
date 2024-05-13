package korlibs.js

import org.khronos.webgl.*

@JsName("BigInt")
external interface JSBigInt

@JsName("BigInt")
external fun JSBigInt(value: dynamic): JSBigInt

external open class BigInt64Array(size: Int) : ArrayBufferView {
    override val buffer: ArrayBuffer
    override val byteOffset: Int
    override val byteLength: Int
}
fun bigInt64ArrayOf(vararg values: JSBigInt): BigInt64Array {
    return BigInt64Array(values.size).also { for (n in values.indices) it[n] = values[n] }
}
operator fun BigInt64Array.get(index: Int): JSBigInt = JSBigInt(asDynamic()[index])
operator fun BigInt64Array.set(index: Int, value: JSBigInt) { asDynamic()[index] = JSBigInt(value) }

fun JSBigInt.toLong(): Long {
    val bi64 = BigInt64Array(1)
    val i32 = Int32Array(bi64.buffer)
    val low = i32[0].toLong() and 0xFFFFFFFFL
    val high = i32[1].toLong() and 0xFFFFFFFFL
    return low or (high shl 32)
}
fun Long.toJsBigInt(): JSBigInt {
    val bi64 = BigInt64Array(1)
    val i32 = Int32Array(bi64.buffer)
    i32[0] = (this ushr 0).toInt()
    i32[1] = (this ushr 32).toInt()
    return bi64[0]
}
