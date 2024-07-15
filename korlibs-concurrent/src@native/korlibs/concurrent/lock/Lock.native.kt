package korlibs.concurrent.lock

import korlibs.time.*

actual class Lock actual constructor() : LockImpl(), BaseLockWithNotifyAndWait {
    actual companion object {}
}

/*
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