package korlibs.time

interface TimeFormat {
    fun format(dd: TimeSpan): String
    fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean = true): TimeSpan?

    companion object {
        val DEFAULT_FORMAT = TimeFormat("HH:mm:ss[.SSS]")
        val FORMAT_TIME = TimeFormat("HH:mm:ss")


        fun parse(time: String): TimeSpan = DEFAULT_FORMAT.parse(time)

        operator fun invoke(pattern: String) = PatternTimeFormat(pattern)
    }
}

fun TimeFormat.parse(str: String, doAdjust: Boolean = true): TimeSpan =
    tryParse(str, doThrow = true, doAdjust = doAdjust) ?: throw DateException("Not a valid format: '$str' for '$this'")
fun TimeFormat.parseTime(str: String): Time = Time(parse(str))

fun TimeFormat.format(time: Double): String = format(time.milliseconds)
fun TimeFormat.format(time: Long): String = format(time.milliseconds)
fun TimeFormat.format(time: Time): String = format(time.encoded)
