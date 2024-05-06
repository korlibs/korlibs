package korlibs.io.socket

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import java.io.*
import java.net.*
import javax.net.ssl.*

class JvmAsyncSocket(private var socket: Socket? = null, val secure: Boolean = false) : AsyncSocket {
    private val connectionQueue = Mutex()
    private val readQueue = Mutex()
    private val writeQueue = Mutex()

    private var socketIs: InputStream? = null
    private var socketOs: OutputStream? = null

    init {
        setStreams()
    }

    private fun setStreams() {
        socketIs = socket?.getInputStream()
        socketOs = socket?.getOutputStream()
    }

    override val address: AsyncSocketAddress get() = socket?.remoteSocketAddress.toAsyncAddress()

    override suspend fun connect(host: String, port: Int) {
        connectionQueue.withLock {
            withContext(Dispatchers.IO) {
                socket = if (secure) SSLSocketFactory.getDefault().createSocket(host, port) else Socket(host, port)
                setStreams()
            }
        }
    }

    override val connected: Boolean get() = socket?.isConnected ?: false

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        return readQueue.withLock { withContext(Dispatchers.IO) { socketIs?.read(buffer, offset, len) ?: -1 } }
    }
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) {
        writeQueue.withLock { withContext(Dispatchers.IO) { socketOs?.write(buffer, offset, len) } }
    }

    override fun close() {
        CoroutineScope(Dispatchers.IO).launch {
            socket?.close()
            socketIs?.close()
            socketOs?.close()
            socket = null
            socketIs = null
            socketOs = null
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

    override fun close() {
        CoroutineScope(Dispatchers.IO).launch { ssc.close() }
    }
}


fun SocketAddress?.toAsyncAddress(): AsyncSocketAddress {
    if (this is InetSocketAddress) {
        return AsyncSocketAddress(this.hostString, this.port)
    }
    return AsyncSocketAddress()
}

