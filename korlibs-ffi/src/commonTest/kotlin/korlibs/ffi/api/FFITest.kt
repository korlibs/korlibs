package korlibs.ffi.api

import kotlin.test.*

class FFITest {
    @Test
    fun testFFIPointer() {
        assertEquals(FFIPointer.NULL, FFIPointer(0L))
        assertEquals("FFIPointer(address=0x0000000000000000)", FFIPointer.NULL.toString())
        assertEquals("FFIPointer(address=0x0000000000001234)", FFIPointer(0x1234L).toString())
    }

    @Test
    fun testFFIFunctionRef() {
        var closed = false
        val funcRef = FFIFunctionRef { println("Hello") }
        funcRef.closer = { closed = true }
        assertEquals(-1, funcRef.slot)
        assertNull(funcRef.slots)
        val slots = arrayOfNulls<FFIFunctionRef<*>>(4)
        val slot = funcRef.allocIn(slots as Array<FFIFunctionRef<() -> Unit>?>)
        assertEquals(0, slot)
        assertEquals(funcRef, slots[0])
        funcRef.close()
        assertTrue(closed)
        assertEquals(-1, funcRef.slot)
        assertNull(funcRef.slots)
        assertNull(slots[0])
    }
}
