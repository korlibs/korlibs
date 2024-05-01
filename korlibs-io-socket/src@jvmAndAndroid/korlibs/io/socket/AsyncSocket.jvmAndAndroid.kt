package korlibs.io.socket

actual suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean): AsyncSocket {
    return JvmNioAsyncClient().let { if (secure) AsyncClientSSLProcessor(it) else it }
}

actual suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServerSocket {
    return if (secure) {
        JvmAsyncServerSocket(port, host, backlog, secure = secure).apply { init() }
    } else {
        JvmNioAsyncServer(port, host, backlog).apply { init() }
    }
}
