package korlibs.time

import korlibs.time.internal.*
import kotlin.math.*
import kotlin.time.*

/**
 * https://en.wikipedia.org/wiki/ISO_8601
 *
 * <https://www.loc.gov/standards/datetime/iso-tc154-wg5_n0038_iso_wd_8601-1_2016-02-16.pdf>
 */
object ISO8601 : DateFormat {
    // Date Calendar Variants
    val DATE_CALENDAR_COMPLETE = IsoPatternDateFormatEx("YYYY-MM-DD")
    val DATE_CALENDAR_REDUCED0 = IsoPatternDateFormatEx("YYYY-MM")
    val DATE_CALENDAR_REDUCED1 = IsoPatternDateFormatEx("YYYY")
    val DATE_CALENDAR_REDUCED2 = IsoPatternDateFormatEx("YY")
    val DATE_CALENDAR_EXPANDED0 = IsoPatternDateFormatEx("±YYYYYY-MM-DD")
    val DATE_CALENDAR_EXPANDED1 = IsoPatternDateFormatEx("±YYYYYY-MM")
    val DATE_CALENDAR_EXPANDED2 = IsoPatternDateFormatEx("±YYYYYY")
    val DATE_CALENDAR_EXPANDED3 = IsoPatternDateFormatEx("±YYY")

    // Date Ordinal Variants
    val DATE_ORDINAL_COMPLETE = IsoPatternDateFormatEx("YYYY-DDD")
    val DATE_ORDINAL_EXPANDED = IsoPatternDateFormatEx("±YYYYYY-DDD")

    // Date Week Variants
    val DATE_WEEK_COMPLETE = IsoPatternDateFormatEx("YYYY-Www-D")
    val DATE_WEEK_REDUCED = IsoPatternDateFormatEx("YYYY-Www")
    val DATE_WEEK_EXPANDED0 = IsoPatternDateFormatEx("±YYYYYY-Www-D")
    val DATE_WEEK_EXPANDED1 = IsoPatternDateFormatEx("±YYYYYY-Www")

    val DATE_ALL = listOf(
        DATE_CALENDAR_COMPLETE, DATE_CALENDAR_REDUCED0, DATE_CALENDAR_REDUCED1, DATE_CALENDAR_REDUCED2,
        DATE_CALENDAR_EXPANDED0, DATE_CALENDAR_EXPANDED1, DATE_CALENDAR_EXPANDED2, DATE_CALENDAR_EXPANDED3,
        DATE_ORDINAL_COMPLETE, DATE_ORDINAL_EXPANDED,
        DATE_WEEK_COMPLETE, DATE_WEEK_REDUCED, DATE_WEEK_EXPANDED0, DATE_WEEK_EXPANDED1
    )

    // Time Variants
    val TIME_LOCAL_COMPLETE = IsoPatternDateFormatEx("hh:mm:ss")
    val TIME_LOCAL_REDUCED0 = IsoPatternDateFormatEx("hh:mm")
    val TIME_LOCAL_REDUCED1 = IsoPatternDateFormatEx("hh")
    val TIME_LOCAL_FRACTION0 = IsoPatternDateFormatEx("hh:mm:ss,ss")
    val TIME_LOCAL_FRACTION1 = IsoPatternDateFormatEx("hh:mm,mm")
    val TIME_LOCAL_FRACTION2 = IsoPatternDateFormatEx("hh,hh")

    // Time UTC Variants
    val TIME_UTC_COMPLETE = IsoPatternDateFormatEx("hh:mm:ssZ")
    val TIME_UTC_REDUCED0 = IsoPatternDateFormatEx("hh:mmZ")
    val TIME_UTC_REDUCED1 = IsoPatternDateFormatEx("hhZ")
    val TIME_UTC_FRACTION0 = IsoPatternDateFormatEx("hh:mm:ss,ssZ")
    val TIME_UTC_FRACTION1 = IsoPatternDateFormatEx("hh:mm,mmZ")
    val TIME_UTC_FRACTION2 = IsoPatternDateFormatEx("hh,hhZ")

    // Time Relative Variants
    val TIME_RELATIVE0 = IsoPatternDateFormatEx("±hh:mm")
    val TIME_RELATIVE1 = IsoPatternDateFormatEx("±hh")

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
    val DATETIME_COMPLETE = IsoPatternDateFormatEx("YYYY-MM-DDThh:mm:ss")
    val DATETIME_UTC_COMPLETE = IsoPatternDateFormatEx("YYYY-MM-DDThh:mm:ssZ")
    val DATETIME_UTC_COMPLETE_FRACTION = IsoPatternDateFormatEx("YYYY-MM-DDThh:mm:ss.sssZ")

