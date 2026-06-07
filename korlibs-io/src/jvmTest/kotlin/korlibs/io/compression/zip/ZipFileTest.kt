package korlibs.io.compression.zip

import korlibs.io.async.suspendTest
import korlibs.io.async.useIt
import korlibs.io.file.std.openAsZip
import korlibs.io.file.std.resourcesVfs
import korlibs.io.stream.openAsync
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ZipFileTest {
    @Test
    fun test() = suspendTest {
        resourcesVfs["krita1.kra"].open().useIt { stream ->
            val zip = ZipFile(stream)
            //println(zip.files)
            val vfs = stream.openAsZip()
            assertEquals(65859L, vfs["mergedimage.png"].size())
        }
    }

    @Test
    fun testInvalidZipFile() = suspendTest {
        val e = assertFailsWith<IllegalArgumentException> { ZipFile(resourcesVfs["Buildings.xp"].readAll().openAsync()) }
        assertEquals("Not a zip file : aa0221ea03a1ae62fe8150bb987f20841042082184faea3f429fcac518770100 : 1571", e.message)
    }
}
