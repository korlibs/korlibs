package korlibs.io.lang

import korlibs.concurrent.lock.*
import korlibs.concurrent.thread.*
import korlibs.time.*
import kotlin.test.*

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
