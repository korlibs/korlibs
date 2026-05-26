package korlibs.io.async

import korlibs.time.milliseconds
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay

class AsyncQueueTest {
	@Test
	fun test() = suspendTest {
		val completed = CompletableDeferred<Unit>()
		val queue = AsyncQueue().withContext()
		var log = ""

		queue {
			delay(10.milliseconds)
			log += "a"
		}
		queue {
			delay(5.milliseconds)
			log += "b"
		}
		queue {
			delay(1.milliseconds)
			log += "c"
			completed.complete(Unit)
		}
		completed.await()
		assertEquals("abc", log)
	}
}
