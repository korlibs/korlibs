package korlibs.time

import korlibs.Serializable
import kotlin.time.*

data class PatternTimeFormat(
    val format: String,
    val locale: KlockLocale = KlockLocale.default,
) : BasePatternDateTimeFormat(format), TimeFormat, Serializable {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    fun withLocale(locale: KlockLocale) = this.copy(locale = locale)

    override fun matchChunkToRegex(it: String): String? = matchTimeChunkToRegex(it)

    override fun format(dd: Duration): String {
        val time = Time(dd)
        return chunks.joinToString("") {
            formatTimeChunk(it, locale, time.hour, time.minute, time.second, time.millisecond, clampHours = false)
                ?: formatElseChunk(it)
        }
    }

    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? {
        val info = _tryParseBase(str, doThrow, doAdjust, locale, TimezoneNames.DEFAULT) ?: return null
        return info.hour.hours + info.minute.minutes + info.second.seconds + info.millisecond.milliseconds
    }

    override fun toString(): String = format
}
