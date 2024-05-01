package korlibs.io.stream

import korlibs.concurrent.lock.*
import korlibs.datastructure.*
import korlibs.io.async.*
import korlibs.io.lang.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import kotlin.math.*

interface AsyncBaseStream : AsyncCloseable {
}

interface AsyncInputOpenable {
    suspend fun openRead(): AsyncInputStream
}

suspend inline fun <T> AsyncInputOpenable.openUse(block: (AsyncInputStream) -> T): T = openRead().use(block)

interface AsyncInputStream : AsyncBaseStream {
    suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int
    suspend fun read(): Int = ByteArray(1).let { if (read(it, 0, 1) > 0) it[0].toInt() and 0xFF else -1 }
    //suspend fun read(): Int
}

interface AsyncOutputStream : AsyncBaseStream {
    suspend fun write(buffer: ByteArray, offset: Int = 0, len: Int = buffer.size - offset)
    suspend fun write(byte: Int) = ByteArray(1).let { it[0] = byte.toByte(); write(it, 0, 1) }
    //suspend fun write(byte: Int)
}

open class DummyAsyncOutputStream : AsyncOutputStream {
    companion object : DummyAsyncOutputStream()
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = Unit
    override suspend fun write(byte: Int) = Unit
    override suspend fun close() = Unit
}

interface AsyncGetPositionStream : AsyncBaseStream {
    suspend fun getPosition(): Long = throw UnsupportedOperationException()
}

interface AsyncPositionStream : AsyncGetPositionStream {
    suspend fun setPosition(value: Long): Unit = throw UnsupportedOperationException()
}

interface AsyncGetLengthStream : AsyncBaseStream {
    suspend fun hasLength() = try { getLength() >= 0L } catch (t: UnsupportedOperationException) { false }
    suspend fun getLength(): Long = throw UnsupportedOperationException()
}

interface AsyncLengthStream : AsyncGetLengthStream {
    suspend fun setLength(value: Long): Unit = throw UnsupportedOperationException()
}

interface AsyncPositionLengthStream : AsyncPositionStream, AsyncLengthStream {
}

interface AsyncInputStreamWithLength : AsyncInputStream, AsyncGetPositionStream, AsyncGetLengthStream {
}

interface AsyncRAInputStream {
    suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int
}

interface AsyncRAOutputStream {
    suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int)
}

open class AsyncStreamBase : AsyncCloseable, AsyncRAInputStream, AsyncRAOutputStream, AsyncLengthStream {
    //var refCount = 0

    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int =
        throw UnsupportedOperationException()

    override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int): Unit =
        throw UnsupportedOperationException()

    override suspend fun setLength(value: Long): Unit = throw UnsupportedOperationException()
    override suspend fun getLength(): Long = throw UnsupportedOperationException()

    override suspend fun close(): Unit = Unit
}

fun AsyncStreamBase.toAsyncStream(position: Long = 0L): AsyncStream = AsyncStream(this, position)

class AsyncStream(val base: AsyncStreamBase, var position: Long = 0L, val queue: Boolean = false) : Extra by Extra.Mixin(), AsyncInputStream, AsyncInputStreamWithLength, AsyncOutputStream, AsyncPositionLengthStream,
    AsyncCloseable {
    override fun toString(): String = "AsyncStream($base, position=$position)"

    private val readQueue = Mutex()
    private val writeQueue = Mutex()

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = when {
        queue -> readQueue.withLock { readInternal(buffer, offset, len) }
        else -> readInternal(buffer, offset, len)
    }

    override suspend fun write(buffer: ByteArray, offset: Int, len: Int): Unit = when {
        queue -> writeQueue.withLock { writeInternal(buffer, offset, len) }
        else -> writeInternal(buffer, offset, len)
    }

    private suspend fun readInternal(buffer: ByteArray, offset: Int, len: Int): Int {
        val read = base.read(position, buffer, offset, len)
        if (read >= 0) position += read
        return read
    }

    private suspend fun writeInternal(buffer: ByteArray, offset: Int, len: Int) {
        base.write(position, buffer, offset, len)
        position += len
    }

    override suspend fun setPosition(value: Long) { this.position = value }
    override suspend fun getPosition(): Long = this.position
    override suspend fun setLength(value: Long): Unit = base.setLength(value)
    override suspend fun getLength(): Long = base.getLength()
    suspend fun size(): Long = base.getLength()

    suspend fun hasAvailable() = hasLength()
    suspend fun getAvailable(): Long = getLength() - getPosition()
    suspend fun eof(): Boolean = hasAvailable() && this.getAvailable() <= 0L

    override suspend fun close(): Unit = base.close()

    fun duplicate(): AsyncStream = AsyncStream(base, position)
}


//fun SyncStream._toAsync(dispatcher: CoroutineDispatcher? = Dispatchers.Unconfined): AsyncStream = this.base._toAsync(dispatcher).toAsyncStream(this.position)
//fun SyncStreamBase._toAsync(dispatcher: CoroutineDispatcher? = Dispatchers.Unconfined): AsyncStreamBase = when (this) {
//    //is MemorySyncStreamBase -> MemoryAsyncStreamBase(this.data)
//    else -> SyncAsyncStreamBase(this, dispatcher)
//}

class SyncAsyncStreamBase(val sync: SyncStreamBase, val dispatcher: CoroutineDispatcher? = null) : AsyncStreamBase() {
    private suspend inline fun <T> doIo(crossinline block: () -> T): T = when {
        dispatcher != null -> withContext(dispatcher) { block() }
        else -> block()
    }
    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int = doIo { sync.read(position, buffer, offset, len) }
    override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) = doIo { sync.write(position, buffer, offset, len) }
    override suspend fun setLength(value: Long) = doIo { sync.length = value }
    override suspend fun getLength(): Long = doIo { sync.length }
}
