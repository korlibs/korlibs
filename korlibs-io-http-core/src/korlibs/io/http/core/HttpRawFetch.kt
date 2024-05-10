package korlibs.io.http.core

import korlibs.io.stream.*

data class HttpRawFetchResult(val status: Int, val statusText: String, val headers: List<Pair<String, String>>, val bodyRaw: AsyncInputStream)

expect suspend fun httpRawFetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream? = null): HttpRawFetchResult
