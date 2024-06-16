@file:Suppress("PackageDirectoryMismatch")
package korlibs.concurrent.thread

import korlibs.io.concurrent.*
import korlibs.time.*
import kotlinx.coroutines.*
import kotlin.native.runtime.*

actual class NativeThread actual constructor(val code: (NativeThread) -> Unit) {
    actual var isDaemon: Boolean = false
    actual var userData: Any? = null

    actual var threadSuggestRunning: Boolean = true

    actual var priority: Int = 0
    actual var name: String? = null

    private var dispatcher: CoroutineDispatcher? = null

    actual fun start() {
        threadSuggestRunning = true
        dispatcher = Dispatchers.createSingleThreadedDispatcher("NativeThread")
        CoroutineScope(dispatcher!!).launch {
            try {
                code(this@NativeThread)
            } finally {
                dispatcher?.cancel()
                dispatcher = null
            }
        }
    }

    actual fun interrupt() {
        // No operation
        threadSuggestRunning = false
        dispatcher?.cancel()
        dispatcher = null
    }

    actual companion object {
        actual val isSupported: Boolean get() = true
        actual val currentThreadId: Long get() = korlibs.concurrent.thread.__currentThreadId
        actual val currentThreadName: String? get() = "Thread-$currentThreadId"

        @OptIn(NativeRuntimeApi::class)
        actual fun gc(full: Boolean) {
            GC.schedule()
        }

        actual fun sleep(time: FastDuration): Unit {
            //platform.posix.nanosleep()
            platform.posix.usleep(time.microseconds.toUInt())

        }
        actual inline fun spinWhile(cond: () -> Boolean): Unit {
            while (cond()) {
                // @TODO: try to improve performance like: Thread.onSpinWait() or SpinWait.SpinUntil
                Unit
            }
        }
    }
}

internal expect val __currentThreadId: Long
