package korlibs.time

import korlibs.time.internal.MicroStrReader
import korlibs.time.internal.padded
import korlibs.time.internal.readTimeZoneOffset
import kotlin.math.absoluteValue
import kotlin.time.*

// https://en.wikipedia.org/wiki/ISO_8601
object ISO8601 : DateComponentsFormat {
    // Date Calendar Variants
    val DATE_CALENDAR_COMPLETE = ISODateFormatEx("YYYY-MM-DD")
    val DATE_CALENDAR_REDUCED0 = ISODateFormatEx("YYYY-MM")
    val DATE_CALENDAR_REDUCED1 = ISODateFormatEx("YYYY")
    val DATE_CALENDAR_REDUCED2 = ISODateFormatEx("YY")
    val DATE_CALENDAR_EXPANDED0 = ISODateFormatEx("±YYYYYY-MM-DD")
    val DATE_CALENDAR_EXPANDED1 = ISODateFormatEx("±YYYYYY-MM")
    val DATE_CALENDAR_EXPANDED2 = ISODateFormatEx("±YYYYYY")
    val DATE_CALENDAR_EXPANDED3 = ISODateFormatEx("±YYY")

    // Date Ordinal Variants
    val DATE_ORDINAL_COMPLETE = ISODateFormatEx("YYYY-DDD")
    val DATE_ORDINAL_EXPANDED = ISODateFormatEx("±YYYYYY-DDD")

    // Date Week Variants
    val DATE_WEEK_COMPLETE = ISODateFormatEx("YYYY-Www-D")
    val DATE_WEEK_REDUCED = ISODateFormatEx("YYYY-Www")
    val DATE_WEEK_EXPANDED0 = ISODateFormatEx("±YYYYYY-Www-D")
    val DATE_WEEK_EXPANDED1 = ISODateFormatEx("±YYYYYY-Www")

    // Time Variants
    val TIME_LOCAL_COMPLETE = ISOTimeFormatEx("hh:mm:ss")
    val TIME_LOCAL_REDUCED0 = ISOTimeFormatEx("hh:mm")
    val TIME_LOCAL_REDUCED1 = ISOTimeFormatEx("hh")
    val TIME_LOCAL_FRACTION0 = ISOTimeFormatEx("hh:mm:ss,ss")
    val TIME_LOCAL_FRACTION1 = ISOTimeFormatEx("hh:mm,mm")
    val TIME_LOCAL_FRACTION2 = ISOTimeFormatEx("hh,hh")

    // Time UTC Variants
    val TIME_UTC_COMPLETE = ISOTimeFormatEx("hh:mm:ssZ")
    val TIME_UTC_REDUCED0 = ISOTimeFormatEx("hh:mmZ")
    val TIME_UTC_REDUCED1 = ISOTimeFormatEx("hhZ")
    val TIME_UTC_FRACTION0 = ISOTimeFormatEx("hh:mm:ss,ssZ")
    val TIME_UTC_FRACTION1 = ISOTimeFormatEx("hh:mm,mmZ")
    val TIME_UTC_FRACTION2 = ISOTimeFormatEx("hh,hhZ")

    // Time Relative Variants
    val TIME_RELATIVE0 = ISOTimeFormatEx("±hh:mm")
    val TIME_RELATIVE1 = ISOTimeFormatEx("±hh")

    // Date + Time Variants
    val DATETIME_COMPLETE = ISODateFormatEx("YYYY-MM-DDThh:mm:ss")
    val DATETIME_UTC_COMPLETE = ISODateFormatEx("YYYY-MM-DDThh:mm:ssZ")
    val DATETIME_UTC_COMPLETE_FRACTION = ISODateFormatEx("YYYY-MM-DDThh:mm:ss.sssZ")

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
        DateComponents.Mode.DATE -> DATETIME_COMPLETE.extended.format.format(dd)
        DateComponents.Mode.TIME -> TIME_LOCAL_COMPLETE.extended.format.format(dd)
        DateComponents.Mode.DATE_TIME_SPAN -> INTERVAL_DECIMAL0.format.format(dd)
    }

    /**
     * Generic parser handling all scenarios
     */
    override fun tryParse(str: String, mode: DateComponents.Mode?, doThrow: Boolean): DateComponents? =
        ISODateComponentsFormat.tryParseGeneric(str, mode, doThrow)
}

data class ISOTimeFormatEx(val basicFormat: String?, val extendedFormat: String?) : TimeFormat {
    companion object {
        operator fun invoke(extendedFormat: String): ISOTimeFormatEx = ISOTimeFormatEx(
            extendedFormat.replace("-", "").replace(":", ""),
            extendedFormat
        )
    }
    val basic = ISOTimeFormat(basicFormat ?: extendedFormat ?: TODO())
    val extended = ISOTimeFormat(extendedFormat ?: basicFormat ?: TODO())

    override fun format(dd: Duration): String = extended.format(dd)
    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): Duration? =
        basic.tryParse(str, false, doAdjust) ?: extended.tryParse(str, false, doAdjust)
        ?: (if (doThrow) throw DateException("Invalid format $str") else null)
}

data class ISODateFormatEx(val basicFormat: String?, val extendedFormat: String?) : DateFormat {
    companion object {
        operator fun invoke(extendedFormat: String): ISODateFormatEx = ISODateFormatEx(
            extendedFormat.replace("-", "").replace(":", ""),
            extendedFormat
        )
    }

    val basic = ISODateFormat(basicFormat ?: extendedFormat ?: TODO())
    val extended = ISODateFormat(extendedFormat ?: basicFormat ?: TODO())

    override fun format(dd: DateTimeTz): String = extended.format(dd)
    override fun tryParse(str: String, doThrow: Boolean, doAdjust: Boolean): DateTimeTz? = null
        ?: basic.tryParse(str, false, doAdjust)
        ?: extended.tryParse(str, false, doAdjust)
        ?: (if (doThrow) throw DateException("Invalid format $str") else null)
}

data class ISOTimeFormat(val format: ISODateComponentsFormat) : TimeFormat by format.toTimeFormat() {
    constructor(format: String) : this(ISODateComponentsFormat(format))
}

data class ISODateTimeSpanFormat(val format: ISODateComponentsFormat) : DateTimeSpanFormat by format.toDateTimeSpanFormat() {
    constructor(format: String) : this(ISODateComponentsFormat(format))
}

data class ISODateFormat(val format: ISODateComponentsFormat) : DateFormat by format.toDateFormat() {
    constructor(format: String, twoDigitBaseYear: Int = 1900) : this(ISODateComponentsFormat(format, twoDigitBaseYear))
}
