package korlibs.io.socket

import kotlinx.coroutines.*

actual suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean): AsyncSocket {
    return JvmNioAsyncClient().let { if (secure) AsyncClientSSLProcessor(it) else it }
}

actual suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServerSocket = when {
    secure -> JvmAsyncServerSocket(port, host, backlog, secure = secure).apply { init() }
    else -> JvmNioAsyncServer(port, host, backlog).apply { init() }
}

actual suspend fun AsyncSocket.Companion.unix(path: String): AsyncSocket = JvmAsyncSocket(withContext(Dispatchers.IO) {
    val address = UnixDomainSocketAddress_of(path)
    //println("[a]")
    SocketChannel_open(StandardProtocolFamily_UNIX).apply {
        //println("[b]")
        this.connect(address)
        //println("[c]")
    }
})

actual suspend fun AsyncServerSocket.Companion.unix(path: String, backlog: Int): AsyncServerSocket =
    UnixServerSocket(path, backlog).apply { init() }
