package korlibs.io.util.checksum

import kotlin.test.Test
import kotlin.test.assertEquals

class Adler32SimpleTest {
	@Test
	fun test() {
		assertEquals(1541148634, "The quick brown fox jumps over the lazy dog".encodeToByteArray().checksum(Adler32))
	}
}
