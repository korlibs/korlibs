package korlibs.io.http.core

import korlibs.io.stream.*

data class HttpFetchResult(
    val status: Int,
    val statusText: String,
    val headers: List<Pair<String, String>>,
    val bodyRaw: AsyncInputStream
)

internal expect val defaultHttpFetch: HttpFetch

interface HttpFetch {
    suspend fun fetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream? = null): HttpFetchResult

    companion object : HttpFetch by defaultHttpFetch
}
