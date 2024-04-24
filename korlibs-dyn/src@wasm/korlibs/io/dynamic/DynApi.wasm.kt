package korlibs.io.dynamic

@JsFun("(obj, key) => { return obj[key]; }")
private external fun JsAny_get(obj: JsAny, key: String): JsAny?

@JsFun("(obj, key, value) => { obj[key] = value; }")
private external fun JsAny_set(obj: JsAny, key: String, value: JsAny?)

@JsFun("(obj, key, args) => { obj[key].apply(obj, args); }")
private external fun JsAny_invoke(obj: JsAny, key: String, args: JsArray<JsAny?>)

@JsFun("() => { return ((typeof globalThis !== 'undefined') ? globalThis : ((typeof global !== 'undefined') ? global : self)); }")
private external fun getJsGlobalDynamic(): JsAny

private val jsGlobal: JsAny = getJsGlobalDynamic()

private fun <T : JsAny?> jsArrayOf(vararg values: T): JsArray<T> {
    val array = JsArray<T>()
    //array.length = values.size
    for (n in values.indices) array[n] = values[n]
    return array
}

internal actual object DynamicInternal : DynApi {
	override val global: Any get() = jsGlobal

    override fun get(instance: Any?, key: String): Any? {
        if (instance == null) return null
        return JsAny_get((instance as JsAny), key)
    }
    override fun set(instance: Any?, key: String, value: Any?) {
        if (instance == null) return
        return JsAny_set((instance as JsAny), key, value as? JsAny?)
    }
    override fun invoke(instance: Any?, key: String, args: Array<out Any?>): Any? =
        JsAny_invoke(instance as JsAny, key, jsArrayOf(*args.map { it as? JsAny? }.toTypedArray()))
}
