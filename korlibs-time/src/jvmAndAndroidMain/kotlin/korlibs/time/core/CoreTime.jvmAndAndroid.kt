package korlibs.time.core

import java.util.TimeZone
import korlibs.time.milliseconds
import kotlin.time.Duration

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
