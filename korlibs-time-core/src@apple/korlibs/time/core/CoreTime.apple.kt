package korlibs.time.core

import korlibs.time.*
import korlibs.time.darwin.*
import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.posix.*
import platform.Foundation.*
import kotlin.time.*

@OptIn(UnsafeNumber::class, kotlinx.cinterop.ExperimentalForeignApi::class)
actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillisDouble(): Double = memScoped {
        val timeVal = alloc<timeval>()
        gettimeofday(timeVal.ptr, null)
        val sec = timeVal.tv_sec
        val usec = timeVal.tv_usec
        ((sec * 1_000L) + (usec / 1_000L)).toLong()
    }.toDouble()

    override fun localTimezoneOffset(time: Long): Duration = autoreleasepool {
        CFAbsoluteTimeGetCurrent()
        return getLocalTimezoneOffsetDarwin(CFTimeZoneCopySystem(), DateTime(time))
    }

    override fun unaccurateSleep(duration: Duration) {
        val micros = duration.inWholeMicroseconds
        val s = micros / 1_000_000
        val u = micros % 1_000_000
        if (s > 0) platform.posix.sleep(s.convert())
        if (u > 0) platform.posix.usleep(u.convert())
    }

    override fun unaccurateYield() {
        platform.posix.sched_yield()
    }
}
