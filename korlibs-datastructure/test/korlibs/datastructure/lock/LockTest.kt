package korlibs.datastructure.lock

import korlibs.concurrent.lock.*
import korlibs.concurrent.thread.*
import korlibs.datastructure.thread.*
import korlibs.platform.*
import korlibs.time.*
import kotlin.test.*

class LockTest {
    @Test
    fun test() {
        val lock = Lock()
        var a = 0
        lock {
            lock {
                a++
            }
        }
        assertEquals(1, a)
    }

    @Test
    fun test2() {
        val lock = NonRecursiveLock()
        var a = 0
        lock {
            a++
        }
        assertEquals(1, a)
    }

    @Test
    fun testWaitNotify() {
        if (!NativeThread.isSupported) return

        val lock = Lock()
        var log = arrayListOf<String>()
        lock {
            nativeThread {
                lock { // will start when lock.waitForever() is called
                    log += "b"
                    lock {
                        lock.notify()
                    }
                }
            }
            NativeThread.sleep(100.fastMilliseconds)
            lock {
                log += "a"
                lock.waitForever()
                log += "c" // will continue when lock.notify() is called
            }
        }
        assertEquals("abc", log.joinToString(""))
    }

    @Test
    fun testNotifyError() {
        val lock = Lock()
        assertFails { lock.notify() }
    }

    @Test
    fun testWaitError() {
        val lock = Lock()
        assertFails { lock.wait(1.seconds) }
    }
}