    // Interval Variants
    val INTERVAL_COMPLETE0 = IsoPatternDateFormat("PnnYnnMnnDTnnHnnMnnS")
    val INTERVAL_COMPLETE1 = IsoPatternDateFormat("PnnYnnW")

    val INTERVAL_REDUCED0 = IsoPatternDateFormat("PnnYnnMnnDTnnHnnM")
    val INTERVAL_REDUCED1 = IsoPatternDateFormat("PnnYnnMnnDTnnH")
    val INTERVAL_REDUCED2 = IsoPatternDateFormat("PnnYnnMnnD")
    val INTERVAL_REDUCED3 = IsoPatternDateFormat("PnnYnnM")
    val INTERVAL_REDUCED4 = IsoPatternDateFormat("PnnY")

    val INTERVAL_DECIMAL0 = IsoPatternDateFormat("PnnYnnMnnDTnnHnnMnn,nnS")
    val INTERVAL_DECIMAL1 = IsoPatternDateFormat("PnnYnnMnnDTnnHnn,nnM")
    val INTERVAL_DECIMAL2 = IsoPatternDateFormat("PnnYnnMnnDTnn,nnH")
    val INTERVAL_DECIMAL3 = IsoPatternDateFormat("PnnYnnMnn,nnD")
    val INTERVAL_DECIMAL4 = IsoPatternDateFormat("PnnYnn,nnM")
    val INTERVAL_DECIMAL5 = IsoPatternDateFormat("PnnYnn,nnW")
    val INTERVAL_DECIMAL6 = IsoPatternDateFormat("PnnY")

    val INTERVAL_ZERO_OMIT0 = IsoPatternDateFormat("PnnYnnDTnnHnnMnnS")
    val INTERVAL_ZERO_OMIT1 = IsoPatternDateFormat("PnnYnnDTnnHnnM")
    val INTERVAL_ZERO_OMIT2 = IsoPatternDateFormat("PnnYnnDTnnH")
    val INTERVAL_ZERO_OMIT3 = IsoPatternDateFormat("PnnYnnD")

    val INTERVAL_ALL = listOf(
        INTERVAL_COMPLETE0, INTERVAL_COMPLETE1,
        INTERVAL_REDUCED0, INTERVAL_REDUCED1, INTERVAL_REDUCED2, INTERVAL_REDUCED3, INTERVAL_REDUCED4,
        INTERVAL_DECIMAL0, INTERVAL_DECIMAL1, INTERVAL_DECIMAL2, INTERVAL_DECIMAL3, INTERVAL_DECIMAL4,
        INTERVAL_DECIMAL5, INTERVAL_DECIMAL6,
        INTERVAL_ZERO_OMIT0, INTERVAL_ZERO_OMIT1, INTERVAL_ZERO_OMIT2, INTERVAL_ZERO_OMIT3
    )

    // Detects and parses all the variants
    val DATE = object : DateFormat {
        override fun format(dd: DateComponents): String = DATE_CALENDAR_COMPLETE.format(dd)

        override fun tryParseComponents(str: String, doThrow: Boolean, dateDefaults: Boolean): DateComponents? {
            DATE_ALL.fastForEach { format ->
                val result = format.extended.tryParseComponents(str, false, dateDefaults)
                if (result != null) return result
            }
            DATE_ALL.fastForEach { format ->
                val result = format.basic.tryParseComponents(str, false, dateDefaults)
                if (result != null) return result
            }
            return if (doThrow) throw DateException("Invalid format") else null
        }
    }
    val TIME = object : TimeFormat {
        override fun format(dd: Duration): String = TIME_LOCAL_FRACTION0.format(dd)

        override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? {
            TIME_ALL.fastForEach { format ->
                val result = format.extended.tryParseDuration(str, false, doAdjust)
                if (result != null) return result
            }
            TIME_ALL.fastForEach { format ->
                val result = format.basic.tryParseDuration(str, false, doAdjust)
                if (result != null) return result
            }
            return if (doThrow) throw DateException("Invalid format") else null
        }
    }
    val INTERVAL = object : DateTimeSpanFormat {
        override fun format(dd: DateTimeSpan): String = INTERVAL_DECIMAL0.format(dd)

        override fun tryParse(str: String, doThrow: Boolean): DateTimeSpan? {
            INTERVAL_ALL.fastForEach { format ->
                val result = format.tryParseDateTimeSpan(str, false)
                if (result != null) return result
            }
            return if (doThrow) throw DateException("Invalid format") else null
        }
    }
}

