package korlibs.time

import korlibs.time.internal.MicroStrReader
import korlibs.time.internal.padded
import korlibs.time.internal.readTimeZoneOffset
import kotlin.math.absoluteValue
import kotlin.time.*

// https://en.wikipedia.org/wiki/ISO_8601
object ISO8601 : DateComponentsFormat {
    // Date Calendar Variants
    val DATE_CALENDAR_COMPLETE = ISODateFormatEx("YYYY-MM-DD")
    val DATE_CALENDAR_REDUCED0 = ISODateFormatEx("YYYY-MM")
    val DATE_CALENDAR_REDUCED1 = ISODateFormatEx("YYYY")
    val DATE_CALENDAR_REDUCED2 = ISODateFormatEx("YY")
    val DATE_CALENDAR_EXPANDED0 = ISODateFormatEx("±YYYYYY-MM-DD")
    val DATE_CALENDAR_EXPANDED1 = ISODateFormatEx("±YYYYYY-MM")
    val DATE_CALENDAR_EXPANDED2 = ISODateFormatEx("±YYYYYY")
    val DATE_CALENDAR_EXPANDED3 = ISODateFormatEx("±YYY")

    // Date Ordinal Variants
    val DATE_ORDINAL_COMPLETE = ISODateFormatEx("YYYY-DDD")
    val DATE_ORDINAL_EXPANDED = ISODateFormatEx("±YYYYYY-DDD")

    // Date Week Variants
    val DATE_WEEK_COMPLETE = ISODateFormatEx("YYYY-Www-D")
    val DATE_WEEK_REDUCED = ISODateFormatEx("YYYY-Www")
    val DATE_WEEK_EXPANDED0 = ISODateFormatEx("±YYYYYY-Www-D")
    val DATE_WEEK_EXPANDED1 = ISODateFormatEx("±YYYYYY-Www")

    val DATE_ALL = listOf(
        DATE_CALENDAR_COMPLETE, DATE_CALENDAR_REDUCED0, DATE_CALENDAR_REDUCED1, DATE_CALENDAR_REDUCED2,
        DATE_CALENDAR_EXPANDED0, DATE_CALENDAR_EXPANDED1, DATE_CALENDAR_EXPANDED2, DATE_CALENDAR_EXPANDED3,
        DATE_ORDINAL_COMPLETE, DATE_ORDINAL_EXPANDED,
        DATE_WEEK_COMPLETE, DATE_WEEK_REDUCED, DATE_WEEK_EXPANDED0, DATE_WEEK_EXPANDED1
    )

    // Time Variants
    val TIME_LOCAL_COMPLETE = ISOTimeFormatEx("hh:mm:ss")
    val TIME_LOCAL_REDUCED0 = ISOTimeFormatEx("hh:mm")
    val TIME_LOCAL_REDUCED1 = ISOTimeFormatEx("hh")
    val TIME_LOCAL_FRACTION0 = ISOTimeFormatEx("hh:mm:ss,ss")
    val TIME_LOCAL_FRACTION1 = ISOTimeFormatEx("hh:mm,mm")
    val TIME_LOCAL_FRACTION2 = ISOTimeFormatEx("hh,hh")

    // Time UTC Variants
    val TIME_UTC_COMPLETE = ISOTimeFormatEx("hh:mm:ssZ")
    val TIME_UTC_REDUCED0 = ISOTimeFormatEx("hh:mmZ")
    val TIME_UTC_REDUCED1 = ISOTimeFormatEx("hhZ")
    val TIME_UTC_FRACTION0 = ISOTimeFormatEx("hh:mm:ss,ssZ")
    val TIME_UTC_FRACTION1 = ISOTimeFormatEx("hh:mm,mmZ")
    val TIME_UTC_FRACTION2 = ISOTimeFormatEx("hh,hhZ")

    // Time Relative Variants
    val TIME_RELATIVE0 = ISOTimeFormatEx("±hh:mm")
    val TIME_RELATIVE1 = ISOTimeFormatEx("±hh")

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
    val DATETIME_COMPLETE = ISODateFormatEx("YYYY-MM-DDThh:mm:ss")
    val DATETIME_UTC_COMPLETE = ISODateFormatEx("YYYY-MM-DDThh:mm:ssZ")
    val DATETIME_UTC_COMPLETE_FRACTION = ISODateFormatEx("YYYY-MM-DDThh:mm:ss.sssZ")

    // Interval Variants
    val INTERVAL_COMPLETE0 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnnMnnS")
    val INTERVAL_COMPLETE1 = ISODateTimeSpanFormat("PnnYnnW")

