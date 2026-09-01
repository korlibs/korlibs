package korlibs.io.async

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect val Dispatchers.CIO: CoroutineDispatcher
val Dispatchers.ResourceDecoder: CoroutineDispatcher get() = CIO
expect val Dispatchers.ConcurrencyLevel: Int
internal expect fun Dispatchers._createFixedThreadDispatcher(name: String, threadCount: Int = 1): CoroutineDispatcher
