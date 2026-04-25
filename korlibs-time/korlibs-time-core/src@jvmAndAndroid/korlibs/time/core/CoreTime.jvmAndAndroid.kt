package korlibs.time.core

import korlibs.time.*
import java.util.*
import java.util.concurrent.*
import kotlin.time.*

actual var CoreTime: ICoreTime = object : ICoreTime {
    override fun currentTimeMillisDouble(): Double = System.currentTimeMillis().toDouble()
    override fun localTimezoneOffset(time: Long): Duration = TimeZone.getDefault().getOffset(time).milliseconds
    override fun unaccurateSleep(duration: Duration) {
        Thread.sleep(duration.inWholeMilliseconds)
    }

    override fun unaccurateYield() {
        Thread.yield()
        if (Thread.interrupted()) throw InterruptedException()
    }
}
