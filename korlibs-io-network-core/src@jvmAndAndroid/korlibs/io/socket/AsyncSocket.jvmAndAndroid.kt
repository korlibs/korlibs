package korlibs.io.socket

import kotlinx.coroutines.*
import java.net.ProtocolFamily
import java.net.StandardProtocolFamily
import java.net.UnixDomainSocketAddress
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.SocketChannel

actual suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean): AsyncSocket {
    return JvmNioAsyncClient().let { if (secure) AsyncClientSSLProcessor(it) else it }
}

actual suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServerSocket = when {
    secure -> JvmAsyncServerSocket(port, host, backlog, secure = secure).apply { init() }
    else -> JvmNioAsyncServer(port, host, backlog).apply { init() }
}

actual suspend fun AsyncSocket.Companion.unix(path: String): AsyncSocket = JvmAsyncSocket(withContext(Dispatchers.IO) {
    val address = UnixDomainSocketAddress.of(path)
    //println("[a]")
    SocketChannel.open(StandardProtocolFamily.UNIX).apply {
        //println("[b]")
        this.connect(address)
        //println("[c]")
    }
})

actual suspend fun AsyncServerSocket.Companion.unix(path: String, backlog: Int): AsyncServerSocket =
    UnixServerSocket(path, backlog).apply { init() }
