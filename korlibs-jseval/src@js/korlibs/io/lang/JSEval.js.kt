package korlibs.io.lang

actual object JSEval {
    actual const val available: Boolean = true
    actual val globalThis: Any? get() = js("(globalThis)")

    actual suspend operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val keys = params.keys.toList()
        val func = eval("(function(${keys.joinToString()}) { $code })")
        return func.apply(globalThis, keys.map { params[it] }.toTypedArray())
    }
}
