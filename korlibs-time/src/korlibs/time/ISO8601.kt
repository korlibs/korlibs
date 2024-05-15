package korlibs.time

import korlibs.time.internal.MicroStrReader
import korlibs.time.internal.fastForEach
import korlibs.time.internal.padded
import korlibs.time.internal.readTimeZoneOffset
import kotlin.math.absoluteValue
import kotlin.time.*

// https://en.wikipedia.org/wiki/ISO_8601
object ISO8601 {
    data class BaseIsoTimeFormat(val format: String) : TimeFormat {
        companion object {
            private val ref = DateTime(1900, 1, 1)
        }
        private val dateTimeFormat = BaseIsoDateTimeFormat(format)

        override fun format(dd: Duration): String = dateTimeFormat.format(ref + dd)

        override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? =
            dateTimeFormat.tryParse(str, doThrow, doAdjust)?.let { it.utc - ref }
    }

    data class BaseIsoDateTimeFormat(val format: String, val twoDigitBaseYear: Int = 1900) : DateFormat {
        internal val internalFormat by lazy { ISODateComponentsFormat(format, twoDigitBaseYear) }

        override fun format(dd: DateTimeTz): String = buildString {
            val d = dd.local
            val s = d.copyDayOfMonth(hours = 0, minutes = 0, seconds = 0, milliseconds = 0)
            val time = d - s
            val fmtReader = MicroStrReader(format)
            while (fmtReader.hasMore) {
                when {
                    fmtReader.tryRead("Z") -> {
                        //if (dd.offset != TimezoneOffset.UTC) {
                        if (dd.offset != TimezoneOffset.UTC) {
                            dd.offset.deltaHoursAbs
                            append(if (dd.offset.positive) "+" else "-")
                            append(dd.offset.deltaHoursAbs.padded(2))
                            append(":")
                            append(dd.offset.deltaMinutesAbs.padded(2))
                        } else {
                            append("Z")
                        }
                    }

                    fmtReader.tryRead("YYYYYY") -> append(d.yearInt.absoluteValue.padded(6))
                    fmtReader.tryRead("YYYY") -> append(d.yearInt.absoluteValue.padded(4))
                    fmtReader.tryRead("YY") -> append((d.yearInt.absoluteValue % 100).padded(2))
                    fmtReader.tryRead("MM") -> append(d.month1.padded(2))
                    fmtReader.tryRead("DD") -> append(d.dayOfMonth.padded(2))
                    fmtReader.tryRead("DDD") -> append(d.dayOfWeekInt.padded(3))
                    fmtReader.tryRead("ww") -> append(d.weekOfYear1.padded(2))
                    fmtReader.tryRead("D") -> append(d.dayOfWeek.index1Monday)
                    fmtReader.tryRead("hh") -> {
                        val nextComma = fmtReader.tryRead(',')
                        val result = if (nextComma || fmtReader.tryRead('.')) {
                            var decCount = 0
                            while (fmtReader.tryRead('h')) decCount++
                            time.hours.padded(2, decCount)
                        } else {
                            d.hours.padded(2)
                        }
                        append(if (nextComma) result.replace('.', ',') else result)
                    }

                    fmtReader.tryRead("mm") -> {
                        val nextComma = fmtReader.tryRead(',')
                        val result = if (nextComma || fmtReader.tryRead('.')) {
                            var decCount = 0
                            while (fmtReader.tryRead('m')) decCount++
                            (time.minutes % 60.0).padded(2, decCount)
                        } else {
                            d.minutes.padded(2)
                        }
                        append(if (nextComma) result.replace('.', ',') else result)
                    }

                    fmtReader.tryRead("ss") -> {
                        val nextComma = fmtReader.tryRead(',')
                        val result = if (nextComma || fmtReader.tryRead('.')) {
                            var decCount = 0
                            while (fmtReader.tryRead('s')) decCount++
                            (time.seconds % 60.0).padded(2, decCount)
                        } else {
                            d.seconds.padded(2)
                        }
                        append(if (nextComma) result.replace('.', ',') else result)
                    }

                    fmtReader.tryRead("±") -> append(if (d.yearInt < 0) "-" else "+")
                    else -> append(fmtReader.readChar())
                }
            }
        }

