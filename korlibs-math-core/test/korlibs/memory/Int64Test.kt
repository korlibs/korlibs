package korlibs.memory

import kotlin.random.*
import kotlin.test.*

class Int64Test {
    val random = Random(0L)
    val values = listOf<Number>(-1, +1, 2, -2, 3, 10, 15, 31, 32, -15, -31, -32, 100, 1000, 10000, 0, 7, Int.MIN_VALUE, Int.MAX_VALUE, UInt.MAX_VALUE.toLong(), Long.MIN_VALUE, Long.MAX_VALUE).map { it.toLong() } + (0 until 20).map { random.nextLong() }

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
}
