package korlibs.time

import kotlin.time.*

data class DateComponents(
    var years: Int = 1970,
    var months: Int = 1,
    var days: Int = 1,
    var hours: Int = 0,
    var minutes: Int = 0,
    var seconds: Int = 0,
    var nanoseconds: Int = 0,
    var offset: Duration? = null,
) {
    val milliseconds: Int get() = nanoseconds / 1_000_000
}