        override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? {
            return internalFormat.tryParse(str, setDate = true, doThrow = doThrow)?.toDateTimeTz(doThrow, doAdjust).also {
                if (doThrow && it == null) throw DateException("Can't parse $str with $format")
            }
        }
    }

    class IsoIntervalFormat(val format: String) : DateTimeSpanFormat {
        internal val internalFormat by lazy { ISODateComponentsFormat(format, 0) }

        override fun format(dd: DateTimeSpan): String = buildString {
            val fmtReader = MicroStrReader(format)
            var time = false
            while (fmtReader.hasMore) {
                when {
                    fmtReader.tryRead("T") -> append('T').also { time = true }
                    fmtReader.tryRead("nnY") -> append(dd.years).append('Y')
                    fmtReader.tryRead("nnM") -> append(if (time) dd.minutes else dd.months).append('M')
                    fmtReader.tryRead("nnD") -> append(dd.daysIncludingWeeks).append('D')
                    fmtReader.tryRead("nnH") -> append(dd.hours).append('H')
                    fmtReader.tryRead("nnS") -> append(dd.seconds).append('S')
                    else -> append(fmtReader.readChar())
                }
            }
        }

        override fun tryParse(str: String, doThrow: Boolean): DateTimeSpan? {
            var time = false
            var years = 0.0
            var months = 0.0
            var days = 0.0
            var hours = 0.0
            var minutes = 0.0
            var seconds = 0.0

            val reader = MicroStrReader(str)
            val fmtReader = MicroStrReader(format)

            while (fmtReader.hasMore) {
                when {
                    fmtReader.tryRead("nn,nnY") || fmtReader.tryRead("nnY") -> {
                        years = reader.tryReadDouble() ?: return null
                        if (!reader.tryRead("Y")) return null
                    }
                    fmtReader.tryRead("nn,nnM") || fmtReader.tryRead("nnM") -> {
                        if (time) {
                            minutes = reader.tryReadDouble() ?: return null
                        } else {
                            months = reader.tryReadDouble() ?: return null
                        }
                        if (!reader.tryRead("M")) return null
                    }
                    fmtReader.tryRead("nn,nnD") || fmtReader.tryRead("nnD") -> {
                        days = reader.tryReadDouble() ?: return null
                        if (!reader.tryRead("D")) return null
                    }
                    fmtReader.tryRead("nn,nnH") || fmtReader.tryRead("nnH") -> {
                        hours = reader.tryReadDouble() ?: return null
                        if (!reader.tryRead("H")) return null
                    }
                    fmtReader.tryRead("nn,nnS") || fmtReader.tryRead("nnS") -> {
                        seconds = reader.tryReadDouble() ?: return null
                        if (!reader.tryRead("S")) return null
                    }
                    else -> {
                        val char = fmtReader.readChar()
                        if (char != reader.readChar()) return null
                        if (char == 'T') time = true
                    }
                }
            }
            return ((years * 12) + months).toInt().months + (days.days + hours.hours + minutes.minutes + seconds.seconds)
        }
    }


    data class IsoTimeFormat(val basicFormat: String?, val extendedFormat: String?) : TimeFormat {
        companion object {
            operator fun invoke(extendedFormat: String): IsoTimeFormat = IsoTimeFormat(
                extendedFormat.replace("-", "").replace(":", ""),
                extendedFormat
            )
        }
        val basic = BaseIsoTimeFormat(basicFormat ?: extendedFormat ?: TODO())
        val extended = BaseIsoTimeFormat(extendedFormat ?: basicFormat ?: TODO())

        override fun format(dd: Duration): String = extended.format(dd)
        override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? =
            basic.tryParse(str, false, doAdjust) ?: extended.tryParse(str, false, doAdjust)
            ?: (if (doThrow) throw DateException("Invalid format $str") else null)
    }

    data class IsoDateTimeFormat(val basicFormat: String?, val extendedFormat: String?) : DateFormat {
        companion object {
            operator fun invoke(extendedFormat: String): IsoDateTimeFormat = IsoDateTimeFormat(
                extendedFormat.replace("-", "").replace(":", ""),
                extendedFormat
            )
        }

