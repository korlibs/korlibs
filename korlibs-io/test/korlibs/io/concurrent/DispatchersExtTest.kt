package korlibs.io.concurrent

import korlibs.concurrent.thread.*
import korlibs.datastructure.algo.Historiogram
import korlibs.io.async.suspendTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.test.*

class DispatchersExtTest {
    @Test
    @Ignore
    fun test() = suspendTest {
        val historiogram = Historiogram()
        val dispatcher = Dispatchers.createFixedThreadDispatcher("hello", threadCount = 4)
        try {
            withContext(dispatcher) {
                for (n in 0 until 8192) {
                    historiogram.add(NativeThread.currentThreadId.toInt())
                    yield()
                }
            }
            val map = historiogram.getMapCopy().toMap()
            val seq = "${map.size}:${map.values.sum()}"
            //assertEquals(if (Platform.hasMultithreadedSharedHeap) "4:8192" else "1:8192", "${map.size}:${map.values.sum()}")
            assertTrue {
                seq in setOf("1:8192", "2:8192", "3:8192", "4:8192")
            }
        } finally {
            dispatcher.close()
        }
    }
}
