package korlibs.time

import korlibs.Serializable
import kotlin.time.*

interface TimeFormat : Serializable {
    fun format(dd: Duration): String
    fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean = true): Duration?

    companion object {
        val DEFAULT_FORMAT = TimeFormat("HH:mm:ss[.SSS]")
        val FORMAT_TIME = TimeFormat("HH:mm:ss")


        fun parse(time: String): Duration = DEFAULT_FORMAT.parse(time)

        operator fun invoke(pattern: String) = PatternTimeFormat(pattern)
    }
}

fun TimeFormat.parse(str: String, doAdjust: Boolean = true): Duration =
    tryParse(str, doThrow = true, doAdjust = doAdjust) ?: throw DateException("Not a valid format: '$str' for '$this'")
fun TimeFormat.parseTime(str: String): Time = Time(parse(str))

fun TimeFormat.format(time: Double): String = format(time.milliseconds)
fun TimeFormat.format(time: Long): String = format(time.milliseconds)
fun TimeFormat.format(time: Time): String = format(time.encoded)
