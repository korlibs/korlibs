package korlibs.io.async

import kotlinx.coroutines.*

expect val Dispatchers.CIO: CoroutineDispatcher
val Dispatchers.ResourceDecoder: CoroutineDispatcher get() = CIO

