package korlibs.time

import kotlin.time.*

interface TimeFormat {
    fun format(dd: Duration): String
    fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean = true): Duration?

    companion object {
        val DEFAULT_FORMAT: TimeFormat = TimeFormat("HH:mm:ss[.SSS]")
        val FORMAT_TIME: TimeFormat = TimeFormat("HH:mm:ss")

        fun parse(time: String): Duration = DEFAULT_FORMAT.parse(time)

        operator fun invoke(pattern: String): TimeFormat = PatternDateFormat(pattern).toTimeFormat()
    }
}

fun DateFormat.toTimeFormat(): TimeFormat = object : TimeFormat {
    override fun format(dd: Duration): String = this@toTimeFormat.format(dd.toComponents())
    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? =
        this@toTimeFormat.tryParseDateTimeSpan(str, doThrow, doAdjust = false)?.timeSpan
}

fun TimeFormat.parse(str: String, doAdjust: Boolean = true): Duration =
    tryParse(str, doThrow = true, doAdjust = doAdjust) ?: throw DateException("Not a valid format: '$str' for '$this'")
fun TimeFormat.parseTime(str: String): Time = Time(parse(str))

fun TimeFormat.format(time: Double): String = format(time.milliseconds)
fun TimeFormat.format(time: Long): String = format(time.milliseconds)
fun TimeFormat.format(time: Time): String = format(time.encoded)
