package korlibs.io.lang

import korlibs.io.util.*
import korlibs.memory.ByteArrayBuilder
import org.khronos.webgl.*

external class TextDecoder(charset: String) : JsAny {
    val encoding: String
    fun decode(data: ArrayBufferView): String
}

external class TextEncoder(charset: String) : JsAny {
    val encoding: String
    fun encode(data: String): Uint8Array
}


actual val platformCharsetProvider: CharsetProvider = CharsetProvider { normalizedName, name ->
    for (n in listOf(name, normalizedName)) {
        try {
            val te = wrapWasmJsExceptions { TextEncoder(n) }
            val td = wrapWasmJsExceptions { TextDecoder(n) }
            return@CharsetProvider JsCharset(te, td)
            //} catch (e: dynamic) { // @TODO: Not working on WASM. Do we really have a Throwable from JS?
        } catch (e: Throwable) {
            continue
        }
    }
    return@CharsetProvider null
}

class JsCharset(val textEncoder: TextEncoder, val textDecoder: TextDecoder) : Charset(textDecoder.encoding) {
    override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
        if (textEncoder.encoding != textDecoder.encoding) unsupported("Unsupported encoding '${textDecoder.encoding}'")
        out.append(textEncoder.encode(src.substring(start, end)).toByteArray())
    }

    override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
        out.append(textDecoder.decode(src.toInt8Array().subarray(start, end)))
        // @TODO: This charset won't support partial characters.
        return end - start
    }

    override fun equals(other: Any?): Boolean = other is JsCharset && this.name == other.name
    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = "JsCharset($name)"
}

@JsFun("(block) => { try { return { result: block(), error: null }; } catch (e) { return { result: null, error: e }; } }")
private external fun <T : JsAny> runCatchingJsExceptions(block: () -> T): JsResult<T>

private fun <T : JsAny> wrapWasmJsExceptions(block: () -> T): T {
    val result = runCatchingJsExceptions { block() }
    if (result.error != null) throw Exception(result.error!!.message)
    return result.result!!
}

private external interface JsResult<T : JsAny> : JsAny {
    val result: T?
    val error: JsError?
}

@JsName("Error")
private external class JsError : JsAny {
    val message: String?
}

private fun ByteArray.toInt8Array(): Int8Array {
    //val tout = this.asDynamic()
    //if (tout is Int8Array) {
    //    return tout.unsafeCast<Int8Array>()
    //} else {
    val out = Int8Array(this.size)
    for (n in 0 until out.length) out[n] = this[n]
    return out
    //}
}

private fun ArrayBuffer.toByteArray(): ByteArray = Int8Array(this).toByteArray()
private fun Uint8Array.toByteArray(): ByteArray {
    return Int8Array(this.buffer).toByteArray()
}
private fun Int8Array.toByteArray(): ByteArray {
    //val tout = this.asDynamic()
    //if (tout is ByteArray) {
    //    return tout.unsafeCast<ByteArray>()
    //} else {
    val out = ByteArray(this.length)
    for (n in out.indices) out[n] = this[n]
    return out
    //}
}

