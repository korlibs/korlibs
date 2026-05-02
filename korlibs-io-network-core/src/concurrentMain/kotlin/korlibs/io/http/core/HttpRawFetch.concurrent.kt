package korlibs.io.http.core

internal actual val defaultHttpFetch: HttpFetch by lazy { SocketHttpFetch() }
