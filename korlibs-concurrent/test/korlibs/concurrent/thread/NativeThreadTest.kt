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

        //for (n in 0 until 10000) {
        val test = FixedPoolNativeThreadDispatcher(2, "TEST", priority = NativeThreadPriority.HIGHER, preciseTimings = true)
        repeat(1) {
        //repeat(10000000) {
            val time = measureTime {
                val done2 = CompletableDeferred<Unit>()
                val done1 = CompletableDeferred<Unit>()
                var a = atomic(0)
                var b = atomic(0)
                CoroutineScope(test).launch {
                    a.addAndGet(1)
                    delay(1.milliseconds)
                    a.addAndGet(2)
                    done2.complete(Unit)
                }
                CoroutineScope(test).launch {
                    b.addAndGet(1)
                    val time = measureTime {
                        delay(1.milliseconds)
                    }
                    //println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! time=$time")
                    b.addAndGet(2)
                    done1.complete(Unit)
                }
                //println("Sleep: ${NativeThread.current}")
                //NativeThread.sleep(10.seconds)
                done1.await()
                done2.await()
                assertEquals(3, a.value)
                assertEquals(3, b.value)
            }
            println("TEST: $it, time=$time")
        }
        test.close()
    }
}