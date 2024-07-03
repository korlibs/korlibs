package korlibs.memory

import korlibs.platform.*
import kotlin.random.*
import kotlin.test.*
import kotlin.time.*

class Int64Test {
    val random = Random(0L)
    val values = listOf<Number>(-1, +1, 2, -2, 3, 10, 15, 31, 32, -15, -31, -32, 100, 1000, 10000, 0, 7, Int.MIN_VALUE, Int.MAX_VALUE, Int.MIN_VALUE.toLong() - 1, Int.MAX_VALUE - 1, Int.MIN_VALUE + 1, Int.MAX_VALUE.toLong() + 1, UInt.MAX_VALUE.toLong(), Long.MIN_VALUE, Long.MAX_VALUE).map { it.toLong() } + (0 until 20).map { random.nextLong() }

    inline fun forEach(block: (Long) -> Unit) { for (a in values) block(a) }
    inline fun forEachPair(block: (Long, Long) -> Unit) { for (a in values) for (b in values) block(a, b) }

    @Test fun testNeg() = forEach { a-> assertEquals(-a, (-a).toInt64().toLong(), "-$a") }
    @Test fun testPlus() = forEach { a-> assertEquals(+a, (+a).toInt64().toLong(), "+$a") }
    @Test fun testInv() = forEach { a-> assertEquals(a.inv(), (a).toInt64().inv().toLong(), "$a.inv()") }

    @Test fun testAdd() = forEachPair { a, b -> assertEquals(a + b, (a.toInt64() + b.toInt64()).toLong(), "$a + $b") }
    @Test fun testSub() = forEachPair { a, b -> assertEquals(a - b, (a.toInt64() - b.toInt64()).toLong(), "$a - $b") }
    @Test fun testMul() = forEachPair { a, b -> assertEquals(a * b, (a.toInt64() * b.toInt64()).toLong(), "$a * $b") }
    @Test fun testDiv() = forEachPair { a, b -> if (b != 0L) assertEquals(a / b, (a.toInt64() / b.toInt64()).toLong(), "$a / $b") }
    @Test fun testRem() = forEachPair { a, b -> if (b != 0L) assertEquals(a % b, (a.toInt64() % b.toInt64()).toLong(), "$a % $b") }

    @Test fun testCompare() = forEachPair { a, b -> assertEquals(a compareTo b, (a.toInt64() compareTo b.toInt64()), "$a compareTo $b") }

    @Test fun testOr() = forEachPair { a, b -> assertEquals(a and b, (a.toInt64() and b.toInt64()).toLong(), "$a or $b") }
    @Test fun testXor() = forEachPair { a, b -> assertEquals(a xor b, (a.toInt64() xor b.toInt64()).toLong(), "$a xor $b") }
    @Test fun testAnd() = forEachPair { a, b -> assertEquals(a and b, (a.toInt64() and b.toInt64()).toLong(), "$a and $b") }

    @Test fun testShl() = forEachPair { a, b -> assertEquals(a shl b.toInt(), (a.toInt64() shl b.toInt()).toLong(), "$a shl $b") }
    @Test fun testShr() = forEachPair { a, b -> assertEquals(a shr b.toInt(), (a.toInt64() shr b.toInt()).toLong(), "$a shr $b") }
    @Test fun testUshr() = forEachPair { a, b -> assertEquals(a ushr b.toInt(), (a.toInt64() ushr b.toInt()).toLong(), "$a ushr $b") }

    @Test
    fun testArray() {
        val array = Int64Array(10) { ((it + 1) * 3).toInt64() }
        array[7] = 777L.toInt64()
        assertEquals("3, 6, 9, 12, 15, 18, 21, 777, 27, 30", array.joinToString(", ") { it.toString() })
    }


