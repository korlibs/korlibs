package korlibs.io.lang

actual val JSEval = object : IJSEval {
    override val available: Boolean = false
    override val globalThis: Any? get() = null
    override operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        TODO("Not implemented on Native")
    }
}
