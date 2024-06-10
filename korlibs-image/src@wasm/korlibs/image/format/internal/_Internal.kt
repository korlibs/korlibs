package korlibs.image.format.internal

import org.khronos.webgl.*

internal fun Int32Array.toIntArray(): IntArray {
    //val tout = this.asDynamic()
    //if (tout is ByteArray) {
    //    return tout.unsafeCast<ByteArray>()
    //} else {
    val out = IntArray(this.length)
    for (n in out.indices) out[n] = this[n]
    return out
    //}
}

internal fun arraycopy(src: Int32Array, srcPos: Int, dst: IntArray, dstPos: Int, size: Int) {
    for (n in 0 until size) dst[dstPos + n] = src[srcPos + n]
}
internal fun arraycopy(src: IntArray, srcPos: Int, dst: Int32Array, dstPos: Int, size: Int) {
    for (n in 0 until size) dst[dstPos + n] = src[srcPos + n]
}
