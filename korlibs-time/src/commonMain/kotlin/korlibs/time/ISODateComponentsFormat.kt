package korlibs.time

import korlibs.time.internal.*
import kotlin.math.*
import kotlin.time.*

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
                fmtReader.tryRead("YYYYYY") -> year = reader.tryReadInt(6) ?: return reportParse("YYYYYY", doThrow)
                fmtReader.tryRead("YYYY") -> year = reader.tryReadInt(4) ?: return reportParse("YYYY", doThrow)
                //fmtReader.tryRead("YY") -> year = twoDigitBaseYear + (reader.tryReadInt(2) ?: return null) // @TODO: Kotlin compiler BUG?
                fmtReader.tryRead("YY") -> {
                    val base = reader.tryReadInt(2) ?: return reportParse("YY", doThrow)
                    year = twoDigitBaseYear + base
                }
                fmtReader.tryRead("MM") -> month = reader.tryReadInt(2) ?: return reportParse("MM", doThrow)
                fmtReader.tryRead("DD") -> dayOfMonth = reader.tryReadInt(2) ?: return reportParse("DD", doThrow)
                fmtReader.tryRead("DDD") -> dayOfYear = reader.tryReadInt(3) ?: return reportParse("DDD", doThrow)
                fmtReader.tryRead("ww") -> weekOfYear = reader.tryReadInt(2) ?: return reportParse("ww", doThrow)
                fmtReader.tryRead("D") -> dayOfWeek = reader.tryReadInt(1) ?: return reportParse("D", doThrow)

                fmtReader.tryRead("hh") -> {
                    val nextComma = fmtReader.tryRead(',')
                    hours = if (nextComma || fmtReader.tryRead('.')) {
                        var count = 3
                        while (fmtReader.tryRead('h')) count++
                        reader.tryReadDouble(count) ?: return reportParse("incorrect hours", doThrow)
                    } else {
                        reader.tryReadDouble(2) ?: return reportParse("incorrect hours", doThrow)
                    }
                }
                fmtReader.tryRead("mm") -> {
                    val nextComma = fmtReader.tryRead(',')
                    minutes = if (nextComma || fmtReader.tryRead('.')) {
                        var count = 3
                        while (fmtReader.tryRead('m')) count++
                        reader.tryReadDouble(count) ?: return reportParse("incorrect minutes", doThrow)
                    } else {
                        reader.tryReadDouble(2) ?: return reportParse("incorrect seconds", doThrow)
                    }
                }
                fmtReader.tryRead("ss") -> {
                    val nextComma = fmtReader.tryRead(',')
                    seconds = if (nextComma || fmtReader.tryRead('.')) {
                        var count = 3
                        while (fmtReader.tryRead('s')) count++
                        reader.tryReadDouble() ?: return reportParse("incorrect seconds", doThrow)
                    } else {
                        reader.tryReadDouble(2) ?: return reportParse("incorrect seconds", doThrow)
                    }
                }
                fmtReader.tryRead("±") -> {
                    sign = when (reader.readChar()) {
                        '+' -> +1
                        '-' -> -1
                        else -> return reportParse("±", doThrow)
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
                    if (char != reader.readChar()) return reportParse("separator", doThrow)
                    if (char == 'T') time = true
                }
            }
        }
        if (reader.hasMore) return reportParse("uncomplete", doThrow)

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

    companion object {
        fun tryParseGeneric(str: String, mode: DateComponents.Mode?, doThrow: Boolean): DateComponents? {
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
                    else -> return reportParse("Unexpected character '$c'", doThrow)
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

        private fun reportParse(reason: String, doThrow: Boolean): DateComponents? {
            if (doThrow) error(reason)
            //println("reason: $reason")
            return null
        }
    }
}
