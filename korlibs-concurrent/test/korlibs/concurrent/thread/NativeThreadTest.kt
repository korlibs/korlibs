package korlibs.concurrent.thread

import korlibs.io.async.*
import korlibs.time.*
import kotlinx.atomicfu.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.*
import kotlin.time.*

class NativeThreadTest {
    @Test
    fun test() = runTest {
        if (!NativeThread.isSupported) return@runTest
        // @TODO: Thread priority is not implemented in mingw
        val thread = NativeThread.start(priority = NativeThreadPriority.HIGHER) {
            println("NEW: " + NativeThread.current + " :: priority=" + NativeThread.current.priority)
        }
        println("PREV: " + NativeThread.current)
        thread.join()

        val savedCurrentThreadId = NativeThread.currentThreadId
        val workerThreadId = runBlockingNoJs { withContext(Dispatchers.CIO) { NativeThread.currentThreadId } }
        val workerCurrentThreadId = workerThreadId
        assertEquals(savedCurrentThreadId, NativeThread.currentThreadId)
        assertNotEquals(workerCurrentThreadId, NativeThread.currentThreadId)
    }

    @Test
    fun testThreadPool() = runTest {
        if (!NativeThread.isSupported) return@runTest

        val test = FixedPoolNativeThreadDispatcher(2, "TEST", priority = NativeThreadPriority.HIGHER, preciseTimings = true)
        //val test = NativeThreadDispatcher("TEST", priority = NativeThreadPriority.HIGHER, preciseTimings = true)
        //withContext(test) {
        repeat(1) {
        //repeat(1000) {
            val time = measureTime {
                val done2 = CompletableDeferred<Unit>()
                val done1 = CompletableDeferred<Unit>()
                val a = atomic(0)
                val b = atomic(0)
                CoroutineScope(test).launch {
                    a.addAndGet(1)
                    val time = measureTime {
                        delay(1.milliseconds)
                        a.addAndGet(2)
                    }
                    //println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! time1=$time")
                    done2.complete(Unit)
                }
                CoroutineScope(test).launch {
                    b.addAndGet(1)
                    val time = measureTime {
                        delay(1.milliseconds)
                        b.addAndGet(2)
                        done1.complete(Unit)
                    }
                    //println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! time2=$time")
                }
                //println("Sleep: ${NativeThread.current}")
                //NativeThread.sleep(10.seconds)
                val awaitTime = measureTime {
                    done1.await()
                    done2.await()
                }
                //println("############### awaitTime=$awaitTime")
                assertEquals(3, a.value)
                assertEquals(3, b.value)
            }
            println("TEST: $it, time=$time")
        }

        test.close()
    }

    /*
    class FastDeferred<T> {
        private val completeLock = Lock()
        private var completed = false
        private var value: T? = null
        private val notifiers = arrayListOf<() -> Unit>()

        fun complete(value: T) {
            if (completed) return

            val notifiers = completeLock {
                completed = true
                this.value = value
                this.notifiers.toList().also { this.notifiers.clear() }
            }
            notifiers.forEach { it() }
        }

        suspend fun await() = suspendCancellableCoroutine<T> { c ->
            val completed = completeLock {
                if (!completed) {
                    notifiers += {
                        c.resume(value as T)
                    }
                }
                completed
            }
            if (completed) {
                c.resume(value as T)
            }
        }
    }
    */
}