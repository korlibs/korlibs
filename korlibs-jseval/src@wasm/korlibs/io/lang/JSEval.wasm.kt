package korlibs.io.lang

import kotlinx.coroutines.*
import org.khronos.webgl.*
import kotlin.js.*

class ExternalJsException(val e: Any?) : Throwable("Error evaluating JS: $e")

actual val JSEval = object : IJSEval {
    override val available: Boolean = true
    override val globalThis: Any? get() = jsGlobal

    override operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val keys = params.keys.toList()
        val code = "(function(${keys.joinToString()}) { try { return (function() { $code })(); } catch (e) { return { ___ERROR___ : e }; } })"
        val func = eval(code)
        val global: JsAny = jsGlobal
        val params = keys.map { ensureJsParam(params[it]) }
        //println("CODE: $code")
        //println("PARAMS: $params")
        val args: JsArray<JsAny?> = jsArrayOf(*params.toTypedArray()).unsafeCast()
        //println("ARGS: $args")
        val result = func?.apply(global, args)
        val errorObj = result?.let { jsObjectGet(it, "___ERROR___".toJsString()) }
        if (errorObj != null) {
            throw ExternalJsException(errorObj)
        } else {
            return result
        }
    }

    override suspend fun invokeSuspend(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val result = invoke(code, params)
        return ensureWasmParam(when (result) {
            is Promise<*> -> result.await()
            else -> result
        })
    }

    private fun ensureWasmParam(value: Any?): Any? {
        return when (value) {
            is JsString -> value.toString()
            is Int8Array -> value.toByteArray()
            else -> value
        }
    }

    private fun ensureJsParam(value: Any?): JsAny? {
        return when (value) {
            null -> null
            is ByteArray -> value.toInt8Array()
            is String -> value.toJsString()
            //is JsAny -> value
            else -> value.toJsReference()
        }
    }
}

@JsName("Function")
external class JsFunction {
    fun apply(obj: JsAny, args: JsArray<JsAny?>): JsAny?
}

@JsFun("(code) => { return eval(code); }")
private external fun eval(str: String): JsFunction?

@JsFun("() => { return ((typeof globalThis !== 'undefined') ? globalThis : ((typeof global !== 'undefined') ? global : self)); }")
private external fun getJsGlobalDynamic(): JsAny

private val jsGlobal: JsAny = getJsGlobalDynamic()

private fun <T : JsAny?> jsArrayOf(vararg values: T): JsArray<T> {
    val array = JsArray<T>()
    //array.length = values.size
    for (n in values.indices) array[n] = values[n]
    return array
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

@JsFun("(obj, key) => { return obj ? obj[key] : null; }") @Suppress("UNUSED_PARAMETER")
private external fun jsObjectGet(obj: JsAny, key: JsAny?): JsAny?

@JsFun("(obj, key, value) => { if (obj) obj[key] = value }") @Suppress("UNUSED_PARAMETER")
private external fun jsObjectSet(obj: JsAny, key: JsAny?, value: JsAny?): JsAny?

@JsFun("(obj) => { return Object.keys(obj); }") @Suppress("UNUSED_PARAMETER")
private external fun jsObjectKeys(obj: JsAny?): JsArray<JsString>
