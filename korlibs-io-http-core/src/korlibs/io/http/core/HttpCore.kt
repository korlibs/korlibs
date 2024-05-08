package korlibs.io.http.core

import korlibs.io.stream.*

data class HttpRawFetchResult(val code: Int, val codeString: String, val headers: List<Pair<String, String>>, val bodyRaw: AsyncInputStream)

//expect suspend fun httpRawFetch(url: String, headers: List<Pair<String, String>>, body: AsyncInputStreamWithLength? = null): HttpRawFetchResult

suspend fun httpRawFetch(url: String, headers: List<Pair<String, String>>, body: AsyncInputStreamWithLength? = null): HttpRawFetchResult {
    TODO()
}
