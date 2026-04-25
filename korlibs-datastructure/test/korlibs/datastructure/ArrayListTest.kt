package korlibs.datastructure

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ArrayListTest {
    @Test
    fun testRotated() {
        assertEquals(listOf(1, 2, 3, 4), listOf(1, 2, 3, 4).rotated(0))

        assertEquals(listOf(4, 1, 2, 3), listOf(1, 2, 3, 4).rotated(+1))
        assertEquals(listOf(3, 4, 1, 2), listOf(1, 2, 3, 4).rotated(+2))
        assertEquals(listOf(2, 3, 4, 1), listOf(1, 2, 3, 4).rotated(+3))

        assertEquals(listOf(2, 3, 4, 1), listOf(1, 2, 3, 4).rotated(-1))
        assertEquals(listOf(3, 4, 1, 2), listOf(1, 2, 3, 4).rotated(-2))
        assertEquals(listOf(4, 1, 2, 3), listOf(1, 2, 3, 4).rotated(-3))
    }

    @Test
    fun testInt() {
        val values = IntArrayList(2)
        assertEquals(0, values.size)
        assertEquals(2, values.capacity)
        values.add(1)
        assertEquals(listOf(1), values.toList())
        assertEquals(1, values.size)
        assertEquals(2, values.capacity)
        values.add(2)
        assertEquals(listOf(1, 2), values.toList())
        assertEquals(2, values.size)
        assertEquals(2, values.capacity)
        values.add(3)
        assertEquals(listOf(1, 2, 3), values.toList())
        assertEquals(3, values.size)
        assertEquals(6, values.capacity)

        run {
            val v = IntArrayList()
            v.add(1)
            v.add(2)
            v.add(3)
            assertEquals(listOf(1, 2, 3), v.toList())
            v.removeAt(1)
            assertEquals(listOf(1, 3), v.toList())
            assertEquals(2, v.size)
            v.removeAt(1)
            assertEquals(listOf(1), v.toList())
            v.removeAt(0)
            assertEquals(listOf(), v.toList())
        }
    }

    @Test
    fun testFloat() {
        val values = FloatArrayList(2)
        assertEquals(0, values.size)
        assertEquals(2, values.capacity)
        values.add(1f)
        assertEquals(listOf(1f), values.toList())
        assertEquals(1, values.size)
        assertEquals(2, values.capacity)
        values.add(2f)
        assertEquals(listOf(1f, 2f), values.toList())
        assertEquals(2, values.size)
        assertEquals(2, values.capacity)
        values.add(3f)
        assertEquals(listOf(1f, 2f, 3f), values.toList())
        assertEquals(3, values.size)
        assertEquals(6, values.capacity)

        run {
            val v = FloatArrayList()
            v.add(1f)
            v.add(2f)
            v.add(3f)
            assertEquals(listOf(1f, 2f, 3f), v.toList())
            v.removeAt(1)
            assertEquals(listOf(1f, 3f), v.toList())
            assertEquals(2, v.size)
            v.removeAt(1)
            assertEquals(listOf(1f), v.toList())
            v.removeAt(0)
            assertEquals(listOf(), v.toList())
        }
    }

    @Test
    fun testDouble() {
        val values = DoubleArrayList(2)
        assertEquals(0, values.size)
        assertEquals(2, values.capacity)
        values.add(1.0)
        assertEquals(listOf(1.0), values.toList())
        assertEquals(1, values.size)
        assertEquals(2, values.capacity)
        values.add(2.0)
        assertEquals(listOf(1.0, 2.0), values.toList())
        assertEquals(2, values.size)
        assertEquals(2, values.capacity)
        values.add(3.0)
        assertEquals(listOf(1.0, 2.0, 3.0), values.toList())
        assertEquals(3, values.size)
        assertEquals(6, values.capacity)

        run {
            val v = DoubleArrayList()
            v.add(1.0)
            v.add(2.0)
            v.add(3.0)
            assertEquals(listOf(1.0, 2.0, 3.0), v.toList())
            v.removeAt(1)
            assertEquals(listOf(1.0, 3.0), v.toList())
            assertEquals(2, v.size)
            v.removeAt(1)
            assertEquals(listOf(1.0), v.toList())
            v.removeAt(0)
            assertEquals(listOf(), v.toList())
        }
    }

    @Test
    fun map() {
        assertEquals(intArrayListOf(0, 6, 12, 18, 24), (0 until 10).mapInt { it * 3 }.filter { it % 2 == 0 })
    }

    @Test
    fun list() {
        assertEquals(intArrayListOf(1, 2, 3).toList(), listOf(1, 2, 3))
        assertEquals(listOf(1, 2, 3), intArrayListOf(1, 2, 3).toList())
    }

    @Test
    fun demo() {
        val a1 = intArrayListOf(1, 2, 3, 4)
        val a2 = IntArrayList(a1)
        assertEquals(listOf(1, 2, 3, 4), a1.toList())
        assertEquals(listOf(1, 2, 3, 4), a2.toList())
    }

    @Test
    fun listIterator() {
        assertEquals(listOf(2, 3, 4), intArrayListOf(1, 2, 3, 4).listIterator(1).asSequence().toList())
    }

    @Test
    fun testInsertAt() {
        assertEquals(listOf(4, 3, 2, 1), intArrayListOf().insertAt(0, 1).insertAt(0, 2).insertAt(0, 3).insertAt(0, 4).toList())
        assertEquals(listOf(21, 22, 23, 11, 12, 13, 1, 2, 3), intArrayListOf().insertAt(0, intArrayOf(1, 2, 3)).insertAt(0, intArrayOf(11, 12, 13)).insertAt(0, intArrayOf(21, 22, 23)).toList())
    }

    @Test
    fun testRemoveAt() {
        assertEquals(listOf(1, 5), intArrayListOf(1, 2, 3, 4, 5).apply { removeAt(1, 3) }.toList())
        assertEquals(listOf(1), intArrayListOf(1, 2, 3, 4, 5).apply { removeAt(1, 4) }.toList())
        assertEquals(listOf(5), intArrayListOf(1, 2, 3, 4, 5).apply { removeAt(0, 4) }.toList())
        assertEquals(listOf(), intArrayListOf(1, 2, 3, 4, 5).apply { removeAt(0, 5) }.toList())
    }

    @Test
    fun testHashCodeIntArrayList() {
        val a = IntArrayList(10)
        val hc0 = a.hashCode()
        a.add(10)
        val hc1 = a.hashCode()
        a.add(20)
        val hc2 = a.hashCode()
        a.removeAt(a.size - 1)
        val hc3 = a.hashCode()
        val b = IntArrayList(10).also { it.add(a) }
        assertNotEquals(hc1, hc0)
        assertNotEquals(hc1, hc2)
        assertEquals(hc1, hc3)
        assertEquals(a, b)
    }

    @Test
    fun testHashCodeFloatArrayList() {
        val a = FloatArrayList(10)
        val hc0 = a.hashCode()
        a.add(10f)
        val hc1 = a.hashCode()
        a.add(20f)
        val hc2 = a.hashCode()
        a.removeAt(a.size - 1)
        val hc3 = a.hashCode()
        val b = FloatArrayList(10).also { it.add(a) }
        assertNotEquals(hc1, hc0)
        assertNotEquals(hc1, hc2)
        assertEquals(hc1, hc3)
        assertEquals(a, b)
    }

    @Test
    fun testHashCodeDoubleArrayList() {
        val a = DoubleArrayList(10)
        val hc0 = a.hashCode()
        a.add(10.0)
        val hc1 = a.hashCode()
        a.add(20.0)
        val hc2 = a.hashCode()
        a.removeAt(a.size - 1)
        val hc3 = a.hashCode()
        val b = DoubleArrayList(10).also { it.add(a) }
        assertNotEquals(hc1, hc0)
        assertNotEquals(hc1, hc2)
        assertEquals(hc1, hc3)
        assertEquals(a, b)
    }

    @Test
    fun testDoubleMultiArgAdd() {
        val v = DoubleArrayList()
        v.add(1.0, 2.0)
        assertEquals(listOf(1.0, 2.0), v.toList())
        v.add(3.0, 4.0, 5.0)
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0), v.toList())
        v.add(6.0, 7.0, 8.0, 9.0)
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0), v.toList())
        val v2 = DoubleArrayList()
        v2.add(1.0, 2.0, 3.0, 4.0, 5.0)
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0), v2.toList())
        val v3 = DoubleArrayList()
        v3.add(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0), v3.toList())
    }

    @Test
    fun testDoublePlusAssign() {
        val v = DoubleArrayList()
        v += 1.0
        v += doubleArrayOf(2.0, 3.0)
        v += DoubleArrayList().also { it.add(4.0) }
        v += listOf(5.0, 6.0)
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0), v.toList())
    }

    @Test
    fun testDoubleAddBulk() {
        val v = DoubleArrayList()
        v.add(doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0), offset = 1, length = 3)
        assertEquals(listOf(2.0, 3.0, 4.0), v.toList())

        val v2 = DoubleArrayList()
        val src = DoubleArrayList().also { it.add(10.0); it.add(20.0) }
        v2.add(src)
        assertEquals(listOf(10.0, 20.0), v2.toList())

        val v3 = DoubleArrayList()
        v3.add(listOf(5.0, 6.0, 7.0))
        assertEquals(listOf(5.0, 6.0, 7.0), v3.toList())
    }

    @Test
    fun testDoubleGetSetSetAt() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0); it.add(3.0) }
        assertEquals(2.0, v[1])
        assertEquals(2.0, v.getAt(1))
        v[1] = 99.0
        assertEquals(99.0, v[1])
        val ret = v.setAt(1, 55.0)
        assertEquals(55.0, ret)
        assertEquals(55.0, v[1])
        // set beyond length extends the list
        v[5] = 7.0
        assertEquals(6, v.size)
        assertEquals(7.0, v[5])
    }

    @Test
    fun testDoubleContains() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0); it.add(3.0) }
        assertEquals(true, v.contains(2.0))
        assertEquals(false, v.contains(99.0))
        assertEquals(true, v.containsAll(listOf(1.0, 3.0)))
        assertEquals(false, v.containsAll(listOf(1.0, 99.0)))
        assertEquals(false, v.isEmpty())
        assertEquals(true, DoubleArrayList().isEmpty())
    }

    @Test
    fun testDoubleIndexOf() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0); it.add(3.0); it.add(2.0) }
        assertEquals(1, v.indexOf(2.0, 0, v.size))
        assertEquals(3, v.lastIndexOf(2.0, 0, v.size))
        assertEquals(-1, v.indexOf(99.0, 0, v.size))
    }

    @Test
    fun testDoubleInsertAt() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(3.0) }
        v.insertAt(1, 2.0)
        assertEquals(listOf(1.0, 2.0, 3.0), v.toList())

        val v2 = DoubleArrayList().also { it.add(1.0); it.add(4.0) }
        v2.insertAt(1, 2.0, 3.0)
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0), v2.toList())

        val v3 = DoubleArrayList().also { it.add(1.0); it.add(5.0) }
        v3.insertAt(1, doubleArrayOf(0.0, 2.0, 3.0, 4.0), start = 1, end = 4)
        assertEquals(listOf(1.0, 2.0, 3.0, 4.0, 5.0), v3.toList())
    }

    @Test
    fun testDoubleSwap() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0); it.add(3.0) }
        v.swap(0, 2)
        assertEquals(listOf(3.0, 2.0, 1.0), v.toList())
    }

    @Test
    fun testDoubleRemoveAt() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0); it.add(3.0); it.add(4.0); it.add(5.0) }
        val removed = v.removeAt(1)
        assertEquals(2.0, removed)
        assertEquals(listOf(1.0, 3.0, 4.0, 5.0), v.toList())

        val removed2 = v.removeAt(1, 2)
        assertEquals(3.0, removed2)
        assertEquals(listOf(1.0, 5.0), v.toList())
    }

    @Test
    fun testDoubleCloneAndToDoubleArray() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0); it.add(3.0) }
        val clone = v.clone()
        assertEquals(v.toList(), clone.toList())
        clone.add(4.0)
        assertEquals(3, v.size) // original unaffected
        val arr = v.toDoubleArray()
        assertEquals(3, arr.size)
        assertEquals(1.0, arr[0])
    }

    @Test
    fun testDoubleListIteratorAndSubList() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0); it.add(3.0); it.add(4.0) }
        assertEquals(listOf(2.0, 3.0, 4.0), v.listIterator(1).asSequence().toList())
        assertEquals(listOf(2.0, 3.0), v.subList(1, 3))
    }

    @Test
    fun testDoubleToString() {
        val v = DoubleArrayList().also { it.add(1.0); it.add(2.0) }
        if (v.toString() != "[1.0, 2.0]" && v.toString() != "[1, 2]") {
            throw AssertionError("expected: <[1.0, 2.0]> or <[1, 2]> but was: $v")
        }
    }

    @Test
    fun testHashCodeLongArrayList() {
        val a = ArrayList<Any>(10)
        val hc0 = a.hashCode()
        a.add(Unit)
        val hc1 = a.hashCode()
        a.add(Unit)
        val hc2 = a.hashCode()
        a.removeAt(a.size - 1)
        val hc3 = a.hashCode()
        val b = ArrayList<Any>(10).also { it.addAll(a) }
        assertNotEquals(hc1, hc0)
        assertNotEquals(hc1, hc2)
        assertEquals(hc1, hc3)
        assertEquals(a, b)
    }
}
