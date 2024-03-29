package korlibs.time

import korlibs.time.core.*
import korlibs.time.internal.*
import kotlin.time.*

/**
 * Class for measuring relative times with as much precision as possible.
 */
@OptIn(CoreTimeInternalApi::class)
object PerformanceCounter {
    /**
     * Returns a performance counter measure in nanoseconds.
     */
    val nanoseconds: Double get() = CoreTime.nanoTime().toDouble()

    /**
     * Returns a performance counter measure in microseconds.
     */
    val microseconds: Double get() = CoreTime.nanoTime().toDouble() / 1_000.0

    /**
     * Returns a performance counter measure in milliseconds.
     */
    val milliseconds: Double get() = CoreTime.nanoTime().toDouble() / 1_000_000.0

    /**
     * Returns a performance counter as a [Duration].
     */
    val reference: Duration get() = CoreTime.nanoTime().nanoseconds
}
