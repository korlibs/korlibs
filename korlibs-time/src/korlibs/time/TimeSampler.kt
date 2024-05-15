package korlibs.time

import kotlin.time.*

/**
 * Class to count the number of times a sample was added in a timeWindow
 */
class TimeSampler(var timeWindow: Duration = 1.seconds) {
    private val events = ArrayDeque<Double>()

    val count: Int get() = events.size

    fun add() {
        val now = DateTime.now()
        events.add(now.unixMillisDouble)
        while (events.first() < (now - timeWindow).unixMillisDouble) {
            events.removeFirst()
        }
    }
}
