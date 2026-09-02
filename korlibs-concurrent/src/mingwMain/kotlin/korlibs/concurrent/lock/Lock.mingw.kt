package korlibs.concurrent.lock

import korlibs.concurrent.thread.NativeThread
import korlibs.concurrent.thread.sleepWhile
import korlibs.time.FastDuration
import korlibs.time.millisecondsInt
import korlibs.time.slow
import kotlin.time.TimeSource
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.cinterop.Arena
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.windows.CONDITION_VARIABLE
import platform.windows.CRITICAL_SECTION
import platform.windows.DeleteCriticalSection
import platform.windows.EnterCriticalSection
import platform.windows.InitializeConditionVariable
import platform.windows.InitializeCriticalSection
import platform.windows.LeaveCriticalSection
import platform.windows.SleepConditionVariableCS
import platform.windows.WakeConditionVariable

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

    actual override fun wait(time: FastDuration): Unit = memScoped {
        //println("WAITING... ${NativeThread.current} :: $millis, time=$time")
        //println("nlocks.value=${nlocks.value}")

        val nlocks = nlocks.value - 1
        repeat(nlocks) { unlock() }
        if (time.isPositiveInfinity || time.milliseconds >= Int.MAX_VALUE) {
            //SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, INFINITE)
            //while (SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, 0.convert()) == 0) Unit
            NativeThread.sleepWhile { SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, 10.convert()) == 0 }
        } else {
            val millis = time.slow.millisecondsInt.coerceAtLeast(1)
            if (millis < 10) {
                val ttime = time.slow
                val time = TimeSource.Monotonic.markNow()
                //NativeThread.sleepWhile(exact = true) {
                //println(SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, 0.convert()))
                //println("ttime=$ttime")
                NativeThread.sleepWhile(exact = true) {
                    SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, 0.convert()) == 0 && time.elapsedNow() < ttime
                }
            } else {
                SleepConditionVariableCS(cond!!.ptr, mutex!!.ptr, millis.convert())
            }
            //val time = measureTime {

            //}
            //println("wait=time=$time, millis.convert()=${millis}")
            //println("WAITED ${NativeThread.current}")
        }
        repeat(nlocks) { lock() }
    }
}
