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

    fun withLocale(locale: KlockLocale?): PatternDateFormat = this.copy(locale = locale)
    fun withTimezoneNames(tzNames: TimezoneNames): PatternDateFormat = this.copy(tzNames = this.tzNames + tzNames)

    override fun matchChunkToRegex(it: String): String? = matchDateChunkToRegex(it) ?: matchTimeChunkToRegex(it)

    // EEE, dd MMM yyyy HH:mm:ss z -- > Sun, 06 Nov 1994 08:49:37 GMT
    // YYYY-MM-dd HH:mm:ss

    override fun format(dd: DateComponents): String = chunks.joinToString("") { formatAnyChunk(it, dd, realLocale) }
    override fun format(dd: DateTimeTz): String = format(dd.toComponents())
    override fun format(dd: DateTimeSpan): String = format(dd.toComponents())

    override fun tryParseComponents(str: String, doThrow: Boolean): DateComponents? =
        _tryParseBase(str, doThrow, realLocale, tzNames)
    override fun toString(): String = format
}
