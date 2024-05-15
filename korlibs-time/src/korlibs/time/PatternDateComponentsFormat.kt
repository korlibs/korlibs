package korlibs.time

import korlibs.time.internal.*
import kotlin.math.*
import kotlin.time.*

data class PatternDateComponentsFormat(
    val format: String,
    val locale: KlockLocale? = null,
    val tzNames: TimezoneNames = TimezoneNames.DEFAULT,
) : DateComponentsFormat {
    private val realLocale get() = locale ?: KlockLocale.default

    // EEE, dd MMM yyyy HH:mm:ss z -- > Sun, 06 Nov 1994 08:49:37 GMT
    // YYYY-MM-dd HH:mm:ss

    override fun format(dd: DateComponents): String {
        val locale = this.realLocale
        return chunks.joinToString("") { name ->
            val nlen = name.length
            when (name) {
                "E", "EE", "EEE" -> dd.dayOfWeekSure.localShortName(locale)
                "EEEE", "EEEEE", "EEEEEE" -> dd.dayOfWeekSure.localName(locale)
                "z", "zzz" -> dd.timezoneOffset.timeZone
                "d", "dd" -> dd.days.padded(nlen)
                "do" -> locale.getOrdinalByDay(dd.days)
                "M", "MM" -> dd.months.padded(nlen)
                "MMM" -> Month[dd.months].localName(locale).substr(0, 3)
                "MMMM" -> Month[dd.months].localName(locale)
                "MMMMM" -> Month[dd.months].localName(locale).substr(0, 1)
                "y" -> dd.years.toString()
                "yy" -> (dd.years % 100).padded(2)
                "yyy" -> (dd.years % 1000).padded(3)
                "yyyy" -> dd.years.padded(4)
                "YYYY" -> dd.years.padded(4)

                "X", "XX", "XXX", "x", "xx", "xxx" -> {
                    val offset = dd.timezoneOffset
                    when {
                        name.startsWith("X") && offset.totalMinutesInt == 0 -> "Z"
                        else -> {
                            val p = if (offset.totalMinutesInt >= 0) "+" else "-"
                            val hours = (offset.totalMinutesInt / 60).absoluteValue
                            val minutes = (offset.totalMinutesInt % 60).absoluteValue
                            when (name) {
                                "X", "x" -> "$p${hours.padded(2)}"
                                "XX", "xx" -> "$p${hours.padded(2)}${minutes.padded(2)}"
                                "XXX", "xxx" -> "$p${hours.padded(2)}:${minutes.padded(2)}"
                                else -> name
                            }
                        }
                    }
                }

                "H", "HH" -> (if (dd.clampHours) mconvertRangeZero(dd.hours, 24) else dd.hours).padded(nlen)
                "k", "kk" -> (if (dd.clampHours) mconvertRangeNonZero(dd.hours, 24) else dd.hours).padded(nlen)

                "h", "hh" -> mconvertRangeNonZero(dd.hours, 12).padded(nlen)
                "K", "KK" -> mconvertRangeZero(dd.hours, 12).padded(nlen)

                "m", "mm" -> dd.minutes.padded(nlen)
                "s", "ss" -> dd.seconds.padded(nlen)

                "S", "SS", "SSS", "SSSS", "SSSSS", "SSSSSS", "SSSSSSS", "SSSSSSSS", "SSSSSSSSS" -> {
                    ((dd.milliseconds % 1000) * 1_000_000).toString().padStart(9, '0').take(name.length)
                }
                //"a" -> if (hour < 12) "am" else if (hour < 24) "pm" else ""
                "a" -> locale.h12Marker[if (dd.hours < 12) 0 else 1]

                else -> if (name.startsWith('\'')) name.substring(1, name.length - 1) else name
            }
        }
    }

    override fun tryParse(str: String, setDate: Boolean?, doThrow: Boolean): DateComponents? {
        var nanoseconds = 0
        var second = 0
        var minute = 0
        var hour = 0
        var isetDate = false
        var day: Int? = null
        var month: Int? = null
        var fullYear: Int? = null
        var offset: Duration? = null
        var isPm = false
        var is12HourFormat = false
        val result = rx2.find(str) ?: return null //println("Parser error: Not match, $str, $rx2");
        for ((name, value) in chunks.zip(result.groupValues.drop(1))) {
            if (value.isEmpty()) continue

            when (name) {
                "E", "EE", "EEE", "EEEE", "EEEEE", "EEEEEE" -> Unit // day of week (Sun | Sunday)
                "z", "zzz" -> { // timezone (GMT)
                    offset = MicroStrReader(value).readTimeZoneOffset(tzNames)
                }
                "d", "dd" -> {
                    isetDate = true
                    day = value.toInt()
                }
                "do" -> {
                    isetDate = true
                    day = realLocale.getDayByOrdinal(value)
                }
                "M", "MM" -> {
                    isetDate = true
                    month = value.toInt()
                }
                "MMM" -> {
                    isetDate = true
                    month = realLocale.monthsShort.indexOf(value) + 1
                }
                "y", "yyyy", "YYYY" -> {
                    isetDate = true
                    fullYear = value.toInt()
                }
                "yy" -> if (doThrow) throw RuntimeException("Not guessing years from two digits.") else return null
                "yyy" -> fullYear = value.toInt() + if (value.toInt() < 800) 2000 else 1000 // guessing year...
                "H", "HH", "k", "kk" -> hour = value.toInt()
                "h", "hh", "K", "KK" -> {
                    hour = value.toInt()
                    is12HourFormat = true
                }
                "m", "mm" -> minute = value.toInt()
                "s", "ss" -> second = value.toInt()
                "S", "SS", "SSS", "SSSS", "SSSSS", "SSSSSS", "SSSSSSS", "SSSSSSSS", "SSSSSSSSS" -> nanoseconds = value.padEnd(9, '0').toInt()
                "X", "XX", "XXX", "x", "xx", "xxx" -> {
                    when {
                        name.startsWith("X") && value.first() == 'Z' -> offset = 0.hours
                        name.startsWith("x") && value.first() == 'Z' -> {
                            if (doThrow) throw RuntimeException("Zulu Time Zone is only accepted with X-XXX formats.") else return null
                        }
                        value.first() != 'Z' -> {
                            val valueUnsigned = value.replace(":", "").removePrefix("-").removePrefix("+")
                            val hours = when (name.length) {
                                1 -> valueUnsigned.toInt()
                                else -> valueUnsigned.take(2).toInt()
                            }
                            val minutes = when (name.length) {
                                1 -> 0
                                else -> valueUnsigned.drop(2).toInt()
                            }
                            offset = hours.hours + minutes.minutes
                            if (value.first() == '-') {
                                offset = -offset
                            }
                        }
                    }
                }
                "MMMM" -> month = realLocale.months.indexOf(value) + 1
                "MMMMM" -> if (doThrow) throw RuntimeException("Not possible to get the month from one letter.") else return null
                "a" -> isPm = value.equals("pm", ignoreCase = true)
                else -> {
                    // ...
                }
            }
        }
        //return DateTime.createClamped(fullYear, month, day, hour, minute, second)
        if (is12HourFormat) {
            if (isPm) {
                if (hour != 12) {
                    hour += 12
                }
            } else {
                if (hour == 12) {
                    hour = 0
                }
            }
        }

        val isDate = setDate ?: isetDate
        val defaultYear = if (isDate) 1970 else 0
        val defaultMonth = if (isDate) 1 else 0
        val defaultDay = if (isDate) 1 else 0

        return DateComponents(isDate = isDate, fullYear ?: defaultYear, month ?: defaultMonth, day ?: defaultDay, hour, minute, second, nanoseconds, offset)
    }


    private val openOffsets = LinkedHashMap<Int, Int>()
    private val closeOffsets = LinkedHashMap<Int, Int>()

    private val chunks by lazy {
        arrayListOf<String>().also { chunks ->
            val s = MicroStrReader(format)
            while (s.hasMore) {
                if (s.peekChar() == '\'') {
                    val escapedChunk = s.readChunk {
                        s.tryRead('\'')
                        while (s.hasMore && s.readChar() != '\'') Unit
                    }
                    chunks.add(escapedChunk)
                    continue
                }
                val offset = chunks.size
                if (s.tryRead('[')) {
                    openOffsets.increment(offset)
                    continue
                }
                if (s.tryRead(']')) {
                    closeOffsets.increment(offset - 1)
                    continue
                }
                chunks.add(s.tryReadOrNull("do") ?: s.readRepeatedChar())
            }
        }.toList()
    }

    private val regexChunks by lazy {
        chunks.map {
            matchChunkToRegex(it) ?: when {
                it.startsWith('\'') -> "(" + Regex.escape(it.substr(1, it.length - 2)) + ")"
                else -> "(" + Regex.escape(it) + ")"
            }
        }
    }

    //val escapedFormat = Regex.escape(format)
    private val rx2: Regex by lazy { Regex("^${matchingRegexString()}$") }

    /**
     * @return the regular expression string used for matching this format, able to be composed into another regex
     */
    fun matchingRegexString(): String = regexChunks.mapIndexed { index, it ->
        val opens = openOffsets.getOrElse(index) { 0 }
        val closes = closeOffsets.getOrElse(index) { 0 }
        buildString {
            repeat(opens) { append("(?:") }
            append(it)
            repeat(closes) { append(")?") }
        }
    }.joinToString("")

    private fun matchChunkToRegex(it: String): String? = matchDateChunkToRegex(it) ?: matchTimeChunkToRegex(it)

    private fun matchDateChunkToRegex(it: String): String? = when (it) {
        "E", "EE", "EEE", "EEEE", "EEEEE", "EEEEEE" -> """(\w+)"""
        "z", "zzz" -> """([\w\s\-\+:]+)"""
        "do" -> """(\d{1,2}\w+)"""
        "d" -> """(\d{1,2})"""
        "dd" -> """(\d{2})"""
        "M" -> """(\d{1,5})"""
        "MM" -> """(\d{2})"""
        "MMM", "MMMM", "MMMMM" -> """(\w+)"""
        "y" -> """(\d{1,5})"""
        "yy" -> """(\d{2})"""
        "yyy" -> """(\d{3})"""
        "yyyy" -> """(\d{4})"""
        "YYYY" -> """(\d{4})"""
        "X", "XX", "XXX", "x", "xx", "xxx", "Z" -> """([\w:\+\-]+)"""
        else -> null
    }

    private fun matchTimeChunkToRegex(it: String): String? = when (it) {
        "H", "k" -> """(\d{1,})"""
        "HH", "kk" -> """(\d{2,})"""
        "h", "K" -> """(\d{1,2})"""
        "hh", "KK" -> """(\d{2})"""
        "m" -> """(\d{1,2})"""
        "mm" -> """(\d{2})"""
        "s" -> """(\d{1,2})"""
        "ss" -> """(\d{2})"""
        "S", "SS", "SSS", "SSSS", "SSSSS", "SSSSSS", "SSSSSSS", "SSSSSSSS", "SSSSSSSSS" -> """(\d{1,9})"""
        "a" -> """(\w+)"""
        " " -> """(\s+)"""
        else -> null
    }

    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }
}

private fun mconvertRangeZero(value: Int, size: Int): Int = (value umod size)
private fun mconvertRangeNonZero(value: Int, size: Int): Int = (value umod size).let { if (it == 0) size else it }
