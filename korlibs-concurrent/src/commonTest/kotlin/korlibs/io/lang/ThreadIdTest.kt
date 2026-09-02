package korlibs.io.lang

import korlibs.concurrent.thread.NativeThread
import kotlin.test.Test
import kotlin.test.assertEquals

class ThreadIdTest {
    @Test
    fun testCurrentThreadIdReturnsAlwaysTheSameValueOnTheSameThread() {
        assertEquals(NativeThread.current.id, NativeThread.current.id)
        assertEquals(NativeThread.current, NativeThread.current)
    }
}
