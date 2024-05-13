package korlibs.io.lang

import kotlinx.coroutines.*
import org.w3c.dom.*
import kotlin.js.*

@JsName("globalThis") private external val jsGlobalThis: WindowOrWorkerGlobalScope

actual val JSEval = object : IJSEval {
    override val available: Boolean = true
    override val globalThis: Any? get() = jsGlobalThis

    override operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val keys = params.keys.toList()
        val func = eval("(function(${keys.joinToString()}) { $code })")
        return func.apply(globalThis, keys.map { params[it] }.toTypedArray())
    }

    override suspend fun invokeSuspend(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val result = invoke(code, params)
        return when (result) {
            is Promise<*> -> result.await()
            else -> result
        }
    }
}
