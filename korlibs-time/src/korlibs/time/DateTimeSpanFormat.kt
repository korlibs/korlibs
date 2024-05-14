package korlibs.time

import kotlin.time.*

interface DateTimeSpanFormat {
    fun format(dd: DateTimeSpan): String
    fun tryParse(str: String, doThrow: Boolean): DateTimeSpan?
}

fun DateTimeSpanFormat.format(dd: Duration): String = format(dd + 0.months)
fun DateTimeSpanFormat.format(dd: MonthSpan): String = format(dd + 0.seconds)

fun DateTimeSpanFormat.parse(str: String): DateTimeSpan =
    tryParse(str, doThrow = true) ?: throw DateException("Not a valid format: '$str' for '$this'")
