package korlibs.time

import korlibs.Serializable
import korlibs.time.internal.*
import korlibs.time.internal.MicroStrReader
import korlibs.time.internal.increment
import korlibs.time.internal.padded
import korlibs.time.internal.readTimeZoneOffset
import korlibs.time.internal.substr
import kotlin.math.absoluteValue
import kotlin.math.log10
import kotlin.math.pow

data class PatternDateFormat(
    val format: String,
    val locale: KlockLocale? = null,
    val tzNames: TimezoneNames = TimezoneNames.DEFAULT,
) : BasePatternDateTimeFormat(format), DateFormat, Serializable {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    val realLocale get() = locale ?: KlockLocale.default

    fun withLocale(locale: KlockLocale?) = this.copy(locale = locale)
    fun withTimezoneNames(tzNames: TimezoneNames) = this.copy(tzNames = this.tzNames + tzNames)

    override fun matchChunkToRegex(it: String): String? = matchDateChunkToRegex(it) ?: matchTimeChunkToRegex(it)

    // EEE, dd MMM yyyy HH:mm:ss z -- > Sun, 06 Nov 1994 08:49:37 GMT
    // YYYY-MM-dd HH:mm:ss

    override fun format(dd: DateTimeTz): String {
        val utc = dd.local
        val locale = realLocale
        return chunks.joinToString("") {
            formatDateChunk(it, dd, locale)
                ?: formatTimeChunk(it, locale, utc.hours, utc.minutes, utc.seconds, utc.milliseconds, clampHours = true)
                ?: formatElseChunk(it)
        }
    }

    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? {
        val info = _tryParseBase(str, doThrow, doAdjust, realLocale, tzNames) ?: return null
        if (!doAdjust) {
            if (info.month !in 1..12) if (doThrow) error("Invalid month ${info.month}") else return null
            if (info.day !in 1..32) if (doThrow) error("Invalid day ${info.day}") else return null
            if (info.hour !in 0..24) if (doThrow) error("Invalid hour ${info.hour}") else return null
            if (info.minute !in 0..59) if (doThrow) error("Invalid minute ${info.minute}") else return null
            if (info.second !in 0..59) if (doThrow) error("Invalid second ${info.second}") else return null
            if (info.millisecond !in 0 .. 999) if (doThrow) error("Invalid millisecond ${info.millisecond}") else return null
        }
        val dateTime = DateTime.createAdjusted(info.fullYear, info.month, info.day, info.hour umod 24, info.minute, info.second, info.millisecond)
        return dateTime.toOffsetUnadjusted(info.offset ?: 0.hours)
    }

    override fun toString(): String = format
}
