package korlibs.concurrent.thread

import korlibs.time.*
import kotlin.time.*

actual typealias NativeNativeThread = Long

internal actual fun NativeNativeThread_getId(thread: NativeNativeThread): Long = 0L
internal actual fun NativeNativeThread_getPriority(thread: NativeNativeThread): NativeThreadPriority = NativeThreadPriority.NORMAL
internal actual fun NativeNativeThread_getName(thread: NativeNativeThread): String? = "main"
internal actual fun NativeNativeThread_getIsDaemon(thread: NativeNativeThread): Boolean = true
internal actual fun NativeNativeThread_interrupt(thread: NativeNativeThread): Unit = TODO()
internal actual fun NativeNativeThread_join(thread: NativeNativeThread): Unit = TODO()

internal actual val NativeThreadThread_isSupported: Boolean = false
internal actual fun NativeThreadThread_current(): NativeNativeThread = 0L
internal actual fun NativeThreadThread_start(name: String?, isDaemon: Boolean, priority: NativeThreadPriority, code: () -> Unit): NativeNativeThread = TODO()
internal actual fun NativeThreadThread_gc(full: Boolean): Unit = Unit
internal actual fun NativeThreadThread_sleep(time: FastDuration): Unit {
    warnSleep
    val start = TimeSource.Monotonic.markNow()
    NativeThreadThread_spinWhile { start.elapsedNow() < time }
}
@PublishedApi internal actual inline fun NativeThreadThread_spinWhile(cond: () -> Boolean): Unit {
    while (cond()) {
        // @TODO: try to improve performance like: Thread.onSpinWait() or SpinWait.SpinUntil
        Unit
    }
}

private val warnSleep by lazy {
    println("!!! Sync sleeping on JS")
}
