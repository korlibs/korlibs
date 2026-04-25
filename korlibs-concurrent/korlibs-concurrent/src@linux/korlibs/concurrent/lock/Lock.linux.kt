package korlibs.concurrent.lock

import korlibs.time.*
import kotlinx.atomicfu.*
import kotlinx.atomicfu.locks.*
import kotlinx.cinterop.*
import platform.posix.*
import kotlin.time.*

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
actual class Lock actual constructor() : BaseLockWithNotifyAndWait {
    actual companion object {}

    private val nrlock = SynchronizedObject()
    private val nplocks = atomic(0)
    private val nlocks = atomic(0)
    private var arena: Arena? = null
    private var mutex: pthread_mutex_t? = null
    private var cond: pthread_cond_t? = null

    actual inline operator fun <T> invoke(callback: () -> T): T {
        lock()
        try {
            return callback()
        } finally {
            unlock()
        }
    }

    @PublishedApi internal fun lock() {
        val mut = synchronized(nrlock) {
            if (arena == null) arena = Arena()
            if (cond == null) cond = arena!!.alloc<pthread_cond_t>().also { pthread_cond_init(it.ptr, null) }
            if (mutex == null) mutex = arena!!.alloc<pthread_mutex_t>().also {
                memScoped {
                    val attr = alloc<pthread_mutexattr_t>()
                    pthread_mutexattr_init(attr.ptr)
                    pthread_mutexattr_settype(attr.ptr, PTHREAD_MUTEX_RECURSIVE.convert())
                    pthread_mutex_init(it.ptr, attr.ptr)
                }
            }
            nplocks.incrementAndGet()
            mutex!!
        }
        pthread_mutex_lock(mut.ptr)
        nlocks.incrementAndGet()
    }

    @PublishedApi internal fun unlock() {
        synchronized(nrlock) {
            pthread_mutex_unlock(mutex!!.ptr)
            nlocks.decrementAndGet()
            val nlocksValue = nplocks.decrementAndGet()
            if (nlocksValue == 0) {
                if (cond != null) pthread_cond_destroy(cond?.ptr)
                if (mutex != null) pthread_mutex_destroy(mutex?.ptr)
                arena?.clear()
                cond = null
                mutex = null
                arena = null
            }
        }
    }

    actual override fun notify(unit: Unit) {
        //println("SIGNALING...")
        pthread_cond_signal(cond!!.ptr)
        //println(" --> SIGNALED")
        //pthread_cond_broadcast() // notifyall
    }

    actual override fun wait(time: FastDuration): Unit = memScoped {
        val nlocks = nlocks.value - 1
        repeat(nlocks) { unlock() }
        if (time.isPositiveInfinity) {
            while (pthread_cond_timedwait(cond!!.ptr, mutex!!.ptr, allocTimespecNowPlusAdd(10.milliseconds).ptr) == ETIMEDOUT) Unit
        } else {
            pthread_cond_timedwait(cond!!.ptr, mutex!!.ptr, allocTimespecNowPlusAdd(time.slow).ptr)
        }
        repeat(nlocks) { lock() }
    }
}
