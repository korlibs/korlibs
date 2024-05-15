package korlibs.time

import kotlin.test.Test
import kotlin.test.assertEquals

class ISO8601Test {
    @Test
    fun testDate() {
        val date = DateTime(2019, Month.April, 14)
        assertEquals("2019-04-14", date.format(ISO8601.DATE_CALENDAR_COMPLETE))
        assertEquals("2019-04-14", date.format(ISO8601.DATE_CALENDAR_COMPLETE.extended))
        assertEquals("20190414", date.format(ISO8601.DATE_CALENDAR_COMPLETE.basic))

        assertEquals("2019-04", date.format(ISO8601.DATE_CALENDAR_REDUCED0))
        assertEquals("2019-04", date.format(ISO8601.DATE_CALENDAR_REDUCED0.extended))
        assertEquals("201904", date.format(ISO8601.DATE_CALENDAR_REDUCED0.basic))

        assertEquals("2019", date.format(ISO8601.DATE_CALENDAR_REDUCED1))
        assertEquals("2019", date.format(ISO8601.DATE_CALENDAR_REDUCED1.extended))
        assertEquals("2019", date.format(ISO8601.DATE_CALENDAR_REDUCED1.basic))

        assertEquals("19", date.format(ISO8601.DATE_CALENDAR_REDUCED2))
        assertEquals("19", date.format(ISO8601.DATE_CALENDAR_REDUCED2.extended))
        assertEquals("19", date.format(ISO8601.DATE_CALENDAR_REDUCED2.basic))

        assertEquals("+002019-04-14", date.format(ISO8601.DATE_CALENDAR_EXPANDED0))

        assertEquals("2019-W15-7", date.format(ISO8601.DATE_WEEK_COMPLETE))
        assertEquals("+002019-W15-7", date.format(ISO8601.DATE_WEEK_EXPANDED0))

        assertEquals(date, ISO8601.DATE.parse("2019-04-14").utc)
        assertEquals(date, ISO8601.DATE.parse("2019-W15-7").utc)
    }

    @Test
    fun testTime() {
        val time = 15.hours + 30.minutes + 12.seconds
        assertEquals("15:30:12", ISO8601.TIME_LOCAL_COMPLETE.format(time))
        assertEquals("153012", ISO8601.TIME_LOCAL_COMPLETE.basic.format(time))

        assertEquals("15:30:12,00", ISO8601.TIME_LOCAL_FRACTION0.format(time))
        assertEquals("15:30:12,63", ISO8601.TIME_LOCAL_FRACTION0.format(time + 630.milliseconds))

        assertEquals("15:30,20", ISO8601.TIME_LOCAL_FRACTION1.format(time))
        assertEquals("15,50", ISO8601.TIME_LOCAL_FRACTION2.format(time))

        assertEquals("15,50Z", ISO8601.TIME_UTC_FRACTION2.format(time))
    }

    @Test
    fun testTime_2() {
        assertEquals(15.hours + 30.minutes + 12.seconds + 630.milliseconds, ISO8601.TIME_LOCAL_FRACTION0.parse("15:30:12,63"))
    }

    @Test
    fun testInterval() {
        if (KotlinVersion.CURRENT < KotlinVersion(1, 9, 20)) return

        assertEquals(24.hours, 1.days)
        assertEquals((27 * 24).hours, 27.days)
        val time = 1.years + 0.months + 27.days + 11.hours + 9.minutes + 11.seconds
        assertEquals("P1Y0M27DT11H9M11S", ISO8601.INTERVAL_COMPLETE0.format(time))

        assertEquals(time, ISO8601.INTERVAL_COMPLETE0.parse("P1Y0M27DT11H9M11S"))
        assertEquals(time, ISO8601.INTERVAL.parse("P1Y0M27DT11H9M11S"))
    }

