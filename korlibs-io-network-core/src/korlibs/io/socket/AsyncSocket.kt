package korlibs.io.socket

import korlibs.io.async.*
import korlibs.io.lang.*
import korlibs.io.stream.*
import kotlinx.atomicfu.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.*
import kotlin.coroutines.cancellation.CancellationException

expect suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean = false): AsyncSocket
expect suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String = "127.0.0.1", backlog: Int = -1, secure: Boolean = false): AsyncServerSocket

expect suspend fun AsyncSocket.Companion.unix(path: String): AsyncSocket
expect suspend fun AsyncServerSocket.Companion.unix(path: String, backlog: Int = -1): AsyncServerSocket

data class AsyncSocketAddress(val address: String = "0.0.0.0", val port: Int = 0)

/**
 * A socket that can be used to connect to a server.
 * [secure] indicates if the connection should be secure (SSL).
 */
interface AsyncSocket : AsyncInputStream, AsyncOutputStream {
	companion object { }
	suspend fun connect(host: String, port: Int)

	/** The address of the socket */
    val address: AsyncSocketAddress get() = AsyncSocketAddress()

	/** Is the socket connected */
	val connected: Boolean

	/** Read [len] bytes into [buffer] starting at [offset] */
	override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int

	/** Write [len] bytes from [buffer] starting at [offset] */
	override suspend fun write(buffer: ByteArray, offset: Int, len: Int)

	/** Close the socket */
	override suspend fun close()

	object Stats {
		val writeCountStart = atomic(0L)
		val writeCountEnd = atomic(0L)
		val writeCountError = atomic(0L)

		override fun toString(): String = "AsyncClient.Stats($writeCountStart/$writeCountEnd/$writeCountError)"
	}
}

@OptIn(ExperimentalStdlibApi::class)
interface AsyncServerSocket : AsyncCloseable {
	val requestPort: Int
	val host: String
	val backlog: Int
	val port: Int

	companion object {
		val ANY_PORT = 0
	}

	suspend fun accept(): AsyncSocket

	suspend fun listen(handler: suspend (AsyncSocket) -> Unit): AutoCloseable {
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
		return object : AutoCloseable {
			override fun close() {
				//Console.error("AsyncServer.listen: Closing server...")
				job.cancel()
			}
		}
	}

	suspend fun listenFlow(): Flow<AsyncSocket> = flow { while (true) emit(accept()) }

	// Provide a default implementation
	override suspend fun close() {
	}
}

class PairAsyncSocket : AsyncSocket {
	val readDeque = DequeSyncStream()
	val writeDeque = DequeSyncStream()

	override suspend fun connect(host: String, port: Int) = Unit
	override val connected: Boolean get() = true
	override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = readDeque.read(buffer, offset, len)
	override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = writeDeque.write(buffer, offset, len)
	override suspend fun close() {
		//readDeque.close()
		//writeDeque.close()
	}
}
