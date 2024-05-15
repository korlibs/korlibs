package korlibs.time

interface DateComponentsFormat {
    fun format(dd: DateComponents): String
    fun tryParse(str: String, doThrow: Boolean = false, doAdjust: Boolean = true): DateComponents?
}
