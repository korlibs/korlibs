@file:Suppress("PackageDirectoryMismatch")

package korlibs.concurrent.lock

import korlibs.concurrent.thread.*
import korlibs.time.*
import kotlinx.atomicfu.*
import kotlinx.atomicfu.locks.*
import kotlin.time.*

abstract class BaseLock {
    abstract fun lock()
    abstract fun unlock()
    abstract fun notify(unit: Unit = Unit)
    abstract fun wait(time: FastDuration): Boolean
    fun wait(time: Duration): Boolean = wait(time.fast)

    inline operator fun <T> invoke(callback: () -> T): T {
        lock()
        try {
            return callback()
        } finally {
            unlock()
        }
    }
}

/**
 * Reentrant typical lock.
 */
class Lock() : NonRecursiveLock() {
    private val reentrantLock = reentrantLock()
    private var current = atomic(0L)

    override fun lock() {
        //println("LOCK0: ${NativeThread.currentThreadId} - ${locked.value}")
        reentrantLock.lock()
        locked.incrementAndGet()
        current.value = NativeThread.currentThreadId
        //println("LOCK1: ${NativeThread.currentThreadId} - ${locked.value}")
    }

    override fun unlock() {
        //println("UNLOCK0: ${NativeThread.currentThreadId} - ${locked.value}")
        reentrantLock.unlock()
        locked.decrementAndGet()
        //println("UNLOCK1: ${NativeThread.currentThreadId} - ${locked.value}")
    }

    override fun notify(unit: Unit) {
        check(locked.value > 0) { "Must wait inside a synchronization block" }
        check(current.value == NativeThread.currentThreadId) { "Must lock the notify thread" }
        super.notify(unit)
    }
}

/**
 * Optimized lock that cannot be called inside another lock,
 * don't keep the current thread id, or a list of threads to awake
 * It is lightweight and just requires an atomic.
 * Does busy-waiting instead of sleeping the thread.
 */
open class NonRecursiveLock : BaseLock() {
    private var notified = atomic(false)
    protected var locked = atomic(0)

    override fun lock() {
        // Should we try to sleep this thread and awake it later? If the lock is short, might not be needed
        if (NativeThread.isSupported) NativeThread.spinWhile { !locked.compareAndSet(0, 1) }
    }

    override fun unlock() {
        // Should we try to sleep this thread and awake it later? If the lock is short, might not be needed
        if (NativeThread.isSupported) NativeThread.spinWhile { !locked.compareAndSet(1, 0) }
    }

    override fun notify(unit: Unit) {
        notified.value = true
    }
    override fun wait(time: FastDuration): Boolean {
        //println("WAIT!")
        if (!NativeThread.isSupported) return true
        val lockCount = locked.value
        check(lockCount > 0) { "Must wait inside a synchronization block" }
        val start = TimeSource.Monotonic.markNow()
        notified.value = false
        repeat(lockCount) { unlock() }
        try {
            NativeThread.sleepWhile { !notified.value && start.elapsedNow() < time }
        } finally {
            repeat(lockCount) { lock() }
        }
        return notified.value
    }
}


fun BaseLock.waitPrecise(time: Duration): Boolean = waitPrecise(time.fast)

fun BaseLock.waitPrecise(time: FastDuration): Boolean {
    val startTime = FastDuration.now()
    val doWait = time - 10.fastMilliseconds
    val signaled = if (doWait > 0.fastSeconds) wait(doWait) else false
    if (!signaled && doWait > 0.fastSeconds) {
        val elapsed = (FastDuration.now() - startTime)
        //println(" !!!!! SLEEP EXACT: ${elapsed - time}")
        NativeThread.sleepExact(time - elapsed)
    }
    return signaled
}

fun BaseLock.wait(time: FastDuration, precise: Boolean): Boolean {
    return if (precise) waitPrecise(time) else wait(time)
}

fun BaseLock.wait(time: Duration, precise: Boolean): Boolean {
    return if (precise) waitPrecise(time) else wait(time)
}

fun NonRecursiveLock.waitForever() {
    while (!wait(100.fastSeconds)) Unit
}

fun Lock.waitForever() {
    this { while (!wait(100.fastSeconds)) Unit }
}
