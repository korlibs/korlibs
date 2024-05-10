package korlibs.io.lang

import javax.script.*

actual object JSEval {
    actual val globalThis: Any? get() = invoke("globalThis")
    actual val available: Boolean get() = engine != null

    val engine: ScriptEngine? by lazy {
        listOf("nashorn", "rhino", "JavaScript", "js", "ECMAScript", "ecmascript").firstNotNullOfOrNull {
            ScriptEngineManager().getEngineByName(it)
        }.also {
            //it?.context?.writer = OutputStreamWriter(System.out)
        }
    }

    actual operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val engine = engine ?: error("Can't find JavaScript engine")
        return engine.eval("(function() { $code })()", engine.createBindings().also { for ((k, v) in params) it[k] = v })
    }
}
