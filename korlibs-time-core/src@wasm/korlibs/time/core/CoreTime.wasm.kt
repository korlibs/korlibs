package korlibs.time.core

import korlibs.time.*
import kotlin.time.*

actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillis(): Long = Date_now().toLong()
    override fun nanoTime(): Long = (performance_now() * 1_000_000L).toLong()
    override fun localTimezoneOffset(time: Long): Duration = Date_localTimezoneOffsetMinutes(time.toDouble()).minutes
}

@JsFun("() => { return globalThis.performance.now(); }")
private external fun performance_now(): Double
@JsFun("() => { return Date.now(); }")
private external fun Date_now(): Double
@JsFun("(rtime) => { return -(new Date(rtime)).getTimezoneOffset(); }")
private external fun Date_localTimezoneOffsetMinutes(rtime: Double): Int
