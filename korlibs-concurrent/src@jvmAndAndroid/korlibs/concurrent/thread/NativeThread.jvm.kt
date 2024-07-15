package korlibs.concurrent.thread

import korlibs.time.*
import kotlin.time.Duration.Companion.seconds

private fun FastDuration.toMillisNanos(): Pair<Long, Int> {
    val nanoSeconds = (milliseconds * 1_000_000).toLong()
    val millis = (nanoSeconds / 1_000_000L)
    val nanos = (nanoSeconds % 1_000_000L).toInt()
    return millis to nanos
}

actual typealias NativeNativeThread = Thread

internal actual fun NativeNativeThread_getId(thread: NativeNativeThread): Long = thread.id
internal actual fun NativeNativeThread_getPriority(thread: NativeNativeThread): NativeThreadPriority = NativeThreadPriority.from(thread.priority, Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, Thread.NORM_PRIORITY)
internal actual fun NativeNativeThread_getName(thread: NativeNativeThread): String? = thread.name
internal actual fun NativeNativeThread_getIsDaemon(thread: NativeNativeThread): Boolean = thread.isDaemon
internal actual fun NativeNativeThread_interrupt(thread: NativeNativeThread) = thread.interrupt()
internal actual fun NativeNativeThread_join(thread: NativeNativeThread) = thread.join()

internal actual val NativeThreadThread_isSupported: Boolean = true
internal actual fun NativeThreadThread_current(): NativeNativeThread = Thread.currentThread()
internal actual fun NativeThreadThread_start(name: String?, isDaemon: Boolean, priority: NativeThreadPriority, code: () -> Unit): NativeNativeThread {
    return Thread(code).also { it.name = name ?: "unknown"; it.isDaemon = it.isDaemon; it.priority = priority.convert(Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, Thread.NORM_PRIORITY); it.start() }
}
internal actual fun NativeThreadThread_gc(full: Boolean) {
    System.gc()
}
internal actual fun NativeThreadThread_sleep(time: FastDuration) {
    val compensatedTime = time
    if (compensatedTime > 0.seconds) {
        val (millis, nanos) = compensatedTime.toMillisNanos()
        Thread.sleep(millis, nanos)
    }
}
private val java_lang_Thread = Class.forName("java.lang.Thread")
@PublishedApi internal val onSpinWait = runCatching { java_lang_Thread.getMethod("onSpinWait") }.getOrNull()

@PublishedApi internal actual inline fun NativeThreadThread_spinWhile(cond: () -> Boolean): Unit {
    while (cond()) { onSpinWait?.invoke(null) }
}
