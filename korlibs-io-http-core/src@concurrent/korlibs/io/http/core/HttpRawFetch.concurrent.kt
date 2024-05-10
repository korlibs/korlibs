package korlibs.io.http.core

import korlibs.io.stream.*

actual suspend fun httpRawFetch(method: String, host: String, port: Int, path: String, secure: Boolean, headers: List<Pair<String, String>>, body: AsyncInputStream?): HttpRawFetchResult =
    httpRawFetchPortable(method, host, port, path, secure, headers, body)
