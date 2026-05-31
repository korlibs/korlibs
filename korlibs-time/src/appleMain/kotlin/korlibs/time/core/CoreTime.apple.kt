package korlibs.time.core

import korlibs.time.DateTime
import korlibs.time.darwin.getLocalTimezoneOffsetDarwin
import kotlin.time.Duration
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.alloc
import kotlinx.cinterop.autoreleasepool
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.CoreFoundation.CFAbsoluteTimeGetCurrent
import platform.CoreFoundation.CFTimeZoneCopySystem
import platform.posix.gettimeofday
import platform.posix.timeval

@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillisDouble(): Double = memScoped {
        val timeVal = alloc<timeval>()
        gettimeofday(timeVal.ptr, null)
        val sec = timeVal.tv_sec
        val usec = timeVal.tv_usec
        ((sec * 1_000L) + (usec / 1_000L))
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