    @Test
    fun testWeekOfYear() {
        assertEquals(1, DateTime(2019, Month.January, 1).dayOfYear)

        assertEquals(1, DateTime(2019, Month.January, 1).weekOfYear1)
        assertEquals(1, DateTime(2019, Month.January, 6).weekOfYear1)
        assertEquals(2, DateTime(2019, Month.January, 7).weekOfYear1)
        assertEquals(2, DateTime(2019, Month.January, 13).weekOfYear1)
        assertEquals(3, DateTime(2019, Month.January, 14).weekOfYear1)

        assertEquals(1, DateTime(2018, Month.January, 1).weekOfYear1)
        assertEquals(1, DateTime(2018, Month.January, 7).weekOfYear1)
        assertEquals(2, DateTime(2018, Month.January, 8).weekOfYear1)
        assertEquals(2, DateTime(2018, Month.January, 14).weekOfYear1)
        assertEquals(3, DateTime(2018, Month.January, 15).weekOfYear1)

        assertEquals(1, DateTime(2018, Month.January, 1).weekOfYear1)
        assertEquals(1, DateTime(2018, Month.January, 7).weekOfYear1)
        assertEquals(2, DateTime(2018, Month.January, 8).weekOfYear1)
        assertEquals(2, DateTime(2018, Month.January, 14).weekOfYear1)
        assertEquals(3, DateTime(2018, Month.January, 15).weekOfYear1)

        assertEquals(44, DateTime(2007, Month.November, 3).weekOfYear1)
        assertEquals(6, DateTime(2007, Month.November, 3).dayOfWeek.index1Monday)
    }

    @Test
    fun testDateTimeComplete() {
        assertEquals("20190917T114805", ISO8601.DATETIME_COMPLETE.basic.format(1568720885000))
        assertEquals("2019-09-17T11:48:05", ISO8601.DATETIME_COMPLETE.extended.format(1568720885000))

        assertEquals("Tue, 17 Sep 2019 11:48:05 UTC", ISO8601.DATETIME_COMPLETE.parse("20190917T114805").utc.toStringDefault())
        assertEquals("Tue, 17 Sep 2019 11:48:05 UTC", ISO8601.DATETIME_COMPLETE.parse("2019-09-17T11:48:05").utc.toStringDefault())
    }

    @Test
    fun testDateTimeUtcComplete() {
        assertEquals("20190917T114805Z", ISO8601.DATETIME_UTC_COMPLETE.basic.format(1568720885000))
        assertEquals("2019-09-17T11:48:05Z", ISO8601.DATETIME_UTC_COMPLETE.extended.format(1568720885000))

        assertEquals("Tue, 17 Sep 2019 11:48:05 UTC", ISO8601.DATETIME_UTC_COMPLETE.parse("20190917T114805Z").utc.toStringDefault())
        assertEquals("Tue, 17 Sep 2019 11:48:05 UTC", ISO8601.DATETIME_UTC_COMPLETE.parse("2019-09-17T11:48:05Z").utc.toStringDefault())
    }

    @Test
    fun testDateTimeUtcCompleteFraction() {
        assertEquals("20190917T114805.000Z", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.basic.format(1568720885000))
        assertEquals("2019-09-17T11:48:05.000Z", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.extended.format(1568720885000))

        assertEquals("Tue, 17 Sep 2019 11:48:05 UTC", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.parse("20190917T114805.000Z").utc.toStringDefault())
        assertEquals("Tue, 17 Sep 2019 11:48:05 UTC", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.parse("2019-09-17T11:48:05.000Z").utc.toStringDefault())
    }

    @Test
    fun testDateTimeUtcFraction() {
        assertEquals("2019-09-17T11:48:05.123Z", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.extended.format(1568720885123))
        assertEquals("2019-09-17T11:48:05.023Z", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.extended.format(1568720885023))
        assertEquals("2022-02-05T15:32:18.096Z", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.extended.format(1644075138096))
    }

    @Test
    fun testIssue84() {
        if (KotlinVersion.CURRENT < KotlinVersion(1, 9, 20)) return // @TODO: This should be only for WASM

        val badUtc = DateTime(
            date = Date(2020, 1, 4),
            time = Time(2, 42, 55, millisecond = 500)
        )
        assertEquals(
            "2020-01-04T02:42:55,50",
            badUtc.format(ISODateFormatEx("YYYY-MM-DDThh:mm:ss,ss"))
        )
    }

