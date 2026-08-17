package korlibs.io.lang

import korlibs.concurrent.lock.Lock
import korlibs.concurrent.thread.NativeThread

actual abstract class NativeThreadLocal<T> {
    private val lock = Lock()
    private val perThread = LinkedHashMap<Long, T>()
    actual abstract fun initialValue(): T
    actual fun get(): T = lock { perThread.getOrPut(NativeThread.current.id) { initialValue() } }
    actual fun set(value: T) { lock { perThread[NativeThread.current.id] = value } }
}