        val basic = BaseIsoDateTimeFormat(basicFormat ?: extendedFormat ?: TODO())
        val extended = BaseIsoDateTimeFormat(extendedFormat ?: basicFormat ?: TODO())

        override fun format(dd: DateTimeTz): String = extended.format(dd)
        override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? = null
            ?: basic.tryParse(str, false, doAdjust)
            ?: extended.tryParse(str, false, doAdjust)
            ?: (if (doThrow) throw DateException("Invalid format $str") else null)
    }

    // Date Calendar Variants
    val DATE_CALENDAR_COMPLETE = IsoDateTimeFormat("YYYY-MM-DD")
    val DATE_CALENDAR_REDUCED0 = IsoDateTimeFormat("YYYY-MM")
    val DATE_CALENDAR_REDUCED1 = IsoDateTimeFormat("YYYY")
    val DATE_CALENDAR_REDUCED2 = IsoDateTimeFormat("YY")
    val DATE_CALENDAR_EXPANDED0 = IsoDateTimeFormat("±YYYYYY-MM-DD")
    val DATE_CALENDAR_EXPANDED1 = IsoDateTimeFormat("±YYYYYY-MM")
    val DATE_CALENDAR_EXPANDED2 = IsoDateTimeFormat("±YYYYYY")
    val DATE_CALENDAR_EXPANDED3 = IsoDateTimeFormat("±YYY")

    // Date Ordinal Variants
    val DATE_ORDINAL_COMPLETE = IsoDateTimeFormat("YYYY-DDD")
    val DATE_ORDINAL_EXPANDED = IsoDateTimeFormat("±YYYYYY-DDD")

    // Date Week Variants
    val DATE_WEEK_COMPLETE = IsoDateTimeFormat("YYYY-Www-D")
    val DATE_WEEK_REDUCED = IsoDateTimeFormat("YYYY-Www")
    val DATE_WEEK_EXPANDED0 = IsoDateTimeFormat("±YYYYYY-Www-D")
    val DATE_WEEK_EXPANDED1 = IsoDateTimeFormat("±YYYYYY-Www")

    val DATE_ALL = listOf(
        DATE_CALENDAR_COMPLETE, DATE_CALENDAR_REDUCED0, DATE_CALENDAR_REDUCED1, DATE_CALENDAR_REDUCED2,
        DATE_CALENDAR_EXPANDED0, DATE_CALENDAR_EXPANDED1, DATE_CALENDAR_EXPANDED2, DATE_CALENDAR_EXPANDED3,
        DATE_ORDINAL_COMPLETE, DATE_ORDINAL_EXPANDED,
        DATE_WEEK_COMPLETE, DATE_WEEK_REDUCED, DATE_WEEK_EXPANDED0, DATE_WEEK_EXPANDED1
    )

    // Time Variants
    val TIME_LOCAL_COMPLETE = IsoTimeFormat("hh:mm:ss")
    val TIME_LOCAL_REDUCED0 = IsoTimeFormat("hh:mm")
    val TIME_LOCAL_REDUCED1 = IsoTimeFormat("hh")
    val TIME_LOCAL_FRACTION0 = IsoTimeFormat("hh:mm:ss,ss")
    val TIME_LOCAL_FRACTION1 = IsoTimeFormat("hh:mm,mm")
    val TIME_LOCAL_FRACTION2 = IsoTimeFormat("hh,hh")

    // Time UTC Variants
    val TIME_UTC_COMPLETE = IsoTimeFormat("hh:mm:ssZ")
    val TIME_UTC_REDUCED0 = IsoTimeFormat("hh:mmZ")
    val TIME_UTC_REDUCED1 = IsoTimeFormat("hhZ")
    val TIME_UTC_FRACTION0 = IsoTimeFormat("hh:mm:ss,ssZ")
    val TIME_UTC_FRACTION1 = IsoTimeFormat("hh:mm,mmZ")
    val TIME_UTC_FRACTION2 = IsoTimeFormat("hh,hhZ")

    // Time Relative Variants
    val TIME_RELATIVE0 = IsoTimeFormat("±hh:mm")
    val TIME_RELATIVE1 = IsoTimeFormat("±hh")

