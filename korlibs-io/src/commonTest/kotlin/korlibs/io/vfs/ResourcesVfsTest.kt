package korlibs.io.vfs

import korlibs.io.async.*
import korlibs.io.file.*
import korlibs.io.file.std.*
import korlibs.platform.*
import kotlinx.coroutines.flow.*
import kotlin.test.*

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
