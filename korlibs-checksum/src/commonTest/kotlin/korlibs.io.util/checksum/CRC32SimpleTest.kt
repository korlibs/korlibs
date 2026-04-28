package korlibs.io.util.checksum

import kotlin.test.Test
import kotlin.test.assertEquals

class CRC32SimpleTest {
	@Test
	fun test() {
		assertEquals(0x414fa339, "The quick brown fox jumps over the lazy dog".encodeToByteArray().checksum(CRC32))
	}
}
