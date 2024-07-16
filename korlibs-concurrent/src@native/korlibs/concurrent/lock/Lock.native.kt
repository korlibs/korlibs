package korlibs.concurrent.lock

import korlibs.time.*
import kotlinx.cinterop.*
import platform.posix.*
import kotlin.time.*

/*
actual class Lock actual constructor() : LockImpl(), BaseLockWithNotifyAndWait {
    actual companion object {}
}

actual class Lock actual constructor() : BaseLockWithNotifyAndWait {
    actual companion object {}

    actual override fun lock() {
    }

    actual override fun unlock() {
    }

    actual override fun notify(unit: Unit) {
        pthread_cond_signal
    }

    actual override fun wait(time: FastDuration): Boolean {
    }
}
*/

@OptIn(UnsafeNumber::class)
fun timespec.toDuration(): Duration = (tv_sec.toLong().seconds + tv_nsec.toLong().nanoseconds)
@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
fun Duration.toTimespec(out: timespec): timespec {
    val nanos = inWholeNanoseconds
    val s: Long = (nanos / 1_000_000_000)
    val n: Long = (nanos % 1_000_000_000)
    out.tv_sec = s.convert()
    out.tv_nsec = n.convert()
    return out
}

@OptIn(ExperimentalForeignApi::class)
fun NativePlacement.allocTimespecNowPlusAdd(add: Duration): timespec {
    val tspec = alloc<timespec>()
    clock_gettime(CLOCK_REALTIME.convert(), tspec.ptr)
    val clockTime = tspec.toDuration()
    val waitTime = (clockTime + add)
    return waitTime.toTimespec(tspec)
}