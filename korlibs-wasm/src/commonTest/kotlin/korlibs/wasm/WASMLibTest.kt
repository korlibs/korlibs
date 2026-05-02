package korlibs.wasm

import korlibs.io.async.*
import korlibs.platform.*
import kotlin.test.*

class WASMLibTest {
    @Test
    fun test() = suspendTest {
        if (!ADDER.isAvailable) {
            println("WASM not available in this platform ${Platform.rawOsName}")
            return@suspendTest
        }
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        assertEquals(16, adder.add(7, 9))
    }

    @Test
    fun testShortArrayToByteArray() {
        with(ADDER) {
            val shorts = shortArrayOf(0x0102, 0x0304)
            val bytes = shorts.toByteArray()
            assertEquals(4, bytes.size)
            // Little-endian: 0x0102 -> [0x02, 0x01]
            assertEquals(0x02.toByte(), bytes[0])
            assertEquals(0x01.toByte(), bytes[1])
            assertEquals(0x04.toByte(), bytes[2])
            assertEquals(0x03.toByte(), bytes[3])
        }
    }

    @Test
    fun testIntArrayToByteArray() {
        with(ADDER) {
            val ints = intArrayOf(0x01020304, 0x05060708)
            val bytes = ints.toByteArray()
            assertEquals(8, bytes.size)
            // Little-endian: 0x01020304 -> [0x04, 0x03, 0x02, 0x01]
            assertEquals(0x04.toByte(), bytes[0])
            assertEquals(0x03.toByte(), bytes[1])
            assertEquals(0x02.toByte(), bytes[2])
            assertEquals(0x01.toByte(), bytes[3])
            assertEquals(0x08.toByte(), bytes[4])
            assertEquals(0x07.toByte(), bytes[5])
            assertEquals(0x06.toByte(), bytes[6])
            assertEquals(0x05.toByte(), bytes[7])
        }
    }

    @Test
    fun testShortArrayToByteArrayEmpty() {
        with(ADDER) {
            assertEquals(0, shortArrayOf().toByteArray().size)
        }
    }

    @Test
    fun testIntArrayToByteArrayEmpty() {
        with(ADDER) {
            assertEquals(0, intArrayOf().toByteArray().size)
        }
    }

    @Test
    fun testShortArrayToByteArraySize() {
        with(ADDER) {
            val original = shortArrayOf(1, -1, 32767, -32768, 0)
            assertEquals(original.size * 2, original.toByteArray().size)
        }
    }

    @Test
    fun testIntArrayToByteArraySize() {
        with(ADDER) {
            val original = intArrayOf(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
            assertEquals(original.size * 4, original.toByteArray().size)
        }
    }

    @Test
    fun testWriteShortsAndReadShorts() = suspendTest {
        if (!ADDER.isAvailable) return@suspendTest
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        val shorts = shortArrayOf(1, -1, 32767, -32768, 0)
        adder.writeShorts(0, shorts)
        val result = adder.readShorts(0, shorts.size)
        assertContentEquals(shorts, result)
    }

    @Test
    fun testWriteIntsAndReadInts() = suspendTest {
        if (!ADDER.isAvailable) return@suspendTest
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        val ints = intArrayOf(0, 1, -1, Int.MAX_VALUE, Int.MIN_VALUE)
        adder.writeInts(0, ints)
        val result = adder.readInts(0, ints.size)
        assertContentEquals(ints, result)
    }

    @Test
    fun testReadShortsEmpty() = suspendTest {
        if (!ADDER.isAvailable) return@suspendTest
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        val result = adder.readShorts(0, 0)
        assertEquals(0, result.size)
    }

    @Test
    fun testReadIntsEmpty() = suspendTest {
        if (!ADDER.isAvailable) return@suspendTest
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        val result = adder.readInts(0, 0)
        assertEquals(0, result.size)
    }

    @Test
    fun testWriteShortsRoundtripValues() = suspendTest {
        if (!ADDER.isAvailable) return@suspendTest
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        val shorts = shortArrayOf(0x1234.toShort(), 0x5678.toShort())
        adder.writeShorts(0, shorts)
        val result = adder.readShorts(0, shorts.size)
        assertEquals(0x1234.toShort(), result[0])
        assertEquals(0x5678.toShort(), result[1])
    }

    @Test
    fun testWriteIntsRoundtripValues() = suspendTest {
        if (!ADDER.isAvailable) return@suspendTest
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        val ints = intArrayOf(0x12345678, 0xABCDEF01.toInt())
        adder.writeInts(0, ints)
        val result = adder.readInts(0, ints.size)
        assertEquals(0x12345678, result[0])
        assertEquals(0xABCDEF01.toInt(), result[1])
    }

    @Test
    fun testInvokeFuncIntVariants() = suspendTest {
        if (!ADDER.isAvailable) return@suspendTest
        val adder = ADDER.also { it.initOnce(coroutineContext) }
        assertEquals(0, adder.add(0, 0))
        assertEquals(10, adder.add(3, 7))
        assertEquals(-1, adder.add(-5, 4))
    }


    object ADDER : WASMLib(byteArrayOf(
        0x00, 0x61, 0x73, 0x6d, 0x01, 0x00, 0x00, 0x00, 0x01, 0x0a, 0x02, 0x60, 0x02, 0x7f, 0x7f, 0x01,
        0x7f, 0x60, 0x00, 0x00, 0x03, 0x03, 0x02, 0x00, 0x01, 0x04, 0x04, 0x01, 0x70, 0x00, 0x01, 0x05,
        0x03, 0x01, 0x00, 0x01, 0x06, 0x06, 0x01, 0x7f, 0x00, 0x41, 0x08, 0x0b, 0x07, 0x18, 0x03, 0x06,
        0x6d, 0x65, 0x6d, 0x6f, 0x72, 0x79, 0x02, 0x00, 0x05, 0x74, 0x61, 0x62, 0x6c, 0x65, 0x01, 0x00,
        0x03, 0x61, 0x64, 0x64, 0x00, 0x00, 0x09, 0x07, 0x01, 0x00, 0x41, 0x00, 0x0b, 0x01, 0x01, 0x0a,
        0x0c, 0x02, 0x07, 0x00, 0x20, 0x00, 0x20, 0x01, 0x6a, 0x0b, 0x02, 0x00, 0x0b
    )) {
        fun add(a: Int, b: Int): Int = invokeFuncInt("add", a, b)
    }
}
