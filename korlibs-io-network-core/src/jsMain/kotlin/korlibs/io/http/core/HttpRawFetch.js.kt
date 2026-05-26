package korlibs.io.http.core

import korlibs.io.stream.AsyncInputStream
import korlibs.io.stream.openAsync
import korlibs.io.stream.readAll
import korlibs.memory.asInt8Array
import korlibs.platform.jsGlobalThis
import kotlinx.coroutines.await
import org.w3c.fetch.RequestInit

internal actual val defaultHttpFetch: HttpFetch = object : HttpFetch {
    override suspend fun fetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpFetchResult {
        val scheme = if (secure) "https" else "http"
        val headersJsObj = js("({})")
        for ((key, value) in headers) {
            if (key.equals("connection", ignoreCase = true)) continue
            if (key.equals("content-length", ignoreCase = true)) continue
            headersJsObj[key] = value
        }
        val url = "$scheme://$host:$port$path"

        val fetchInit = js("({})")
        fetchInit["method"] = method
        fetchInit["headers"] = headersJsObj
        body?.readAll()?.let { fetchInit["body"] = it }

        val result = jsGlobalThis.fetch(url, fetchInit.unsafeCast<RequestInit>()).await()
        val body = result.arrayBuffer().await().asInt8Array().unsafeCast<ByteArray>()

        val rheaders = result.headers
        val keys = JsArray.from(rheaders.asDynamic().keys()).unsafeCast<Array<String>>()
        val outHeaders = keys.map { it to rheaders.get(it) }.toMutableList()
            .filter { !it.first.equals("content-encoding", ignoreCase = true) && !it.first.equals("transfer-encoding", ignoreCase = true) }
        return HttpFetchResult(result.status.toInt(), result.statusText, outHeaders.unsafeCast<List<Pair<String, String>>>(), body.openAsync())
    }
}

@JsName("Array")
private external object JsArray {
    fun from(any: dynamic): Array<dynamic>
}
