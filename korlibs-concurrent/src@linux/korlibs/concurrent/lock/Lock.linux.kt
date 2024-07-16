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
            if (mutex == null) mutex = arena!!.alloc<pthread_mutex_t>().also { pthread_mutex_init(it.ptr, null) }
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

    actual override fun wait(time: FastDuration): Boolean = memScoped {
        var tspec: timespec = alloc<timespec>()
        clock_gettime(CLOCK_REALTIME.convert(), tspec.ptr)
        val clockTime = tspec.toDuration()
        //val waitTime = (clockTime + time - 10.milliseconds)
        val waitTime = (clockTime + time.slow)
        tspec = waitTime.toTimespec(tspec)
        //println("WAITING... $time")
        //val startTime = CoreTime.currentTimeMillisDouble()
        val nlocks = nlocks.value - 1
        //if (nlocks != 0) println("!!!!!! nlocks=$nlocks")
        repeat(nlocks) { unlock() }
        (pthread_cond_timedwait(cond!!.ptr, mutex!!.ptr, tspec.ptr)).also {
            //println("WAITED ${NativeThread.current}")
            repeat(nlocks) { lock() }
            //val elapsedTime = (CoreTime.currentTimeMillisDouble() - startTime).milliseconds
            //println(" --> WAITED it=$it : timedout=${it == ETIMEDOUT}, elapsedTime=$elapsedTime, clockTime=$clockTime, waitTime=$waitTime -- ${waitTime - clockTime}, requested=${time}")
        } != ETIMEDOUT
    }
}
