package korlibs.io.concurrent

import kotlinx.coroutines.*

actual val CONCURRENCY_COUNT: Int = 1

actual fun Dispatchers.createFixedThreadDispatcher(name: String, threadCount: Int): CoroutineDispatcher {
    return createRedirectedDispatcher(name, Dispatchers.Main)
}
