package korlibs.io.lang

import javax.script.*

actual val JSEval = object : IJSEval {
    override val globalThis: Any? get() = engine?.eval("globalThis")
    override val available: Boolean get() = engine != null

    val availableEngines get() = ScriptEngineManager().engineFactories.map { it.names }

    val engine: ScriptEngine? by lazy {
        listOf("nashorn", "rhino", "JavaScript", "js", "ECMAScript", "ecmascript").firstNotNullOfOrNull {
            ScriptEngineManager().getEngineByName(it)
        }.also {
            //it?.context?.writer = OutputStreamWriter(System.out)
        }
    }

    override operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val engine = engine ?: error("Can't find JavaScript engine in $availableEngines")
        return engine.eval("(function() { $code })()", engine.createBindings().also { for ((k, v) in params) it[k] = v })
    }
}
