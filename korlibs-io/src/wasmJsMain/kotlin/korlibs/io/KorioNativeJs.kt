package korlibs.io

import korlibs.io.runtime.JsRuntimeBrowser
import korlibs.platform.Platform
import org.w3c.dom.Element
import org.w3c.dom.HTMLCollection
import org.w3c.dom.get

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
