package korlibs.io

import korlibs.io.runtime.*
import korlibs.io.wasm.*
import korlibs.platform.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.performance.*
import kotlin.collections.set

abstract external class GlobalScope : EventTarget, WindowOrWorkerGlobalScope, GlobalPerformance, JsAny {
	fun postMessage(message: JsAny, targetOrigin: JsAny = definedExternally, transfer: JsAny = definedExternally)
	fun requestAnimationFrame(callback: (Double) -> Unit): Int
	fun cancelAnimationFrame(handle: Int)
}

@JsFun("() => { return ((typeof globalThis !== 'undefined') ? globalThis : ((typeof global !== 'undefined') ? global : self)); }")
external fun getJsGlobalDynamic(): GlobalScope

//val jsGlobalDynamic: dynamic = getJsGlobalDynamic()
val jsGlobal: GlobalScope = getJsGlobalDynamic()

val isDenoJs get() = Platform.isJsDenoJs
val isWeb get() = Platform.isJsBrowser
val isWorker get() = Platform.isJsWorker
val isNodeJs get() = Platform.isJsNodeJs
val isShell get() = !isWeb && !isNodeJs && !isWorker

val jsRuntime by lazy {
    when {
        //isDenoJs -> JsRuntimeDeno
        //isNodeJs -> JsRuntimeNode
        else -> JsRuntimeBrowser
    }
}

fun HTMLCollection.toList(): List<Element?> = (0 until length).map { this[it] }
fun <T : Element> HTMLCollection.toTypedList(): List<T> = (0 until length).map { this[it]!!.unsafeCast<T>() }

private external class Date(time: Double)
