package korlibs.io.http.core

import korlibs.io.stream.*
import korlibs.memory.*
import korlibs.platform.*
import kotlinx.coroutines.*
import org.w3c.fetch.*

actual suspend fun httpRawFetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpRawFetchResult {
    val scheme = if (secure) "https" else "http"
    val headersJsObj = js("({})")
    for ((key, value) in headers) headersJsObj[key] = value
    val url = "$scheme://$host:$port$path"
    val result = jsGlobalThis.fetch(url, RequestInit(method = method, headers = headersJsObj, body = body?.readAll())).await()
    val body = result.arrayBuffer().await().asInt8Array().unsafeCast<ByteArray>()

    val rheaders = result.headers
    val keys = JsArray.from(rheaders.asDynamic().keys())
    val outHeaders = keys.map { it to rheaders.get(it) }.toMutableList()
    return HttpRawFetchResult(result.status.toInt(), result.statusText, outHeaders, body.openAsync())
}

@JsName("Array")
private external object JsArray {
    fun from(any: dynamic): Array<dynamic>
}
