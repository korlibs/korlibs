package korlibs.time

import korlibs.time.core.*
import korlibs.time.internal.*
import kotlin.time.*

/**
 * Class for measuring relative times with as much precision as possible.
 */
@OptIn(CoreTimeInternalApi::class)
object PerformanceCounter {
    private val start = TimeSource.Monotonic.markNow()

    /**
     * Returns a performance counter measure in nanoseconds.
     */
    val nanoseconds: Double get() = start.elapsedNow().nanoseconds

    /**
     * Returns a performance counter measure in microseconds.
     */
    val microseconds: Double get() = start.elapsedNow().microseconds

    /**
     * Returns a performance counter measure in milliseconds.
     */
    val milliseconds: Double get() = start.elapsedNow().milliseconds

    /**
     * Returns a performance counter as a [Duration].
     */
    val reference: Duration get() = start.elapsedNow()
}