// ISO 8601 (first week is the one after 1 containing a thursday)
fun Year.first(dayOfWeek: DayOfWeek): DateTime {
    val start = DateTime(this.year, 1, 1)
    var n = 0
    while (true) {
        val time = (start + n.days)
        if (time.dayOfWeek == dayOfWeek) return time
        n++
    }
}

val DateTime.weekOfYear0: Int
    get() {
        val firstThursday = year.first(DayOfWeek.Thursday)
        val offset = firstThursday.dayOfMonth - 3
        return (dayOfYear - offset) / 7
    }

val DateTime.weekOfYear1: Int get() = weekOfYear0 + 1
val DateTimeTz.weekOfYear0: Int get() = local.weekOfYear0
val DateTimeTz.weekOfYear1: Int get() = local.weekOfYear1

data class IsoPatternDateFormat(val format: String, val twoDigitBaseYear: Int = 1900) : DateFormat {
    override fun format(dd: DateComponents): String = buildString {
        val fmtReader = MicroStrReader(format)
        var time = false
        while (fmtReader.hasMore) {
            when {
                fmtReader.tryRead("Z") -> {
                    //if (dd.offset != TimezoneOffset.UTC) {
                    if (dd.offsetSure != TimezoneOffset.UTC) {
                        dd.offsetSure.deltaHoursAbs
                        append(if (dd.offsetSure.positive) "+" else "-")
                        append(dd.offsetSure.deltaHoursAbs.padded(2))
                        append(":")
                        append(dd.offsetSure.deltaMinutesAbs.padded(2))
                    } else {
                        append("Z")
                    }
                }
                fmtReader.tryRead("YYYYYY") -> append(dd.years.absoluteValue.padded(6))
                fmtReader.tryRead("YYYY") -> append(dd.years.absoluteValue.padded(4))
                fmtReader.tryRead("YY") -> append((dd.years.absoluteValue % 100).padded(2))
                fmtReader.tryRead("MM") -> append(dd.months.padded(2))
                fmtReader.tryRead("DD") -> append(dd.days.padded(2))
                fmtReader.tryRead("DDD") -> append(dd.dayOfYear.padded(3))
                fmtReader.tryRead("ww") -> append(dd.weekOfYear.padded(2))
                fmtReader.tryRead("D") -> dd.dayOfWeek.also { append(if (it == 0) 7 else it) } // 3.2.2 The	week calendar
                fmtReader.tryRead("hh") -> {
                    val nextComma = fmtReader.tryRead(',')
                    val result = if (nextComma || fmtReader.tryRead('.')) {
                        var decCount = 0
                        while (fmtReader.tryRead('h')) decCount++
                        dd.time.hours.padded(2, decCount)
                    } else {
                        dd.hours.padded(2)
                    }
                    append(if (nextComma) result.replace('.', ',') else result)
                }
                fmtReader.tryRead("mm") -> {
                    val nextComma = fmtReader.tryRead(',')
                    val result = if (nextComma || fmtReader.tryRead('.')) {
                        var decCount = 0
                        while (fmtReader.tryRead('m')) decCount++
                        (dd.time.minutes % 60.0).padded(2, decCount)
                    } else {
                        dd.minutes.padded(2)
                    }
                    append(if (nextComma) result.replace('.', ',') else result)
                }
                fmtReader.tryRead("ss") -> {
                    val nextComma = fmtReader.tryRead(',')
                    val result = if (nextComma || fmtReader.tryRead('.')) {
                        var decCount = 0
                        while (fmtReader.tryRead('s')) decCount++
                        (dd.time.seconds % 60.0).padded(2, decCount)
                    } else {
                        dd.seconds.padded(2)
                    }
                    append(if (nextComma) result.replace('.', ',') else result)
                }
                fmtReader.tryRead("±") -> append(if (dd.years < 0) "-" else "+")
                fmtReader.tryRead("T") -> append('T').also { time = true }
                fmtReader.tryRead("nnY") -> append(dd.years).append('Y')
                fmtReader.tryRead("nnM") -> append(if (time) dd.minutes else dd.months).append('M')
                fmtReader.tryRead("nnD") -> append(dd.days).append('D')
                fmtReader.tryRead("nnH") -> append(dd.hours).append('H')
                fmtReader.tryRead("nnS") -> append(dd.seconds).append('S')
                else -> append(fmtReader.readChar())
            }
        }
    }

    override fun tryParseComponents(str: String, doThrow: Boolean, dateDefaults: Boolean): DateComponents? {
        return _tryParse(str, dateDefaults).also {
            if (doThrow && it == null) throw DateException("Can't parse $str with $format")
        }
    }

    private fun reportParse(reason: String): DateComponents? {
        //println("reason: $reason")
        return null
    }

