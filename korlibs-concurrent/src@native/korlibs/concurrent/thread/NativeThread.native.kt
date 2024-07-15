package korlibs.concurrent.thread

import korlibs.concurrent.lock.*
import korlibs.time.*
import kotlinx.cinterop.*
import platform.posix.*
import kotlin.native.runtime.*

actual typealias NativeNativeThread = Long

//private val SCHED_POLICY = SCHED_RR
internal val SCHED_POLICY = SCHED_OTHER

internal actual fun NativeNativeThread_getId(thread: NativeNativeThread): Long = thread
internal actual fun NativeNativeThread_getName(thread: NativeNativeThread): String? = threadInfosLock { threadInfos[thread]?.name }
internal actual fun NativeNativeThread_getIsDaemon(thread: NativeNativeThread): Boolean = true
internal actual val NativeThreadThread_isSupported: Boolean = true

class ThreadInfo(
    val name: String?,
    val code: () -> Unit
)

private val threadInfosLock = Lock()
private val threadInfos = LinkedHashMap<Long, ThreadInfo>()

@OptIn(ExperimentalForeignApi::class)
@PublishedApi
internal fun __threadStart(code: COpaquePointer?): COpaquePointer? {
    val threadId: Long = NativeThreadThread_current()
    val ref = code!!.asStableRef<ThreadInfo>()
    val ptr = ref.get()
    ref.dispose()
    threadInfosLock { threadInfos[threadId] = ptr }
    try {
        ptr.code()
    } finally {
        threadInfosLock { threadInfos.remove(threadId) }
    }
    return null
}

@OptIn(NativeRuntimeApi::class)
internal actual fun NativeThreadThread_gc(full: Boolean): Unit {
    GC.collect()
}
internal actual fun NativeThreadThread_sleep(time: FastDuration): Unit {
    usleep(time.fastNanoseconds.toLong().toUInt())
}
@PublishedApi internal actual inline fun NativeThreadThread_spinWhile(cond: () -> Boolean): Unit {
    while (cond()) {
        sched_yield()
    }
}
