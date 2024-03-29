package korlibs.time.core

import korlibs.time.*
import java.util.*
import kotlin.time.*

actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
    override fun localTimezoneOffset(time: Long): Duration = TimeZone.getDefault().getOffset(time).milliseconds
    override fun sleep(time: Duration) {
        val nanos = time.nanoseconds.toLong()
        Thread.sleep(nanos / 1_000_000, (nanos % 1_000_000).toInt())
    }
}
