package korlibs.io.http.core

import korlibs.io.stream.AsyncInputStream
import korlibs.io.stream.openAsync
import korlibs.io.stream.readAll
import korlibs.memory.asInt8Array
import korlibs.wasm.jsEmptyObj
import korlibs.wasm.jsObject
import korlibs.wasm.jsToArray
import korlibs.wasm.setAny
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.js.Promise
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.toByteArray
import org.khronos.webgl.toInt8Array
import org.w3c.fetch.Response

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

private suspend fun <T> Promise<JsAny?>.await2(): T = suspendCancellableCoroutine { cont: CancellableContinuation<T> ->
    this@await2.then(
        onFulfilled = { cont.resume(it as T); null },
        onRejected = {
            cont.resumeWithException(RuntimeException(it.toString(), it.toThrowableOrNull() ?: error("Unexpected non-Kotlin exception $it"))); null
        }
    )
}
