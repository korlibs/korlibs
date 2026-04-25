package korlibs.memory

import kotlin.test.assertEquals

class BitsTest {
	@kotlin.test.Test
	fun name() {
		val a = byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08)
		assertEquals("0102030405060708", "%016X".format(a.getS64BE(0)))
		assertEquals("0807060504030201", "%016X".format(a.getS64LE(0)))

		assertEquals("01020304", "%08X".format(a.getS32BE(0)))
		assertEquals("04030201", "%08X".format(a.getS32LE(0)))

		assertEquals("010203", "%06X".format(a.getS24BE(0)))
		assertEquals("030201", "%06X".format(a.getS24LE(0)))

		assertEquals("0102", "%04X".format(a.getS16BE(0)))
		assertEquals("0201", "%04X".format(a.getS16LE(0)))

		assertEquals("01", "%02X".format(a.getS8(0)))

		val data = (0 until 128).map { ((it + 35363) * 104723).toByte() }.toByteArray()
        assertEquals(data.getS64BE(0), data.getS64LE(0).reverseBytes())
        assertEquals(data.getS32BE(0), data.getS32LE(0).reverseBytes())
		assertEquals(data.getU32BE(0).toInt(), data.getU32LE(0).toInt().reverseBytes())
		assertEquals(data.getS16BE(0).toShort(), data.getS16LE(0).toShort().reverseBytes())
		assertEquals(data.getU16BE(0).toShort(), data.getU16LE(0).toShort().reverseBytes())
	}
}
