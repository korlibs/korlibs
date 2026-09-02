package korlibs.io.async

import korlibs.time.seconds
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

public expect class AsyncEntryPointResult

expect fun asyncEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult
expect fun asyncTestEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult

@OptIn(ExperimentalStdlibApi::class)
internal fun runBlockingNoJs_transformContext(context: CoroutineContext): CoroutineContext =
    context.minusKey(CoroutineDispatcher.Key).minusKey(Job.Key)

expect fun <T> runBlockingNoJs(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
fun CoroutineContext.onCancel(check: () -> Boolean = { true }, block: (cancelled: Boolean) -> Unit): AutoCloseable {
    var running = true
    CoroutineScope(this).launch {
        //suspendCancellableCoroutine<Unit> { c ->
        //    c.invokeOnCancellation {
        //        println("CANCELLED!")
        //        block(true)
        //        c.resume(Unit)
        //    }
        //}

        withContext(CoroutineName("onCancel"))  {
            try {
                while (running && check()) kotlinx.coroutines.delay(0.1.seconds)
            } catch (e: CancellationException) {
                if (running) block(true)
            } finally {
                if (running) block(false)
            }
        }
    }
    return AutoCloseable { running = false }
}

