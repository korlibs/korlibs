package korlibs.io.concurrent

import korlibs.io.async.*
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

@Deprecated("", ReplaceWith("Dispatchers.ConcurrencyLevel", "kotlinx.coroutines.Dispatchers", "korlibs.io.async.ConcurrencyLevel"))
val CONCURRENCY_COUNT: Int get() = Dispatchers.ConcurrencyLevel

fun Dispatchers.createFixedThreadDispatcher(name: String, threadCount: Int = 1): CoroutineDispatcher =
    _createFixedThreadDispatcher(name, threadCount)

fun Dispatchers.createSingleThreadedDispatcher(name: String): CoroutineDispatcher =
    _createFixedThreadDispatcher(name, 1)

@OptIn(ExperimentalStdlibApi::class)
fun Dispatchers.createRedirectedDispatcher(name: String, parent: CoroutineDispatcher): CoroutineDispatcher {
    return object : CoroutineDispatcher(), AutoCloseable {
        override fun close() = Unit
        override fun dispatch(context: CoroutineContext, block: Runnable) = parent.dispatch(context, block)
        @InternalCoroutinesApi
        override fun dispatchYield(context: CoroutineContext, block: Runnable) = parent.dispatchYield(context, block)
        override fun isDispatchNeeded(context: CoroutineContext): Boolean = parent.isDispatchNeeded(context)
        @ExperimentalCoroutinesApi
        override fun limitedParallelism(parallelism: Int): CoroutineDispatcher = this
        override fun toString(): String = "Dispatcher-$name"
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Suppress("OPT_IN_USAGE")
fun CoroutineDispatcher.close() {
    when (this) {
        is AutoCloseable -> this.close()
        is CloseableCoroutineDispatcher -> this.close()
    }
}
