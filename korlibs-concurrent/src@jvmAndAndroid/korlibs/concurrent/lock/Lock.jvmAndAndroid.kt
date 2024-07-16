package korlibs.concurrent.lock

import korlibs.time.*

/*
actual class Lock actual constructor() : LockImpl(), BaseLockWithNotifyAndWait {
    actual companion object {}
}
*/
actual class Lock actual constructor() : BaseLockWithNotifyAndWait {
    @PublishedApi internal val lock = java.util.concurrent.locks.ReentrantLock()

    actual companion object {}

    actual inline operator fun <T> invoke(callback: () -> T): T = synchronized(lock) {
        callback()
    }

    actual override fun notify(unit: Unit) {
        (lock as java.lang.Object).notify()
    }

    actual override fun wait(time: FastDuration): Boolean {
        val nanoSeconds = time.nanoseconds.toLong()
        val millis = nanoSeconds / 1_000_000
        val nanos = nanoSeconds % 1_000_000
        (lock as java.lang.Object).wait(millis, nanos.toInt())
        return true
    }
}