    @Test
    fun testIssue102() {
        val time = 15.hours + 30.minutes + 12.seconds + 160.milliseconds

        assertEquals("15:30:12", ISOTimeFormatEx("hh:mm:ss").format(time))
        assertEquals("153012", ISOTimeFormatEx("hh:mm:ss").basic.format(time))

        assertEquals("15:30:12.2", ISOTimeFormatEx("hh:mm:ss.s").format(time))
        assertEquals("15:30:12,2", ISOTimeFormatEx("hh:mm:ss,s").format(time))
        assertEquals("15:30:12.16", ISOTimeFormatEx("hh:mm:ss.ss").format(time))
        assertEquals("15:30:12,16", ISOTimeFormatEx("hh:mm:ss,ss").format(time))
        assertEquals("15:30:12.160", ISOTimeFormatEx("hh:mm:ss.sss").format(time))
        assertEquals("15:30:12,160", ISOTimeFormatEx("hh:mm:ss,sss").format(time))

        assertEquals("15:30.2", ISOTimeFormatEx("hh:mm.m").format(time))
        assertEquals("15:30,2", ISOTimeFormatEx("hh:mm,m").format(time))
        assertEquals("15:30.20", ISOTimeFormatEx("hh:mm.mm").format(time))
        assertEquals("15:30,20", ISOTimeFormatEx("hh:mm,mm").format(time))

        assertEquals("15.5", ISOTimeFormatEx("hh.h").format(time))
        assertEquals("15,5", ISOTimeFormatEx("hh,h").format(time))
        assertEquals("15.50", ISOTimeFormatEx("hh.hh").format(time))
        assertEquals("15,50", ISOTimeFormatEx("hh,hh").format(time))

        assertEquals("15,5Z", ISOTimeFormatEx("hh,hZ", null).format(time))
    }

    @Test
    fun testIssue102_2() {
        val time = 15.hours + 30.minutes + 12.seconds + 160.milliseconds
        assertEquals(time, ISOTimeFormatEx("hh:mm:ss,ss").parse("15:30:12,16"))
        assertEquals(time, ISOTimeFormatEx("hh:mm:ss.sss").parse("15:30:12.160"))
    }

    @Test
    fun testTimeZoneParsing() {
        val string1 = "2022-10-06T19:57:29.285+02:00"
        val string2 = "2022-10-06T17:57:29.285+00:00"

        val parsed1 = ISO8601.DATETIME_UTC_COMPLETE_FRACTION.parse(string1)
        val parsed2 = ISO8601.DATETIME_UTC_COMPLETE_FRACTION.parse(string2)

        assertEquals(string1, ISO8601.DATETIME_UTC_COMPLETE_FRACTION.format(parsed1))
        assertEquals(string2.removeSuffix("+00:00") + "Z", ISO8601.DATETIME_UTC_COMPLETE_FRACTION.format(parsed2))
        assertEquals(parsed1.utc, parsed2.utc)
    }

    @Test
    fun testISO8601GenericParser_duration() {
        val time = 1.years + 3.months + 27.days + 11.hours + 9.minutes + 11.seconds
        assertEquals(time, ISO8601.tryParse("P1Y3M27DT11H9M11S")?.toDateTimeSpan())
        assertEquals(time, ISO8601.tryParse("P1Y3M27DT11H9M11S", DateComponents.Mode.DATE_TIME_SPAN)?.toDateTimeSpan())
        assertEquals(time, ISO8601.toDateTimeSpanFormat().parse("P1Y3M27DT11H9M11S"))
    }

    @Test
    fun testISO8601GenericParser_full() {
        val dt = DateTimeTz.local(DateTime(2022, 10, 6, 19, 57, 29, 285), TimezoneOffset(2.hours))
        assertEquals(dt, ISO8601.parse("2022-10-06T19:57:29.285+02:00").toDateTimeTz())
        assertEquals(dt, ISO8601.toDateFormat().parse("2022-10-06T19:57:29.285+02:00"))
    }

    @Test
    fun testISO8601GenericParser_simple() {
        // Plain dates
        assertEquals(Date(2022, 11, 30), ISO8601.toDateFormat().parse("2022-11-30").local.date)
        assertEquals(Date.fromDayOfYear(2022, 255), ISO8601.toDateFormat().parse("2022-255").local.date)
        assertEquals(Date.fromWeekAndDay(2022, 3, 2), ISO8601.toDateFormat().parse("2022-W3-2").local.date)
    }
}