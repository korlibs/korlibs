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

//fun jsNew(clazz: dynamic): dynamic = js("(new (clazz))()")
//fun jsNew(clazz: dynamic, a0: dynamic): dynamic = js("(new (clazz))(a0)")
//fun jsNew(clazz: dynamic, a0: dynamic, a1: dynamic): dynamic = js("(new (clazz))(a0, a1)")
//fun jsNew(clazz: dynamic, a0: dynamic, a1: dynamic, a2: dynamic): dynamic = js("(new (clazz))(a0, a1, a2)")
//fun jsEnsureNumber(v: dynamic): Number = js("(+v)")
//fun jsEnsureInt(v: dynamic): Int = js("(v|0)")
//fun jsEnsureString(v: dynamic): String = js("(String(v))")
fun jsObjectKeysArray(obj: JsAny?): Array<String> = (jsToArray(jsObjectKeys(obj)) as JsArray<JsString>).toList().map { it.toString() }.toTypedArray()
fun jsObjectToMap(obj: JsAny?): Map<String, JsAny?> = jsObjectKeysArray(obj).associate { it to obj!!.getAny(it.toJsString()) }
fun jsToArray(obj: JsAny?): Array<Any?> = Array<Any?>(obj!!.unsafeCast<JsArray<*>>().length) { obj.getAny(it) }
fun jsArray(vararg elements: JsAny?): Array<JsAny?> {
    val out = jsEmptyArray<JsAny?>()
    for (e in elements) out.push(e)
    return out.toList().toTypedArray()
}

//inline fun <reified T> jsToArrayT(obj: JsAny): Array<T> = Array<T>(obj.getAny("length")) { obj[it] }
fun jsObject(vararg pairs: Pair<String, Any?>): JsAny {
    val out = jsEmptyObj()
    for (pair in pairs) out.setAny(pair.first.toJsString(), pair.second as? JsAny?)
    return out
}

fun Map<String, Any?>.toJsObject() = jsObject(*this.entries.map { it.key to it.value }.toTypedArray())

fun jsToObjectMap(obj: JsAny?): Map<String, Any?>? {
    if (obj == null) return null
    val out = linkedMapOf<String, Any?>()
    val keys = jsObjectKeys(obj)
    for (n in 0 until keys.length) {
        val key = keys[n]
        out["$key"] = obj.getAny(key)
    }
    return out
}

fun <T, R : JsAny?> List<T>.mapToJsArray(key: (T) -> R): JsArray<R> {
    return jsArrayOf(*this.map { key(it) }.toTypedArray())
}

fun <T : JsAny?> JsArray<T>.toList(): List<T> {
    val out = ArrayList<T>(length)
    for (n in 0 until length) out.add(this[n]!!)
    return out
}

inline fun <reified T : JsAny?> JsArray<T>.toTypedArray(): Array<T> {
    val out = Array<T?>(length) { null }
    for (n in 0 until length) out[n] = (this[n]!!)
    return out as Array<T>
}

fun JsNumber.toLong(): Long = this.toDouble().toLong()

@JsFun("(obj, key) => { return obj[key]; }")
internal external fun JsAny_get(obj: JsAny, key: JsAny?): JsAny?

@JsFun("(obj, key) => { return obj[key]; }")
internal external fun JsAny_get(obj: JsAny, key: Int): JsAny?

@JsFun("(obj, key) => { return obj[key]; }")
internal external fun JsAny_get(obj: JsAny, key: String): JsAny?

@JsFun("(obj, key, value) => { obj[key] = value; }")
internal external fun JsAny_set(obj: JsAny, key: JsAny?, value: JsAny?)

@JsFun("(obj, value) => { obj.push(value); }")
internal external fun JsArray_push(obj: JsAny, value: JsAny?)

@JsFun("(obj, key) => { return obj[key] !== undefined; }")
internal external fun JsAny_has(obj: JsAny, key: JsAny?): Boolean

