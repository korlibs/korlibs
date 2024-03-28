package korlibs.time

import kotlin.js.*

actual fun currentTimeMillis(): Long = Date.now().toLong()
