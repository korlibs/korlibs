package korlibs.js

import kotlin.js.*

@JsName("Array")
external class JSArray<T> {
    var length: Int
    //@nativeGetter operator fun get(index: Int): T = definedExternally
    //@nativeSetter operator fun set(index: Int, value: T): T = definedExternally
    fun concat(vararg arrays: JSArray<T>): JSArray<T>
    fun indexOf(e: T): Int
    fun lastIndexOf(e: T): Int
    fun splice(start: Int, deleteCount: Int, vararg items: T): JSArray<T>
    fun unshift(vararg items: T)
    fun push(vararg items: T)
    fun shift(): T
    fun pop(): T
    companion object {
        fun from(value: dynamic): Array<dynamic>
        fun <T> from(value: JSIterable<T>): Array<T>
    }
}

inline operator fun <T> JSArray<T>.get(index: Int): T = asDynamic()[index]
inline operator fun <T> JSArray<T>.set(index: Int, value: T): Unit { asDynamic()[index] = value }
fun <T> JSArray.Companion.createEmpty(): JSArray<T> = js("([])").unsafeCast<JSArray<T>>()

val Symbol_asyncIterator get() = Symbol.asyncIterator

external interface JSAsyncIterable<T> {

}

external interface JSIterable<T> {
    fun next(): JSIterableResult<T>
}

external interface JSIterableResult<T> {
    val done: Boolean
    val value: T?
}

fun jsEmptyArray(): dynamic = JSArray<Any>()

fun <T> JSIterable<T>.toArray(): Array<T> = JSArray.from(this)
fun <T> JSIterable<T>.toList(): List<T> = JSArray.from(this).toList()

inline fun <reified T> jsToArrayT(obj: dynamic): Array<T> = Array<T>(obj.length) { obj[it] }

fun jsToArray(obj: dynamic): Array<Any?> = Array<Any?>(obj.length) { obj[it] }
fun jsArray(vararg elements: dynamic): Array<dynamic> {
    val out = jsEmptyArray()
    for (e in elements) out.push(e)
    return out
}
