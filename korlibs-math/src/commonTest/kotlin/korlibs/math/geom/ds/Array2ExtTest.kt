package korlibs.math.geom.ds

import korlibs.datastructure.Array2
import korlibs.math.geom.PointInt
import kotlin.test.Test
import kotlin.test.assertEquals

class Array2ExtTest {
    val array = Array2(10, 10) { 0 }

    @Test
    fun test() {
        array[PointInt(5, 5)] = 10
        assertEquals(10, array[5, 5])
    }
}
