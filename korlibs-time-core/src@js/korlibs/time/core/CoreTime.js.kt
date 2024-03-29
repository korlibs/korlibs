package korlibs.time.core

import korlibs.time.*
import kotlin.js.*
import kotlin.time.*

actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillis(): Long = Date.now().toLong()
    override fun localTimezoneOffset(time: Long): Duration = Date(time.toDouble()).getTimezoneOffset().minutes
}
