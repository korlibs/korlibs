package korlibs.time

import kotlin.time.*

/** Allows to [format] and [parse] instances of [Date], [DateTime] and [DateTimeTz] */
interface DateFormat {
    fun format(dd: DateComponents): String = TODO()
    fun tryParseComponents(str: String, doThrow: Boolean = false, dateDefaults: Boolean = false): DateComponents? = TODO()

    fun format(dd: DateTimeTz): String = format(dd.toComponents())
    fun format(dd: DateTimeSpan): String = format(dd.toComponents())
    fun format(dd: MonthSpan): String = format(DateTimeSpan(dd, 0.seconds))
    fun format(dd: Duration): String = format(DateTimeSpan(MonthSpan(0), dd))

    fun tryParse(str: String, doThrow: Boolean = false, doAdjust: Boolean = true): DateTimeTz? =
        tryParseComponents(str, doThrow, dateDefaults = true)?.toDateTimeTz(doAdjust = doAdjust, doThrow = doThrow)
    fun tryParseDateTimeSpan(str: String, doThrow: Boolean = false, doAdjust: Boolean = true): DateTimeSpan? =
        tryParseComponents(str, doThrow, dateDefaults = false)?.toDateTimeSpan(doAdjust = doAdjust, doThrow = doThrow)
    fun tryParseDuration(str: String, doThrow: Boolean = false, doAdjust: Boolean = true): Duration? =
        tryParseDateTimeSpan(str, doThrow, doAdjust)?.timeSpan

    companion object {
        val DEFAULT_FORMAT = DateFormat("EEE, dd MMM yyyy HH:mm:ss z")
        val FORMAT1 = DateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        val FORMAT2 = DateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val FORMAT_DATE = DateFormat("yyyy-MM-dd")

        val ISO_DATE_TIME_OFFSET = DateFormat("yyyy-MM-dd'T'HH:mm[:ss[.S*]]Z")

        val FORMATS = listOf(DEFAULT_FORMAT, FORMAT1, FORMAT2, ISO_DATE_TIME_OFFSET, FORMAT_DATE)

        fun parse(date: String): DateTimeTz {
            var lastError: Throwable? = null
            for (format in FORMATS) {
                try {
                    return format.parse(date)
                } catch (e: Throwable) {
                    lastError = e
                }
            }
            throw lastError!!
        }

        operator fun invoke(pattern: String) = PatternDateFormat(pattern)
    }
}

fun DateFormat.parse(str: String, doAdjust: Boolean = true): DateTimeTz =
    tryParse(str, doThrow = true, doAdjust = doAdjust) ?: throw DateException("Not a valid format: '$str' for '$this'")
fun DateFormat.parseDateTimeSpan(str: String, doAdjust: Boolean = true): DateTimeSpan =
    tryParseDateTimeSpan(str, doThrow = true, doAdjust = doAdjust) ?: throw DateException("Not a valid format: '$str' for '$this'")
fun DateFormat.parseDuration(str: String, doAdjust: Boolean = true): Duration =
    tryParseDuration(str, doThrow = true, doAdjust = doAdjust) ?: throw DateException("Not a valid format: '$str' for '$this'")
fun DateFormat.parseDate(str: String): Date = parse(str).local.date

fun DateFormat.parseUtc(str: String): DateTime = parse(str).utc
fun DateFormat.parseLocal(str: String): DateTime = parse(str).local

fun DateFormat.format(date: Double): String = format(DateTime.fromUnixMillis(date))
fun DateFormat.format(date: Long): String = format(DateTime.fromUnixMillis(date))

fun DateFormat.format(dd: DateTime): String = format(dd.toOffsetUnadjusted(0.minutes))
fun DateFormat.format(dd: Date): String = format(dd.dateTimeDayStart)
