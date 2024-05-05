package korlibs.io.lang

import korlibs.concurrent.lock.*
import korlibs.concurrent.thread.*

actual abstract class NativeThreadLocal<T> {
    private val lock = Lock()
    private val perThread = LinkedHashMap<Long, T>()
    actual abstract fun initialValue(): T
    actual fun get(): T = lock { perThread.getOrPut(NativeThread.currentThreadId) { initialValue() } }
    actual fun set(value: T) { lock { perThread[NativeThread.currentThreadId] = value } }
}
