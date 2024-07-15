package korlibs.concurrent.thread

import korlibs.time.*
import kotlin.jvm.*
import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

inline class NativeThreadPriority(val ratio: Double) {
    companion object {
        val LOWEST = NativeThreadPriority(0.0)
        val LOW = NativeThreadPriority(0.25)
        val NORMAL = NativeThreadPriority(0.5)
        val HIGHER = NativeThreadPriority(0.75)
        val EVEN_HIGHER = NativeThreadPriority(0.9)
        val HIGHEST = NativeThreadPriority(1.0)

        fun from(value: Int, min: Int, max: Int, norm: Int = (max + min) / 2): NativeThreadPriority {
            if (value == norm) return NORMAL
            return NativeThreadPriority((value - min).toDouble() / (max - min))
        }
    }
    fun convert(min: Int, max: Int, norm: Int = (max + min) / 2): Int {
        if (this == NORMAL) return norm
        return (min + (max - min) * ratio).toInt()
    }
}

expect class NativeNativeThread

internal expect fun NativeNativeThread_getId(thread: NativeNativeThread): Long
internal expect fun NativeNativeThread_getPriority(thread: NativeNativeThread): NativeThreadPriority
internal expect fun NativeNativeThread_getName(thread: NativeNativeThread): String?
internal expect fun NativeNativeThread_getIsDaemon(thread: NativeNativeThread): Boolean
internal expect fun NativeNativeThread_interrupt(thread: NativeNativeThread)
internal expect fun NativeNativeThread_join(thread: NativeNativeThread)

internal expect val NativeThreadThread_isSupported: Boolean
internal expect fun NativeThreadThread_current(): NativeNativeThread
internal expect fun NativeThreadThread_start(name: String?, isDaemon: Boolean, priority: NativeThreadPriority, code: () -> Unit): NativeNativeThread
internal expect fun NativeThreadThread_gc(full: Boolean)
internal expect fun NativeThreadThread_sleep(time: FastDuration)
@PublishedApi internal expect inline fun NativeThreadThread_spinWhile(cond: () -> Boolean): Unit


@JvmInline
// @TODO: Mark this as experimental or something so people know this is not fully supported in all the targets.
// @TODO: isSupported is required to be used.
value class NativeThread(val native: NativeNativeThread) {
    val id: Long get() = NativeNativeThread_getId(native)
    val priority: NativeThreadPriority get() = NativeNativeThread_getPriority(native)
    val name: String? get() = NativeNativeThread_getName(native)
    val isDaemon: Boolean get() = NativeNativeThread_getIsDaemon(native)
    fun interrupt() = NativeNativeThread_interrupt(native)
    fun join() = NativeNativeThread_join(native)

    companion object {
        val isSupported: Boolean get() = NativeThreadThread_isSupported
        @Deprecated("", ReplaceWith("current.id", "korlibs.concurrent.thread.NativeNative.Companion.current"))
        val currentThreadId: Long get() = current.id
        @Deprecated("", ReplaceWith("current.name", "korlibs.concurrent.thread.NativeNative.Companion.current"))
        val currentThreadName: String? get() = current.name
        val current: NativeThread get() = NativeThread(NativeThreadThread_current())

        fun start(name: String? = null, isDaemon: Boolean = false, priority: NativeThreadPriority = NativeThreadPriority.NORMAL, code: () -> Unit): NativeThread {
            return NativeThread(NativeThreadThread_start(name, isDaemon, priority, code))
        }
        fun gc(full: Boolean): Unit = NativeThreadThread_gc(full)
        fun sleep(time: FastDuration): Unit = NativeThreadThread_sleep(time)
        inline fun spinWhile(cond: () -> Boolean): Unit = NativeThreadThread_spinWhile(cond)
    }
}

fun NativeThread.Companion.sleep(time: Duration): Unit = NativeThread.sleep(time.fast)
fun NativeThread.Companion.sleepUntil(date: DateTime, exact: Boolean = true) {
    NativeThread.sleep(date - DateTime.now(), exact)
}

public fun nativeThread(
    isDaemon: Boolean = false,
    name: String? = null,
    priority: NativeThreadPriority = NativeThreadPriority.NORMAL,
    block: () -> Unit
): NativeThread {
    return NativeThread.start(name, isDaemon, priority, block)
}

fun NativeThread.Companion.sleep(time: Duration, exact: Boolean) = NativeThread.sleep(time.fast, exact)

fun NativeThread.Companion.sleep(time: FastDuration, exact: Boolean) {
    if (exact) sleepExact(time) else NativeThread.sleep(time)
}

// https://stackoverflow.com/questions/13397571/precise-thread-sleep-needed-max-1ms-error#:~:text=Scheduling%20Fundamentals
// https://www.softprayog.in/tutorials/alarm-sleep-and-high-resolution-timers
fun NativeThread.Companion.sleepExact(time: Duration) = sleepExact(time.fast)
fun NativeThread.Companion.sleepExact(time: FastDuration) {
    val start = TimeSource.Monotonic.markNow()
    //val imprecision = 10.milliseconds
    //val imprecision = 1.milliseconds
    val imprecision = 4.milliseconds
    val javaSleep = time - imprecision
    if (javaSleep >= 0.seconds) {
        NativeThread.sleep(javaSleep)
    }
    NativeThread.spinWhile { start.elapsedNow() < time }
}

//fun NativeThread.Companion.sleepUntil(date: DateTime, exact: Boolean = true) {
//    sleep(date - DateTime.now(), exact)
//}

inline fun NativeThread.Companion.sleepWhile(cond: () -> Boolean) {
    while (cond()) {
        NativeThread.sleep(1.milliseconds)
    }
}
