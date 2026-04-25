package korlibs.time

import kotlin.time.*

// https://en.wikipedia.org/wiki/ISO_8601
object ISO8601 : DateComponentsFormat {
    // Date Calendar Variants
    val DATE_CALENDAR_COMPLETE = ISODateFormat("YYYY-MM-DD")
    val DATE_CALENDAR_REDUCED0 = ISODateFormat("YYYY-MM")
    val DATE_CALENDAR_REDUCED1 = ISODateFormat("YYYY")
    val DATE_CALENDAR_REDUCED2 = ISODateFormat("YY")
    val DATE_CALENDAR_EXPANDED0 = ISODateFormat("±YYYYYY-MM-DD")
    val DATE_CALENDAR_EXPANDED1 = ISODateFormat("±YYYYYY-MM")
    val DATE_CALENDAR_EXPANDED2 = ISODateFormat("±YYYYYY")
    val DATE_CALENDAR_EXPANDED3 = ISODateFormat("±YYY")

    // Date Ordinal Variants
    val DATE_ORDINAL_COMPLETE = ISODateFormat("YYYY-DDD")
    val DATE_ORDINAL_EXPANDED = ISODateFormat("±YYYYYY-DDD")

    // Date Week Variants
    val DATE_WEEK_COMPLETE = ISODateFormat("YYYY-Www-D")
    val DATE_WEEK_REDUCED = ISODateFormat("YYYY-Www")
    val DATE_WEEK_EXPANDED0 = ISODateFormat("±YYYYYY-Www-D")
    val DATE_WEEK_EXPANDED1 = ISODateFormat("±YYYYYY-Www")

    // Time Variants
    val TIME_LOCAL_COMPLETE = ISOTimeFormat("hh:mm:ss")
    val TIME_LOCAL_REDUCED0 = ISOTimeFormat("hh:mm")
    val TIME_LOCAL_REDUCED1 = ISOTimeFormat("hh")
    val TIME_LOCAL_FRACTION0 = ISOTimeFormat("hh:mm:ss,ss")
    val TIME_LOCAL_FRACTION1 = ISOTimeFormat("hh:mm,mm")
    val TIME_LOCAL_FRACTION2 = ISOTimeFormat("hh,hh")

    // Time UTC Variants
    val TIME_UTC_COMPLETE = ISOTimeFormat("hh:mm:ssZ")
    val TIME_UTC_REDUCED0 = ISOTimeFormat("hh:mmZ")
    val TIME_UTC_REDUCED1 = ISOTimeFormat("hhZ")
    val TIME_UTC_FRACTION0 = ISOTimeFormat("hh:mm:ss,ssZ")
    val TIME_UTC_FRACTION1 = ISOTimeFormat("hh:mm,mmZ")
    val TIME_UTC_FRACTION2 = ISOTimeFormat("hh,hhZ")

    // Time Relative Variants
    val TIME_RELATIVE0 = ISOTimeFormat("±hh:mm")
    val TIME_RELATIVE1 = ISOTimeFormat("±hh")

    // Date + Time Variants
    val DATETIME_COMPLETE = ISODateFormat("YYYY-MM-DDThh:mm:ss")
    val DATETIME_UTC_COMPLETE = ISODateFormat("YYYY-MM-DDThh:mm:ssZ")
    val DATETIME_UTC_COMPLETE_FRACTION = ISODateFormat("YYYY-MM-DDThh:mm:ss.sssZ")

    // Interval Variants
    val INTERVAL_COMPLETE0 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnnMnnS")
    val INTERVAL_COMPLETE1 = ISODateTimeSpanFormat("PnnYnnW")

    val INTERVAL_REDUCED0 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnnM")
    val INTERVAL_REDUCED1 = ISODateTimeSpanFormat("PnnYnnMnnDTnnH")
    val INTERVAL_REDUCED2 = ISODateTimeSpanFormat("PnnYnnMnnD")
    val INTERVAL_REDUCED3 = ISODateTimeSpanFormat("PnnYnnM")
    val INTERVAL_REDUCED4 = ISODateTimeSpanFormat("PnnY")

