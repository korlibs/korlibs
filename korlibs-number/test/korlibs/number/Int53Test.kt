package korlibs.number

import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("INTEGER_OVERFLOW")
class Int53Test {
    @Test
    fun test() {
        assertEquals(30.toInt53(), 10.toInt53() + 20.toInt53())
        assertEquals(false, 10.toInt53() > 20.toInt53())
        assertEquals(true, 10.toInt53() < 20.toInt53())
        assertEquals(Int.MIN_VALUE, Int.MAX_VALUE + 1)
        assertEquals(true, (Int.MAX_VALUE.toInt53() + 1) > 0.toInt53())
        assertEquals(2147483648.toInt53(), (Int.MAX_VALUE.toInt53() + 1))
        assertEquals(500.toInt53(), 1001.toInt53() / 2)
        assertEquals((-500).toInt53(), (-1001).toInt53() / 2)
        assertEquals(0x0FFFFF, Int53.MAX_VALUE.high)
        assertEquals(0xFFFFFFFF.toInt(), Int53.MAX_VALUE.low)

        assertEquals(0x1FFFFF, (-1L).toInt53().high)
        assertEquals(0xFFFFFFFF.toInt(), (-1L).toInt53().low)

        assertEquals(0x1FFFFF.toInt(), Int53.fromLowHigh(-1, -1).high)
        assertEquals(0xFFFFFFFF.toInt(), Int53.fromLowHigh(-1, -1).low)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testExtractBits() {
        val bits = 0x123456789ABCDL.toInt53()

        assertEquals(0x12345, bits.high)
        assertEquals(0x12345, bits.extract(32, 32))

        assertEquals(0x6789ABCD, bits.low)
        assertEquals(0x6789ABCD, bits.extract(0, 32))
        //for (n in 0 until 1000000) {
            assertEquals(0xCD, bits.extract(0, 8))
            assertEquals(0xAB, bits.extract(8, 8))
            assertEquals(0x89, bits.extract(16, 8))
            assertEquals(0x67, bits.extract(24, 8))
            assertEquals(0x45, bits.extract(32, 8))
            assertEquals(0x23, bits.extract(40, 8))
            assertEquals(0x01, bits.extract(48, 8))

        //}
    }

    @Test
    fun testInsertBits() {
        assertEquals(
            0x123456789ABCDL.toInt53(),
            Int53.ZERO
                .insert(0x01, 48, 8)
                .insert(0x23, 40, 8)
                .insert(0x45, 32, 8)
                .insert(0x67, 24, 8)
                .insert(0x89, 16, 8)
                .insert(0xAB, 8, 8)
                .insert(0xCD, 0, 8)
        )
    }

    @Test
    fun testInsertBitsNoClear() {
        assertEquals(
            0x123456789ABCDL.toInt53(),
            Int53.ZERO
                .insertNoClear(0x01, 48, 8)
                .insertNoClear(0x23, 40, 8)
                .insertNoClear(0x45, 32, 8)
                .insertNoClear(0x67, 24, 8)
                .insertNoClear(0x89, 16, 8)
                .insertNoClear(0xAB, 8, 8)
                .insertNoClear(0xCD, 0, 8)
        )
    }
}