    @Test
    @Ignore
    fun testBenchmark() {
        // WASM_JS   : DOUBLE: 9.7ms, INT64: 11.1ms, POINT: 25.5ms, LONG: 10.6ms, RAW_DOUBLE: 8.7ms
        // JVM       : DOUBLE: 3.982750ms, INT64: 4.492167ms, POINT: 3.213834ms, LONG: 3.985125ms, RAW_DOUBLE: 3.432125ms
        // JS        : DOUBLE: 54.5ms, INT64: 55ms, POINT: 11.3ms, LONG: 588.4ms, RAW_DOUBLE: 6.2ms
        // K/N_DEBUG : DOUBLE: 146.328833ms, INT64: 311.786916ms, POINT: 393.054125ms, LONG: 68.517459ms, RAW_DOUBLE: 41.593542ms

        data class PointInt(val x: Int, val y: Int)
        class PointArray(val values: IntArray) {
            constructor(size: Int) : this(IntArray(size * 2))

            val size = values.size / 2
            inline val indices get() = 0 until size
            operator fun get(index: Int): PointInt = PointInt(index * 2, index * 2 + 1)
            operator fun set(index: Int, value: PointInt) { values[index * 2] = value.x; values[index * 2 + 1] = value.y }
        }

        fun pointLoop(data: PointArray, m: Int) {
            for (n in data.indices) {
                data[n] = PointInt((n + m) * 77777, (n + m) * 99999)
            }
        }
        fun int64Loop(data: Int64Array, m: Int) {
            for (n in data.indices) {
                data[n] = Int64((n + m) * 77777, (n + m) * 99999)
            }
        }
        fun doubleLoop(data: DoubleArray, m: Int) {
            for (n in data.indices) {
                data[n] = Double.fromLowHigh((n + m) * 77777, (n + m) * 99999)
            }
        }
        fun rawDoubleLoop(data: DoubleArray, m: Int) {
            for (n in data.indices) {
                data[n] = ((n + m).toDouble() * 77777) + ((n + m).toDouble() * 99999)
            }
        }
        fun longLoop(data: LongArray, m: Int) {
            for (n in data.indices) {
                data[n] = Long.fromLowHigh((n + m) * 77777, (n + m) * 99999)
            }
        }
        val ddata = DoubleArray(10000000)
        val i64data = Int64Array(10000000)
        val ldata = LongArray(10000000)
        val pdata = PointArray(10000000)

        repeat(10) { M ->
            val int64Time = measureTime { int64Loop(i64data, M) }
            val doubleTime = measureTime { doubleLoop(ddata, M) }
            val longTime = measureTime { longLoop(ldata, M) }
            val pointTime = measureTime { pointLoop(pdata, M) }
            val rawDoubleTime = measureTime { rawDoubleLoop(ddata, M) }

            println("${Platform.rawPlatformName}[$M] : DOUBLE: $doubleTime, INT64: ${int64Time}, POINT: $pointTime, LONG: $longTime, RAW_DOUBLE: $rawDoubleTime")
            //println("${Platform.rawPlatformName}[$M] : DOUBLE: $doubleTime")
        }
    }

    @Test
    fun testArray2() {
        assertTrue { int64ArrayOf(1, 2, 3, 4).contentEquals(int64ArrayOf(1, 2, 3, 4)) }

        val array = int64ArrayOf(-1, -2, -3)
        assertEquals(Int64(-1), array[0])
        assertEquals(Int64(-2), array[1])
        assertEquals(Int64(-3), array[2])
    }

    @Test
    fun testEquals() {
        val a = 0xffffff80_00000001uL.toLong().toInt64()
        val b = (-1L).toInt64()

        assertTrue { !a.raw.equalsRaw(b.raw) }
        assertTrue { !a.equalsSafe(b) }

        assertTrue { a.equalsSafe(a) }
        assertTrue { b.equalsSafe(b) }

        // @TODO: Int64/Long should be implemented at JS level where equality is implemented
        //assertTrue { a == b } // :( we cannot control this
        //assertTrue { a.raw == b.raw } // :( we cannot control this
    }
}
