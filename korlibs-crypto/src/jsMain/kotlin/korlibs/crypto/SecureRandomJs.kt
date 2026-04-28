package korlibs.crypto

@JsName("crypto")
private external object crypto {
    fun getRandomValues(v: ByteArray)
}

actual fun fillRandomBytes(array: ByteArray) {
    // https://developer.mozilla.org/en-US/docs/Web/API/Crypto/getRandomValues
    crypto.getRandomValues(array)
}

actual fun seedExtraRandomBytes(array: ByteArray) {
    seedExtraRandomBytesDefault(array)
}
