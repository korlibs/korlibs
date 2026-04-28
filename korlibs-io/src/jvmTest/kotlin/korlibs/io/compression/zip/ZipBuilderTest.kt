package korlibs.io.compression.zip

import korlibs.io.async.*
import korlibs.io.compression.*
import korlibs.io.compression.deflate.*
import korlibs.io.compression.lzma.*
import korlibs.io.file.*
import korlibs.io.file.std.*
import java.io.File
import kotlin.test.*

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
