package korlibs.io.socket

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import java.net.*
import java.nio.*
import java.nio.channels.ByteChannel
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import javax.net.ssl.*

class JvmAsyncSocket private constructor(val secure: Boolean = false, unit: Unit) : AsyncSocket {
    private val connectionQueue = Mutex()
    private val readQueue = Mutex()
    private val writeQueue = Mutex()
    override lateinit var address: AsyncSocketAddress
        private set
    private var channel: ByteChannel? = null

    constructor(channel: SocketChannel, secure: Boolean = false) : this(secure, Unit) {
        setSocketChannel(channel)
    }
    constructor(socket: Socket, secure: Boolean = false) : this(secure, Unit) {
        setSocket(socket)
    }

    private fun setSocket(socket: Socket) {
        channel = socket.toChannel()
        address = socket.remoteSocketAddress.toAsyncAddress()
    }
    private fun setSocketChannel(channel: SocketChannel) {
        this.channel = channel
        this.address = channel.remoteAddress.toAsyncAddress()
    }

    override suspend fun connect(host: String, port: Int) {
        connectionQueue.withLock {
            withContext(Dispatchers.IO) {
                when {
                    secure -> setSocket(SSLSocketFactory.getDefault().createSocket(host, port))
                    else -> setSocketChannel(SocketChannel.open(InetSocketAddress(host, port)))
                }
            }
        }
    }

    //override suspend fun connectUnix(path: String): JvmAsyncSocket {
    //    connectionQueue.withLock {
    //        withContext(Dispatchers.IO) {
    //            setSocketChannel(SocketChannel.open(UnixDomainSocketAddress.of(path)))
    //        }
    //    }
    //    return this
    //}

    override val connected: Boolean get() = channel?.isOpen ?: false

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        return readQueue.withLock { withContext(Dispatchers.IO) { channel?.read(ByteBuffer.wrap(buffer, offset, len)) ?: -1 } }
    }
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) {
        writeQueue.withLock { withContext(Dispatchers.IO) { channel?.write(ByteBuffer.wrap(buffer, offset, len)) } }
    }

    override suspend fun close() {
        CoroutineScope(Dispatchers.IO).launch {
            channel?.close()
            channel = null
        }
    }
}

private fun Socket.toChannel(): ByteChannel {
    val socket = this
    val inputStream = socket.getInputStream()
    val outputStream = socket.getOutputStream()
    remoteSocketAddress

    return object : ByteChannel {
        override fun close() = socket.close()
        override fun isOpen(): Boolean = socket.isConnected
        override fun read(dst: ByteBuffer): Int {
            return inputStream.read(dst.array(), dst.arrayOffset() + dst.position(), dst.remaining())
        }

        override fun write(src: ByteBuffer): Int {
            outputStream.write(src.array(), src.arrayOffset() + src.position(), src.remaining())
            return src.remaining()
        }
    }
}

class JvmAsyncServerSocket(
    override val requestPort: Int,
    override val host: String,
    override val backlog: Int = -1,
    val secure: Boolean = false
) : AsyncServerSocket {
    val ssc: ServerSocket = when {
        secure -> SSLServerSocketFactory.getDefault().createServerSocket()
        else -> ServerSocket()
    }.also {
        // So accept only waits in 100 ms, so we are able to cancel, and do not blocks too much time IO threads
        it.soTimeout = 100
    }
    suspend fun init() {
        withContext(Dispatchers.IO) {
            ssc.bind(InetSocketAddress(host, requestPort), backlog)
        }
    }
    override val port: Int get() = (ssc.localSocketAddress as? InetSocketAddress)?.port ?: -1

    override suspend fun accept(): AsyncSocket {
        while (true) {
            try {
                return withContext(Dispatchers.IO) { JvmAsyncSocket(ssc.accept()) }
            } catch (e: SocketTimeoutException) {
                continue
            }
        }
    }

    override suspend fun close() {
        CoroutineScope(Dispatchers.IO).launch { ssc.close() }
    }
}

class UnixServerSocket(
    val path: String,
    override val backlog: Int = -1,
    val secure: Boolean = false
) : AsyncServerSocket {
    lateinit var ssc: ServerSocketChannel

    suspend fun init() {
        withContext(Dispatchers.IO) {
            ssc = ServerSocketChannel_open(StandardProtocolFamily_UNIX).also {
                it.bind(UnixDomainSocketAddress_of(path))
            }
        }
    }

    override val requestPort: Int get() = -1
    override val host: String get() = "unix:$path"
    override val port: Int get() = -1

    override suspend fun accept(): AsyncSocket {
        while (true) {
            try {
                return withContext(Dispatchers.IO) { JvmAsyncSocket(ssc.accept()) }
            } catch (e: SocketTimeoutException) {
                continue
            }
        }
    }

    override suspend fun close() {
        CoroutineScope(Dispatchers.IO).launch { ssc.close() }
    }
}

fun SocketAddress?.toAsyncAddress(): AsyncSocketAddress {
    if (this is InetSocketAddress) {
        return AsyncSocketAddress(this.hostString, this.port)
    }
    return AsyncSocketAddress()
}


val StandardProtocolFamily_UNIX: ProtocolFamily get() = Class.forName("java.net.StandardProtocolFamily").getDeclaredField("UNIX").let {
    it.isAccessible = true
    it.get(null) as StandardProtocolFamily
}

fun SocketChannel_open(protocol: ProtocolFamily): SocketChannel {
    val clazz = Class.forName("java.nio.channels.SocketChannel")
    val method = clazz.getDeclaredMethod("open", ProtocolFamily::class.java) ?: error("Can't find SocketChannel.open method")
    return method.invoke(null, protocol) as SocketChannel
}
fun ServerSocketChannel_open(protocol: ProtocolFamily): ServerSocketChannel {
    val clazz = Class.forName("java.nio.channels.ServerSocketChannel")
    val method = clazz.getDeclaredMethod("open", ProtocolFamily::class.java) ?: error("Can't find ServerSocketChannel.open method")
    return method.invoke(null, protocol) as ServerSocketChannel
}

fun UnixDomainSocketAddress_of(path: String): SocketAddress {
    val clazz = Class.forName("java.net.UnixDomainSocketAddress") ?: error("Can't find UnixDomainSocketAddress class")
    val method = clazz.getDeclaredMethod("of", String::class.java)
        ?: error("Can't find UnixDomainSocketAddress.of method")
    return method
        .invoke(null, path) as SocketAddress
}
