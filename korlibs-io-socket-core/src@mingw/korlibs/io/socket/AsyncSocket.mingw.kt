package korlibs.io.socket

actual suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean): AsyncSocket = NativeAsyncSocket(Win32Socket(secure))

actual suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServerSocket {
    val socket = Win32Socket(secure)
    socket.bind(host, port, backlog)
    return NativeAsyncServer(socket, port, backlog)
}

class NativeAsyncServer(val socket: Win32Socket, override val requestPort: Int, override val backlog: Int) :
    AsyncServerSocket {
    override val host: String get() = socket.getLocalEndpoint().ip.str
    override val port: Int get() = socket.getLocalEndpoint().port
    override suspend fun accept(): AsyncSocket = NativeAsyncSocket(socket.accept())
    override fun close() = socket.close()
}

class NativeAsyncSocket(val socket: Win32Socket) : AsyncSocket {
    override val address get() = socket.endpoint.toAsyncAddress()

    override suspend fun connect(host: String, port: Int) { socket.connect(host, port) }
    override val connected: Boolean get() = socket.connected
    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = socket.suspendRecvUpTo(buffer, offset, len)
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = socket.suspendSend(buffer, offset, len)
    override fun close() = socket.close()
}
