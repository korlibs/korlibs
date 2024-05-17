package korlibs.io.http.core

import korlibs.io.stream.*

data class HttpFetchResult(
    val status: Int,
    val statusText: String,
    val headers: List<Pair<String, String>>,
    val bodyRaw: AsyncInputStream
)

expect suspend fun httpRawFetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream? = null): HttpFetchResult

interface HttpFetch {
    suspend fun fetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream? = null): HttpFetchResult

    companion object : HttpFetch {
        override suspend fun fetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpFetchResult =
            httpRawFetch(method, host, port, path, secure, headers, body)
    }
}
