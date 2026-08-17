package korlibs.memory

import korlibs.memory.asInt32
import korlibs.memory.asInt8
import korlibs.memory.getS16Array
import korlibs.memory.getS8Array
import korlibs.memory.setArray
import kotlin.test.Test
import kotlin.test.assertEquals

class NBufferTest2 {
	@Test
	fun testBasicUsage() {
		val data = Buffer(16)

		val i8 = data.asInt8()
		i8[0] = 0
		i8[1] = 1
		i8[2] = 2
		i8[3] = 3

		i8[4] = 4
		i8[5] = 5
		i8[6] = 6
		i8[7] = 7

		val i32 = data.asInt32()
		assertEquals(0x03020100, i32[0])
		assertEquals(0x07060504, i32[1])

		val i32_off1 = i32.slice(1)
		assertEquals(0x07060504, i32_off1[0])
		i32_off1[1] = 0x0B0A0908

		assertEquals(0x0B0A0908, i32[2])
	}

	@Test
	fun testArrayCopyOverlapping() {
		val i32 = Int32Buffer(10)
		i32[0] = 0x01020304
		i32[1] = 0x05060708
		arraycopy(i32, 0, i32, 1, 4)
		assertEquals(0x01020304, i32[0])
		assertEquals(0x01020304, i32[1])
		assertEquals(0x05060708, i32[2])
		assertEquals(0x00000000, i32[3])
		assertEquals(0x00000000, i32[4])

		val fast = i32.buffer

		assertEquals(listOf(4, 3, 2, 1, 4, 3, 2, 1, 8, 7, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0), (0 until (10 * 4)).map { fast.asInt8()[it].toInt() }.toList())

		val out = ByteArray(10)
		fast.getS8Array(1 * Byte.SIZE_BYTES, out, 1, 5)

		assertEquals(listOf(0, 3, 2, 1, 4, 3, 0, 0, 0, 0), out.toList().map { it.toInt() })

		val outS = ShortArray(10)
		fast.getS16Array(1 * Short.SIZE_BYTES, outS, 1, 5)

		assertEquals(listOf(0, 258, 772, 258, 1800, 1286, 0, 0, 0, 0), outS.toList().map { it.toInt() })

		fast.setArray(1 * Short.SIZE_BYTES, shortArrayOf(1, 2, 3, 4, 5, 6), 1, 4)
		fast.getS16Array(1 * Short.SIZE_BYTES, outS, 1, 5)

		assertEquals(listOf(0, 2, 3, 4, 5, 1286, 0, 0, 0, 0), outS.toList().map { it.toInt() })
	}

	@Test
	fun testNBuffer() {
		val mem = Buffer.allocDirect(10)
		for (n in 0 until 8) {
			mem.set8(n, n.toByte())
		}
		assertEquals(0x03020100, mem.getS32LE(0 * Int.SIZE_BYTES))
		assertEquals(0x07060504, mem.getS32LE(1 * Int.SIZE_BYTES))

		assertEquals(0x03020100, mem.getS32(0))
		assertEquals(0x04030201, mem.getS32(1))
		assertEquals(0x05040302, mem.getS32(2))
	}
}
