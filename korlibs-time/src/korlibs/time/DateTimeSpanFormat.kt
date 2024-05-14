package korlibs.time

import kotlin.time.*

interface DateTimeSpanFormat {
    fun format(dd: DateTimeSpan): String
    fun tryParse(str: String, doThrow: Boolean): DateTimeSpan?

    companion object {
        operator fun invoke(pattern: String): DateTimeSpanFormat = PatternDateFormat(pattern).toDateTimeSpanFormat()
    }
}

fun DateFormat.toDateTimeSpanFormat(): DateTimeSpanFormat = object : DateTimeSpanFormat {
    override fun format(dd: DateTimeSpan): String = this@toDateTimeSpanFormat.format(dd)
    override fun tryParse(str: String, doThrow: Boolean): DateTimeSpan? =
        this@toDateTimeSpanFormat.tryParseDateTimeSpan(str, doThrow)
}

fun DateTimeSpanFormat.format(dd: Duration): String = format(dd + 0.months)
fun DateTimeSpanFormat.format(dd: MonthSpan): String = format(dd + 0.seconds)

fun DateTimeSpanFormat.parse(str: String): DateTimeSpan =
    tryParse(str, doThrow = true) ?: throw DateException("Not a valid format: '$str' for '$this'")
