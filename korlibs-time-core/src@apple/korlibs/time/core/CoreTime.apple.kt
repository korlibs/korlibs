package korlibs.time.core

import korlibs.time.*
import kotlinx.cinterop.*
import platform.posix.*
import platform.Foundation.*
import kotlin.time.*

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillis(): Long = memScoped {
        val timeVal = alloc<timeval>()
        gettimeofday(timeVal.ptr, null)
        val sec = timeVal.tv_sec
        val usec = timeVal.tv_usec
        ((sec * 1_000L) + (usec / 1_000L)).toLong()
    }

    override fun localTimezoneOffset(time: Long): Duration = autoreleasepool {
        CFAbsoluteTimeGetCurrent()
        return getLocalTimezoneOffsetDarwin(CFTimeZoneCopySystem(), DateTime(time))
    }

    override fun sleep(time: Duration) {
        val micros = time.inWholeMicroseconds
        val s = micros / 1_000_000
        val u = micros % 1_000_000
        if (s > 0) platform.posix.sleep(s.convert())
        if (u > 0) platform.posix.usleep(u.convert())
    }

    private fun getLocalTimezoneOffsetDarwin(tz: CFTimeZoneRef?, time: DateTime): Duration {
        val secondsSince2001 = time.cfAbsoluteTime()
        return (CFTimeZoneGetSecondsFromGMT(tz, secondsSince2001.toDouble()) / 60.0).minutes
    }
}
