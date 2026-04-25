package korlibs.io.lang

expect val JSEval: IJSEval

interface IJSEval {
    /**
     * Determines if [JSEval] is available.
     * Available on the JS target.
     * Not available on the other targets for now.
     *
     * Later it might be implemented by using
     * external libraries and operating system libraries.
     */
    val available: Boolean

    /**
     * Returns a reference to JavaScript globalThis, window or self. Null if [available]=false
     */
    val globalThis: Any?

    /**
     * Executes a fragment of [code] in JavaScript when [available] is true.
     *
     * The [params] define the local variables that will be available and their values.
     * The code is executed inside a `function(key1, key2...) { }` block, thus, you can
     * add a `return` statement inside to return a value.
     *
     * If [available] is false, this function will throw an exception.
     */
    operator fun invoke(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any?

    suspend fun invokeSuspend(
        // language: javascript
        code: String,
        params: Map<String, Any?>,
    ): Any? = invoke(code, params)
}

operator fun IJSEval.invoke(
    // language: javascript
    code: String,
    vararg params: Pair<String, Any?>,
): Any? = invoke(code, params.toMap())

suspend fun IJSEval.invokeSuspend(
    // language: javascript
    code: String,
    vararg params: Pair<String, Any?>,
): Any? = invokeSuspend(code, params.toMap())

/**
 * Executes a javascript [expr] expressions and return the result.
 */
fun IJSEval.expr(
    // language: javascript
    expr: String,
    vararg params: Pair<String, Any?>,
): Any? = invoke("return $expr;", params.toMap())

/**
 * Executes a javascript [expr] expressions and return the result.
 */
suspend fun IJSEval.exprSuspend(
    // language: javascript
    expr: String,
    vararg params: Pair<String, Any?>,
): Any? = invokeSuspend("return $expr;", params.toMap())
