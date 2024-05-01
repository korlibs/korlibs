package korlibs.crypto

import org.khronos.webgl.*

actual fun fillRandomBytes(array: ByteArray) {
    val temp = Int8Array(array.size)
    _fillRandomBytes(temp)
    for (n in 0 until array.size) array[n] = temp[n]
}

actual fun seedExtraRandomBytes(array: ByteArray) {
    seedExtraRandomBytesDefault(array)
}

@JsFun("(array) => { globalThis.crypto.getRandomValues(array) }")
private external fun _fillRandomBytes(array: Int8Array)
