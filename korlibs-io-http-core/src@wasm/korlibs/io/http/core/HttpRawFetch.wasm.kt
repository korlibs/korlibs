package korlibs.io.http.core

import korlibs.io.stream.*
import korlibs.memory.*
import korlibs.wasm.*
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.khronos.webgl.*
import org.w3c.fetch.*
import kotlin.js.*

actual suspend fun httpRawFetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpRawFetchResult {
    val scheme = if (secure) "https" else "http"
    val url = "$scheme://$host:$port$path"
    val result = window.fetch(url, RequestInit(method = method, headers = Headers().also {
        for ((key, value) in headers) it.append(key, value)
    }, body = body?.readAll()?.toInt8Array())).await<Response>()
    val reader = result.body.getReader().unsafeCast<ReadableStreamDefaultReader>()
    return HttpRawFetchResult(result.status.toInt(), result.statusText, result.headers.toList(), object : AsyncInputStream {
        private var bytesDeque = SimpleBytesDeque()
        private var done = false
        private var position = 0L

        private suspend fun ensureData() {
            if (done) return
            if (bytesDeque.availableRead >= 1024) return
            val res = reader.read().await()
            done = res.done
            bytesDeque.write(res.value)
        }

        override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
            ensureData()
            return this.bytesDeque.read(buffer, offset, len).also {
                if (it >= 0) position += it else close()
            }
        }

        override suspend fun close() = reader.cancel()
    })
}

private fun Headers.toList(): List<Pair<String, String>> {
    val rheaders = this
    val keys = JsArray.from(rheaders.asDynamic().keys())
    val outHeaders = keys.map { it to rheaders.get(it)!! }.toMutableList()
    return outHeaders
}

@JsName("Array")
private external object JsArray {
    fun from(any: dynamic): Array<dynamic>
}

private external interface Chunk : JsAny {
    val done: Boolean
    val value: Int8Array
}

private external interface ReadableStreamDefaultReader {
    fun read(): Promise<Chunk>
    fun cancel()
}
