package korlibs.io.async

import korlibs.io.lang.*
import korlibs.memory.*
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds

class AsyncRingBufferChunked(val maxSize: Int = DEFAULT_MAX_SIZE) : IAsyncRingBuffer {
    companion object {
        const val DEFAULT_MAX_SIZE = 8 * 1024 * 1024
    }

    var name: String? = null
    private val chunks = SimpleChunkedByteDeque()
    private var completed = false

    private suspend fun waitToWriteMore(len: Int) {
        if (chunks.availableRead > maxSize) {
            while (chunks.availableRead > 1 + (maxSize / 2)) {
                //println("WRITE WAITING: availableRead=${chunks.availableRead} > ${1 + (maxSize / 2)}")
                //delay(100.milliseconds) // @TODO: Proper synchronization
                delay(1.milliseconds) // @TODO: Proper synchronization
            }
        }
    }

    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) {
        if (len <= 0) return
        if (completed) error("Trying to write to a completed $this")

        waitToWriteMore(len)
        chunks.write(buffer, offset, len)
    }

    override suspend fun write(byte: Int) {
        waitToWriteMore(1)
        chunks.write(byte)
    }

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        if (len <= 0) return 0
        if (offset < 0 || offset + len > buffer.size) throw OutOfBoundsException()
        while (true) {
            val out = chunks.read(buffer, offset, len)

            if (out <= 0 && !completed) {
                //println("READ WAITING: out=$out, completed=$completed")
                //delay(100.milliseconds) // @TODO: Proper synchronization
                delay(1.milliseconds) // @TODO: Proper synchronization
                continue
            }

            return out
        }
    }

    override suspend fun close() {
        completed = true
    }

    override fun toString(): String = "AsyncByteArrayDequeV2($name)"
}
