package korlibs.io.runtime

import korlibs.io.file.*
import korlibs.io.file.std.*
import korlibs.io.net.http.*
import korlibs.io.stream.*
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.khronos.webgl.*
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.xhr.*

private external val navigator: dynamic // browser

open class JsRuntimeBrowser : JsRuntime() {
    companion object : JsRuntimeBrowser()

    val jsNavigator get() = navigator

    override val rawOsName: String = navigator.platform.unsafeCast<String>()
    override val isBrowser get() = true

    val href by lazy { document.location?.href ?: "." }
    val baseUrl by lazy { if (href.endsWith("/")) href else href.substringBeforeLast('/') }

    override fun existsSync(path: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun currentDir(): String = baseUrl

    override fun openVfs(path: String): VfsFile {
        return UrlVfs(currentDir())[path].withCatalogJail().root.also {
            logger.info { "BROWSER openVfs: currentDir=${currentDir()}, path=$path, urlVfs=$it" }
        }
    }

    override fun localStorage(): VfsFile = MapLikeStorageVfs(object : SimpleStorage {
        override suspend fun get(key: String): String? = kotlinx.browser.localStorage[key]
        override suspend fun set(key: String, value: String) { kotlinx.browser.localStorage[key] = value }
        override suspend fun remove(key: String) = kotlinx.browser.localStorage.removeItem(key)
    }).root
    override fun tempVfs(): VfsFile = MemoryVfs()
    override fun createHttpClient(): HttpClient = HttpClient
}
