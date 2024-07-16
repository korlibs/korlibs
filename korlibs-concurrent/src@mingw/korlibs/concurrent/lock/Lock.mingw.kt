package korlibs.concurrent.lock

import korlibs.time.*
import kotlinx.atomicfu.*
import kotlinx.atomicfu.locks.*
import kotlinx.cinterop.*
import platform.windows.*

@OptIn(ExperimentalForeignApi::class)
actual class Lock actual constructor() : BaseLockWithNotifyAndWait {
    actual companion object {}

    private val nrlock = SynchronizedObject()
    private val nplocks = atomic(0)
    private val nlocks = atomic(0)
    private var arena: Arena? = null
    private var mutex: CRITICAL_SECTION? = null
    private var cond: CONDITION_VARIABLE? = null

    actual inline operator fun <T> invoke(callback: () -> T): T {
        lock()
        try {
            return callback()
        } finally {
            unlock()
        }
    }

    @PublishedApi internal fun lock() {
        //println("LOCKING... ${NativeThread.current}")
        val mut = synchronized(nrlock) {
            if (arena == null) arena = Arena()
            if (cond == null) cond = arena!!.alloc<CONDITION_VARIABLE>().also { InitializeConditionVariable(it.ptr) }
            if (mutex == null) mutex = arena!!.alloc<CRITICAL_SECTION>().also { InitializeCriticalSection(it.ptr) }
            nplocks.incrementAndGet()
            mutex!!
        }
        EnterCriticalSection(mut.ptr)
        nlocks.incrementAndGet()
        //println("LOCKED... ${NativeThread.current}")
    }

    @PublishedApi internal fun unlock() {
        //println("UNLOCKING... ${NativeThread.current}")
        synchronized(nrlock) {
            LeaveCriticalSection(mutex!!.ptr)
            nlocks.decrementAndGet()
            val nlocksValue = nplocks.decrementAndGet()
            if (nlocksValue == 0) {
                if (mutex != null) DeleteCriticalSection(mutex?.ptr)
                arena?.clear()
                cond = null
                mutex = null
                arena = null
            }
        }
        //println("UNLOCKED... ${NativeThread.current}")
    }

    actual override fun notify(unit: Unit) {
        //println("SIGNALING... ${NativeThread.current}")
        //WakeAllConditionVariable(cond!!.ptr)
        WakeConditionVariable(cond!!.ptr)
        //println(" --> SIGNALED ${NativeThread.current}")
        //pthread_cond_broadcast() // notifyall
    }

    actual override fun wait(time: FastDuration): Boolean {
        return memScoped {
            val millis = time.slow.millisecondsInt.coerceAtLeast(0)
            //println("WAITING... ${NativeThread.current} :: $millis, time=$time")

            //println("nlocks.value=${nlocks.value}")
            val nlocks = nlocks.value - 1
            //if (nlocks != 0) println("!!!!!! nlocks=$nlocks")
            repeat(nlocks) { unlock() }
            SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, millis.convert()).also {
                //println("WAITED ${NativeThread.current}")
                repeat(nlocks) { lock() }
            }
        } != ERROR_TIMEOUT
    }
}
