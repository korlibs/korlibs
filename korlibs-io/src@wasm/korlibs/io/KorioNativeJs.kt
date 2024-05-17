package korlibs.io

import korlibs.io.runtime.*
import korlibs.platform.*
import org.w3c.dom.*

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
