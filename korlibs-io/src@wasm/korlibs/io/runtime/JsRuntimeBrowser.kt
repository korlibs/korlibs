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
    override fun createHttpClient(): HttpClient = HttpClientBrowserWasm()
}

class HttpClientBrowserWasm : HttpClient() {
    override suspend fun requestInternal(
        method: Http.Method,
        url: String,
        headers: Http.Headers,
        content: AsyncInputStreamWithLength?
    ): Response {
        val deferred = CompletableDeferred<Response>(Job())

        //println("HTTP REQUEST: $method, $url, $headers, $content\n")

        val xhr = XMLHttpRequest()
        xhr.open(method.name, url, true)
        //xhr.responseType = XMLHttpRequestResponseType.ARRAYBUFFER // @TODO: Error calling this accessor
        xhr.responseType = TYPE_ARRAYBUFFER()

        //println("HttpClientBrowserJs.requestInternal: $method, $url, $headers, $content")

        xhr.onload = { e ->
            //val u8array = Uint8Array(xhr.response as ArrayBuffer)
            //val out = ByteArray(u8array.length)
            //for (n in out.indices) out[n] = u8array[n]

            val out = Int8Array(xhr.response!!.unsafeCast<ArrayBuffer>()).toByteArray()

            //js("debugger;")
            deferred.complete(
                Response(
                    status = xhr.status.toInt(),
                    statusText = xhr.statusText,
                    headers = Http.Headers(xhr.getAllResponseHeaders()),
                    rawContent = out.openAsync(),
                    content = out.openAsync(),
                )
            )
            null
        }

        xhr.onerror = { e ->
            deferred.completeExceptionally(RuntimeException("Error status=${xhr.status},'${xhr.statusText}' opening $url"))
            null
        }

        for (header in headers) {
            val hnname = header.first.lowercase().trim()
            if (hnname !in unsafeHeadersNormalized) {
                xhr.setRequestHeader(header.first, header.second)
            }
        }

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                xhr.abort()
            }
        }

        if (content != null) {
            xhr.send(Blob(jsArrayOf(content.readAll().toInt8Array())))
        } else {
            xhr.send()
        }
        content?.close()
        return deferred.await()
    }

    companion object {
        val unsafeHeadersNormalized = setOf(
            "connection",
            Http.Headers.ContentLength.lowercase()
        )
    }
}
