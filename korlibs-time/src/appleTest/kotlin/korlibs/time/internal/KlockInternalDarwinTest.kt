@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.time.internal

import korlibs.time.DateTime
import korlibs.time.Month
import korlibs.time.darwin.APPLE_REFERENCE_DATE
import korlibs.time.darwin.cfAbsoluteTime
import korlibs.time.darwin.fromCFAbsoluteTime
import korlibs.time.darwin.getLocalTimezoneOffsetDarwin
import korlibs.time.darwin.toDateTime
import korlibs.time.darwin.toNSDate
import korlibs.time.invoke
import korlibs.time.minutes
import korlibs.time.toStringDefault
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.convert
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.toKStringFromUtf8
import platform.CoreFoundation.CFArrayGetCount
import platform.CoreFoundation.CFArrayGetValueAtIndex
import platform.CoreFoundation.CFArrayRef
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.CFStringGetCStringPtr
import platform.CoreFoundation.CFTimeZoneCopyKnownNames
import platform.CoreFoundation.CFTimeZoneCreateWithName
import platform.CoreFoundation.kCFStringEncodingUTF8
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

@OptIn(ExperimentalForeignApi::class)
class KlockInternalDarwinTest {
    @Test
    fun test() {
        val names = CFTimeZoneCopyKnownNames().toStrArray().filterNotNull().toList()
        assertEquals(true, "Europe/Madrid" in names)
        val EuropeMadrid = CFTimeZoneCreateWithName(null, CFStringCreateWithCString(null, "Europe/Madrid", kCFStringEncodingUTF8), true)
        //val CET = CFTimeZoneCreateWithName(null, CFStringCreateWithCString(null, "CET", kCFStringEncodingUTF8), true)
        //val CEST = CFTimeZoneCreateWithName(null, CFStringCreateWithCString(null, "CEST", kCFStringEncodingUTF8), true)

        // CEST (UTC +2)
        // CET (UTC +1)
        assertEquals(
            """
                CEST: 120
                CET: 60
            """.trimIndent(),
            """
                CEST: ${getLocalTimezoneOffsetDarwin(EuropeMadrid, DateTime(2023, Month.July, 10)).minutes.toInt()}
                CET: ${getLocalTimezoneOffsetDarwin(EuropeMadrid, DateTime(2023, Month.January, 10)).minutes.toInt()}
            """.trimIndent()
        )
    }

    @Test
    fun testCFAbsoluteTime() {
        assertEquals(0.0, DateTime.fromCFAbsoluteTime(0.0).cfAbsoluteTime())
        assertEquals(1000.0, DateTime.fromCFAbsoluteTime(1000.0).cfAbsoluteTime())
        assertEquals(-1000000.0, DateTime.fromCFAbsoluteTime(-1000000.0).cfAbsoluteTime())
        assertEquals("Mon, 01 Jan 2001 00:00:00 UTC", DateTime.fromCFAbsoluteTime(0.0).toStringDefault())
    }

    @Test
    fun testNSDate() {
        assertEquals("Mon, 01 Jan 2001 00:00:00 UTC", NSDate(0.0).toDateTime().toStringDefault())
        assertEquals(0L, DateTime.APPLE_REFERENCE_DATE.toNSDate().timeIntervalSinceReferenceDate.toLong())
        assertEquals(0L, DateTime(2001, Month.January, 1).toNSDate().timeIntervalSinceReferenceDate.toLong())
        assertEquals(-31622400L, DateTime(2000, Month.January, 1).toNSDate().timeIntervalSinceReferenceDate.toLong())
        assertEquals(946684800L, DateTime(2000, Month.January, 1).toNSDate().timeIntervalSince1970.toLong())
        assertEquals(0L, DateTime.EPOCH.toNSDate().timeIntervalSince1970.toLong())
    }
}

@OptIn(ExperimentalForeignApi::class)
fun CFArrayRef?.toStrArray(): Array<String?> {
    val array = this

    return Array(CFArrayGetCount(array).convert()) {
        val ptr = CFArrayGetValueAtIndex(array, it.convert())
        CFStringGetCStringPtr(ptr?.reinterpret(), kCFStringEncodingUTF8)?.toKStringFromUtf8()
    }
}