    val INTERVAL_DECIMAL0 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnnMnn,nnS")
    val INTERVAL_DECIMAL1 = ISODateTimeSpanFormat("PnnYnnMnnDTnnHnn,nnM")
    val INTERVAL_DECIMAL2 = ISODateTimeSpanFormat("PnnYnnMnnDTnn,nnH")
    val INTERVAL_DECIMAL3 = ISODateTimeSpanFormat("PnnYnnMnn,nnD")
    val INTERVAL_DECIMAL4 = ISODateTimeSpanFormat("PnnYnn,nnM")
    val INTERVAL_DECIMAL5 = ISODateTimeSpanFormat("PnnYnn,nnW")
    val INTERVAL_DECIMAL6 = ISODateTimeSpanFormat("PnnY")

    val INTERVAL_ZERO_OMIT0 = ISODateTimeSpanFormat("PnnYnnDTnnHnnMnnS")
    val INTERVAL_ZERO_OMIT1 = ISODateTimeSpanFormat("PnnYnnDTnnHnnM")
    val INTERVAL_ZERO_OMIT2 = ISODateTimeSpanFormat("PnnYnnDTnnH")
    val INTERVAL_ZERO_OMIT3 = ISODateTimeSpanFormat("PnnYnnD")

    // Detects and parses all the variants
    val DATE = this.toDateFormat()
    val TIME = this.toTimeFormat()
    val INTERVAL = this.toDateTimeSpanFormat()

    override fun format(dd: DateComponents): String = when (dd.mode) {
        DateComponents.Mode.DATE -> DATETIME_COMPLETE.format.extended.format(dd)
        DateComponents.Mode.TIME -> TIME_LOCAL_COMPLETE.format.extended.format(dd)
        DateComponents.Mode.DATE_TIME_SPAN -> INTERVAL_DECIMAL0.format.format(dd)
    }

    /**
     * Generic parser handling all scenarios
     */
    override fun tryParse(str: String, mode: DateComponents.Mode?, doThrow: Boolean): DateComponents? =
        ISODateComponentsFormat.tryParseGeneric(str, mode, doThrow)
}

open class ISODateComponentsFormatEx(val basicFormat: String?, val extendedFormat: String?) : DateComponentsFormat {
    constructor(extendedFormat: String) : this(extendedFormat.replace("-", "").replace(":", ""), extendedFormat)
    val basic = ISODateComponentsFormat(basicFormat ?: extendedFormat ?: TODO())
    val extended = ISODateComponentsFormat(extendedFormat ?: basicFormat ?: TODO())
    override fun format(dd: DateComponents): String = extended.format(dd)
    override fun tryParse(str: String, mode: DateComponents.Mode?, doThrow: Boolean): DateComponents? {
        return basic.tryParse(str, mode, doThrow = false)
            ?: extended.tryParse(str, mode, doThrow = false)
            ?: (if (doThrow) throw DateException("Invalid format $str") else null)
    }
}

data class ISOTimeFormat(val format: ISODateComponentsFormatEx) : TimeFormat by format.toTimeFormat() {
    constructor(format: String) : this(ISODateComponentsFormatEx(format))
    val basic = format.basic.toTimeFormat()
    val extended = format.extended.toTimeFormat()
}

data class ISODateFormat(val format: ISODateComponentsFormatEx) : DateFormat by format.toDateFormat() {
    constructor(format: String) : this(ISODateComponentsFormatEx(format))
    val basic = format.basic.toDateFormat()
    val extended = format.extended.toDateFormat()
}

data class ISODateTimeSpanFormat(val format: ISODateComponentsFormatEx) : DateTimeSpanFormat by format.toDateTimeSpanFormat() {
    constructor(format: String) : this(ISODateComponentsFormatEx(format))
    //val basic = format.basic.toDateTimeSpanFormat()
    //val extended = format.extended.toDateTimeSpanFormat()
}
