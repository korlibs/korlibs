package korlibs.io.async

import kotlinx.coroutines.*
import kotlin.coroutines.*

@Suppress("ACTUAL_WITHOUT_EXPECT", "ACTUAL_TYPE_ALIAS_TO_CLASS_WITH_DECLARATION_SITE_VARIANCE")
@JsName("Promise")
actual external class AsyncEntryPointResult(func: (resolve: (JsAny?) -> Unit, reject: (JsAny?) -> Unit) -> Unit)

actual fun asyncEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult {
    return AsyncEntryPointResult { resolve, reject ->
        CoroutineScope(EmptyCoroutineContext).launch(start = CoroutineStart.UNDISPATCHED) {
            try {
                callback()
                resolve(null)
            } catch (e: Throwable) {
                reject(e.toJsReference())
            }
        }
    }
}
actual fun asyncTestEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult = asyncEntryPoint(callback)

actual fun <T> runBlockingNoJs(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T {
    //kotlinx.coroutines.runBlocking(context) { block() }
    throw UnsupportedOperationException("Calling runBlockingNoJs on JavaScript")
}

/*
actual fun asyncEntryPoint(callback: suspend () -> Unit): dynamic = kotlin.js.Promise<dynamic> { resolve, reject ->
	callback.startCoroutine(object : Continuation<Unit> {
		override val context: CoroutineContext = Dispatchers.Default
		override fun resumeWith(result: Result<Unit>) {
			val exception = result.exceptionOrNull()
			if (exception != null) {
                println("WARNING:: EntryPoint exception")
				reject(exception)
			} else {
				//resolve(undefined)
				resolve(Unit)
			}
		}
	})
}
*/
