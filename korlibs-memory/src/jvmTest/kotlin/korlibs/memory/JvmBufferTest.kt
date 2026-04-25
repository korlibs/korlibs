import korlibs.io.lang.*
import korlibs.memory.*
import kotlin.test.*

class JvmBufferTest {
    @Test
    fun testMmap() {
        val dir = Environment.tempPath
        val mem1 = Buffer.mmap("$dir/JvmBufferTest.testMmap.bin", 0L, 16L, BufferMapMode.READ_WRITE)
        val mem2 = Buffer.mmap("$dir/JvmBufferTest.testMmap.bin", 0L, 16L, BufferMapMode.READ_WRITE)

        mem1.asInt32()[0] = 0x12345678
        assertEquals(0x12345678, mem2.asInt32()[0])

        mem1.close()
        mem2.close()
    }
}