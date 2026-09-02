package korlibs.datastructure.lock

import korlibs.concurrent.thread.NativeThread
import korlibs.concurrent.thread.nativeThread
import korlibs.time.fastMilliseconds
import kotlin.test.Test
import kotlin.test.assertEquals

class LockJvmTest {
    @Test
    fun testWaitNotifyJvm() {
        val lock = java.lang.Object()
        var log = arrayListOf<String>()
        synchronized(lock) {
            nativeThread {
                synchronized(lock) {
                    //NativeThread.sleep(10.fastMilliseconds)
                    log += "b"
                    synchronized(lock) {
                        lock.notify()
                    }
                }
            }
            NativeThread.sleep(100.fastMilliseconds)
            synchronized(lock) {
                log += "a"
                lock.wait()
                log += "c"
            }
        }
        assertEquals("abc", log.joinToString(""))
    }

}