@file:OptIn(ExperimentalForeignApi::class)

package korlibs.concurrent.thread

import kotlinx.cinterop.*
import platform.windows.*

fun <T> NativeNativeThread.useHandle(block: (HANDLE?) -> T): T {
    val thread = OpenThread(((READ_CONTROL or SYNCHRONIZE or DELETE).convert()) , 0, this.convert())
    try {
        //println("OPEN THREAD: thread=$thread, id=$this")
        return block(thread)
    } finally {
        CloseHandle(thread)
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual fun NativeNativeThread_getPriority(thread: NativeNativeThread): NativeThreadPriority =
    thread.useHandle {
        NativeThreadPriority.from(GetThreadPriority(it), THREAD_PRIORITY_LOWEST, THREAD_PRIORITY_HIGHEST)
    }

internal actual fun NativeNativeThread_interrupt(thread: NativeNativeThread): Unit {
    thread.useHandle {
        TerminateThread(it, 0.convert())
    }
}
internal actual fun NativeNativeThread_join(thread: NativeNativeThread): Unit {
    thread.useHandle {
        WaitForSingleObject(it, INFINITE)
    }
}
internal actual fun NativeThreadThread_current(): NativeNativeThread =
    GetCurrentThreadId().toLong()

internal actual fun NativeThreadThread_start(name: String?, isDaemon: Boolean, priority: NativeThreadPriority, code: () -> Unit): NativeNativeThread {
    return memScoped {
        val attributes = alloc<SECURITY_ATTRIBUTES>()
        val threadId = alloc<DWORDVar>()

        attributes.lpSecurityDescriptor

        val thread = CreateThread(
            attributes.ptr,
            0.convert(),
            staticCFunction(::__win32_threadStart),
            StableRef.create(ThreadInfo(name, code)).asCPointer(),
            0.convert(),
            threadId.ptr
        )
        SetThreadPriority(thread, priority.convert(THREAD_PRIORITY_LOWEST, THREAD_PRIORITY_HIGHEST))
        threadId.value.toLong()
    }
}

fun __win32_threadStart(value: COpaquePointer?): DWORD {
    return __threadStart(value).toLong().convert()
}
