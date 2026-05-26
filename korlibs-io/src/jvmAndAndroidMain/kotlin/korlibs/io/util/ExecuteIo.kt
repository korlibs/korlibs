package korlibs.io.util

import korlibs.io.async.CIO
import korlibs.io.async.preferSyncIo
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> jvmExecuteIo(callback: suspend () -> T): T = when {
    coroutineContext.preferSyncIo -> callback()
    else -> withContext(Dispatchers.CIO) { callback() }
}
