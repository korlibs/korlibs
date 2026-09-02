@file:OptIn(ExperimentalForeignApi::class)

package korlibs.concurrent.thread

import kotlinx.cinterop.COpaquePointerVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toLong
import kotlinx.cinterop.value
import platform.posix.PTHREAD_EXPLICIT_SCHED
import platform.posix._opaque_pthread_t
import platform.posix.pthread_attr_destroy
import platform.posix.pthread_attr_init
import platform.posix.pthread_attr_setinheritsched
import platform.posix.pthread_attr_setschedparam
import platform.posix.pthread_attr_setschedpolicy
import platform.posix.pthread_attr_t
import platform.posix.pthread_cancel
import platform.posix.pthread_create
import platform.posix.pthread_getschedparam
import platform.posix.pthread_join
import platform.posix.pthread_self
import platform.posix.pthread_setschedparam
import platform.posix.pthread_tVar
import platform.posix.sched_get_priority_max
import platform.posix.sched_get_priority_min
import platform.posix.sched_param

val NativeNativeThread.pthread: CPointer<_opaque_pthread_t>? get() = toCPointer<_opaque_pthread_t>()

@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeNativeThread_getPriority(thread: NativeNativeThread): NativeThreadPriority {
    val value = memScoped {
        val param = alloc<sched_param>()
        val kind = alloc<IntVar>()
        pthread_getschedparam(thread.pthread, kind.ptr, param.ptr)
        param.sched_priority
    }
    return NativeThreadPriority.from(value, sched_get_priority_min(SCHED_POLICY), sched_get_priority_max(SCHED_POLICY))
}
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
internal actual fun NativeThreadThread_current(): NativeNativeThread = pthread_self().toLong()
@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeThreadThread_start(name: String?, isDaemon: Boolean, priority: NativeThreadPriority, code: () -> Unit): NativeNativeThread {
    return memScoped {
        val threadPtr = alloc<pthread_tVar>()
        val attr = alloc<pthread_attr_t>()
        val schedParam = alloc<sched_param>().also {
            //println("min=${sched_get_priority_min(SCHED_POLICY)}")
            //println("max=${sched_get_priority_max(SCHED_POLICY)}")
            it.sched_priority = priority.convert(sched_get_priority_min(SCHED_POLICY), sched_get_priority_max(SCHED_POLICY)).convert()
        }

        pthread_attr_init(attr.ptr)
        try {
            pthread_attr_setinheritsched(attr.ptr, PTHREAD_EXPLICIT_SCHED)
            pthread_attr_setschedpolicy(attr.ptr, SCHED_POLICY)
            pthread_attr_setschedparam(attr.ptr, schedParam.ptr)
            val err = pthread_create(threadPtr.ptr, attr.ptr, staticCFunction(::__threadStart), StableRef.create(ThreadInfo(name, code)).asCPointer())
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