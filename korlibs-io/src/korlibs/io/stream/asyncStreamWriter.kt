package korlibs.io.stream

import korlibs.io.async.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

/**
 * Creates a an [AsyncInputStream] from a [process] function that writes to a [AsyncOutputStream].
 *
 * The [process] function is executed lazily when the data is tried to be read.
 */
suspend fun asyncStreamWriter(bufferSize: Int = AsyncRingBufferChunked.DEFAULT_MAX_SIZE, name: String? = null, lazy: Boolean = false, process: suspend (out: AsyncOutputStream) -> Unit): AsyncInputStream {
    return object : AsyncInputStream {
        val deque = when {
            lazy -> AsyncRingBuffer(bufferSize).also { it.name = name }
            else -> AsyncRingBufferChunked(bufferSize).also { it.name = name }
        }
        var lastError: Throwable? = null

        var job: Job? = null

        private fun checkException() {
            if (lastError != null) throw RuntimeException("Error in asyncStreamWriter", lastError!!)
        }

        private val temp = ByteArray(1)

        private suspend fun ensureJob() {
            if (job != null) return
            job = launchImmediately(coroutineContext) {
                try {
                    process(object : AsyncOutputStream {
                        override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = deque.write(buffer, offset, len)
                        override suspend fun write(byte: Int) = deque.write(byte)
                        override suspend fun close() = deque.close()
                    })
                } catch (e: Throwable) {
                    lastError = e
                    e.printStackTrace()
                } finally {
                    deque.close()
                }
            }
        }

        override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
            ensureJob()
            //println("asyncStreamWriter[$deque].read($len)")
            checkException()
            return deque.read(buffer, offset, len).also {
                //println("/asyncStreamWriter[$deque].read($len) -> $it")
            }
        }
        override suspend fun read(): Int {
            return if (read(temp, 0, 1) > 0) {
                temp[0].toInt() and 0xFF
            } else {
                -1
            }
        }
        override suspend fun close() {
            //println("asyncStreamWriter[$deque].close")
            job?.cancel()
            job = null
        }
    }
}
