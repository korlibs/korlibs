package korlibs.crypto

actual fun fillRandomBytes(array: ByteArray) {
    val size = array.size
    val jsBytes = _createRandomBytes(size)
    for (n in 0 until size) array[n] = _getByteAt(jsBytes, n)
}

actual fun seedExtraRandomBytes(array: ByteArray) {
    seedExtraRandomBytesDefault(array)
}

@JsFun("(size) => { const a = new Int8Array(size); globalThis.crypto.getRandomValues(a); return a; }")
private external fun _createRandomBytes(size: Int): JsAny

@JsFun("(array, index) => { return array[index]; }")
private external fun _getByteAt(array: JsAny, index: Int): Byte
