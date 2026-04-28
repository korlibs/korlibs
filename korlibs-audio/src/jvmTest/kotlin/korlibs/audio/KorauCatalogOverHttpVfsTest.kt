package korlibs.audio

import korlibs.time.milliseconds
import korlibs.audio.format.readSoundInfo
import korlibs.io.async.suspendTest
import korlibs.io.file.*
import korlibs.io.file.std.MemoryVfsMix
import korlibs.io.file.std.UrlVfs
import korlibs.io.file.std.resourcesVfs
import korlibs.io.file.std.withCatalogJail
import korlibs.io.net.http.*
import org.junit.Test
import kotlin.test.assertEquals

class KorauCatalogOverHttpVfsTest {
    val log = arrayListOf<String>()
    lateinit var http: FakeHttpClientWithServer
    lateinit var vfs: VfsFile

    suspend fun create(catalog: Boolean, readFullFileMaxSize: Long = 1024L) {
        http = FakeHttpServerClient {
            router {
                prehook {
                    log += "${it.method} ${it.path} ${it.headers}"
                }
                static("/", MemoryVfsMix(buildMap {
                    if (catalog) {
                        this["/\$catalog.json"] = """[
                            {"name": "placeholder.mp3", "size": 36072, "modifiedTime": 0, "createTime": 0, "isDirectory": false},
                        ]"""
                    }
                    this["placeholder.mp3"] = resourcesVfs["placeholder.mp3"].readBytes()
                }))
            }
        }
        vfs = UrlVfs("https://127.0.0.1:8080/", client = http).withCatalogJail { it.readFullFileMaxSize = readFullFileMaxSize }
    }

    @Test
    fun test() = suspendTest {
        create(catalog = true)
        val info = vfs["placeholder.mp3"].readSoundInfo()
        assertEquals(2507.712.milliseconds, info!!.duration)
        assertEquals("""
            GET /${'$'}catalog.json Headers()
            GET /placeholder.mp3 Headers((range, [bytes=0-32767]))
            GET /placeholder.mp3 Headers((range, [bytes=32768-36071]))
        """.trimIndent(), log.joinToString("\n"))
    }

    @Test
    fun testLongCache() = suspendTest {
        create(catalog = true, readFullFileMaxSize = 2L * 1024 * 1024)
        val info = vfs["placeholder.mp3"].readSoundInfo()
        assertEquals(2507.712.milliseconds, info!!.duration)
        assertEquals("""
            GET /${'$'}catalog.json Headers()
            GET /placeholder.mp3 Headers()
        """.trimIndent(), log.joinToString("\n"))
    }

    @Test
    fun testNoCatalog() = suspendTest {
        create(catalog = false)
        val info = vfs["placeholder.mp3"].readSoundInfo()
        assertEquals(2507.712.milliseconds, info!!.duration)
        assertEquals("""
            GET /${'$'}catalog.json Headers()
            HEAD /placeholder.mp3 Headers()
            GET /placeholder.mp3 Headers((range, [bytes=0-32767]))
            GET /placeholder.mp3 Headers((range, [bytes=32768-36071]))
        """.trimIndent(), log.joinToString("\n"))
    }

    @Test
    fun testNoCatalogLongCache() = suspendTest {
        create(catalog = false, readFullFileMaxSize = 2L * 1024 * 1024)
        val info = vfs["placeholder.mp3"].readSoundInfo()
        assertEquals(2507.712.milliseconds, info!!.duration)
        assertEquals("""
            GET /${'$'}catalog.json Headers()
            HEAD /placeholder.mp3 Headers()
            GET /placeholder.mp3 Headers()
        """.trimIndent(), log.joinToString("\n"))
    }
}
