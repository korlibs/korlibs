@file:Suppress("PackageDirectoryMismatch")

package korlibs.time.jvm

import java.util.Date
import korlibs.time.DateTime

fun Date.toDateTime() = DateTime(this.time)
fun DateTime.toDate() = Date(this.unixMillisLong)
