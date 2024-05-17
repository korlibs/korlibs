package korlibs.io.http.core

import korlibs.io.stream.*
import korlibs.memory.*
import korlibs.platform.*
import kotlinx.coroutines.*
import org.w3c.fetch.*

actual suspend fun httpRawFetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpFetchResult {
    val scheme = if (secure) "https" else "http"
    val headersJsObj = js("({})")
    for ((key, value) in headers) {
        if (key.equals("connection", ignoreCase = true)) continue
        if (key.equals("content-length", ignoreCase = true)) continue
        headersJsObj[key] = value
    }
    val url = "$scheme://$host:$port$path"
    val result = jsGlobalThis.fetch(url, RequestInit(method = method, headers = headersJsObj, body = body?.readAll())).await()
    val body = result.arrayBuffer().await().asInt8Array().unsafeCast<ByteArray>()

    val rheaders = result.headers
    val keys = JsArray.from(rheaders.asDynamic().keys())
    val outHeaders = keys.map { it to rheaders.get(it) }.toMutableList()
    return HttpFetchResult(result.status.toInt(), result.statusText, outHeaders, body.openAsync())
}

@JsName("Array")
private external object JsArray {
    fun from(any: dynamic): Array<dynamic>
}

/*

class HttpClientBrowserJs : HttpClient() {
    override suspend fun requestInternal(
        method: Http.Method,
        url: String,
        headers: Http.Headers,
        content: AsyncInputStreamWithLength?
    ): Response {
        val deferred = CompletableDeferred<Response>(Job())
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


private class HttpClientNodeJs : HttpClient() {
    override suspend fun requestInternal(
        method: Http.Method,
        url: String,
        headers: Http.Headers,
        content: AsyncInputStreamWithLength?
    ): Response {
        val deferred = CompletableDeferred<Response>(Job())
        //println(url)

        val http = NodeHTTP.asDynamic()
        val jsurl = NodeURL.asDynamic()
        val info = jsurl.parse(url)
        val reqHeaders = jsEmptyObj()

        for (header in headers) {
            reqHeaders[header.first] = header.second
        }

        val req = jsEmptyObj()
        req.method = method.name
        req.host = info["hostname"]
        req.port = info["port"]
        req.path = info["path"]
        req.agent = false
        req.encoding = null
        req.headers = reqHeaders

        val r = http.request(req) { res ->
            val statusCode: Int = res.statusCode
            val statusMessage: String = res.statusMessage ?: ""
            val jsHeadersObj = res.headers
            val body = jsEmptyArray()
            res.on("data") { d -> body.push(d) }
            res.on("end") {
                val r = jsGlobal.asDynamic().Buffer.concat(body)
                val u8array = Int8Array(r.unsafeCast<ArrayBuffer>())
                val out = ByteArray(u8array.length)
                for (n in 0 until u8array.length) out[n] = u8array[n]
                val response = Response(
                    status = statusCode,
                    statusText = statusMessage,
                    headers = Http.Headers(
                        (jsToObjectMap(jsHeadersObj) ?: LinkedHashMap()).mapValues { "${it.value}" }
                    ),
                    rawContent = out.openAsync(),
                    content = out.openAsync()
                )

                //println(response.headers)

                deferred.complete(response)
            }
        }.on("error") { e ->
            deferred.completeExceptionally(kotlin.RuntimeException("Error: $e"))
        }

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                r.abort()
            }
        }

        if (content != null) {
            r.end(content.readAll().toTypedArray())
        } else {
            r.end()
        }
        content?.close()

        return deferred.await()
    }
}

 */