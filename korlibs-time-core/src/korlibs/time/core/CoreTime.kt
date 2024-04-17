package korlibs.time.core

import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds

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
    /** Timezone offset for a specific [time]stamp */
    fun localTimezoneOffset(time: Long): Duration
    /** Synchronous sleep (may spinlock on JS and WASM, use with care) */
    fun unaccurateSleep(duration: Duration) {
        val start = TimeSource.Monotonic.markNow()
        while (start.elapsedNow() < duration) Unit
    }
    /** Yields this thread without waiting */
    fun unaccurateYield() {
        unaccurateSleep(0.milliseconds)
    }

    // https://andy-malakov.blogspot.com/2010/06/alternative-to-threadsleep.html
    // https://www.geisswerks.com/ryan/FAQS/timing.html
    /** Accurately synchronously sleep (may spinlock on JS and WASM, use with care) */
    fun sleep(duration: Duration) {
        val end = TimeSource.Monotonic.markNow() + duration
        var timeLeft = duration

        val LONG_SLEEP_PRECISION = 20.milliseconds
        val SLEEP_PRECISION = 4.milliseconds
        val SPIN_YIELD_PRECISION = 0.1.milliseconds

        if (timeLeft > LONG_SLEEP_PRECISION) unaccurateSleep(timeLeft - LONG_SLEEP_PRECISION)
        // Handles `Thread.interrupted()`, etc.
        if (timeLeft <= SPIN_YIELD_PRECISION) unaccurateYield()

        do {
            when {
                timeLeft > SLEEP_PRECISION -> unaccurateSleep(1.milliseconds)
                timeLeft > SPIN_YIELD_PRECISION -> unaccurateYield()
            }
            timeLeft = end - TimeSource.Monotonic.markNow()
        } while (timeLeft > 0.milliseconds)
    }
}
