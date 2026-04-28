package korlibs.io

import korlibs.io.runtime.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.performance.*
import kotlin.collections.set

abstract external class GlobalScope : EventTarget, WindowOrWorkerGlobalScope, GlobalPerformance {
	fun postMessage(message: dynamic, targetOrigin: dynamic = definedExternally, transfer: dynamic = definedExternally)
	fun requestAnimationFrame(callback: (Double) -> Unit): Int
	fun cancelAnimationFrame(handle: Int): Unit
}

val jsGlobalDynamic: dynamic = js("((typeof globalThis !== 'undefined') ? globalThis : ((typeof global !== 'undefined') ? global : self))")
val jsGlobal: GlobalScope = jsGlobalDynamic

val isDenoJs by lazy { js("(typeof Deno === 'object' && Deno.statSync !== undefined)").unsafeCast<Boolean>() }
val isWeb by lazy { js("(typeof window === 'object')").unsafeCast<Boolean>() }
val isWorker by lazy { js("(typeof importScripts === 'function')").unsafeCast<Boolean>() }
val isNodeJs by lazy { js("((typeof process !== 'undefined') && process.release && (process.release.name.search(/node|io.js/) !== -1))").unsafeCast<Boolean>() }
val isShell get() = !isWeb && !isNodeJs && !isWorker

var _jsRuntime: JsRuntime? = null

val jsRuntime: JsRuntime get() {
	_jsRuntime = _jsRuntime ?: when {
        isDenoJs -> JsRuntimeDeno
        //isNodeJs -> JsRuntimeNode
        else -> JsRuntimeBrowser
    }
	return _jsRuntime!!
}

fun HTMLCollection.toList(): List<Element?> = (0 until length).map { this[it] }
fun <T : Element> HTMLCollection.toTypedList(): List<T> = (0 until length).map { this[it].unsafeCast<T>() }

private external class Date(time: Double)
