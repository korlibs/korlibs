package korlibs.io.async

import kotlinx.coroutines.*
import kotlin.coroutines.*

public expect class AsyncEntryPointResult

expect fun asyncEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult
expect fun asyncTestEntryPoint(callback: suspend () -> Unit): AsyncEntryPointResult

@OptIn(ExperimentalStdlibApi::class)
internal fun runBlockingNoJs_transformContext(context: CoroutineContext): CoroutineContext =
    context.minusKey(CoroutineDispatcher.Key).minusKey(Job.Key)

expect fun <T> runBlockingNoJs(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
