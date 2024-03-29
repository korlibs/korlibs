package korlibs.time.core

import korlibs.time.*
import kotlin.js.*
import kotlin.time.*

@JsName("globalThis")
private external val globalThis: dynamic

actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillis(): Long = Date.now().toLong()
    override fun nanoTime(): Long = (globalThis.performance.now().unsafeCast<Double>() * 1_000_000L).toLong()
    override fun localTimezoneOffset(time: Long): Duration = Date(time.toDouble()).getTimezoneOffset().minutes
}
