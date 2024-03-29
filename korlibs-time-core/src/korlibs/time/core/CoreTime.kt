package korlibs.time.core

import korlibs.time.*
import kotlin.time.*

@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
public annotation class CoreTimeInternalApi

@CoreTimeInternalApi
expect var CoreTime: ICoreTime

@CoreTimeInternalApi
inline fun <T> TestUnsafeSetTemporalCoreTime(tempCoreTime: ICoreTime, block: () -> T): T {
    val oldCoreTime = CoreTime
    CoreTime = tempCoreTime
    try {
        return block()
    } finally {
        CoreTime = oldCoreTime
    }
}

interface ICoreTime {
    /** Unix timestamp in milliseconds */
    fun currentTimeMillis(): Long
    /** Nano-second resolution */
    fun nanoTime(): Long
    /** Timezone offset for a specific [time]stamp */
    fun localTimezoneOffset(time: Long): Duration
    /** Synchronous sleep (may spinlock, use with care) */
    fun sleep(time: Duration) {
        val start = nanoTime()
        val timeNanoseconds = time.nanoseconds
        while (nanoTime() - start < timeNanoseconds) Unit
    }
}
