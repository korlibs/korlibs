package korlibs.io.async

import korlibs.io.concurrent.*
import kotlinx.coroutines.*

actual val Dispatchers.CIO: CoroutineDispatcher get() = Dispatchers.Unconfined

actual val Dispatchers.ConcurrencyLevel: Int get() = 1

actual fun Dispatchers._createFixedThreadDispatcher(name: String, threadCount: Int): CoroutineDispatcher =
    createRedirectedDispatcher(name, Dispatchers.Main)
