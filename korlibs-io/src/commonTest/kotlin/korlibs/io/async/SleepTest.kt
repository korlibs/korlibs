package korlibs.io.async

import korlibs.time.DateTime
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

class SleepTest {
	// @TODO: Change once we don't wait all the delay time
	val time get() = DateTime.now()

	@Test
	fun name() = suspendTest {
		val start = time
		delay(duration = 10.milliseconds)
		delay(duration = 20.milliseconds)
		val end = time
		assertTrue((end - start) > 25.milliseconds)
	}
}
