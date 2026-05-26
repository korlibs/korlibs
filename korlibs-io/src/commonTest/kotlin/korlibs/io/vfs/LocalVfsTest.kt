package korlibs.io.vfs

import korlibs.io.async.suspendTest
import korlibs.io.async.suspendTestNoBrowser
import korlibs.io.file.Vfs
import korlibs.io.file.VfsOpenMode
import korlibs.io.file.std.StandardPaths
import korlibs.io.file.std.localCurrentDirVfs
import korlibs.io.file.std.resourcesVfs
import korlibs.io.file.std.tempVfs
import korlibs.io.lang.FileNotFoundException
import korlibs.io.lang.UTF8
import korlibs.io.lang.toString
import korlibs.io.stream.readAll
import korlibs.io.stream.slice
import korlibs.io.util.expectException
import korlibs.platform.Platform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.toList

class LocalVfsTest {
	val temp by lazy { tempVfs }

	@Test
	fun name() = suspendTestNoBrowser {
		val content = "HELLO WORLD!"
		temp["korio.temp"].writeString(content)
		temp["korio.temp2"].writeFile(temp["korio.temp"])
		temp["korio.temp3"].writeFile(temp["korio.temp"])
		temp["korio.temp3"].writeStream(temp["korio.temp"].open().slice(0 until 3, closeParent = true))
		assertEquals(content, temp["korio.temp2"].readString())
		assertEquals("HEL", temp["korio.temp3"].readString())
		assertEquals(true, temp["korio.temp"].delete(), "deleting korio.temp")
		assertEquals(true, temp["korio.temp2"].delete(), "deleting korio.temp2")
		assertEquals(true, temp["korio.temp3"].delete(), "deleting korio.temp3")
		assertEquals(false, temp["korio.temp3"].delete(), "deleting korio.temp3")
		assertEquals(
			tempVfs["korio.temp3"].absolutePath.replace('\\', '/'),
			temp["korio.temp3"].absolutePath
		)
	}

	@Test
	fun testExec() = suspendTestNoBrowser {
        if (Platform.isAndroid) return@suspendTestNoBrowser
        if (Platform.isIos) return@suspendTestNoBrowser
        if (Platform.isJsDenoJs) return@suspendTestNoBrowser
        //val str = ">hello< '1^&) \" $ \\ \n \r \t \$test (|&,; 2" // @TODO: Couldn't get line breaks working on windows
        //val str = ">hello< '1^&) \" $ \\ \$test %test% (|&,; 2" // @TODO: Fails on windows/nodejs
        val str = "hello world"
        //val str = "1"
		when {
            Platform.isJsBrowserOrWorker -> Unit // Skip
			else -> assertEquals(str, temp.execToString(listOf("echo", str)).trim())
		}
	}

    @Test
    fun testExecNonExistant() = suspendTestNoBrowser {
        if (Platform.isAndroid) return@suspendTestNoBrowser
        if (Platform.isIos) return@suspendTestNoBrowser
        if (Platform.isJsDenoJs) return@suspendTestNoBrowser
        val message = assertFailsWith<FileNotFoundException> {
            localCurrentDirVfs["directory-that-does-not-exist"].execToString("echo", "1")
        }
        assertTrue { message.message!!.contains("is not a directory, to execute 'echo'") }
    }

    @Test
	fun ensureParent() = suspendTestNoBrowser {
		temp["korio.temp.folder/test.txt"].ensureParents().writeString("HELLO")
		temp["korio.temp.folder/test.txt"].delete()
		temp["korio.temp.folder"].delete()
	}

	private val local by lazy { localCurrentDirVfs }
	private val existing1 by lazy { local["__existing"] }
	private val unexisting1 by lazy { local["__unexisting"] }

	@Test
	fun openModeRead() = suspendTestNoBrowser {
        if (Platform.isAndroid) return@suspendTestNoBrowser
		when {
            Platform.isJsBrowserOrWorker -> Unit // Ignore
			else -> {
				existing1.writeString("hello")
				val readBytes = existing1.openUse(VfsOpenMode.READ) { readAll() }
				assertEquals("hello", readBytes.toString(UTF8))
				expectException<FileNotFoundException> { unexisting1.open(VfsOpenMode.READ).close() }
				//unexisting1.open(VfsOpenMode.READ).close()
			}
		}
	}

    @Test
    fun testUnixPermissions() = suspendTest({ (Platform.isJvm && Platform.isUnix) || Platform.isMac || Platform.isLinux }) {
        if (Platform.isJsDenoJs) return@suspendTest

        val chmod = "0713".toInt(8)
        val file = tempVfs["korio-temp123.bin"]
        file.delete()
        try {
            file.writeString("123")
            val oldPermission = file.getAttribute<Vfs.UnixPermissions>()
            //println("testUnixPermissions[before]:attribute=${file.getAttribute<Vfs.UnixPermissions>()}")
            file.chmod(Vfs.UnixPermissions(chmod))
            val newPermission = file.getAttribute<Vfs.UnixPermissions>()
            //println("testUnixPermissions[after]:attribute=${file.getAttribute<Vfs.UnixPermissions>()}")
            assertNotEquals(oldPermission!!.rbits, newPermission!!.rbits)
            assertEquals(chmod, newPermission!!.rbits)
            assertEquals(chmod, file.stat().permissions.rbits)
        } finally {
            file.delete()
        }
    }

    @Test
    fun testReadResourceTxt() = suspendTest {
        assertEquals("HELLO", resourcesVfs["resource.txt"].readString())
    }

    @Test
    fun testListFlow() = suspendTest {
        // @TODO: catalog not being generated with amper
        if (Platform.isJs || Platform.isWasm) return@suspendTest

        val cwd = localCurrentDirVfs
        val list = cwd.list().toList()
        //println(cwd.list().toList())
        assertTrue("At least one file in the current directory $list in ${StandardPaths.cwd}") {
            cwd.list().toList().isNotEmpty()
        }
        assertTrue("Checks that all the returned files are children of cwd") {
            cwd.list().toList().all { it.parent == cwd }
        }
    }

}
