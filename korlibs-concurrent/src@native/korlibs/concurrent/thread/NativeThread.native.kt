package korlibs.concurrent.thread

import korlibs.concurrent.lock.*
import korlibs.time.*
import kotlinx.atomicfu.locks.*
import kotlinx.cinterop.*
import platform.posix.*
import kotlin.native.runtime.*

actual typealias NativeNativeThread = Long

//private val SCHED_POLICY = SCHED_RR
internal val SCHED_POLICY = SCHED_OTHER

internal actual fun NativeNativeThread_getId(thread: NativeNativeThread): Long = thread
internal actual fun NativeNativeThread_getName(thread: NativeNativeThread): String? = threadInfosLock.withLock { threadInfos[thread]?.name }
internal actual fun NativeNativeThread_getIsDaemon(thread: NativeNativeThread): Boolean = true
internal actual val NativeThreadThread_isSupported: Boolean = true

class ThreadInfo(
    val name: String?,
    val code: () -> Unit
)

private val threadInfosLock = SynchronizedObject()
private val threadInfos = LinkedHashMap<Long, ThreadInfo>()

@OptIn(ExperimentalForeignApi::class)
@PublishedApi
internal fun __threadStart(code: COpaquePointer?): COpaquePointer? {
    initRuntimeIfNeeded()
    val threadId: Long = NativeThreadThread_current()
    val ref = code!!.asStableRef<ThreadInfo>()
    val ptr = ref.get()
    ref.dispose()
    threadInfosLock.withLock { threadInfos[threadId] = ptr }
    try {
        ptr.code()
    } catch (e: Throwable) {
        e.printStackTrace()
    } finally {
        threadInfosLock.withLock { threadInfos.remove(threadId) }
    }
    return null
}

@OptIn(NativeRuntimeApi::class)
internal actual fun NativeThreadThread_gc(full: Boolean): Unit {
    GC.collect()
}
@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
internal actual fun NativeThreadThread_sleep(time: FastDuration): Unit {
    memScoped {
        val timespec = alloc<timespec>()
        val nanoseconds = time.fastNanoseconds.toLong()
        //val seconds = time.seconds
        //val nseconds = (seconds % 1.0) * 1_000_000_000L
        timespec.tv_sec = (nanoseconds / 1_000_000_000L).convert()
        timespec.tv_nsec = (nanoseconds % 1_000_000_000L).convert()
        nanosleep(timespec.ptr, null)
    }
    //usleep(time.fastMicroseconds.toLong().convert())
}
@PublishedApi internal actual inline fun NativeThreadThread_spinWhile(cond: () -> Boolean): Unit {
    while (cond()) {
        sched_yield()
    }
}
