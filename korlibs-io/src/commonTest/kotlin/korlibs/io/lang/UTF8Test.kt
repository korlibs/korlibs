package korlibs.io.lang

import korlibs.encoding.hex
import kotlin.test.Test
import kotlin.test.assertEquals

class UTF8Test {
	@Test
	fun test() {
		assertEquals(
            expected = byteArrayOf(
                'h'.code.toByte(),
                'e'.code.toByte(),
                'l'.code.toByte(),
                'l'.code.toByte(),
                'o'.code.toByte()).hex,
            actual = "hello".toByteArray(UTF8).hex,
        )
		assertEquals(
            expected = "hello",
            actual = byteArrayOf(
                'h'.code.toByte(),
                'e'.code.toByte(),
                'l'.code.toByte(),
                'l'.code.toByte(),
                'o'.code.toByte(),
            ).toString(UTF8),
        )
	}
}
