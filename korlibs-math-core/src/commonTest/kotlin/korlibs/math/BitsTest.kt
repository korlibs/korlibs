package korlibs.math

import korlibs.memory.*
import kotlin.rotateLeft
import kotlin.rotateRight
import kotlin.test.*

class BitsTest {
	@Test
	fun rotate() {
		val v = 0b10110111_01111011_11101111_11001000.toInt()
		assertEquals(0b0110111_01111011_11101111_11001000_1.toInt(), v.rotateLeft(1))
		assertEquals(0b0_10110111_01111011_11101111_1100100.toInt(), v.rotateRight(1))
		assertEquals(0b110111_01111011_11101111_11001000_10.toInt(), v.rotateLeft(2))
		assertEquals(0b00_10110111_01111011_11101111_110010.toInt(), v.rotateRight(2))
	}


	@Test
	fun clz() {
		assertEquals(32, 0.countLeadingZeros())
		for (n in 0 until 31) {
			assertEquals(31 - n, (1 shl n).countLeadingZeros())
		}
	}

	@Test
	fun testCountTrailingZeros() {
		assertEquals(32, (0b00000000000000000000000000000000).countTrailingZeros())
		assertEquals(0, (0b01111111111111111111111111111111).countTrailingZeros())
		assertEquals(1, (0b11111111111111111111111111111110).toInt().countTrailingZeros())
		for (n in 0 until 32) assertEquals(n, (1 shl n).countTrailingZeros())
		for (n in 0 until 32) assertEquals(n, (0x173F52B1 shl n).countTrailingZeros())
		for (n in 0 until 32) assertEquals(n, ((-1) shl n).countTrailingZeros())
	}

	@Test
	fun testCountTrailingOnes() {
		assertEquals(32, (0b11111111111111111111111111111111).toInt().countTrailingOnes())
		assertEquals(31, (0b01111111111111111111111111111111).toInt().countTrailingOnes())
		assertEquals(0, (0b11111111111111111111111111111110).toInt().countTrailingOnes())
		for (n in 0 until 32) assertEquals(n, (1 shl n).inv().countTrailingOnes())
		for (n in 0 until 32) assertEquals(n, (0x173F52B1 shl n).inv().countTrailingOnes())
		for (n in 0 until 32) assertEquals(n, ((-1) shl n).inv().countTrailingOnes())
	}

	@Test
	fun testCountLeadingZeros() {
		assertEquals(32, (0b00000000000000000000000000000000).countLeadingZeros())
		assertEquals(1, (0b01111111111111111111111111111111).countLeadingZeros())
		assertEquals(0, (0b11111111111111111111111111111110).toInt().countLeadingZeros())
		for (n in 0 until 32) assertEquals(n, (1 shl n).reverseBits().countLeadingZeros())
		for (n in 0 until 32) assertEquals(n, (0x173F52B1 shl n).reverseBits().countLeadingZeros())
		for (n in 0 until 32) assertEquals(n, ((-1) shl n).reverseBits().countLeadingZeros())
	}

	@Test
	fun reinterpret() {
		assertEquals(0x3ff0000000000000L, 1.0.reinterpretAsLong())
		assertEquals(1.0, 0x3ff0000000000000L.reinterpretAsDouble())

		assertEquals(0x3f800000, 1f.reinterpretAsInt())
		assertEquals(1f, 0x3f800000.reinterpretAsFloat())
	}

	@Test
	fun setUnset() {
		assertEquals(1, bit(0))
		assertEquals(2, bit(1))
		assertEquals(4, bit(2))
		assertEquals(8, bit(3))
		assertEquals(0b0101, 0b0111.unsetBits(0b0010))
		assertEquals(0b0110, 0b0100.setBits(0b0010))
	}

    @Test
    fun testIntMaskRange() {
        assertEquals(0, IntMaskRange.fromRange(0, 0).toMask())
        assertEquals(0, IntMaskRange.fromRange(32, 0).toMask())
        assertEquals(0xFF, IntMaskRange.fromRange(0, 8).toMask())
        assertEquals(0xFF00, IntMaskRange.fromRange(8, 8).toMask())
        assertEquals(0xFF0000, IntMaskRange.fromRange(16, 8).toMask())
        assertEquals(0xFF000000L.toInt(), IntMaskRange.fromRange(24, 8).toMask())

        assertEquals(IntMaskRange.fromRange(0, 0), IntMaskRange.fromMask(0))
        assertEquals(IntMaskRange.fromRange(0, 1), IntMaskRange.fromMask(1))
        assertEquals(IntMaskRange.fromRange(0, 4), IntMaskRange.fromMask(0xF))
        assertEquals(IntMaskRange.fromRange(0, 8), IntMaskRange.fromMask(0xFF))
        assertEquals(IntMaskRange.fromRange(8, 8), IntMaskRange.fromMask(0xFF00))
        assertEquals(IntMaskRange.fromRange(16, 8), IntMaskRange.fromMask(0xFF0000))
        assertEquals(IntMaskRange.fromRange(24, 8), IntMaskRange.fromMask(0xFF000000L.toInt()))

        assertEquals(0xFF, IntMaskRange.fromMask(0xFF).extract(0xFF))
        assertEquals(-1, IntMaskRange.fromMask(0xFF).extractSigned(0xFF))
    }
}
