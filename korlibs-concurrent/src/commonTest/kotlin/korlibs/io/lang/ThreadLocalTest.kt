package korlibs.io.lang

import korlibs.concurrent.lock.Lock
import korlibs.concurrent.thread.NativeThread
import korlibs.concurrent.thread.nativeThread
import korlibs.concurrent.thread.sleep
import korlibs.time.seconds
import kotlin.test.Test
import kotlin.test.assertEquals

class ThreadLocalTest {
    @Test
    fun test() {
        if (!NativeThread.isSupported) return

        var n = 0
        val lock = Lock()
        val log = arrayListOf<String>()
        val tl = threadLocal { n++ }
        log += "main:${tl.value}"
        lock {
            nativeThread {
                //NativeThread.sleep(1.seconds)
                log += "thread:${tl.value}"; lock { lock { lock { } }; lock.notify() }
            }
            NativeThread.sleep(0.3.seconds)
            //NativeThread.sleep(1.seconds)
            lock.wait(10.seconds)
        }
        log += "main:${tl.value}"
        assertEquals(listOf("main:0", "thread:1", "main:0"), log)
    }
}
