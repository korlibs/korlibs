package korlibs.io.internal

import korlibs.io.async.suspendTest
import korlibs.io.compression.compress
import korlibs.io.compression.deflate.ZLib
import korlibs.io.compression.uncompress
import korlibs.io.stream.MemorySyncStream
import korlibs.io.stream.readS16LE
import korlibs.io.stream.toAsync
import korlibs.io.stream.write16LE
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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
