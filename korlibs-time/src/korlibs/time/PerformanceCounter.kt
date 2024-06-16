package korlibs.time

import korlibs.time.core.*
import kotlin.time.*

/**
 * Class for measuring relative times with as much precision as possible.
 */
@OptIn(CoreTimeInternalApi::class)
object PerformanceCounter {
    private fun nowMillis(): Double = CoreTime.performanceMillis()
    //private val start = TimeSource.Monotonic.markNow()
    private val start = nowMillis()
    private fun ellapsed(): FastDuration = (nowMillis() - start).fastMilliseconds

    /**
     * Returns a performance counter, measured in nanoseconds.
     */
    val nanoseconds: Double get() = ellapsed().nanoseconds

    /**
     * Returns a performance counter, measured in microseconds.
     */
    val microseconds: Double get() = ellapsed().microseconds

    /**
     * Returns a performance counter, measured in milliseconds.
     */
    val milliseconds: Double get() = ellapsed().milliseconds

    /**
     * Returns a performance counter as a [Duration].
     */
    val reference: Duration get() = ellapsed().slow

    val fastReference: FastDuration get() = ellapsed()
}
