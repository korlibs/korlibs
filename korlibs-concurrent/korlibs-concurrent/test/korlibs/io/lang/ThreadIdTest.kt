package korlibs.io.lang

import korlibs.concurrent.thread.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ThreadIdTest {
    @Test
    fun testCurrentThreadIdReturnsAlwaysTheSameValueOnTheSameThread() {
        assertEquals(NativeThread.currentThreadId, NativeThread.currentThreadId)
        assertEquals(NativeThread.current, NativeThread.current)
    }
}
