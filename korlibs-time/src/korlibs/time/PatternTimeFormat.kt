package korlibs.time

import korlibs.Serializable
import kotlin.time.*

data class PatternTimeFormat(
    val format: String,
    val locale: KlockLocale = KlockLocale.default,
) : TimeFormat, Serializable {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    fun withLocale(locale: KlockLocale) = this.copy(locale = locale)

    private val privFormat by lazy { PatternDateComponentsFormat(format, locale) }

    override fun format(dd: Duration): String = privFormat.format(dd.toDateComponents())

    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? =
        privFormat.tryParse(str, setDate = false, doThrow = doThrow)?.toDuration()

    override fun toString(): String = format
}
