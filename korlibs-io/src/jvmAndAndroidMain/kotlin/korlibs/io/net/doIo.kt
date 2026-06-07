package korlibs.io.net

import korlibs.io.async.CIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal suspend fun <T> doIo(block: suspend CoroutineScope.() -> T): T = withContext(Dispatchers.CIO, block)