    val INTERVAL_REDUCED0 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnnM")
    val INTERVAL_REDUCED1 = ISODateTimeSpanFormat("PnnYnnMnnDTnnH")
    val INTERVAL_REDUCED2 = ISODateTimeSpanFormat("PnnYnnMnnD")
    val INTERVAL_REDUCED3 = ISODateTimeSpanFormat("PnnYnnM")
    val INTERVAL_REDUCED4 = ISODateTimeSpanFormat("PnnY")

    val INTERVAL_DECIMAL0 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnnMnn,nnS")
    val INTERVAL_DECIMAL1 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnn,nnM")
    val INTERVAL_DECIMAL2 = ISODateTimeSpanFormat("PnnYnnMnnDTnn,nnH")
    val INTERVAL_DECIMAL3 = ISODateTimeSpanFormat("PnnYnnMnn,nnD")
    val INTERVAL_DECIMAL4 = ISODateTimeSpanFormat("PnnYnn,nnM")
    val INTERVAL_DECIMAL5 = ISODateTimeSpanFormat("PnnYnn,nnW")
    val INTERVAL_DECIMAL6 = ISODateTimeSpanFormat("PnnY")

    val INTERVAL_ZERO_OMIT0 = ISODateTimeSpanFormat("PnnYnnDTnnHnnMnnS")
    val INTERVAL_ZERO_OMIT1 = ISODateTimeSpanFormat("PnnYnnDTnnHnnM")
    val INTERVAL_ZERO_OMIT2 = ISODateTimeSpanFormat("PnnYnnDTnnH")
    val INTERVAL_ZERO_OMIT3 = ISODateTimeSpanFormat("PnnYnnD")

    val INTERVAL_ALL = listOf(
        INTERVAL_COMPLETE0, INTERVAL_COMPLETE1,
        INTERVAL_REDUCED0, INTERVAL_REDUCED1, INTERVAL_REDUCED2, INTERVAL_REDUCED3, INTERVAL_REDUCED4,
        INTERVAL_DECIMAL0, INTERVAL_DECIMAL1, INTERVAL_DECIMAL2, INTERVAL_DECIMAL3, INTERVAL_DECIMAL4,
        INTERVAL_DECIMAL5, INTERVAL_DECIMAL6,
        INTERVAL_ZERO_OMIT0, INTERVAL_ZERO_OMIT1, INTERVAL_ZERO_OMIT2, INTERVAL_ZERO_OMIT3
    )

    // Detects and parses all the variants
    val DATE = this.toDateFormat()
    val TIME = this.toTimeFormat()
    val INTERVAL = this.toDateTimeSpanFormat()

    override fun format(dd: DateComponents): String = when (dd.mode) {
        DateComponents.Mode.DATE -> DATETIME_COMPLETE.extended.format.format(dd)
        DateComponents.Mode.TIME -> TIME_LOCAL_COMPLETE.extended.format.format(dd)
        DateComponents.Mode.DATE_TIME_SPAN -> INTERVAL_DECIMAL0.format.format(dd)
    }

