@file:Suppress("PackageDirectoryMismatch")

package korlibs.concurrent.lock

import korlibs.concurrent.thread.*
import korlibs.time.*
import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

interface BaseLock {
    fun notify(unit: Unit = Unit)
    fun wait(time: FastDuration): Boolean
    fun wait(time: Duration): Boolean = wait(time.fast)
    //fun lock()
    //fun unlock()
}


//typealias Lock = BaseLock
//typealias NonRecursiveLock = BaseLock

//inline operator fun <T> BaseLock.invoke(callback: () -> T): T {
//    lock()
//    try {
//        return callback()
//    } finally {
//        unlock()
//    }
//}

/**
 * Reentrant typical lock.
 */
expect class Lock() : BaseLock {
    override fun notify(unit: Unit)
    override fun wait(time: FastDuration): Boolean
    inline operator fun <T> invoke(callback: () -> T): T
}

/**
 * Optimized lock that cannot be called inside another lock,
 * don't keep the current thread id, or a list of threads to awake
 * It is lightweight and just requires an atomic.
 * Does busy-waiting instead of sleeping the thread.
 */
expect class NonRecursiveLock() : BaseLock {
    override fun notify(unit: Unit)
    override fun wait(time: FastDuration): Boolean
    inline operator fun <T> invoke(callback: () -> T): T
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
