package korlibs.datastructure.iterators

import korlibs.datastructure.mapInt
import korlibs.datastructure.toIntArrayList
import korlibs.io.async.ConcurrencyLevel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers

class ParallelTest {
    @Test
    fun test() {
        assertTrue("CONCURRENCY_COUNT:${Dispatchers.ConcurrencyLevel} >= 1") { Dispatchers.ConcurrencyLevel >= 1 }
        for (n in 0 until 128) {
            val list = (0 until n).mapInt { it }
            assertEquals(list.mapInt { it * 2 }.toIntArrayList(), list.parallelMapInt { it * 2 }.toIntArrayList())
        }
    }
}
