package korlibs.time

import korlibs.time.core.*
import korlibs.time.core.internal.*
import korlibs.time.internal.*
import kotlin.time.*

@OptIn(CoreTimeInternalApi::class)
class ComputedTime(val weeks: Int = 0, val days: Int = 0, val hours: Int = 0, val minutes: Int = 0, val seconds: Int = 0, val nanoseconds: Int = 0) {
    val milliseconds: Double get() = nanoseconds.toDouble() / 1_000_000
    val daysIncludingWeeks: Int get() = days + (weeks * DayOfWeek.Count)
    val hoursIncludingDaysAndWeeks: Int get() = hours + (daysIncludingWeeks * 24)
    val secondsIncludingMilliseconds: Double get() = seconds + milliseconds / CoreTimeInternal.MILLIS_PER_SECOND

    companion object {
       operator fun invoke(time: Duration): ComputedTime = Moduler(time.milliseconds).run {
            val weeks = int(CoreTimeInternal.MILLIS_PER_WEEK)
            val days = int(CoreTimeInternal.MILLIS_PER_DAY)
            val hours = int(CoreTimeInternal.MILLIS_PER_HOUR)
            val minutes = int(CoreTimeInternal.MILLIS_PER_MINUTE)
            val seconds = int(CoreTimeInternal.MILLIS_PER_SECOND)
            val milliseconds = double(1)
            return ComputedTime(weeks, days, hours, minutes, seconds, (milliseconds * 1_000_000).toInt())
        }
    }
}