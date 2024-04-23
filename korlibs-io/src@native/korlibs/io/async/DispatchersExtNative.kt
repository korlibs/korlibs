package korlibs.io.async

import kotlinx.coroutines.*

actual val Dispatchers.CIO: CoroutineDispatcher get() = Dispatchers.IO
//actual val Dispatchers.CIO: CoroutineDispatcher get() = Dispatchers.IO // @TODO: Enable this once bumped to kotlinx.coroutines 1.7.x
actual val Dispatchers.ResourceDecoder: CoroutineDispatcher get() = CIO
