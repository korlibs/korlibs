package korlibs.time.core

import korlibs.time.*
import kotlinx.cinterop.*
import platform.posix.*
import platform.windows.*
import kotlin.time.*

@OptIn(ExperimentalForeignApi::class)
actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillis(): Long = memScoped {
        val timeVal = alloc<timeval>()
        mingw_gettimeofday(timeVal.ptr, null) // mingw: doesn't expose gettimeofday, but mingw_gettimeofday
        val sec = timeVal.tv_sec
        val usec = timeVal.tv_usec
        ((sec * 1_000L) + (usec / 1_000L))
    }

    override fun localTimezoneOffset(time: Long): Duration = memScoped {
        val timeAsFileTime = UnixMillisecondsToWindowsTicks(time)
        val utcFtime = FILETIME_fromWindowsTicks(this, timeAsFileTime)
        val timezone = getTimeZoneInformation(this)
        val utcStime = utcFtime.toSystemTime(this)
        val localStime = utcStime.toTimezone(this, timezone)
        val localUnix = localStime.toFiletime(this).toUnix()
        val utcUnix = utcStime.toFiletime(this).toUnix()
        return (localUnix - utcUnix).milliseconds
    }

    override fun sleep(time: TimeSpan) {
        val micros = time.inWholeMicroseconds
        val s = micros / 1_000_000
        val u = micros % 1_000_000
        if (s > 0) platform.posix.sleep(s.convert())
        if (u > 0) platform.posix.usleep(u.convert())
    }

    fun FILETIME_fromWindowsTicks(scope: NativePlacement, ticks: Long): FILETIME = scope.run { alloc<FILETIME>().apply { dwHighDateTime = (ticks ushr 32).toUInt(); dwLowDateTime = ticks.toUInt() } }
    fun getTimeZoneInformation(scope: NativePlacement) = scope.run { alloc<TIME_ZONE_INFORMATION>().apply { GetTimeZoneInformation(this.ptr) } }
    fun FILETIME.toSystemTime(scope: NativePlacement): SYSTEMTIME = scope.run { alloc<SYSTEMTIME>().apply { FileTimeToSystemTime(this@toSystemTime.ptr, this.ptr) } }
    fun SYSTEMTIME.toTimezone(scope: NativePlacement, tzi: TIME_ZONE_INFORMATION): SYSTEMTIME = scope.run { alloc<SYSTEMTIME>().apply { SystemTimeToTzSpecificLocalTime(tzi.ptr, this@toTimezone.ptr, this.ptr) } }
    fun SYSTEMTIME.toUtc(scope: NativePlacement, tzi: TIME_ZONE_INFORMATION): SYSTEMTIME = scope.run { alloc<SYSTEMTIME>().apply { TzSpecificLocalTimeToSystemTime(tzi.ptr, this@toUtc.ptr, this.ptr) } }
    fun SYSTEMTIME.toFiletime(scope: NativePlacement): FILETIME = scope.run { alloc<FILETIME>().apply { SystemTimeToFileTime(this@toFiletime.ptr, this.ptr) } }
    fun FILETIME.toWindowsTicks() = ((dwHighDateTime.toULong() shl 32) or (dwLowDateTime.toULong())).toLong()
    fun FILETIME.toUnix() = WindowsTickToUnixMilliseconds(toWindowsTicks())
    fun FILETIME_fromUnix(scope: NativePlacement, unix: Long): FILETIME = FILETIME_fromWindowsTicks(scope, UnixMillisecondsToWindowsTicks(unix))
    private val WINDOWS_TICK = 10_000L
    private val MS_TO_UNIX_EPOCH = 11644473600_000L
    fun WindowsTickToUnixMilliseconds(windowsTicks: Long) = (windowsTicks / WINDOWS_TICK - MS_TO_UNIX_EPOCH)
    fun UnixMillisecondsToWindowsTicks(unix: Long) = ((unix + MS_TO_UNIX_EPOCH) * WINDOWS_TICK)
}