    val TIME_ALL = listOf(
        TIME_LOCAL_COMPLETE,
        TIME_LOCAL_REDUCED0,
        TIME_LOCAL_REDUCED1,
        TIME_LOCAL_FRACTION0,
        TIME_LOCAL_FRACTION1,
        TIME_LOCAL_FRACTION2,
        TIME_UTC_COMPLETE,
        TIME_UTC_REDUCED0,
        TIME_UTC_REDUCED1,
        TIME_UTC_FRACTION0,
        TIME_UTC_FRACTION1,
        TIME_UTC_FRACTION2,
        TIME_RELATIVE0,
        TIME_RELATIVE1
    )

    // Date + Time Variants
    val DATETIME_COMPLETE = IsoDateTimeFormat("YYYY-MM-DDThh:mm:ss")
    val DATETIME_UTC_COMPLETE = IsoDateTimeFormat("YYYY-MM-DDThh:mm:ssZ")
    val DATETIME_UTC_COMPLETE_FRACTION = IsoDateTimeFormat("YYYY-MM-DDThh:mm:ss.sssZ")

    // Interval Variants
    val INTERVAL_COMPLETE0 = IsoIntervalFormat("PnnYnnMnnDTnnHnnMnnS")
    val INTERVAL_COMPLETE1 = IsoIntervalFormat("PnnYnnW")

    val INTERVAL_REDUCED0 = IsoIntervalFormat("PnnYnnMnnDTnnHnnM")
    val INTERVAL_REDUCED1 = IsoIntervalFormat("PnnYnnMnnDTnnH")
    val INTERVAL_REDUCED2 = IsoIntervalFormat("PnnYnnMnnD")
    val INTERVAL_REDUCED3 = IsoIntervalFormat("PnnYnnM")
    val INTERVAL_REDUCED4 = IsoIntervalFormat("PnnY")

    val INTERVAL_DECIMAL0 = IsoIntervalFormat("PnnYnnMnnDTnnHnnMnn,nnS")
    val INTERVAL_DECIMAL1 = IsoIntervalFormat("PnnYnnMnnDTnnHnn,nnM")
    val INTERVAL_DECIMAL2 = IsoIntervalFormat("PnnYnnMnnDTnn,nnH")
    val INTERVAL_DECIMAL3 = IsoIntervalFormat("PnnYnnMnn,nnD")
    val INTERVAL_DECIMAL4 = IsoIntervalFormat("PnnYnn,nnM")
    val INTERVAL_DECIMAL5 = IsoIntervalFormat("PnnYnn,nnW")
    val INTERVAL_DECIMAL6 = IsoIntervalFormat("PnnY")

    val INTERVAL_ZERO_OMIT0 = IsoIntervalFormat("PnnYnnDTnnHnnMnnS")
    val INTERVAL_ZERO_OMIT1 = IsoIntervalFormat("PnnYnnDTnnHnnM")
    val INTERVAL_ZERO_OMIT2 = IsoIntervalFormat("PnnYnnDTnnH")
    val INTERVAL_ZERO_OMIT3 = IsoIntervalFormat("PnnYnnD")

    val INTERVAL_ALL = listOf(
        INTERVAL_COMPLETE0, INTERVAL_COMPLETE1,
        INTERVAL_REDUCED0, INTERVAL_REDUCED1, INTERVAL_REDUCED2, INTERVAL_REDUCED3, INTERVAL_REDUCED4,
        INTERVAL_DECIMAL0, INTERVAL_DECIMAL1, INTERVAL_DECIMAL2, INTERVAL_DECIMAL3, INTERVAL_DECIMAL4,
        INTERVAL_DECIMAL5, INTERVAL_DECIMAL6,
        INTERVAL_ZERO_OMIT0, INTERVAL_ZERO_OMIT1, INTERVAL_ZERO_OMIT2, INTERVAL_ZERO_OMIT3
    )

