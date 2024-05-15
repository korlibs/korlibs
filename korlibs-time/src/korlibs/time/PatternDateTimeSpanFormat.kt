package korlibs.time

import korlibs.*

data class PatternDateTimeSpanFormat(
    val format: String,
    val locale: KlockLocale? = null,
) : DateTimeSpanFormat, Serializable {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    val realLocale: KlockLocale get() = locale ?: KlockLocale.default
    fun withLocale(locale: KlockLocale?): PatternDateTimeSpanFormat = this.copy(locale = locale)

    internal val privFormat by lazy { PatternDateComponentsFormat(format, locale) }

    override fun format(dd: DateTimeSpan): String = privFormat.format(dd.toDateComponents())

    override fun tryParse(str: String, doThrow: Boolean): DateTimeSpan? =
        privFormat.tryParse(str, setDate = false, doThrow = doThrow)?.toDateTimeSpan()

    override fun toString(): String = format
}
