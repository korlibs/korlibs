package korlibs.io.socket

import kotlinx.atomicfu.*
import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.coroutines.cancellation.*
import kotlin.coroutines.cancellation.CancellationException

expect suspend operator fun AsyncSocket.Companion.invoke(secure: Boolean = false): AsyncSocket
expect suspend operator fun AsyncServerSocket.Companion.invoke(port: Int, host: String = "127.0.0.1", backlog: Int = -1, secure: Boolean = false): AsyncServerSocket

data class AsyncSocketAddress(val address: String = "0.0.0.0", val port: Int = 0)

@OptIn(ExperimentalStdlibApi::class)
interface AsyncSocket : AutoCloseable {
	companion object { }
	suspend fun connect(host: String, port: Int)

    val address: AsyncSocketAddress get() = AsyncSocketAddress()

	val connected: Boolean
	suspend fun read(buffer: ByteArray, offset: Int = 0, len: Int = buffer.size - offset): Int
	suspend fun write(buffer: ByteArray, offset: Int = 0, len: Int = buffer.size - offset)
	override fun close()

	object Stats {
		val writeCountStart = atomic(0L)
		val writeCountEnd = atomic(0L)
		val writeCountError = atomic(0L)

		override fun toString(): String = "AsyncClient.Stats($writeCountStart/$writeCountEnd/$writeCountError)"
	}
}

@OptIn(ExperimentalStdlibApi::class)
interface AsyncServerSocket : AutoCloseable {
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
										e.printStackTraceWithExtraMessage("Failed in AsyncServer.listen.handler")
									}
								}
							}
						}
					} catch (e: Throwable) {
						//Console.error("AsyncServer.listen.inner.catch: ${e::class}")
						if (e is CancellationException || e is IOException) throw e
						e.printStackTraceWithExtraMessage("Failed in AsyncServer.listen.accept")
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

	suspend fun listenFlow(): Flow<AsyncClient> = flow { while (true) emit(accept()) }

	// Provide a default implementation
	override fun close() {
	}
}
