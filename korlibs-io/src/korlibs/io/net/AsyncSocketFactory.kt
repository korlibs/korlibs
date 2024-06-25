@file:OptIn(ExperimentalStdlibApi::class)

package korlibs.io.net

import korlibs.io.async.AsyncCloseable
import korlibs.io.async.Signal
import korlibs.io.async.launchImmediately
import korlibs.io.lang.Closeable
import korlibs.io.lang.IOException
import korlibs.io.lang.printStackTraceWithExtraMessage
import korlibs.io.socket.*
import korlibs.io.stream.AsyncInputStream
import korlibs.io.stream.AsyncOutputStream
import korlibs.io.stream.DequeSyncStream
import korlibs.io.stream.SyncStream
import kotlinx.atomicfu.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.coroutineContext

abstract class AsyncSocketFactory {
    open suspend fun createClient(secure: Boolean = false): AsyncClient = TODO()
	open suspend fun createServer(port: Int, host: String = "127.0.0.1", backlog: Int = 511, secure: Boolean = false): AsyncServer = TODO()
    open suspend fun createServerUnix(path: String, backlog: Int, secure: Boolean): AsyncServer =
        TODO("Unsupported")
}

//internal expect val asyncSocketFactory: AsyncSocketFactory

class SocketAsyncClient(val socket: AsyncSocket) : AsyncClient {
    override suspend fun connect(host: String, port: Int) = socket.connect(host, port)
    override val connected: Boolean get() = socket.connected
    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = socket.read(buffer, offset, len)
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = socket.write(buffer, offset, len)
    override suspend fun close() = socket.close()
}

class SocketAsyncServer(val socket: AsyncServerSocket) : AsyncServer {
    override val requestPort: Int get() = socket.requestPort
    override val host: String get() = socket.host
    override val backlog: Int get() = socket.backlog
    override val port: Int get() = socket.port
    override suspend fun accept(): AsyncClient = SocketAsyncClient(socket.accept())
}

internal val asyncSocketFactory: AsyncSocketFactory = object : AsyncSocketFactory() {
    override suspend fun createClient(secure: Boolean): AsyncClient = SocketAsyncClient(AsyncSocket(secure))
    override suspend fun createServer(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServer =
        SocketAsyncServer(AsyncServerSocket(port, host, backlog, secure))
}

suspend fun AsyncSocketFactory.createClient(host: String, port: Int, secure: Boolean = false): AsyncClient =
    createClient(secure).apply { connect(host, port) }

suspend fun createTcpClient(secure: Boolean = false): AsyncClient = asyncSocketFactory.createClient(secure)
suspend fun createTcpServer(port: Int = AsyncServer.ANY_PORT, host: String = "127.0.0.1", backlog: Int = 511, secure: Boolean = false): AsyncServer = asyncSocketFactory.createServer(port, host, backlog, secure)
suspend fun createTcpClient(host: String, port: Int, secure: Boolean = false): AsyncClient = asyncSocketFactory.createClient(host, port, secure)

interface AsyncClient : AsyncInputStream, AsyncOutputStream, AsyncCloseable {
	suspend fun connect(host: String, port: Int)
    val address: AsyncAddress get() = AsyncAddress()

	val connected: Boolean
	override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int
	override suspend fun write(buffer: ByteArray, offset: Int, len: Int)
	override suspend fun close()
	//suspend open fun reconnect() = Unit

	object Stats {
		val writeCountStart = atomic(0L)
		val writeCountEnd = atomic(0L)
		val writeCountError = atomic(0L)

		override fun toString(): String = "AsyncClient.Stats($writeCountStart/$writeCountEnd/$writeCountError)"
	}

	companion object {
		suspend operator fun invoke(host: String, port: Int, secure: Boolean = false, connect: Boolean = true): AsyncClient =
            asyncSocketFactory.createClient(secure).also { if (connect) it.connect(host, port)  }
		suspend fun create(secure: Boolean = false): AsyncClient = asyncSocketFactory.createClient(secure)
		suspend fun createAndConnect(host: String, port: Int, secure: Boolean = false): AsyncClient = invoke(host, port, secure)
	}
}

class FakeAsyncClient(
    val serverToClient: SyncStream = DequeSyncStream(),
    val clientToServer: SyncStream = DequeSyncStream(),
    val onConnect: Signal<Pair<String, Int>> = Signal(),
    val onClose: Signal<Unit> = Signal()
) : AsyncClient {
    override var connected: Boolean = false

    override val address: AsyncAddress get() = AsyncAddress()

    override suspend fun connect(host: String, port: Int) {
        onConnect(host to port)
        connected = true
    }
    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = serverToClient.read(buffer, offset, len)
    // @TODO: BUG: Required override because of Bug
    override suspend fun read(): Int = serverToClient.read()

    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = clientToServer.write(buffer, offset, len)
    override suspend fun close() {
        onClose(Unit)
    }
}

interface AsyncServer : AsyncCloseable {
	val requestPort: Int
	val host: String
	val backlog: Int
	val port: Int

	companion object {
		val ANY_PORT = 0

		suspend operator fun invoke(port: Int, host: String = "127.0.0.1", backlog: Int = -1) =
			asyncSocketFactory.createServer(port, host, backlog)
	}

	suspend fun accept(): AsyncClient

	suspend fun listen(handler: suspend (AsyncClient) -> Unit): AutoCloseable {
		val job = CoroutineScope(coroutineContext).launch {
            try {
                while (true) {
                    try {
                        //Console.error("AsyncServer.listen.accept[0]: $this")
                        val client = accept()
                        //Console.error("AsyncServer.listen.accept[1]")
                        CoroutineScope(coroutineContext).launch {
                            supervisorScope {
                                try {
                                    handler(client)
                                } catch (e: Throwable) {
                                    kotlin.runCatching { client.close() }
                                    if (e !is IOException && e !is CancellationException) {
                                        Exception("Failed in AsyncServer.listen.handler", e).printStackTrace()
                                    }
                                }
                            }
                        }
                    } catch (e: Throwable) {
                        //Console.error("AsyncServer.listen.inner.catch: ${e::class}")
                        if (e is CancellationException || e is IOException) throw e
                        Exception("Failed in AsyncServer.listen.accept", e).printStackTrace()
                    }
                }
            } catch (e: Throwable) {
                //if (e is CancellationException) Console.error("AsyncServer.listen.cancel")
                // close socket?
                runCatching { close() }
            } finally {
                //Console.error("AsyncServer.listen.finally")
            }
		}
		return Closeable {
            //Console.error("AsyncServer.listen: Closing server...")
            job.cancel()
        }
	}

    suspend fun listenFlow(): Flow<AsyncClient> = flow { while (true) emit(accept()) }

    // Provide a default implementation
    override suspend fun close() {
    }
}