    private fun _tryParse(str: String, dateDefaults: Boolean): DateComponents? {
        var sign = +1
        var tzOffset: Duration? = null
        var years = if (dateDefaults) twoDigitBaseYear else 0
        var months = if (dateDefaults) 1 else 0
        var days = if (dateDefaults) 1 else 0

        var dayOfWeek = DateComponents.UNSET
        var dayOfYear = DateComponents.UNSET
        var weekOfYear = DateComponents.UNSET

        var hours = 0.0
        var minutes = 0.0
        var seconds = 0.0

        var ztime = false

        val reader = MicroStrReader(str)
        val fmtReader = MicroStrReader(format)

        while (fmtReader.hasMore) {
            when {
                fmtReader.tryRead("Z") -> tzOffset = reader.readTimeZoneOffset()
                fmtReader.tryRead("YYYYYY") -> years = reader.tryReadInt(6) ?: return reportParse("YYYYYY")
                fmtReader.tryRead("YYYY") -> years = reader.tryReadInt(4) ?: return reportParse("YYYY")
                //fmtReader.tryRead("YY") -> year = twoDigitBaseYear + (reader.tryReadInt(2) ?: return null) // @TODO: Kotlin compiler BUG?
                fmtReader.tryRead("YY") -> {
                    val base = reader.tryReadInt(2) ?: return reportParse("YY")
                    years = (twoDigitBaseYear + base)
                }
                fmtReader.tryRead("MM") -> months = reader.tryReadInt(2) ?: return reportParse("MM")
                fmtReader.tryRead("DD") -> days = reader.tryReadInt(2) ?: return reportParse("DD")
                fmtReader.tryRead("DDD") -> dayOfYear = reader.tryReadInt(3) ?: return reportParse("DDD")
                fmtReader.tryRead("ww") -> weekOfYear = reader.tryReadInt(2) ?: return reportParse("ww")
                fmtReader.tryRead("D") -> dayOfWeek = (reader.tryReadInt(1) ?: return reportParse("D")) % 7

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
                fmtReader.tryRead("nn,nnY") || fmtReader.tryRead("nnY") -> {
                    years = reader.tryReadDouble()?.toInt() ?: return null
                    if (!reader.tryRead("Y")) return null
                }
                fmtReader.tryRead("nn,nnM") || fmtReader.tryRead("nnM") -> {
                    if (ztime) {
                        minutes = reader.tryReadDouble() ?: return null
                    } else {
                        months = reader.tryReadDouble()?.toInt() ?: return null
                    }
                    if (!reader.tryRead("M")) return null
                }
                fmtReader.tryRead("nn,nnD") || fmtReader.tryRead("nnD") -> {
                    days = reader.tryReadDouble()?.toInt() ?: return null
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
                fmtReader.tryRead("±") -> {
                    sign = when (reader.readChar()) {
                        '+' -> +1
                        '-' -> -1
                        else -> return reportParse("±")
                    }
                }
                else -> {
                    val char = fmtReader.readChar()
                    if (char != reader.readChar()) return reportParse("separator")
                    if (char == 'T') ztime = true
                }
            }
        }
        if (reader.hasMore) return reportParse("uncomplete")

        val duration = hours.hours + minutes.minutes + seconds.seconds
        val time = ComputedTime(duration)

        return DateComponents(
            years, months, days, time.hoursIncludingDaysAndWeeks,
            time.minutes, time.seconds, time.milliseconds.toInt(),
            tzOffset ?: Duration.ZERO, dayOfWeek, dayOfYear, weekOfYear
        )
    }

    fun withTwoDigitBaseYear(twoDigitBaseYear: Int = 1900) = IsoPatternDateFormat(format, twoDigitBaseYear)
}

data class IsoPatternDateFormatEx(val basicFormat: String?, val extendedFormat: String?) : DateFormat {
    companion object {
        operator fun invoke(extendedFormat: String): IsoPatternDateFormatEx {
            val basicFormat = extendedFormat.replace(":", "").replace("-", "")
            return IsoPatternDateFormatEx(basicFormat, if (extendedFormat != basicFormat) extendedFormat else null)
        }
    }

    val basic = IsoPatternDateFormat(basicFormat ?: extendedFormat ?: TODO())
    val extended = IsoPatternDateFormat(extendedFormat ?: basicFormat ?: TODO())

    override fun format(dd: DateComponents): String = extended.format(dd)
    override fun tryParseComponents(str: String, doThrow: Boolean, dateDefaults: Boolean): DateComponents? =
        basic.tryParseComponents(str, false, dateDefaults) ?: extended.tryParseComponents(str, false, dateDefaults)
        ?: (if (doThrow) throw DateException("Invalid format $str") else null)
}
