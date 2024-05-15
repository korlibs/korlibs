package korlibs.time

import kotlin.time.*

data class DateComponents(
    var fullYear: Int = 1970,
    var month: Int = 1,
    var day: Int = 1,
    var hour: Int = 0,
    var minute: Int = 0,
    var second: Int = 0,
    var nanosecond: Int = 0,
    var offset: Duration? = null,
) {
    val millisecond get() = nanosecond / 1_000_000
}
