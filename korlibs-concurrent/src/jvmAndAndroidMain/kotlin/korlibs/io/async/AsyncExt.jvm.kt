package korlibs.io.async

import java.util.concurrent.ExecutorService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking

fun <T> Deferred<T>.jvmSyncAwait(): T = runBlocking { await() }

operator fun ExecutorService.invoke(callback: () -> Unit) {
    this.execute(callback)
}
