package korlibs.concurrent.thread

import korlibs.concurrent.lock.*
import korlibs.time.*
import kotlinx.cinterop.*
import platform.posix.*
import kotlin.native.runtime.*

actual typealias NativeNativeThread = Long

val NativeNativeThread.pthread: ULong get() = this.toULong()

//private val SCHED_POLICY = SCHED_RR
private val SCHED_POLICY = SCHED_OTHER

internal actual fun NativeNativeThread_getId(thread: NativeNativeThread): Long = thread
@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeNativeThread_getPriority(thread: NativeNativeThread): NativeThreadPriority {
    val value = memScoped {
        val param = alloc<sched_param>()
        pthread_getschedparam(thread.pthread, null, param.ptr)
        param.reinterpret<IntVar>().value
    }
    return NativeThreadPriority.from(value, sched_get_priority_min(SCHED_POLICY), sched_get_priority_max(SCHED_POLICY))
}
internal actual fun NativeNativeThread_getName(thread: NativeNativeThread): String? = threadInfosLock { threadInfos[thread]?.name }
internal actual fun NativeNativeThread_getIsDaemon(thread: NativeNativeThread): Boolean = true
internal actual fun NativeNativeThread_interrupt(thread: NativeNativeThread): Unit {
    pthread_cancel(thread.pthread)
}
@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeNativeThread_join(thread: NativeNativeThread): Unit {
    memScoped {
        val result = alloc<COpaquePointerVar>()
        pthread_join(thread.pthread, result.ptr)
    }
}

internal actual val NativeThreadThread_isSupported: Boolean = true
internal actual fun NativeThreadThread_current(): NativeNativeThread = pthread_self().toLong()
@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeThreadThread_start(name: String?, isDaemon: Boolean, priority: NativeThreadPriority, code: () -> Unit): NativeNativeThread {
    return memScoped {
        val threadPtr = alloc<pthread_tVar>()
        val attr = alloc<pthread_attr_t>()
        val schedParam = alloc<sched_param>().also {
            //println("min=${sched_get_priority_min(SCHED_POLICY)}")
            //println("max=${sched_get_priority_max(SCHED_POLICY)}")

            // @TODO: Thread priority is not implemented in mingw
            it.reinterpret<IntVar>().value = priority.convert(sched_get_priority_min(SCHED_POLICY), sched_get_priority_max(SCHED_POLICY)).convert()
        }

        pthread_attr_init(attr.ptr)
        try {
            pthread_attr_setinheritsched(attr.ptr, PTHREAD_EXPLICIT_SCHED)
            pthread_attr_setschedpolicy(attr.ptr, SCHED_POLICY)
            pthread_attr_setschedparam(attr.ptr, schedParam.ptr)
            val err = pthread_create(threadPtr.ptr, attr.ptr, staticCFunction(::threadStart), StableRef.create(ThreadInfo(name, code)).asCPointer())
            pthread_setschedparam(threadPtr.value, SCHED_POLICY, schedParam.ptr);
            if (err != 0) {
                error("error creating thread $name")
            }
        } finally {
            pthread_attr_destroy(attr.ptr)
        }
        threadPtr.value.toLong()
    }
}

class ThreadInfo(
    val name: String?,
    val code: () -> Unit
)

private val threadInfosLock = Lock()
private val threadInfos = LinkedHashMap<Long, ThreadInfo>()

@OptIn(ExperimentalForeignApi::class)
private fun threadStart(code: COpaquePointer?): COpaquePointer? {
    val threadId = pthread_self().toLong()
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
