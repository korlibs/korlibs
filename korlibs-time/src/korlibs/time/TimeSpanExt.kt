package korlibs.time

import korlibs.time.core.*
import korlibs.time.internal.*
import kotlin.math.*
import kotlin.time.*

operator fun Duration.plus(other: MonthSpan): DateTimeSpan = DateTimeSpan(other, this)
operator fun Duration.plus(other: DateTimeSpan): DateTimeSpan = DateTimeSpan(other.monthSpan, other.timeSpan + this)

operator fun Duration.minus(other: MonthSpan): DateTimeSpan = this + (-other)
operator fun Duration.minus(other: DateTimeSpan): DateTimeSpan = this + (-other)

/**
 * Formats this [TimeSpan] into something like `12:30:40.100`.
 *
 * For 3 hour, 20 minutes and 15 seconds
 *
 * 1 [components] (seconds): 12015
 * 2 [components] (minutes): 200:15
 * 3 [components] (hours)  : 03:20:15
 * 4 [components] (days)   : 00:03:20:15
 *
 * With milliseconds would add decimals to the seconds part.
 */
fun Duration.toTimeString(components: Int = 3, addMilliseconds: Boolean = false): String =
    toTimeString(milliseconds, components, addMilliseconds)

private val timeSteps = listOf(60, 60, 24)
private fun toTimeStringRaw(totalMilliseconds: Double, components: Int = 3): String {
    var timeUnit = floor(totalMilliseconds / 1000.0).toInt()

    val out = arrayListOf<String>()

    for (n in 0 until components) {
        if (n == components - 1) {
            out += timeUnit.padded(2)
            break
        }
        val step = timeSteps.getOrNull(n) ?: throw RuntimeException("Just supported ${timeSteps.size} steps")
        val cunit = timeUnit % step
        timeUnit /= step
        out += cunit.padded(2)
    }

    return out.reversed().joinToString(":")
}

@PublishedApi
internal fun toTimeString(totalMilliseconds: Double, components: Int = 3, addMilliseconds: Boolean = false): String {
    val milliseconds = (totalMilliseconds % 1000).toInt()
    val out = toTimeStringRaw(totalMilliseconds, components)
    return if (addMilliseconds) "$out.$milliseconds" else out
}
