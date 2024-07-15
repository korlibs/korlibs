package korlibs.concurrent.lock

import korlibs.time.*
import kotlinx.atomicfu.*
import kotlinx.cinterop.*
import platform.windows.*

@OptIn(ExperimentalForeignApi::class)
actual class Lock actual constructor() : BaseLockWithNotifyAndWait {
    actual companion object {}

    private val nrlock = NonRecursiveLock()
    private val nlocks = atomic(0)
    private var arena: Arena? = null
    private var mutex: CRITICAL_SECTION? = null
    private var cond: CONDITION_VARIABLE? = null

    actual override fun lock() {
        val mut = nrlock {
        //val mut = run {
            if (arena == null) {
                arena = Arena()
            }
            if (cond == null) {
                cond = arena!!.alloc<CONDITION_VARIABLE>().also {
                    InitializeConditionVariable(it.ptr)
                    //pthread_cond_destroy(cond.ptr)
                }
            }
            if (mutex == null) {
                mutex = arena!!.alloc<CRITICAL_SECTION>().also {
                    InitializeCriticalSection(it.ptr)
                    //pthread_mutex_destroy(mutex.ptr)
                }
            }
            nlocks.incrementAndGet()
            mutex!!
        }
        EnterCriticalSection(mut.ptr)
    }

    actual override fun unlock() {
        nrlock {
        //run {
            LeaveCriticalSection(mutex!!.ptr)

            if (nlocks.decrementAndGet() == 0) {
                //if (cond != null) DeleteConditionVariable(cond?.ptr)
                if (mutex != null) DeleteCriticalSection(mutex?.ptr)
                arena?.clear()
                cond = null
                mutex = null
                arena = null
            }
        }
    }

    actual override fun notify(unit: Unit) {
        //println("SIGNALING...")
        WakeConditionVariable(cond!!.ptr)
        //println(" --> SIGNALED")
        //pthread_cond_broadcast() // notifyall
    }

    actual override fun wait(time: FastDuration): Boolean {
        val time = time.slow

        return memScoped {
            SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, time.millisecondsInt.convert()) == ERROR_TIMEOUT
        }
    }
}
