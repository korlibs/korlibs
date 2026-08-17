package korlibs.io.file.std

import korlibs.concurrent.thread.NativeThread
import korlibs.io.async.suspendTest
import kotlin.test.assertEquals
import org.junit.Test

class SingleThreadedIoTest {
	@Test
	fun test() = suspendTest {
        val thread1 = NativeThread.current.id
		localCurrentDirVfs["temp.txt"].writeString("temp")
        val thread2 = NativeThread.current.id
		assertEquals(thread1, thread2)
		localCurrentDirVfs["temp.txt"].delete()
	}
}
