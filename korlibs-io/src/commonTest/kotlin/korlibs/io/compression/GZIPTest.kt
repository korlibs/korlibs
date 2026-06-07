package korlibs.io.compression

import korlibs.crypto.sha256
import korlibs.io.async.suspendTest
import korlibs.io.async.useIt
import korlibs.io.compression.deflate.GZIP
import korlibs.io.compression.deflate.GZIPNoCrc
import korlibs.io.file.std.resourcesVfs
import korlibs.io.stream.MemorySyncStream
import korlibs.io.stream.MemorySyncStreamToByteArray
import korlibs.io.stream.openAsync
import korlibs.io.stream.toAsync
import kotlin.test.Test
import kotlin.test.assertEquals

class GZIPTest {
    @Test
    fun test() = suspendTest {
        val compressedBytes = resourcesVfs["cp437_table.gzip"].readBytes()
        assertEquals(503, compressedBytes.size)
        assertEquals("A0A5BE1523C78DEA7984102DE5376255131FF7D4806D2AED6CEB3631FD058ECD", compressedBytes.sha256().hexUpper)
        val bytes = compressedBytes.uncompress(GZIP)
        assertEquals(2576, bytes.size)
        assertEquals("0D645D95D9BFCEB973AA53D89D9D82A1C108CC9B2C15BC57B8DD658FF51F8331", bytes.sha256().hexUpper)
    }

    @Test
    fun testInputStream() = suspendTest {
        val resource = resourcesVfs["cp437_table.gzip"].readBytes()
        //val output = AsyncByteArrayDeque();
        assertEquals("0D645D95D9BFCEB973AA53D89D9D82A1C108CC9B2C15BC57B8DD658FF51F8331", MemorySyncStreamToByteArray {
            val output = this
            resource.openAsync().useIt { input ->
                GZIPNoCrc.uncompress(input, output.toAsync())
            }
        }.sha256().hexUpper)
    }

    @Test
    fun testInputStream2() = suspendTest {
        val compressedBytes = resourcesVfs["cp437_table.gzip"].readBytes()
        //val output = AsyncByteArrayDeque() // @TODO: This code hangs because AsyncByteArrayDeque has a fixed size
        val output = MemorySyncStream()
        GZIPNoCrc.uncompress(compressedBytes.openAsync(), output.toAsync())
    }
}
