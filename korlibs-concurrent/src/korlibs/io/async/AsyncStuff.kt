package korlibs.io.async

import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

public expect class AsyncEntryPointResult

expect fun asyncEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult
expect fun asyncTestEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult

@OptIn(ExperimentalStdlibApi::class)
internal fun runBlockingNoJs_transformContext(context: CoroutineContext): CoroutineContext =
    context.minusKey(CoroutineDispatcher.Key).minusKey(Job.Key)

expect fun <T> runBlockingNoJs(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
fun CoroutineContext.onCancel(block: () -> Unit): AutoCloseable {
    var running = true
    CoroutineScope(this).launch {
        withContext(CoroutineName("onCancel"))  {
            try {
                while (running) kotlinx.coroutines.delay(0.1.seconds)
            } catch (e: CancellationException) {
                if (running) block()
            }
        }
    }
    return AutoCloseable { running = false }
}

