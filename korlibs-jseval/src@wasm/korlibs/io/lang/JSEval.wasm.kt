package korlibs.io.lang

actual object JSEval {
    actual const val available: Boolean = true
    actual val globalThis: Any? get() = jsGlobal

    actual suspend operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val keys = params.keys.toList()
        val func = eval("(function(${keys.joinToString()}) { $code })")
        val global: JsAny = jsGlobal
        val args: JsArray<JsAny?> = jsArrayOf(*keys.map { params[it] as JsAny }.toTypedArray()).unsafeCast()
        return func?.apply(global, args)
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
