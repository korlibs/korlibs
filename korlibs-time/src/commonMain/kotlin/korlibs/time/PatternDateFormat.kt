package korlibs.time

data class PatternDateFormat(
    val format: PatternDateComponentsFormat,
) : DateFormat by format.toDateFormat() {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    constructor(format: String, locale: KlockLocale? = null, tzNames: TimezoneNames = TimezoneNames.DEFAULT) : this(PatternDateComponentsFormat(format, locale, tzNames))

    val realLocale: KlockLocale get() = format.realLocale
    fun withLocale(locale: KlockLocale?): PatternDateFormat = this.copy(format = format.copy(locale = locale))
    fun withTimezoneNames(tzNames: TimezoneNames): PatternDateFormat = this.copy(format = format.copy(tzNames = tzNames))

    override fun toString(): String = format.toString()
}
