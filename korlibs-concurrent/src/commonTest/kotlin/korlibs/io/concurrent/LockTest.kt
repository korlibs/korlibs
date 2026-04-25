package korlibs.io.concurrent

import korlibs.concurrent.lock.*
import korlibs.concurrent.thread.*
import korlibs.time.*
import kotlin.test.*
import kotlin.time.*

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

        repeat(1) {
        //repeat(1000) {
            val time = measureTime {
                val lock = Lock()
                var log = arrayListOf<String>()
                lock {
                    nativeThread(name = "NOTIFY_THREAD") {
                        lock { // will start when lock.waitForever() is called
                            log += "b"
                            lock {
                                lock.notify()
                            }
                        }
                    }
                    NativeThread.sleepExact(1.fastMilliseconds)
                    lock {
                        log += "a"
                        lock.waitForever()
                        log += "c" // will continue when lock.notify() is called
                    }
                }
                assertEquals("abc", log.joinToString(""))
            }
            println("TEST: it=$it, time=$time")
        }
    }

    @Test
    fun testNotifyError() {
        assertFails { Lock().notify() }
    }

    @Test
    fun testWaitError() {
        assertFails { Lock().wait(1.seconds) }
    }
}
