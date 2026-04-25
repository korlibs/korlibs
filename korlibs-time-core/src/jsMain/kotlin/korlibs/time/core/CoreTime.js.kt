package korlibs.time.core

import korlibs.time.*
import kotlin.time.*

@Suppress("RemoveRedundantQualifierName", "RedundantSuppression")
actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillisDouble(): Double = kotlin.js.Date.now()
    override fun localTimezoneOffset(time: Long): Duration = kotlin.js.Date(time.toDouble()).getTimezoneOffset().minutes
}
