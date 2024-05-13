package korlibs.io.runtime

import korlibs.io.file.*
import korlibs.io.file.std.*
import korlibs.io.net.http.*
import korlibs.io.stream.*
import korlibs.memory.*
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.khronos.webgl.*
import org.w3c.dom.get
import org.w3c.dom.set
import org.w3c.fetch.*
import org.w3c.xhr.*
import kotlin.js.*

private external val navigator: dynamic // browser

object JsRuntimeBrowser : JsRuntime() {
    val jsNavigator get() = navigator

    override val rawOsName: String = navigator.platform.unsafeCast<String>()
    override val isBrowser get() = true

    val href by lazy { document.location?.href ?: "." }
    val baseUrl by lazy { if (href.endsWith("/")) href else href.substringBeforeLast('/') }

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
    override fun createHttpClient(): HttpClient = HttpClientBrowserJs()
}

class HttpClientBrowserJs : HttpClient() {
    override suspend fun requestInternal(
        method: Http.Method,
        url: String,
        headers: Http.Headers,
        content: AsyncInputStreamWithLength?
    ): Response {
        val deferred = CompletableDeferred<Response>(Job())

        val response = window.fetch(url, RequestInit(method.name, headers)).await()

        val reader = response.body.getReader().unsafeCast<ReadableStreamDefaultReader>()
        val contentLength = response.headers.get("content-length")?.toLongOrNull()
        return Response(
            status = response.status.toInt(),
            statusText = response.statusText,
            headers = Http.Headers(response.headers),
            rawContent = object : AsyncInputStreamWithLength {
                private var bytesDeque = SimpleBytesDeque()
                private var done = false
                private var position = 0L

                override suspend fun hasLength(): Boolean = contentLength != null
                override suspend fun getLength(): Long = contentLength ?: error("Can't find length")
                override suspend fun getPosition(): Long = position

                private suspend fun ensureData() {
                    if (done) return
                    if (bytesDeque.availableRead >= 1024) return
                    val result = reader.read().await()
                    done = result.done
                    bytesDeque.write(result.value)
                }


                override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
                    ensureData()
                    return this.bytesDeque.read(buffer, offset, len).also {
                        if (it >= 0) position += it
                    }
                }

                override suspend fun close() {
                    reader.cancel()
                }
            },
        )

        val xhr = XMLHttpRequest()
        xhr.open(method.name, url, true)
        xhr.responseType = XMLHttpRequestResponseType.ARRAYBUFFER

        //println("HttpClientBrowserJs.requestInternal: $method, $url, $headers, $content")

        xhr.onload = { e ->
            //val u8array = Uint8Array(xhr.response as ArrayBuffer)
            //val out = ByteArray(u8array.length)
            //for (n in out.indices) out[n] = u8array[n]

            val out = Int8Array(xhr.response.unsafeCast<ArrayBuffer>()).unsafeCast<ByteArray>()

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
        }

        xhr.onerror = { e ->
            deferred.completeExceptionally(RuntimeException("Error status=${xhr.status},'${xhr.statusText}' opening $url"))
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
            xhr.send(content.readAll())
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

private external interface Chunk {
    val done: Boolean
    val value: ByteArray
}

private external interface ReadableStreamDefaultReader {
    fun read(): Promise<Chunk>
    fun cancel()
}

interface JsIterator<T> {

}