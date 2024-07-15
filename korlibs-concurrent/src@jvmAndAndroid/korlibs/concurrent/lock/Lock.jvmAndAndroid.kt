package korlibs.concurrent.lock

import korlibs.time.*

/*
actual class Lock actual constructor() : LockImpl(), BaseLockWithNotifyAndWait {
    actual companion object {}
}
*/
actual class Lock actual constructor() : BaseLockWithNotifyAndWait {
    private val lock = java.util.concurrent.locks.ReentrantLock()

    actual companion object {}

    actual override fun notify(unit: Unit) {
        synchronized(lock) {
            (lock as java.lang.Object).notify()
        }
    }

    actual override fun wait(time: FastDuration): Boolean {
        val nanoSeconds = time.nanoseconds.toLong()
        val millis = nanoSeconds / 1_000_000
        val nanos = nanoSeconds % 1_000_000
        (lock as java.lang.Object).wait(millis, nanos.toInt())
        return true
    }

    actual override fun lock() {
        lock.lock()
    }

    actual override fun unlock() {
        lock.unlock()
    }
}
