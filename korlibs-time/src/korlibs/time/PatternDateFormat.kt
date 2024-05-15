package korlibs.time

import korlibs.Serializable
import korlibs.time.internal.*

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
            if (info.months !in 1..12) if (doThrow) error("Invalid month ${info.months}") else return null
            if (info.days !in 1..32) if (doThrow) error("Invalid day ${info.days}") else return null
            if (info.hours !in 0..24) if (doThrow) error("Invalid hour ${info.hours}") else return null
            if (info.minutes !in 0..59) if (doThrow) error("Invalid minute ${info.minutes}") else return null
            if (info.seconds !in 0..59) if (doThrow) error("Invalid second ${info.seconds}") else return null
            if (info.milliseconds !in 0 .. 999) if (doThrow) error("Invalid millisecond ${info.milliseconds}") else return null
        }
        val dateTime = DateTime.createAdjusted(info.years, info.months, info.days, info.hours umod 24, info.minutes, info.seconds, info.milliseconds)
        return dateTime.toOffsetUnadjusted(info.offset ?: 0.hours)
    }

    override fun toString(): String = format
}
