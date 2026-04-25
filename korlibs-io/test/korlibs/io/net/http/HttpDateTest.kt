package korlibs.io.net.http

import korlibs.time.*
import kotlin.test.Test
import kotlin.test.assertEquals

class HttpDateTest {
	val Http.Companion.Date get() = DateFormat(Http.DateFormatStr)

	@Test
	fun name() {
		assertEquals("Mon, 18 Sep 2017 23:58:45 UTC", Http.Date.format(1505779125916L))
	}

	@Test
	fun reversible() {
		val STR = "Tue, 19 Sep 2017 00:58:45 UTC"

		assertEquals(STR, Http.Date.format(Http.Date.parse(STR)))
	}
}
