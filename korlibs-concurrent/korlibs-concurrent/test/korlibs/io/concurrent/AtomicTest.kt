package korlibs.io.concurrent

import kotlinx.atomicfu.*
import kotlin.test.*

class AtomicTest {
	@Test
	fun test() {
		val value = atomic(0)
		assertEquals(1, value.incrementAndGet())
		assertEquals(1, value.value++)
		assertEquals(2, value.value)
		assertEquals(3, ++value.value)
	}

    @Test
    fun testSingleton() {
        val value = singleton
        value.value = 0
        assertEquals(1, value.incrementAndGet())
        assertEquals(1, value.value++)
        assertEquals(2, value.value)
        assertEquals(3, ++value.value)
    }

    @Test
    fun testSingleton2() {
        val value = singleton
        value.value = 0
        assertEquals(0, value.getAndIncrement())
        assertEquals(1, value.getAndAdd(10))
        assertEquals(11, value.value)
    }

    companion object {
        val singleton = atomic(0)
    }
}
