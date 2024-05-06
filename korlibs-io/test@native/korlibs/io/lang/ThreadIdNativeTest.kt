package korlibs.io.lang

import korlibs.concurrent.thread.*
import kotlinx.coroutines.*
import kotlin.test.*

class ThreadIdNativeTest {
    @Test
    fun testWorkerHaveDifferentThreadId() {
        val savedCurrentThreadId = NativeThread.currentThreadId
        val workerThreadId = runBlocking { withContext(Dispatchers.IO) { NativeThread.currentThreadId }) }
        val workerCurrentThreadId = workerThreadId
        assertEquals(savedCurrentThreadId, NativeThread.currentThreadId)
        assertNotEquals(workerCurrentThreadId, NativeThread.currentThreadId)
    }
}
