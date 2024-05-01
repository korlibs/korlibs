package korlibs.time.core

import korlibs.time.*
import kotlin.js.*
import kotlin.time.*

actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillisDouble(): Double = Date.now()
    override fun localTimezoneOffset(time: Long): Duration = Date(time.toDouble()).getTimezoneOffset().minutes
}
