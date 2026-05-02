package korlibs.io.lang

import korlibs.wasm.eval
import korlibs.wasm.jsArrayOf
import korlibs.wasm.jsGlobal
import korlibs.wasm.jsObjectGet
import kotlin.js.Promise
import kotlinx.coroutines.await
import org.khronos.webgl.Int8Array
import org.khronos.webgl.get
import org.khronos.webgl.set

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
            return ensureWasmParam(result)
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
        //println("value: value=${value!!::class}")
        return when (value) {
            is Int -> value.toDouble()
            is JsNumber -> value.toDouble()
            is JsString -> value.toString()
            is Int8Array -> int8ArrayToByteArray(value)
            else -> value
        }
    }

    private fun ensureJsParam(value: Any?): JsAny? {
        return when (value) {
            null -> null
            is Long -> value.toJsBigInt()
            is Number -> value.toDouble().toJsNumber()
            is ByteArray -> byteArrayToInt8Array(value)
            is String -> value.toJsString()
            //is JsAny -> value
            else -> value.toJsReference()
        }
    }

    private fun int8ArrayToByteArray(value: Int8Array): ByteArray {
        val out = ByteArray(value.length)
        for (n in 0 until value.length) out[n] = value[n]
        return out
    }

    private fun byteArrayToInt8Array(value: ByteArray): Int8Array {
        val out = Int8Array(value.size)
        for (n in value.indices) out[n] = value[n]
        return out
    }
}
