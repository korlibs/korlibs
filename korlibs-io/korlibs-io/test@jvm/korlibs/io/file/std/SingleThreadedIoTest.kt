package korlibs.io.file.std

import korlibs.concurrent.thread.*
import korlibs.io.async.*
import korlibs.io.lang.*
import org.junit.*
import org.junit.Test
import kotlin.test.*

class SingleThreadedIoTest {
	@Test
	fun test() = suspendTest {
		val thread1 = NativeThread.currentThreadId
		localCurrentDirVfs["temp.txt"].writeString("temp")
		val thread2 = NativeThread.currentThreadId
		assertEquals(thread1, thread2)
		localCurrentDirVfs["temp.txt"].delete()
	}
}
