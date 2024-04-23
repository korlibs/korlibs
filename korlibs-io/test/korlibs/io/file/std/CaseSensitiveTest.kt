package korlibs.io.file.std

import korlibs.io.async.*
import korlibs.io.file.*
import korlibs.platform.*
import kotlin.test.*

class CaseSensitiveTest {
    private val cond: () -> Boolean = { !Platform.isJsOrWasm }
    private val condResources: () -> Boolean = { cond() && runBlockingNoSuspensions { resourcesVfs.isCaseSensitive() } }
    private val condLocal: () -> Boolean = { cond() && runBlockingNoSuspensions { rootLocalVfs.isCaseSensitive() } }

    @Test fun testResourcesVfs() = suspendTest(cond = condResources, preferSyncIo = true) { _testResourcesVfs() }
    @Test fun testResourcesVfsAsync() = suspendTest(cond = condResources, preferSyncIo = false) { _testResourcesVfs() }
    @Test fun testLocalVfs() = suspendTest(cond = condLocal, preferSyncIo = true) { _testLocalVfs() }
    @Test fun testLocalVfsAsync() = suspendTest(cond = condLocal, preferSyncIo = false) { _testLocalVfs() }
    @Test fun testLocalVfsFolder() = suspendTest(cond = condLocal, preferSyncIo = true) { _testLocalVfsFolder() }
    @Test fun testLocalVfsFolderAsync() = suspendTest(cond = condLocal, preferSyncIo = false) { _testLocalVfsFolder() }

    private suspend fun _testResourcesVfs() {
        assertEquals(false, resourcesVfs["file-not-exists.file.bin"].exists(), "File that doesn't' exists shouldn't exist")
        assertEquals(false, resourcesVfs["resource.TXT"].exists(), "File with right case sensitivity shouldn't exist")
        assertEquals(true, resourcesVfs["resource.txt"].exists(), "File with proper case, should exist")
        assertEquals(5, resourcesVfs["resource.txt"].readBytes().size, "File can be read properly")
        assertFails(message = "File with improper case sensitivity shouldn't be able to read") { resourcesVfs["resource.Txt"].readBytes().size }
    }

    private suspend fun _testLocalVfs() {
        val vfs = localVfs(StandardPaths.temp)
        val file = vfs["korio-resource.Txt"]
        file.writeString("HELLO")
        try {
            assertEquals(false, vfs["korio-resource.txt"].exists(), "File with improver case sensitivity shouldn't exists [1]")
            assertEquals(false, vfs["korio-resource.TXT"].exists(), "File with improver case sensitivity shouldn't exists [2]")
            assertEquals(true, vfs["korio-resource.Txt"].exists(), "File with improver case sensitivity should exists [3]")

            assertEquals(false, vfs["korio-resource.txt"].isFile(), "File with improver case sensitivity shouldn't be reported as file [1]")
            assertEquals(true, vfs["korio-resource.Txt"].isFile(), "File with improver case sensitivity should be reported as file [2]")
            assertEquals(false, vfs["korio-resource.txt"].isDirectory(), "File with improver case sensitivity shouldn't be reported as directory [3]")
            assertEquals(false, vfs["korio-resource.Txt"].isDirectory(), "File with prover case sensitivity shouldn't be reported as directory [4]")

            assertFails("Can't read file with improper case sensitivity") { vfs["korio-resource.txt"].readBytes() }
            assertFails("Can't read file with improper case sensitivity") { vfs["korio-resource.TXT"].readBytes() }
            assertEquals(5, vfs["korio-resource.Txt"].readBytes().size, "Can read file with proper case sensitivity")
        } finally {
            file.delete()
        }
    }

    private suspend fun _testLocalVfsFolder() {
        val vfs = localVfs(StandardPaths.temp)
        val dir = vfs["korio-resource-temp-Folder"]
        dir.mkdirs()
        dir["demo.txt"].writeString("HELLO")
        try {
            assertEquals(false, vfs["korio-resource-temp-folder"].exists(), "Folder with improper case sensitivity shouldn't exist [1]")
            assertEquals(true, vfs["korio-resource-temp-Folder"].exists(), "Folder with improper case sensitivity should exist [2]")

            assertEquals(false, vfs["korio-resource-temp-folder"].isFile(), "Folder with improper case sensitivity shouldn't be reported as folder [3]")
            assertEquals(false, vfs["korio-resource-temp-Folder"].isFile(), "Folder with proper case sensitivity shouldn't be reported as folder [4]")
            assertEquals(false, vfs["korio-resource-temp-folder"].isDirectory(), "Folder with improper case sensitivity shouldn't be reported as folder [5]")
            assertEquals(true, vfs["korio-resource-temp-Folder"].isDirectory(), "Folder with proper case sensitivity should be reported as folder [6]")

            assertEquals(listOf("demo.txt"), dir.listSimple().filter { it.baseName == "demo.txt" }.map { it.baseName })
            assertFails("File listing shouldn't work for a folder with improper case sensitivity") { vfs["korio-resource-temp-folder"].listSimple() }
        } finally {
            dir.deleteRecursively(includeSelf = true)
        }
    }
}
