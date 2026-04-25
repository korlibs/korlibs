package korlibs.time

import korlibs.Serializable
import kotlin.time.*

interface DateComponentsFormat : Serializable {
    fun format(dd: DateComponents): String
    fun tryParse(str: String, mode: DateComponents.Mode? = null, doThrow: Boolean = false): DateComponents?

    companion object {
        operator fun invoke(
            format: String,
            locale: KlockLocale? = null,
            tzNames: TimezoneNames = TimezoneNames.DEFAULT,
        ): PatternDateComponentsFormat = PatternDateComponentsFormat(format, locale, tzNames)
    }
}

fun DateComponentsFormat.parse(str: String, mode: DateComponents.Mode? = null): DateComponents = tryParse(str, mode, doThrow = true) ?: error("Can't parse '$str'")

fun DateComponentsFormat.toDateFormat(): DateFormat = object : DateFormat {
    override fun format(dd: DateTimeTz): String = this@toDateFormat.format(dd.toDateComponents())
    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? = this@toDateFormat.tryParse(str, mode = DateComponents.Mode.DATE, doThrow)?.toDateTimeTz(doThrow, doAdjust)
}

fun DateComponentsFormat.toTimeFormat(): TimeFormat = object : TimeFormat {
    override fun format(dd: Duration): String = this@toTimeFormat.format(dd.toDateComponents())
    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? = this@toTimeFormat.tryParse(str, mode = DateComponents.Mode.TIME, doThrow)?.toDuration()
}

fun DateComponentsFormat.toDateTimeSpanFormat(): DateTimeSpanFormat = object : DateTimeSpanFormat {
    override fun format(dd: DateTimeSpan): String = this@toDateTimeSpanFormat.format(dd.toDateComponents())
    override fun tryParse(str: String, doThrow: Boolean): DateTimeSpan? = this@toDateTimeSpanFormat.tryParse(str, mode = DateComponents.Mode.DATE_TIME_SPAN, doThrow)?.toDateTimeSpan()
}
