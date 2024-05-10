package korlibs.io.socket

actual suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean): AsyncSocket {
    if (secure) return NativeSecureAsyncSocket(LinuxSSLSocket())
    return NativeAsyncSocket()
}

actual suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServerSocket =
    NativeAsyncServerSocket(port, host, backlog, secure)

class NativeSecureAsyncSocket(val socket: LinuxSSLSocket) : AsyncSocket {
    override val address: AsyncSocketAddress get() = socket.endpoint.toAsyncAddress()
    override val connected: Boolean get() = socket.connected
    override suspend fun connect(host: String, port: Int) { socket.connect(host, port) }
    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = socket.read(buffer, offset, len)
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) { socket.write(buffer, offset, len) }
    override suspend fun close() { socket.close() }
}
