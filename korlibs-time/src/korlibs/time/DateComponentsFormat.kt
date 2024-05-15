package korlibs.time

interface DateComponentsFormat {
    fun format(dd: DateComponents): String
    fun tryParse(str: String, setDate: Boolean? = null, doThrow: Boolean = false): DateComponents?

    companion object {
        operator fun invoke(
            format: String,
            locale: KlockLocale? = null,
            tzNames: TimezoneNames = TimezoneNames.DEFAULT,
        ): PatternDateComponentsFormat = PatternDateComponentsFormat(format, locale, tzNames)
    }
}
