package korlibs.time

import korlibs.Serializable
import korlibs.time.internal.*

data class PatternDateFormat(
    val format: String,
    val locale: KlockLocale? = null,
    val tzNames: TimezoneNames = TimezoneNames.DEFAULT,
) : DateFormat, Serializable {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    val realLocale: KlockLocale get() = locale ?: KlockLocale.default
    fun withLocale(locale: KlockLocale?): PatternDateFormat = this.copy(locale = locale)
    fun withTimezoneNames(tzNames: TimezoneNames): PatternDateFormat = this.copy(tzNames = this.tzNames + tzNames)

    internal val privFormat by lazy { PatternDateComponentsFormat(format, locale, tzNames) }

    override fun format(dd: DateTimeTz): String = privFormat.format(dd.toDateComponents())

    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? =
        privFormat.tryParse(str, setDate = true, doThrow = doThrow)?.toDateTimeTz(doThrow = doThrow, doAdjust = doAdjust)

    override fun toString(): String = format
}
