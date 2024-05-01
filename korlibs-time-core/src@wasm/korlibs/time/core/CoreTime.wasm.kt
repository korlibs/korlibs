package korlibs.time.core

import korlibs.time.*
import kotlin.time.*

actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillisDouble(): Double = Date_now()
    override fun localTimezoneOffset(time: Long): Duration = Date_localTimezoneOffsetMinutes(time.toDouble()).minutes
}

@JsFun("() => { return Date.now(); }")
private external fun Date_now(): Double
@JsFun("(rtime) => { return -(new Date(rtime)).getTimezoneOffset(); }")
private external fun Date_localTimezoneOffsetMinutes(rtime: Double): Int
