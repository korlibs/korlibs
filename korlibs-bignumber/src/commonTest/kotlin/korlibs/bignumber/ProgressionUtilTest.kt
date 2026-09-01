package korlibs.bignumber

import korlibs.bignumber.ranges.getProgressionLastElement
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProgressionUtilTest {
    @Test
    fun test() {
        assertEquals(4.bi, getProgressionLastElement(0.bi, 4.bi, (+1).bi))
        assertEquals(0.bi, getProgressionLastElement((4).bi, 0.bi, (-1).bi))
        assertEquals(0.bi, getProgressionLastElement(4.bi, 0.bi, (+1).bi))
        assertEquals(4.bi, getProgressionLastElement(0.bi, 4.bi, (-1).bi))

        assertEquals((-1).bi, getProgressionLastElement((-4).bi, 0.bi, (+3).bi))
        assertEquals(3.bi, getProgressionLastElement(0.bi, 4.bi, (+3).bi))
        assertEquals((1).bi, getProgressionLastElement((4).bi, 0.bi, (-3).bi))
        assertFailsWith<IllegalArgumentException> { getProgressionLastElement(0.bi, 4.bi, 0.bi) }
    }
}
