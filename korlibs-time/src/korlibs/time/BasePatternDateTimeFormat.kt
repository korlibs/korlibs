package korlibs.time

import korlibs.time.internal.*
import kotlin.jvm.*
import kotlin.math.*
import kotlin.time.*

abstract class BasePatternDateTimeFormat(
    val baseFormat: String,
    private val optionalSupport: Boolean,
) {
    private val openOffsets = LinkedHashMap<Int, Int>()
    private val closeOffsets = LinkedHashMap<Int, Int>()

    internal val chunks by lazy {
        arrayListOf<String>().also { chunks ->
            val s = MicroStrReader(baseFormat)
            while (s.hasMore) {
                if (s.peekChar() == '\'') {
                    val escapedChunk = s.readChunk {
                        s.tryRead('\'')
                        while (s.hasMore && s.readChar() != '\'') Unit
                    }
                    chunks.add(escapedChunk)
                    continue
                }
                if (optionalSupport) {
                    val offset = chunks.size
                    if (s.tryRead('[')) {
                        openOffsets.increment(offset)
                        continue
                    }
                    if (s.tryRead(']')) {
                        closeOffsets.increment(offset - 1)
                        continue
                    }
                }
                chunks.add(s.tryReadOrNull("do") ?: s.tryReadOrNull("S*") ?: s.readRepeatedChar())
            }
        }.toList()
    }

    internal val regexChunks by lazy {
        chunks.map {
            matchChunkToRegex(it) ?: when {
                it.startsWith('\'') -> "(" + Regex.escape(it.substr(1, it.length - 2)) + ")"
                else -> "(" + Regex.escape(it) + ")"
            }
        }
    }

    //val escapedFormat = Regex.escape(format)
    internal val rx2: Regex by lazy { Regex("^${matchingRegexString()}$") }


    /**
     * @return the regular expression string used for matching this format, able to be composed into another regex
     */
    fun matchingRegexString(): String = regexChunks.mapIndexed { index, it ->
        if (optionalSupport) {
            val opens = openOffsets.getOrElse(index) { 0 }
            val closes = closeOffsets.getOrElse(index) { 0 }
            buildString {
                repeat(opens) { append("(?:") }
                append(it)
                repeat(closes) { append(")?") }
            }
        } else {
            it
        }
    }.joinToString("")

    protected abstract fun matchChunkToRegex(it: String): String?

    protected fun matchDateChunkToRegex(it: String): String? {
        return when (it) {
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
    }

    protected fun matchTimeChunkToRegex(it: String): String? {
        return when (it) {
            "H", "k" -> """(\d{1,})"""
            "HH", "kk" -> """(\d{2,})"""
            "h", "K" -> """(\d{1,2})"""
            "hh", "KK" -> """(\d{2})"""
            "m" -> """(\d{1,2})"""
            "mm" -> """(\d{2})"""
            "s" -> """(\d{1,2})"""
            "ss" -> """(\d{2})"""
            "S*", "S", "SS", "SSS", "SSSS", "SSSSS", "SSSSSS", "SSSSSSS", "SSSSSSSS", "SSSSSSSSS" -> """(\d{1,9})"""
            "a" -> """(\w+)"""
            " " -> """(\s+)"""
            else -> null
        }
    }

    protected data class DateInfo(
        var fullYear: Int = 1970,
        var month: Int = 1,
        var day: Int = 1,
        var hour: Int = 0,
        var minute: Int = 0,
        var second: Int = 0,
        var millisecond: Int = 0,
        var offset: TimeSpan? = null,
    )

    protected fun _tryParseBase(str: String, doThrow: Boolean, doAdjust: Boolean, realLocale: KlockLocale, tzNames: TimezoneNames): DateInfo? {
        var millisecond = 0
        var second = 0
        var minute = 0
        var hour = 0
        var day = 1
        var month = 1
        var fullYear = 1970
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
                "d", "dd" -> day = value.toInt()
                "do" -> day = realLocale.getDayByOrdinal(value)
                "M", "MM" -> month = value.toInt()
                "MMM" -> month = realLocale.monthsShort.indexOf(value) + 1
                "y", "yyyy", "YYYY" -> fullYear = value.toInt()
                "yy" -> if (doThrow) throw RuntimeException("Not guessing years from two digits.") else return null
                "yyy" -> fullYear = value.toInt() + if (value.toInt() < 800) 2000 else 1000 // guessing year...
                "H", "HH", "k", "kk" -> hour = value.toInt()
                "h", "hh", "K", "KK" -> {
                    hour = value.toInt()
                    is12HourFormat = true
                }
                "m", "mm" -> minute = value.toInt()
                "s", "ss" -> second = value.toInt()
                "S*" -> millisecond = ("0.${value}".toDouble() * 1000).toInt()
                "S", "SS", "SSS", "SSSS", "SSSSS", "SSSSSS", "SSSSSSS", "SSSSSSSS", "SSSSSSSSS" -> millisecond = value.toInt()
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

        return DateInfo(fullYear, month, day, hour, minute, second, millisecond, offset)
    }

    companion object {
        @JvmStatic
        protected fun formatElseChunk(name: String): String {
            return if (name.startsWith('\'')) name.substring(1, name.length - 1) else name
        }

        @JvmStatic
        protected fun formatDateChunk(name: String, dd: DateTimeTz, realLocale: KlockLocale): String? {
            val utc = dd.local
            val nlen = name.length
            return when (name) {
                "E", "EE", "EEE" -> DayOfWeek[utc.dayOfWeek.index0].localShortName(realLocale)
                "EEEE", "EEEEE", "EEEEEE" -> DayOfWeek[utc.dayOfWeek.index0].localName(realLocale)
                "z", "zzz" -> dd.offset.timeZone
                "d", "dd" -> utc.dayOfMonth.padded(nlen)
                "do" -> realLocale.getOrdinalByDay(utc.dayOfMonth)
                "M", "MM" -> utc.month1.padded(nlen)
                "MMM" -> Month[utc.month1].localName(realLocale).substr(0, 3)
                "MMMM" -> Month[utc.month1].localName(realLocale)
                "MMMMM" -> Month[utc.month1].localName(realLocale).substr(0, 1)
                "y" -> utc.yearInt.toString()
                "yy" -> (utc.yearInt % 100).padded(2)
                "yyy" -> (utc.yearInt % 1000).padded(3)
                "yyyy" -> utc.yearInt.padded(4)
                "YYYY" -> utc.yearInt.padded(4)

                "X", "XX", "XXX", "x", "xx", "xxx" -> {
                    when {
                        name.startsWith("X") && dd.offset.totalMinutesInt == 0 -> "Z"
                        else -> {
                            val p = if (dd.offset.totalMinutesInt >= 0) "+" else "-"
                            val hours = (dd.offset.totalMinutesInt / 60).absoluteValue
                            val minutes = (dd.offset.totalMinutesInt % 60).absoluteValue
                            when (name) {
                                "X", "x" -> "$p${hours.padded(2)}"
                                "XX", "xx" -> "$p${hours.padded(2)}${minutes.padded(2)}"
                                "XXX", "xxx" -> "$p${hours.padded(2)}:${minutes.padded(2)}"
                                else -> name
                            }
                        }
                    }
                }
                else -> null
            }
        }

        @JvmStatic
        protected fun formatTimeChunk(name: String, realLocale: KlockLocale, hour: Int, minute: Int, second: Int, millisecond: Int, clampHours: Boolean): String? {
            val nlen = name.length
            return when (name) {
                "H", "HH" -> (if (clampHours) mconvertRangeZero(hour, 24) else hour).padded(nlen)
                "k", "kk" -> (if (clampHours) mconvertRangeNonZero(hour, 24) else hour).padded(nlen)

                "h", "hh" -> mconvertRangeNonZero(hour, 12).padded(nlen)
                "K", "KK" -> mconvertRangeZero(hour, 12).padded(nlen)

                "m", "mm" -> minute.padded(nlen)
                "s", "ss" -> second.padded(nlen)

                "S*" -> (millisecond.toDouble() / 1000).toString().removePrefix("0.").trimStart('0').takeIf { it.isNotEmpty() } ?: "0"
                "S", "SS", "SSS", "SSSS", "SSSSS", "SSSSSS", "SSSSSSS", "SSSSSSSS", "SSSSSSSSS" -> {
                    val res = millisecond.toString()
                    if (res.length < name.length) "000000000$millisecond".takeLast(name.length) else res
                }
                //"a" -> if (hour < 12) "am" else if (hour < 24) "pm" else ""
                "a" -> realLocale.h12Marker[if (hour < 12) 0 else 1]
                else -> null
            }
        }

        private fun mconvertRangeZero(value: Int, size: Int): Int {
            return (value umod size)
        }

        private fun mconvertRangeNonZero(value: Int, size: Int): Int {
            val res = (value umod size)
            return if (res == 0) size else res
        }
    }
}