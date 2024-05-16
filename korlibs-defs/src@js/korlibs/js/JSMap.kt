package korlibs.js

@JsName("WeakMap")
external class JSWeakMap {
    fun has(k: dynamic): Boolean
    fun set(k: dynamic, v: dynamic): Unit
    fun get(k: dynamic): dynamic
    fun delete(k: dynamic)
}

@JsName("Map")
external class JSMap { }