    // Detects and parses all the variants
    val DATE = object : DateFormat {
        override fun format(dd: DateTimeTz): String = DATE_CALENDAR_COMPLETE.format(dd)

        override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? {
            DATE_ALL.fastForEach { format ->
                val result = format.extended.tryParse(str, false, doAdjust)
                if (result != null) return result
            }
            DATE_ALL.fastForEach { format ->
                val result = format.basic.tryParse(str, false, doAdjust)
                if (result != null) return result
            }
            return if (doThrow) throw DateException("Invalid format") else null
        }
    }
    val TIME = object : TimeFormat {
        override fun format(dd: Duration): String = TIME_LOCAL_FRACTION0.format(dd)

        override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? {
            TIME_ALL.fastForEach { format ->
                val result = format.extended.tryParse(str, false, doAdjust)
                if (result != null) return result
            }
            TIME_ALL.fastForEach { format ->
                val result = format.basic.tryParse(str, false, doAdjust)
                if (result != null) return result
            }
            return if (doThrow) throw DateException("Invalid format") else null
        }
    }
    val INTERVAL = object : DateTimeSpanFormat {
        override fun format(dd: DateTimeSpan): String = INTERVAL_DECIMAL0.format(dd)

        override fun tryParse(str: String, doThrow: Boolean): DateTimeSpan? {
            INTERVAL_ALL.fastForEach { format ->
                val result = format.tryParse(str, false)
                if (result != null) return result
            }
            return if (doThrow) throw DateException("Invalid format") else null
        }
    }
}

class ISODateComponentsFormat(val format: String, val twoDigitBaseYear: Int = 1900) : DateComponentsFormat {
    override fun format(dd: DateComponents): String {
        TODO("Not yet implemented")
    }

