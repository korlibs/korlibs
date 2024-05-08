@file:OptIn(UnsafeNumber::class)

package korlibs.image.format.cg

import kotlinx.cinterop.*
import kotlin.test.*

class CgFloatExtTest {
    @Test
    fun test() {
        assertEquals(10.0, 10.0.toCgFloat().toDouble())
        assertEquals(10.0, 10.0.toCgFloat().toFloat().toDouble())
        assertEquals(10f, 10.0.toCgFloat().toDouble().toFloat())
        assertEquals(10f, 10f.toCgFloat().toFloat())
    }
}