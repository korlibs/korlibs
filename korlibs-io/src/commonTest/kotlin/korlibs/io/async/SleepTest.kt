package korlibs.io.async

import korlibs.time.DateTime
import korlibs.time.milliseconds
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.delay

class SleepTest {
	// @TODO: Change once we don't wait all the delay time
	val time get() = DateTime.now()

	@Test
	fun name() = suspendTest {
		val start = time
		delay(10)
		delay(20)
		val end = time
		assertTrue((end - start) > 25.milliseconds)
	}
}
