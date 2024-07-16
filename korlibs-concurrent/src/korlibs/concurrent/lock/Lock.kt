@file:Suppress("PackageDirectoryMismatch")

package korlibs.concurrent.lock

import korlibs.concurrent.thread.*
import korlibs.time.*
import kotlinx.atomicfu.*
import kotlinx.atomicfu.locks.*
import kotlin.time.*

interface BaseLock {
    companion object {
        val isSupported get() = NativeThread.isSupported
    }

    //fun lock()
    //fun unlock()
}

interface BaseLockWithNotifyAndWait : BaseLock {
    abstract fun notify(unit: Unit = Unit)
    abstract fun notifyAll(unit: Unit = Unit)
    abstract fun wait(time: FastDuration): Boolean
    fun wait(time: Duration): Boolean = wait(time.fast)
}

inline operator fun <T> ReentrantLock.invoke(callback: () -> T): T {
    lock()
    try {
        return callback()
    } finally {
        unlock()
    }
}

expect class Lock() : BaseLockWithNotifyAndWait {
    companion object { }

    override fun notify(unit: Unit)
    override fun wait(time: FastDuration): Boolean
    inline operator fun <T> invoke(callback: () -> T): T
}

inline fun <T> Lock.notify(block: () -> T): T {
    //println("NOTIFYING[0] ...")
    return this {
        //println("NOTIFYING[1] ...")
        block().also {
            //println("NOTIFYING[2] ...")
            notify()
            //println("NOTIFYING[3] ...")
        }
    }
}

val Lock.Companion.isSupported get() = NativeThread.isSupported

/**
 * Reentrant typical lock.
 */
abstract class LockImpl() : BaseLockWithNotifyAndWait {
    private var notified = atomic(false)
    private val reentrantLock = reentrantLock()
    private var current = atomic(0L)
    private var locked = atomic(0)

    inline fun <T> lockUnlock(callback: () -> T): T {
        lock()
        try {
            return callback()
        } finally {
            unlock()
        }
    }

    @PublishedApi internal fun lock() {
        //println("LOCK0: ${NativeThread.currentThreadId} - ${locked.value}")
        reentrantLock.lock()
        locked.incrementAndGet()
        current.value = NativeThread.current.id
        //println("LOCK1: ${NativeThread.currentThreadId} - ${locked.value}")
    }

    @PublishedApi internal fun unlock() {
        //println("UNLOCK0: ${NativeThread.currentThreadId} - ${locked.value}")
        check(locked.value > 0) { "Must unlock inside a synchronization block" }
        reentrantLock.unlock()
        locked.decrementAndGet()
        //println("UNLOCK1: ${NativeThread.currentThreadId} - ${locked.value}")
    }

    override fun notify(unit: Unit) {
        if (!Lock.isSupported) throw UnsupportedOperationException()
        check(locked.value > 0) { "Must notify inside a synchronization block" }
        check(current.value == NativeThread.current.id) { "Must lock the notify thread" }
        notified.value = true
    }

    override fun wait(time: FastDuration): Boolean {
        if (!Lock.isSupported) throw UnsupportedOperationException()
        //println("WAIT!")
        val lockCount = locked.value
        check(lockCount > 0) { "Must wait inside a synchronization block" }
        val start = TimeSource.Monotonic.markNow()
        notified.value = false
        repeat(lockCount) { unlock() }
        check(locked.value == 0) { "Must unlock all locks" }
        try {
            NativeThread.sleepWhile { !notified.value && start.elapsedNow() < time }
        } finally {
            repeat(lockCount) { lock() }
        }
        return notified.value
    }
}

/**
 * Optimized lock that cannot be called inside another lock,
 * don't keep the current thread id, or a list of threads to awake
 * It is lightweight and just requires an atomic.
 * Does busy-waiting instead of sleeping the thread.
 */
class NonRecursiveLock : BaseLock {
    //private var locked = atomic(0)
    @PublishedApi internal val obj = SynchronizedObject()

    inline operator fun <T> invoke(callback: () -> T): T {
        return synchronized(obj) { callback() }
        //lock()
        //try {
        //    return callback()
        //} finally {
        //    unlock()
        //}
    }

    //@PublishedApi internal fun lock() {
    //    // Should we try to sleep this thread and awake it later? If the lock is short, might not be needed
    //    if (NativeThread.isSupported) NativeThread.spinWhile { !locked.compareAndSet(0, 1) }
    //}
    //@PublishedApi internal fun unlock() {
    //    // Should we try to sleep this thread and awake it later? If the lock is short, might not be needed
    //    if (NativeThread.isSupported) NativeThread.spinWhile { !locked.compareAndSet(1, 0) }
    //}
}

fun Lock.waitPrecise(time: Duration): Boolean = waitPrecise(time.fast)

fun Lock.waitPrecise(time: FastDuration): Boolean {
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

fun Lock.wait(time: FastDuration, precise: Boolean): Boolean {
    return if (precise) waitPrecise(time) else wait(time)
}

fun Lock.wait(time: Duration, precise: Boolean): Boolean {
    return if (precise) waitPrecise(time) else wait(time)
}

fun Lock.waitForever() {
    this { waitForeverNoLock() }
}

fun Lock.waitForeverNoLock() {
    while (!wait(100.fastSeconds)) Unit
}
