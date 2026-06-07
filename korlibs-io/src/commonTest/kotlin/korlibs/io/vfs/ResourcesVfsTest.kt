package korlibs.io.vfs

import korlibs.io.async.suspendTest
import korlibs.io.file.baseName
import korlibs.io.file.extensionLC
import korlibs.io.file.std.resourcesVfs
import korlibs.platform.Platform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList

class ResourcesVfsTest {
    @Test
    fun test() = suspendTest {
        assertEquals(
            "HELLO",
            resourcesVfs["resource.txt"].readAll().decodeToString()
        )
    }

    @Test
    fun name() = suspendTest({ Platform.isJvm }) {
        //println("[A]")
        val listing = resourcesVfs["tresfolder"].list()
        //println("[B]")
        //println("listing=$listing")

        //for (v in resourcesVfs["tresfolder"].list().filter { it.extensionLC == "txt" }.toList()) println(v)

        assertEquals(
            "[a.txt, b.txt]",
            resourcesVfs["tresfolder"].list().filter { it.extensionLC == "txt" }.toList().map { it.baseName }.sorted()
                .toString()
        )
    }

    /*
    @Test
    @Ignore // @TODO: Seems to fail on macOS CI
    fun watch() = suspendTest({ OS.isJvm }) {
        var log = String()
        println("watcher start")

        val closeable = resourcesVfs["tresfolder"].watch {
            log = it.toString()
            println(log)
        }

        resourcesVfs["tresfolder/a.txt"].touch(DateTime.now())
        kotlinx.coroutines.delay(100)
        closeable.close()

        println("watcher end")
        assertEquals("MODIFIED(JailVfs(ResourcesVfs[])[/tresfolder/a.txt])", log)
    }
    */
}
