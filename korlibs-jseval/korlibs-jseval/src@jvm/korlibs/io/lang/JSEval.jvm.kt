package korlibs.io.lang

import javax.script.*

actual val JSEval = object : IJSEval {
    override val globalThis: Any? get() = engine?.eval("globalThis")
    override val available: Boolean by lazy {
        (engine != null).also {
            //if (!it) println("JSEval: availableEngines=$availableEngines")
        }
    }

    val availableEngines get() = ScriptEngineManager().engineFactories.map { it.names }

    val engine: ScriptEngine? by lazy {
        listOf("nashorn", "rhino", "JavaScript", "js", "ECMAScript", "ecmascript").firstNotNullOfOrNull {
            ScriptEngineManager().getEngineByName(it)
        }.also {
            it?.context?.setAttribute(ScriptEngine.LANGUAGE_VERSION, 200, ScriptContext.GLOBAL_SCOPE)
            //it?.context?.setAttribute("js", it, ScriptContext.ENGINE_SCOPE)
            //it?.context?.writer = OutputStreamWriter(System.out)
        }
    }

    override operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? {
        val engine = engine ?: error("Can't find JavaScript engine in $availableEngines")
        val result = engine.eval("(function() { $code })()", engine.createBindings().also { for ((k, v) in params) it[k] = v })
        return when (result) {
            is Number -> result.toDouble()
            else -> result
        }
    }
}
