package korlibs.wasm

import org.khronos.webgl.*

fun ArrayBuffer.toByteArray(): ByteArray = Int8Array(this).toByteArray()
fun Uint8Array.toByteArray(): ByteArray = Int8Array(this.buffer).toByteArray()
fun Int8Array.toByteArray(): ByteArray {
    //val tout = this.asDynamic()
    //if (tout is ByteArray) {
    //    return tout.unsafeCast<ByteArray>()
    //} else {
    val out = ByteArray(this.length)
    for (n in out.indices) out[n] = this[n]
    return out
    //}
}

fun ByteArray.toInt8Array(): Int8Array {
    //val tout = this.asDynamic()
    //if (tout is Int8Array) {
    //    return tout.unsafeCast<Int8Array>()
    //} else {
    val out = Int8Array(this.size)
    for (n in 0 until out.length) out[n] = this[n]
    return out
    //}
}