    override fun tryParse(str: String, setDate: Boolean?, doThrow: Boolean): DateComponents? {
        var sign = +1
        var tzOffset: Duration? = null
        var year = twoDigitBaseYear
        var month = 1
        var dayOfMonth = 1

        var dayOfWeek = -1
        var dayOfYear = -1
        var weekOfYear = -1

        var hours = 0.0
        var minutes = 0.0
        var seconds = 0.0

        var time = false

        val reader = MicroStrReader(str)
        val fmtReader = MicroStrReader(format)

        while (fmtReader.hasMore) {
            when {
                fmtReader.tryRead("Z") -> tzOffset = reader.readTimeZoneOffset()
                fmtReader.tryRead("YYYYYY") -> year = reader.tryReadInt(6) ?: return reportParse("YYYYYY")
                fmtReader.tryRead("YYYY") -> year = reader.tryReadInt(4) ?: return reportParse("YYYY")
                //fmtReader.tryRead("YY") -> year = twoDigitBaseYear + (reader.tryReadInt(2) ?: return null) // @TODO: Kotlin compiler BUG?
                fmtReader.tryRead("YY") -> {
                    val base = reader.tryReadInt(2) ?: return reportParse("YY")
                    year = twoDigitBaseYear + base
                }
                fmtReader.tryRead("MM") -> month = reader.tryReadInt(2) ?: return reportParse("MM")
                fmtReader.tryRead("DD") -> dayOfMonth = reader.tryReadInt(2) ?: return reportParse("DD")
                fmtReader.tryRead("DDD") -> dayOfYear = reader.tryReadInt(3) ?: return reportParse("DDD")
                fmtReader.tryRead("ww") -> weekOfYear = reader.tryReadInt(2) ?: return reportParse("ww")
                fmtReader.tryRead("D") -> dayOfWeek = reader.tryReadInt(1) ?: return reportParse("D")

                fmtReader.tryRead("hh") -> {
                    val nextComma = fmtReader.tryRead(',')
                    hours = if (nextComma || fmtReader.tryRead('.')) {
                        var count = 3
                        while (fmtReader.tryRead('h')) count++
                        reader.tryReadDouble(count) ?: return reportParse("incorrect hours")
                    } else {
                        reader.tryReadDouble(2) ?: return reportParse("incorrect hours")
                    }
                }
                fmtReader.tryRead("mm") -> {
                    val nextComma = fmtReader.tryRead(',')
                    minutes = if (nextComma || fmtReader.tryRead('.')) {
                        var count = 3
                        while (fmtReader.tryRead('m')) count++
                        reader.tryReadDouble(count) ?: return reportParse("incorrect minutes")
                    } else {
                        reader.tryReadDouble(2) ?: return reportParse("incorrect seconds")
                    }
                }
                fmtReader.tryRead("ss") -> {
                    val nextComma = fmtReader.tryRead(',')
                    seconds = if (nextComma || fmtReader.tryRead('.')) {
                        var count = 3
                        while (fmtReader.tryRead('s')) count++
                        reader.tryReadDouble() ?: return reportParse("incorrect seconds")
                    } else {
                        reader.tryReadDouble(2) ?: return reportParse("incorrect seconds")
                    }
                }
                fmtReader.tryRead("±") -> {
                    sign = when (reader.readChar()) {
                        '+' -> +1
                        '-' -> -1
                        else -> return reportParse("±")
                    }
                }
                fmtReader.tryRead("nn,nnY") || fmtReader.tryRead("nnY") -> {
                    year = reader.tryReadDouble()?.toInt() ?: return null
                    if (!reader.tryRead("Y")) return null
                }
                fmtReader.tryRead("nn,nnM") || fmtReader.tryRead("nnM") -> {
                    if (time) {
                        minutes = reader.tryReadDouble() ?: return null
                    } else {
                        month = reader.tryReadDouble()?.toInt() ?: return null
                    }
                    if (!reader.tryRead("M")) return null
                }
                fmtReader.tryRead("nn,nnD") || fmtReader.tryRead("nnD") -> {
                    dayOfMonth = reader.tryReadDouble()?.toInt() ?: return null
                    if (!reader.tryRead("D")) return null
                }
                fmtReader.tryRead("nn,nnH") || fmtReader.tryRead("nnH") -> {
                    hours = reader.tryReadDouble() ?: return null
                    if (!reader.tryRead("H")) return null
                }
                fmtReader.tryRead("nn,nnS") || fmtReader.tryRead("nnS") -> {
                    seconds = reader.tryReadDouble() ?: return null
                    if (!reader.tryRead("S")) return null
                }
                else -> {
                    val char = fmtReader.readChar()
                    if (char != reader.readChar()) return reportParse("separator")
                    if (char == 'T') time = true
                }
            }
        }
        if (reader.hasMore) return reportParse("uncomplete")

        val dateTime: Date = when {
            dayOfYear >= 0 -> Date(year, 1, 1) + (dayOfYear - 1).days
            weekOfYear >= 0 -> {
                val reference = Year(year).firstDate(DayOfWeek.Thursday) - 3.days
                val days = ((weekOfYear - 1) * 7 + (dayOfWeek - 1))
                reference + days.days
            }
            else -> Date(year, month, dayOfMonth)
        }
        val span = ComputedTime(hours.hours + minutes.minutes + seconds.seconds)

        return DateComponents(
            isDate = setDate == true,
            years = dateTime.year,
            months = dateTime.month1,
            days = dateTime.day,
            hours = span.hoursIncludingDaysAndWeeks,
            minutes = span.minutes,
            seconds = span.seconds,
            nanoseconds = span.nanoseconds,
            offset = tzOffset,
            sign = sign,
        )
    }

    private fun reportParse(reason: String): DateComponents? {
        //println("reason: $reason")
        return null
    }
}


// ISO 8601 (first week is the one after 1 containing a thursday)
fun Year.firstDate(dayOfWeek: DayOfWeek): Date {
    val start = Date(this.year, 1, 1)
    var n = 0
    while (true) {
        val date = (start + n.days)
        if (date.dayOfWeek == dayOfWeek) return date
        n++
    }
}

fun Year.first(dayOfWeek: DayOfWeek): DateTime = DateTime(firstDate(dayOfWeek))

val DateTime.weekOfYear0: Int
    get() {
        val firstThursday = year.first(DayOfWeek.Thursday)
        val offset = firstThursday.dayOfMonth - 3
        return (dayOfYear - offset) / 7
    }

val DateTime.weekOfYear1: Int get() = weekOfYear0 + 1
val DateTimeTz.weekOfYear0: Int get() = local.weekOfYear0
val DateTimeTz.weekOfYear1: Int get() = local.weekOfYear1
