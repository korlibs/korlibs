package korlibs.io.runtime

import korlibs.io.file.*
import korlibs.io.file.std.*
import korlibs.io.net.*
import korlibs.io.net.http.*
import korlibs.io.stream.*
import korlibs.io.util.toByteArray
import korlibs.io.util.toInt8Array
import korlibs.wasm.*
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.files.*
import org.w3c.xhr.*

external class JsNavigator {
    val platform: String
}

private external val navigator: JsNavigator // browser

@JsFun("() => { return 'arraybuffer'; }")
external private fun TYPE_ARRAYBUFFER(): XMLHttpRequestResponseType

object JsRuntimeBrowser : JsRuntime() {
    val jsNavigator get() = navigator

    override val rawOsName: String = navigator.platform
    override val isBrowser get() = true

    val href by lazy { document.location?.href ?: "." }
    val baseUrl by lazy { if (href.endsWith("/")) href else href.substringBeforeLast('/') }

    override fun existsSync(path: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun currentDir(): String = baseUrl

    override fun envs(): Map<String, String> =
        QueryString.decode((document.location?.search ?: "").trimStart('?')).map { it.key to (it.value.firstOrNull() ?: it.key) }.toMap()

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
