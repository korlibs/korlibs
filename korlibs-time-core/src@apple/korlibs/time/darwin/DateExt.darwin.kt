@file:OptIn(ExperimentalForeignApi::class)

package korlibs.time.darwin

import korlibs.time.*
import kotlinx.cinterop.*
import platform.CoreFoundation.*
import platform.Foundation.*
import kotlin.time.*

private val APPLE_REFERENCE_NSDATE = DateTime(2001, 1, 1, 0, 0, 0, 0)

val DateTime.Companion.APPLE_REFERENCE_DATE: DateTime get() = APPLE_REFERENCE_NSDATE

fun DateTime.cfAbsoluteTime(): Double = (this - APPLE_REFERENCE_NSDATE).seconds

fun DateTime.Companion.fromCFAbsoluteTime(cfAbsoluteTime: Double): DateTime =
    APPLE_REFERENCE_NSDATE + cfAbsoluteTime.seconds

fun NSDate.toDateTime(): DateTime = DateTime.fromCFAbsoluteTime(this.timeIntervalSinceReferenceDate)
fun DateTime.toNSDate(): NSDate = NSDate(cfAbsoluteTime())

fun getLocalTimezoneOffsetDarwin(tz: CFTimeZoneRef?, time: DateTime): Duration {
    val secondsSince2001 = time.cfAbsoluteTime()
    return (CFTimeZoneGetSecondsFromGMT(tz, secondsSince2001.toDouble()) / 60.0).minutes
}
