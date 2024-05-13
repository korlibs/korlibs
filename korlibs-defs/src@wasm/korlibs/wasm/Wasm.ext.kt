package korlibs.wasm

import org.w3c.fetch.*
import kotlin.js.JsAny
import kotlin.js.JsArray
import kotlin.js.JsName
import kotlin.js.JsString
import kotlin.js.get
import kotlin.js.set
import kotlin.js.toJsString

@JsFun("() => { return ((typeof globalThis !== 'undefined') ? globalThis : ((typeof global !== 'undefined') ? global : self)); }")
private external fun getJsGlobalDynamic(): JsAny

val jsGlobal: JsAny = getJsGlobalDynamic()

fun JsAny.toMap(): Map<String, JsAny?> = this.keys().associateWith { this[it] }
fun JsAny.keys(): List<String> = jsObjectKeys(this).toList().map { it.toString() }
operator fun JsAny.get(key: String): JsAny? = jsObjectGet(this, key.toJsString())
//operator fun JsAny.get(key: Int): JsAny? = jsObjectGet(this, key.toJsNumber())
operator fun JsAny.set(key: String, value: JsAny?) = jsObjectSet(this, key.toJsString(), value)
//operator fun JsAny.set(key: Int, value: JsAny?) = jsObjectSet(this, key.toJsNumber(), value)

fun <T : JsAny> JsArray<T>.toList(): List<T> = List<T>(this.length) { this[it]!! }

@JsFun("(obj, key) => { return obj ? obj[key] : null; }") @Suppress("UNUSED_PARAMETER")
external fun jsObjectGet(obj: JsAny, key: JsAny?): JsAny?

@JsFun("(obj, key, value) => { if (obj) obj[key] = value }") @Suppress("UNUSED_PARAMETER")
external fun jsObjectSet(obj: JsAny, key: JsAny?, value: JsAny?): JsAny?

@JsFun("(obj) => { return Object.keys(obj); }") @Suppress("UNUSED_PARAMETER")
external fun jsObjectKeys(obj: JsAny?): JsArray<JsString>

@JsFun("(obj) => { return Array.from(obj); }") @Suppress("UNUSED_PARAMETER")
external fun <T : JsAny> JSArray_from(obj: JsIterator<T>?): JsArray<T>

open external class HeadersExt : Headers {
    fun keys(): JsIterator<JsString>
}

external interface JsIterator<T : JsAny> : JsAny {
    fun next(): JsIteratorResult<T>
}

external interface JsIteratorResult<T : JsAny> : JsAny {
    val done: Boolean
    val value: T?
}


@JsName("Function")
external class JsFunction {
    fun apply(obj: JsAny, args: JsArray<JsAny?>): JsAny?
}

@JsFun("(code) => { return eval(code); }")
external fun eval(str: String): JsFunction?

fun <T : JsAny?> jsArrayOf(vararg values: T): JsArray<T> {
    val array = JsArray<T>()
    //array.length = values.size
    for (n in values.indices) array[n] = values[n]
    return array
}

