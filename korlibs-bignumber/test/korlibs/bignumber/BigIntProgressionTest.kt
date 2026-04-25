package korlibs.bignumber

import korlibs.bignumber.ranges.*
import kotlin.test.*

class BigIntProgressionTest {
    @Test
    fun test() {
        val p1 = BigIntProgression(0.bi, 10.bi, 1.bi)
        val p2 = BigIntProgression(0.bi, 10.bi, 1.bi)
        val p3 = BigIntProgression(10.bi, 0.bi, (-1).bi)
        assertEquals(p1.hashCode(), p2.hashCode())
        assertEquals(p1, p2)
        assertNotEquals(p1, p3)
        assertNotEquals<Any>(p1, Unit)
        assertEquals("0..10 step 1", p1.toString())
    }

    @Test
    fun testNext() {
        run {
            val iterator = BigIntProgressionIterator(0.bi, 0.bi, step = 1.bi)
            iterator.next()
            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
        run {
            val iterator = BigIntProgressionIterator(0.bi, 0.bi, step = (-1).bi)
            iterator.next()
            assertFailsWith<NoSuchElementException> { iterator.next() }
        }
    }

    @Test
    fun testStepAndToStringForDescendingProgression() {
        val descending = BigIntProgression(10.bi, 0.bi, (-1).bi)
        val stepped = descending step 2.bi

        assertEquals("10 downTo 0 step 1", descending.toString())
        assertEquals("10 downTo 0 step 2", stepped.toString())
        assertEquals(listOf(10.bi, 8.bi, 6.bi, 4.bi, 2.bi, 0.bi), stepped.toList())
    }

    @Test
    fun testIteratorStartsEmptyWhenDirectionAndStepMismatch() {
        val iterator = BigIntProgressionIterator(0.bi, 10.bi, step = (-1).bi)
        assertFalse(iterator.hasNext())
        assertFailsWith<NoSuchElementException> { iterator.next() }
    }

    @Test
    fun testZeroStepThrows() {
        assertFailsWith<IllegalArgumentException> { BigIntProgression(0.bi, 10.bi, 0.bi) }
    }
}