@JsFun("(obj, key, params) => { return obj[key].apply(obj, params); }")
internal external fun JsAny_invokeApply(obj: JsAny, key: JsAny?, params: JsArray<JsAny?>): JsAny?

//inline class JsDynamic(val value: JsAny?) {
class JsDynamic(val value: JsAny?) {
    inline fun <T : JsAny> unsafeCast(): T? = value?.unsafeCast<T>()

    override fun toString(): String = value.toString()
    fun toInt(default: Int = 0): Int = unsafeCast<JsNumber>()?.toInt() ?: default
    fun toFloat(default: Float = 0f): Float = unsafeCast<JsNumber>()?.toDouble()?.toFloat() ?: default
    fun toDouble(default: Double = 0.0): Double = unsafeCast<JsNumber>()?.toDouble() ?: default
    fun toLong(default: Long = 0L): Long = unsafeCast<JsNumber>()?.toLong() ?: default

    operator fun contains(key: Int): Boolean = this.value?.hasAny(key) == true
    operator fun contains(key: String): Boolean = this.value?.hasAny(key) == true
    operator fun contains(key: JsAny?): Boolean = this.value?.hasAny(key) == true
    operator fun contains(key: JsDynamic): Boolean = this.value?.hasAny(key.value) == true

    operator fun get(key: Int): JsDynamic = JsDynamic(this.value?.getAny(key))
    operator fun get(key: String): JsDynamic = JsDynamic(this.value?.getAny(key))
    operator fun get(key: JsAny?): JsDynamic = JsDynamic(this.value?.getAny(key))
    operator fun get(key: JsDynamic): JsDynamic = this[key.value]

    operator fun set(key: Int, value: JsAny?) { this.value?.setAny(key, value) }
    operator fun set(key: String, value: JsAny?) { this.value?.setAny(key, value) }
    operator fun set(key: JsAny?, value: JsAny?) { this.value?.setAny(key, value) }
    operator fun set(key: JsDynamic, value: JsAny?) { this.value?.setAny(key.value, value) }

    operator fun set(key: Int, value: JsDynamic) { this.value?.setAny(key, value.value) }
    operator fun set(key: String, value: JsDynamic) { this.value?.setAny(key, value.value) }
    operator fun set(key: JsAny?, value: JsDynamic) { this.value?.setAny(key, value.value) }
    operator fun set(key: JsDynamic, value: JsDynamic) { this.value?.setAny(key.value, value.value) }
}

val JsAny?.jsDyn: JsDynamic get() = JsDynamic(this)

fun JsAny.getAny(key: Int): JsAny? = JsAny_get(this, key)
fun JsAny.getAny(key: String): JsAny? = JsAny_get(this, key)
fun JsAny.getAny(key: JsAny?): JsAny? = JsAny_get(this, key)

fun JsAny.setAny(key: Int, value: JsAny?) = setAny(key.toJsNumber(), value)
fun JsAny.setAny(key: String, value: JsAny?) = setAny(key.toJsString(), value)
fun JsAny.setAny(key: JsAny?, value: JsAny?) = JsAny_set(this, key, value)

fun JsAny.hasAny(key: Int): Boolean = JsAny_has(this, key.toJsNumber())
// @TODO: Not working!
@Deprecated("Not working!")
fun JsAny.hasAny(key: String): Boolean = JsAny_has(this, key.toJsString())
fun JsAny.hasAny(key: JsString): Boolean = JsAny_has(this, key)
fun JsAny.hasAny(key: JsAny?): Boolean = JsAny_has(this, key)

fun JsAny.dynamicInvoke(key: JsString, params: JsArray<JsAny?>): JsAny? = JsAny_invokeApply(this, key, params)

@JsFun("() => { return {}; }")
external fun jsEmptyObj(): JsAny
@JsFun("() => { return []; }")
external fun <T : JsAny?> jsEmptyArray(): JsArray<T>

fun <T : JsAny?> JsArray<T>.push(value: T) { JsArray_push(this, value) }