    override fun tryParse(str: String, mode: DateComponents.Mode?, doThrow: Boolean): DateComponents? {
        val r = MicroStrReader(str)
        var time = false
        var timeZoneSign: Int? = null

        val sign = when {
            r.tryRead('+') -> +1
            r.tryRead('-') -> -1
            else -> +1
        }

        // DateTimeSpan
        if (r.tryRead('P')) {
            var years = 0.0
            var minutes = 0.0
            var months = 0.0
            var weeks = 0.0
            var days = 0.0
            var hours = 0.0
            var seconds = 0.0

            while (r.hasMore) {
                if (r.tryRead('T')) time = true
                val num = r.tryReadDouble() ?: continue
                val kind = r.readChar()
                when (kind) {
                    'Y' -> years = num
                    'M' -> if (time) minutes = num else months = num
                    'W' -> weeks = num
                    'D' -> days = num
                    'H' -> hours = num
                    'S' -> seconds = num
                }
            }

            val span = DateTimeSpan(((years * 12) + months).toInt().months, weeks.weeks + days.days + hours.hours + minutes.minutes + seconds.seconds)
            return DateComponents(mode = DateComponents.Mode.DATE_TIME_SPAN, years = span.years, months = span.months, days = span.daysIncludingWeeks, hours = span.hours, minutes = span.minutes, seconds = span.seconds, nanoseconds = span.nanoseconds, sign = sign)
        }

        data class Item(val value: Double, val ndigits: Int, val time: Boolean, val isWeek: Boolean, val timeZoneSign: Int?)

        val params = arrayListOf<Item>()

        while (r.hasMore) {
            val isWeek = r.tryRead('W')
            val c = r.peekCharOrZero()
            when (c) {
                in '0'..'9' -> {
                    val start = r.offset
                    val value = r.tryReadDouble() ?: 0.0
                    val ndigits = r.offset - start
                    check(ndigits > 0)
                    if (r.peekCharOrZero() == ':') time = true
                    if (r.peekCharOrZero() == '-') time = false
                    params += Item(value, ndigits, time, isWeek, timeZoneSign)
                }
                '.', ',', '+', '-', ':', 'T' -> {
                    if (c == 'T') time = true
                    if (time) {
                        if (c == '+') timeZoneSign = +1
                        if (c == '-') timeZoneSign = -1
                    }
                    r.readChar()
                }
                else -> throw IllegalArgumentException("Unexpected character '$c'")
            }
        }

        var years: Int? = null
        var months: Int? = null
        var weeks: Int? = null
        var weekDay: Int? = null
        var dayOfYear: Int? = null
        var days: Int? = null
        var hours: Double? = null
        var minutes: Double? = null
        var seconds: Double? = null
        var tzHours: Double? = null
        var tzMinutes: Double? = null
        var tzSign: Int? = null
        for (param in params) {
            val (value, ndigits, time, isWeek, timeZoneSign) = param
            when {
                isWeek -> weeks = value.toInt()
                !time -> {
                    when {
                        years == null -> years = value.toInt()
                        weeks != null -> weekDay = value.toInt() // For YYYY-Www-D
                        ndigits >= 3 -> dayOfYear = value.toInt() // For YYYY-DDD
                        months == null -> months = value.toInt()
                        days == null -> days = value.toInt()
                    }
                }
                timeZoneSign != null -> {
                    tzSign = timeZoneSign
                    when {
                        tzHours == null -> tzHours = value
                        tzMinutes == null -> tzMinutes = value
                    }
                }
                else -> {
                    when {
                        hours == null -> hours = value
                        minutes == null -> minutes = value
                        seconds == null -> seconds = value
                    }
                }
            }
        }

        //println("$years, $months, $days, $weeks, $weekDay, $hours, $minutes, $seconds, $tzSign, $tzHours, $tzMinutes")
        //println(params.joinToString("\n"))

        val year = years ?: 0

        val date = when {
            dayOfYear != null -> Date.fromDayOfYear(year, dayOfYear)
            weeks != null -> Date.fromWeekAndDay(year, weeks, weekDay ?: 0)
            else -> Date(year, months ?: 1, days ?: 1)
        }

        val span = ComputedTime((hours ?: 0.0).hours + (minutes ?: 0.0).minutes + (seconds ?: 0.0).seconds)

        return DateComponents(
            mode ?: DateComponents.Mode.DATE,
            years = date.year,
            months = date.month1,
            days = date.day,
            hours = span.hoursIncludingDaysAndWeeks,
            minutes = span.minutes,
            seconds = span.seconds,
            nanoseconds = span.nanoseconds,
            offset = tzHours?.let { (tzHours.hours + (tzMinutes ?: 0.0).minutes) * (tzSign ?: 1) },
            sign = sign,
        )
    }
}

data class ISOTimeFormatEx(val basicFormat: String?, val extendedFormat: String?) : TimeFormat {
    companion object {
        operator fun invoke(extendedFormat: String): ISOTimeFormatEx = ISOTimeFormatEx(
            extendedFormat.replace("-", "").replace(":", ""),
            extendedFormat
        )
    }
    val basic = ISOTimeFormat(basicFormat ?: extendedFormat ?: TODO())
    val extended = ISOTimeFormat(extendedFormat ?: basicFormat ?: TODO())

    override fun format(dd: Duration): String = extended.format(dd)
    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? =
        basic.tryParse(str, false, doAdjust) ?: extended.tryParse(str, false, doAdjust)
        ?: (if (doThrow) throw DateException("Invalid format $str") else null)
}

data class ISODateFormatEx(val basicFormat: String?, val extendedFormat: String?) : DateFormat {
    companion object {
        operator fun invoke(extendedFormat: String): ISODateFormatEx = ISODateFormatEx(
            extendedFormat.replace("-", "").replace(":", ""),
            extendedFormat
        )
    }

    val basic = ISODateFormat(basicFormat ?: extendedFormat ?: TODO())
    val extended = ISODateFormat(extendedFormat ?: basicFormat ?: TODO())

    override fun format(dd: DateTimeTz): String = extended.format(dd)
    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? = null
        ?: basic.tryParse(str, false, doAdjust)
        ?: extended.tryParse(str, false, doAdjust)
        ?: (if (doThrow) throw DateException("Invalid format $str") else null)
}

