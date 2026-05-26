package korlibs.io.compression.zip

import java.io.File
import korlibs.io.async.suspendTest
import korlibs.io.compression.CompressionMethod
import korlibs.io.compression.deflate.Deflate
import korlibs.io.compression.lzma.Lzma
import korlibs.io.compression.withLevel
import korlibs.io.file.VfsFile
import korlibs.io.file.std.MemoryVfs
import korlibs.io.file.std.MemoryVfsMix
import korlibs.io.file.std.SingleFileMemoryVfs
import korlibs.io.file.std.createZipFromTree
import korlibs.io.file.std.createZipFromTreeTo
import korlibs.io.file.std.openAsZip
import kotlin.test.Test
import kotlin.test.assertEquals

class ZipBuilderTest {
    suspend fun VfsFile.dumpZip() = openAsZip().listRecursiveSimple().joinToString(", ") { it.path }

    @Test
    fun testZipBuilder() = suspendTest {
        val STR = "this is a long string to see if compression works as expected"
        val root = MemoryVfsMix(
            "a/b/c/hello.txt" to STR
        )

        val compression = Deflate.withLevel(6)

        //root["a"].createZipFromTreeTo("/tmp/demo.zip".uniVfs, compression = Lzma.withLevel(9))

        assertEquals(
            "/a, /a/b, /a/b/c, /a/b/c/hello.txt",
            root["a"].createZipFromTreeTo(SingleFileMemoryVfs(""), compression = compression, useFolderAsRoot = false).dumpZip()
        )
        assertEquals(
            "/b, /b/c, /b/c/hello.txt",
            root["a"].createZipFromTreeTo(SingleFileMemoryVfs(""), compression = compression, useFolderAsRoot = true).dumpZip()
        )

        assertEquals(
            STR,
            root["a"].createZipFromTreeTo(SingleFileMemoryVfs(""), compression = compression, useFolderAsRoot = true).openAsZip()["/b/c/hello.txt"].readString()
        )

        assertEquals(
            STR,
            root["a"].createZipFromTreeTo(SingleFileMemoryVfs(""), compression = CompressionMethod.Uncompressed, useFolderAsRoot = true).openAsZip()["/b/c/hello.txt"].readString()
        )

        assertEquals(
            STR,
            root["a"].createZipFromTreeTo(SingleFileMemoryVfs(""), compression = Lzma.withLevel(6), useFolderAsRoot = true).openAsZip(compressionMethods = listOf(Deflate, Lzma))["/b/c/hello.txt"].readString()
        )

    }

    @Test
    fun testZip() = suspendTest {
        val vfs = MemoryVfs()
        val indexFile = vfs["textFileTest.txt"]
        indexFile.writeString("TestCode")
        val mediaFolder = vfs["subfolder"]
        mediaFolder.mkdir()
        mediaFolder["test.txt"].writeString("TestCode")
        val bytes = vfs.createZipFromTree()
        val file = File("zipTest.zip")
        file.writeBytes(bytes)
    }
    
}
