package korlibs.time

data class PatternTimeFormat(
    val format: PatternDateComponentsFormat,
) : TimeFormat by format.toTimeFormat() {
    companion object {
        @Suppress("MayBeConstant", "unused")
        private const val serialVersionUID = 1L
    }

    constructor(format: String, locale: KlockLocale? = null) : this(PatternDateComponentsFormat(format, locale))

    val realLocale: KlockLocale get() = format.realLocale
    fun withLocale(locale: KlockLocale?): PatternTimeFormat = this.copy(format = format.copy(locale = locale))

    override fun toString(): String = format.toString()
}
