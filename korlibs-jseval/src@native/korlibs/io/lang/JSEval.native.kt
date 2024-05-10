package korlibs.io.lang

actual object JSEval {
    actual const val available: Boolean = false
    actual val globalThis: Any? get() = null
    actual suspend operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        TODO("Not implemented on Native")
    }
}
