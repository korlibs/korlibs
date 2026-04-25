package korlibs.io.async

import kotlinx.coroutines.*

actual val Dispatchers.CIO: CoroutineDispatcher get() = Dispatchers.IO

@OptIn(ExperimentalCoroutinesApi::class)
actual fun Dispatchers._createFixedThreadDispatcher(name: String, threadCount: Int): CoroutineDispatcher {
    //println("Dispatchers.createSingleThreadedDispatcher['$name'] : Platform.hasMultithreadedSharedHeap=${Platform.hasMultithreadedSharedHeap}")
    return newFixedThreadPoolContext(threadCount, name)
}
