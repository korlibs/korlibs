package korlibs.io.http.core

import korlibs.io.stream.*
import korlibs.memory.*
import korlibs.wasm.*
import kotlinx.coroutines.*
import org.khronos.webgl.*
import org.w3c.fetch.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.js.Promise

@JsFun("(url, params) => globalThis.fetch(url, params)")
private external fun globalThisFetch(url: JsString, params: JsAny): Promise<JsAny>

internal actual val defaultHttpFetch: HttpFetch = object : HttpFetch {
    override suspend fun fetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpFetchResult {
        val scheme = if (secure) "https" else "http"
        val headersJsObj = jsEmptyObj()
        for ((key, value) in headers) {
            if (key.equals("connection", ignoreCase = true)) continue
            if (key.equals("content-length", ignoreCase = true)) continue
            headersJsObj.setAny(key.toJsString(), value.toJsString())
        }
        val url = "$scheme://$host:$port$path"

        val resultPromise = globalThisFetch(url.toJsString(), jsObject(
            "method" to method.toJsString(),
            "headers" to headersJsObj,
            "body" to body?.readAll()?.toInt8Array()
        ))

        val result = resultPromise.await2<Response>()
        val body = result.arrayBuffer().await2<ArrayBuffer>().asInt8Array().toByteArray()

        val rheaders = result.headers
        val keys = jsToArray(rheaders.unsafeCast<HeadersExt>().keys())
        val outHeaders = keys.map { it.toString() }.map { it to (rheaders.get(it) ?: "") }.toMutableList()
        return HttpFetchResult(result.status.toInt(), result.statusText, outHeaders, body.openAsync())
    }

}

private external class HeadersExt : JsAny {
    fun keys(): JsArray<JsString>
}

/*

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

 */

private suspend fun <T> Promise<JsAny?>.await2(): T = suspendCancellableCoroutine { cont: CancellableContinuation<T> ->
    this@await2.then(
        onFulfilled = { cont.resume(it as T); null },
        onRejected = {
            cont.resumeWithException(RuntimeException(it.toString(), it.toThrowableOrNull() ?: error("Unexpected non-Kotlin exception $it"))); null
        }
    )
}
