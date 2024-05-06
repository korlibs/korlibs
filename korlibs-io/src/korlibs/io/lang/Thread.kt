package korlibs.io.lang

import korlibs.concurrent.thread.*
import korlibs.time.*

@Deprecated("", ReplaceWith("NativeThread.currentThreadId", "korlibs.concurrent.thread.NativeThread"))
val currentThreadId: Long get() = korlibs.concurrent.thread.NativeThread.currentThreadId
@Deprecated("", ReplaceWith("NativeThread.currentThreadName", "korlibs.concurrent.thread.NativeThread"))
val currentThreadName: String? get() = korlibs.concurrent.thread.NativeThread.currentThreadName
@Deprecated("", ReplaceWith("NativeThread.sleep(time.milliseconds)", "korlibs.concurrent.thread.NativeThread", "korlibs.time.milliseconds"))
fun Thread_sleep(time: Long): Unit = korlibs.concurrent.thread.NativeThread.sleep(time.milliseconds)
//inline fun spinWhile(cond: () -> Boolean): Unit = NativeThread.spinWhile(cond)

@Deprecated("", ReplaceWith("NativeThread.sleepUntil(dateTime, exact)", "korlibs.concurrent.thread.NativeThread", "korlibs.datastructure.thread.sleepUntil"))
fun Thread_sleepUntil(dateTime: DateTime, exact: Boolean = true) {
    korlibs.concurrent.thread.NativeThread.sleepUntil(dateTime, exact)
}
