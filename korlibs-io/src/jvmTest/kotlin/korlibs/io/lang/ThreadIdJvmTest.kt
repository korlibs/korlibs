package korlibs.io.lang

import korlibs.concurrent.thread.NativeThread
import korlibs.io.async.suspendTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.CompletableDeferred

class ThreadIdJvmTest {
    @Test
    fun testDifferentThreadIdInDifferentThread() = suspendTest {
        val initialCurrentThreadId = NativeThread.currentThreadId
        val threadCurrentThreadIdDeferred = CompletableDeferred<Long>()
        Thread {
            threadCurrentThreadIdDeferred.complete(NativeThread.currentThreadId)
        }.also { it.start() }
        assertNotEquals(initialCurrentThreadId, threadCurrentThreadIdDeferred.await())
        val laterCurrentThreadId = NativeThread.currentThreadId
        assertEquals(initialCurrentThreadId, laterCurrentThreadId)
    }
}