data class ISOTimeFormat(val format: ISODateComponentsFormat) : TimeFormat by format.toTimeFormat() {
    constructor(format: String) : this(ISODateComponentsFormat(format))
}

data class ISODateTimeSpanFormat(val format: ISODateComponentsFormat) : DateTimeSpanFormat by format.toDateTimeSpanFormat() {
    constructor(format: String) : this(ISODateComponentsFormat(format))
}

data class ISODateFormat(val format: ISODateComponentsFormat) : DateFormat by format.toDateFormat() {
    constructor(format: String, twoDigitBaseYear: Int = 1900) : this(ISODateComponentsFormat(format, twoDigitBaseYear))
}

class ISODateComponentsFormat(val format: String, val twoDigitBaseYear: Int = 1900) : DateComponentsFormat {
    override fun format(dd: DateComponents): String = buildString {
        val time = dd.hours.hours + dd.minutes.minutes + dd.seconds.seconds + dd.nanoseconds.nanoseconds
        val fmtReader = MicroStrReader(format)
        var ttime = false
        while (fmtReader.hasMore) {
            when {
                fmtReader.tryRead("Z") -> {
                    //if (dd.offset != TimezoneOffset.UTC) {
                    if (dd.timezoneOffset != TimezoneOffset.UTC) {
                        dd.timezoneOffset.deltaHoursAbs
                        append(if (dd.timezoneOffset.positive) "+" else "-")
                        append(dd.timezoneOffset.deltaHoursAbs.padded(2))
                        append(":")
                        append(dd.timezoneOffset.deltaMinutesAbs.padded(2))
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
                fmtReader.tryRead("ww") -> append(dd.date.weekOfYear1.padded(2))
                fmtReader.tryRead("D") -> append(dd.dayOfWeek.index1Monday)
                fmtReader.tryRead("hh") -> {
                    val nextComma = fmtReader.tryRead(',')
                    val result = if (nextComma || fmtReader.tryRead('.')) {
                        var decCount = 0
                        while (fmtReader.tryRead('h')) decCount++
                        time.hours.padded(2, decCount)
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
                        (time.minutes % 60.0).padded(2, decCount)
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
                        (time.seconds % 60.0).padded(2, decCount)
                    } else {
                        dd.seconds.padded(2)
                    }
                    append(if (nextComma) result.replace('.', ',') else result)
                }

                fmtReader.tryRead("±") -> append(if (dd.years < 0) "-" else "+")

                fmtReader.tryRead("T") -> append('T').also { ttime = true }
                fmtReader.tryRead("nnY") -> append(dd.years).append('Y')
                fmtReader.tryRead("nnM") -> append(if (ttime) dd.minutes else dd.months).append('M')
                fmtReader.tryRead("nnD") -> append(dd.days).append('D')
                fmtReader.tryRead("nnH") -> append(dd.hours).append('H')
                fmtReader.tryRead("nnS") -> append(dd.seconds).append('S')

                else -> append(fmtReader.readChar())
            }
        }
    }


    override fun tryParse(str: String, mode: DateComponents.Mode?, doThrow: Boolean): DateComponents? {
        var sign = +1
        var tzOffset: Duration? = null
        var year: Int? = null
        var month: Int? = null
        var dayOfMonth: Int? = null

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

        val rmode = mode ?: (if (year != null) DateComponents.Mode.DATE else DateComponents.Mode.TIME)

        val date: Date = when {
            year == null -> Date(0, 0, 0)
            rmode != DateComponents.Mode.DATE -> Date(year, month ?: 0, dayOfMonth ?: 0)
            dayOfYear >= 0 -> Date.fromDayOfYear(year, dayOfYear)
            weekOfYear >= 0 -> Date.fromWeekAndDay(year, weekOfYear, dayOfWeek)
            else -> Date(year, month ?: 1, dayOfMonth ?: 1)
        }
        val span = ComputedTime(hours.hours + minutes.minutes + seconds.seconds)

        return DateComponents(
            mode = rmode,
            years = date.year,
            months = date.month1,
            days = date.day,
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

val Date.weekOfYear0: Int
    get() {
        val firstThursday = yearYear.first(DayOfWeek.Thursday)
        val offset = firstThursday.dayOfMonth - 3
        return (dayOfYear - offset) / 7
    }

val Date.weekOfYear1: Int get() = weekOfYear0 + 1

val DateTime.weekOfYear0: Int get() = date.weekOfYear0
val DateTime.weekOfYear1: Int get() = date.weekOfYear1
val DateTimeTz.weekOfYear0: Int get() = local.weekOfYear0
val DateTimeTz.weekOfYear1: Int get() = local.weekOfYear1
