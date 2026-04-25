package korlibs.io.internal

import korlibs.io.async.*
import korlibs.io.compression.*
import korlibs.io.compression.deflate.*
import korlibs.io.stream.*
import kotlinx.coroutines.*
import kotlin.native.concurrent.*
import kotlin.test.*

class TempBytesNativeTest {
    @Test
    fun test() = suspendTest {
        val result = withContext(Dispatchers.IO) {
            runBlocking {
                val memory = MemorySyncStream().toAsync()
                memory.write16LE(11)
                memory.position = 0L

                val mem = ByteArray(1024) { (it % 16).toByte() }
                val mem2 = mem.compress(ZLib).uncompress(ZLib)

                Triple(memory.readS16LE(), memory.size(), mem.contentEquals(mem2))
            }
        }
        assertEquals(Triple(11, 2L, true), result)
    }
}
