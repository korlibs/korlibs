package korlibs.datastructure

import kotlin.test.*

class StackedDoubleArray2Test {
    @Test
    fun test() {
        val value = StackedDoubleArray2(2, 2)
        assertEquals(StackedDoubleArray2.EMPTY, value.getFirst(0, 0))
        value.push(0, 0, 1.0)
        value.push(0, 0, 2.0)
        value.push(0, 0, 3.0)

        assertEquals(3, value.getStackLevel(0, 0))
        assertEquals(1.0, value.getFirst(0, 0))
        assertEquals(2.0, value.get(0, 0, 1))
        assertEquals(3.0, value.getLast(0, 0))

        value.removeLast(0, 0)
        assertEquals(1.0, value.getFirst(0, 0))
        assertEquals(2.0, value.getLast(0, 0))
        assertEquals(2, value.getStackLevel(0, 0))

        value.removeLast(0, 0)
        assertEquals(1.0, value.getFirst(0, 0))
        assertEquals(1.0, value.getLast(0, 0))
        assertEquals(1, value.getStackLevel(0, 0))

        value.removeLast(0, 0)
        assertEquals(StackedDoubleArray2.EMPTY, value.getFirst(0, 0))
        assertEquals(StackedDoubleArray2.EMPTY, value.getLast(0, 0))
        assertEquals(0, value.getStackLevel(0, 0))

        assertEquals(StackedDoubleArray2.EMPTY, value.getFirst(1, 0))
        assertEquals(0, value.getStackLevel(1, 0))
    }

    @Test
    fun test2() {
        val map = StackedDoubleArray2(2, 2)
        for (n in 0 until 5) {
            map[0, 0, n] = 1 + n.toDouble()
            map[1, 0, n] = 1 + (n.toDouble() * 2)
        }
        fun getValuesAt(x: Int, y: Int) = (0 until map.getStackLevel(x, y)).map { map[x, y, it] }

        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0), getValuesAt(0, 0))
        assertEquals(listOf(1.0, 3.0, 5.0, 7.0, 9.0), getValuesAt(1, 0))
        map.removeAt(0, 0, 1)
        map.removeAt(1, 0, 2)
        assertEquals(listOf(1.0, 3.0, 4.0, 5.0), getValuesAt(0, 0))
        assertEquals(listOf(1.0, 3.0, 7.0, 9.0), getValuesAt(1, 0))
        map.removeLast(0, 0)
        map.removeLast(1, 0)
        assertEquals(listOf(1.0, 3.0, 4.0), getValuesAt(0, 0))
        assertEquals(listOf(1.0, 3.0, 7.0), getValuesAt(1, 0))
        map.removeAt(0, 0, 0)
        map.removeAt(1, 0, 2)
        assertEquals(listOf(3.0, 4.0), getValuesAt(0, 0))
        assertEquals(listOf(1.0, 3.0), getValuesAt(1, 0))

        // Invalids
        map.removeAt(1, 0, 2)
        map.removeAt(1, 0, 10)
        map.removeAt(1, 0, -1)
        map.removeAt(2, 0, 0)
        map.removeAt(2, 0, 2)
        map.removeAt(2, 0, 10)
        map.removeAt(2, 0, -1)
    }

    @Test
    fun testInfinite() {
        val infinite = InfiniteGridStackedDoubleArray2()
        infinite.push(-2000000, -1000, 1.0)
        infinite.push(0, 0, 2.0)
        infinite.push(10000, 2000000, 3.0)

        println(infinite.getLast(-2000000, -1000))
        println(infinite.getLast(0, 0))
        println(infinite.getLast(10000, 2000000))
    }

    @Test
    fun testInitialSetLevel() {
        val s = StackedDoubleArray2(DoubleArray2(2, 2, doubleArrayOf(10.0, 20.0, 30.0, 40.0)))
        assertEquals(1, s.getStackLevel(0, 0))
        assertEquals(1, s.getStackLevel(1, 1))
    }
}
