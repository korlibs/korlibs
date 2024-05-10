@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.datastructure.*
import korlibs.io.async.*
import korlibs.io.lang.*
import korlibs.memory.*
import kotlinx.coroutines.sync.*
import kotlin.math.*

interface AsyncBaseStream : AsyncCloseable {
}

interface AsyncInputOpenable {
    suspend fun openRead(): AsyncInputStream
}

suspend inline fun <T> AsyncInputOpenable.openUse(block: (AsyncInputStream) -> T): T = openRead().use(block)

interface AsyncInputStream : AsyncBaseStream {
    suspend fun read(buffer: ByteArray, offset: Int = 0, len: Int = buffer.size - offset): Int
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
    suspend fun hasAvailable() = hasLength()
    suspend fun getAvailable(): Long = getLength() - getPosition()
    suspend fun eof(): Boolean = hasAvailable() && this.getAvailable() <= 0L
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

class AsyncStream(val base: AsyncStreamBase, var position: Long = 0L, val queue: Boolean = false) : Extra by Extra.Mixin(), AsyncInputStream, AsyncInputStreamWithLength, AsyncOutputStream, AsyncPositionLengthStream, AsyncCloseable {
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

    override suspend fun hasLength(): Boolean = base.hasLength()
    override suspend fun setPosition(value: Long) { this.position = value }
    override suspend fun getPosition(): Long = this.position
    override suspend fun setLength(value: Long): Unit = base.setLength(value)
    override suspend fun getLength(): Long = base.getLength()
    suspend fun size(): Long = base.getLength()

    override suspend fun close(): Unit = base.close()

    fun duplicate(): AsyncStream = AsyncStream(base, position)
}


//fun SyncStream._toAsync(dispatcher: CoroutineDispatcher? = Dispatchers.Unconfined): AsyncStream = this.base._toAsync(dispatcher).toAsyncStream(this.position)
//fun SyncStreamBase._toAsync(dispatcher: CoroutineDispatcher? = Dispatchers.Unconfined): AsyncStreamBase = when (this) {
//    //is MemorySyncStreamBase -> MemoryAsyncStreamBase(this.data)
//    else -> SyncAsyncStreamBase(this, dispatcher)
//}

//suspend fun AsyncInputStream.copyTo(target: AsyncOutputStream, chunkSize: Int = 256 * 1024): Long {
suspend fun AsyncInputStream.copyTo(target: AsyncOutputStream, chunkSize: Int = 8 * 1024 * 1024): Long {
    // Optimization to reduce suspensions
    if (this is AsyncStream && base is MemoryAsyncStreamBase) {
        val base = base as MemoryAsyncStreamBase
        target.write(base.data.data, position.toInt(), base.ilength - position.toInt())
        return base.ilength.toLong()
    }

    val rchunkSize = if (this is AsyncGetLengthStream) min(this.getLength(), chunkSize.toLong()).toInt() else chunkSize

    var totalCount = 0L
    this.consume(autoclose = false, temp = ByteArray(rchunkSize)) { data, offset, size ->
        //println("write. offset=$offset, size=$size")
        target.write(data, offset, size)
        totalCount += size
    }
    return totalCount
}

suspend inline fun AsyncInputStream.consume(autoclose: Boolean = true, temp: ByteArray = ByteArray(0x10000), block: (data: ByteArray, offset: Int, size: Int) -> Unit) {
    try {
        while (true) {
            val read = read(temp, 0, temp.size)
            if (read <= 0) break
            block(temp, 0, read)
        }
    } finally {
        if (autoclose) close()
    }
}


suspend fun AsyncInputStream.readBytesUpTo(out: ByteArray, offset: Int, len: Int): Int {
    var total = 0
    var pending = len
    var offset = offset
    while (true) {
        val result = read(out, offset, pending)
        if (result <= 0) break
        offset += result
        pending -= result
        total += result
    }
    return total
}

suspend fun AsyncInputStream.readBytesUpToCopy(out: ByteArray): ByteArray {
    val pos = readBytesUpTo(out, 0, out.size)
    return if (out.size == pos) out else out.copyOf(pos)
}

suspend fun AsyncInputStream.readBytesUpTo(len: Int): ByteArray {
    val BYTES_TEMP_SIZE = 0x1000

    if (len <= BYTES_TEMP_SIZE) return readBytesUpToCopy(ByteArray(len))
    if (this is AsyncPositionLengthStream) return readBytesUpToCopy(ByteArray(min(len, this.getAvailable().toIntClamp())))

    var pending = len
    val temp = ByteArray(BYTES_TEMP_SIZE)
    val bout = ByteArrayBuilder()
    while (pending > 0) {
        val read = this.read(temp, 0, min(temp.size, pending))
        if (read <= 0) break
        bout.append(temp, 0, read)
        pending -= read
    }
    return bout.toByteArray()
}

class BufferedAsyncInputStream(val stream: AsyncInputStream, bufferBits: Int = 10) : AsyncInputStreamWithLength {
    //val bufferedData = SimpleBytesDeque()
    val bufferedData = SimpleBytesDeque(bufferBits)

    suspend fun buffer(count: Int): Boolean {
        val temp = if (count <= bufferedData.tempBuffer.size) bufferedData.tempBuffer else ByteArray(count)
        val len = stream.read(temp, 0, count)
        if (len <= 0) return false
        bufferedData.write(temp, 0, len)
        return true
    }

    suspend fun ensure(count: Int): Boolean {
        if (bufferedData.availableRead < count) return buffer(count)
        return true
    }

    suspend fun readBytesUntil(byte: Byte, including: Boolean = true): ByteArray {
        val builder = ByteArrayBuilder()
        val temp = bufferedData.tempBuffer
        //println("readBytesUntil($byte)")
        loop@while (true) {
            ensure(temp.size)
            if (bufferedData.availableRead <= 0) break@loop
            val len = bufferedData.peek(temp)
            for (n in 0 until len) {
                if (temp[n] == byte) {
                    bufferedData.skip(n + 1)
                    builder.append(temp, 0, n + (if (including) 1 else 0))
                    break@loop
                }
            }
            bufferedData.skip(len)
            builder.append(temp, 0, len)
        }
        return builder.toByteArray()
    }

    suspend fun readLine(includeLineEnding: Boolean = true): String =
        readBytesUntil('\n'.code.toByte(), including = includeLineEnding).decodeToString()

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        if (bufferedData.availableRead > 0) {
            return bufferedData.read(buffer, offset, len)
        }
        return stream.read(buffer, offset, len)
    }

    override suspend fun close() = stream.close()

    override suspend fun hasLength(): Boolean =(stream as? AsyncGetLengthStream)?.hasLength() ?: super.hasLength()
    override suspend fun getPosition(): Long = (stream as? AsyncGetPositionStream)?.getPosition() ?: super.getPosition()
    override suspend fun getLength(): Long = (stream as? AsyncGetLengthStream)?.getLength() ?: super.getLength()
}

suspend fun AsyncInputStream.readExact(buffer: ByteArray, offset: Int, len: Int) {
    var remaining = len
    var coffset = offset
    val reader = this
    while (remaining > 0) {
        val read = reader.read(buffer, coffset, remaining)
        if (read < 0) break
        if (read == 0) throw EOFException("Not enough data. Expected=$len, Read=${len - remaining}, Remaining=$remaining")
        coffset += read
        remaining -= read
    }
}

suspend fun AsyncInputStream.readBytesExact(len: Int): ByteArray = ByteArray(len).apply { readExact(this, 0, len) }

suspend fun AsyncInputStream.readAll(): ByteArray {
    return try {
        when {
            this is AsyncGetPositionStream && this is AsyncGetLengthStream && this.hasLength() -> {
                val available = this.getLength() - this.getPosition()
                this.readBytesExact(available.toInt())
            }
            this is AsyncStream && this.hasAvailable() -> {
                val available = this.getAvailable().toInt()
                this.readBytesExact(available)
            }
            else -> {
                val out = ByteArrayBuilder()
                val temp = ByteArray(0x1000)
                while (true) {
                    val r = this.read(temp, 0, temp.size)
                    if (r <= 0) break
                    out.append(temp, 0, r)
                }
                out.toByteArray()
            }
        }
    } finally {
        this.close()
    }
}

// readAll alias
suspend fun AsyncInputStream.readAvailable(): ByteArray = readAll()

suspend fun AsyncInputStream.skip(count: Int) {
    if (this is AsyncPositionLengthStream) {
        this.setPosition(this.getPosition() + count)
    } else {
        val temp = ByteArray(min(0x1000, count))
        var remaining = count
        while (remaining > 0) {
            val toRead = min(remaining, count)
            readTempExact(toRead, temp)
            remaining -= toRead
        }
    }
}

private suspend fun AsyncInputStream.readTempExact(len: Int, temp: ByteArray): ByteArray =
    temp.apply { readExact(temp, 0, len) }
//suspend private fun AsyncInputStream.readTempExact(len: Int): ByteArray = readTempExact(len, BYTES_TEMP)

fun AsyncBaseStream.toAsyncStream(): AsyncStream {
    val input = this as? AsyncInputStream
    val output = this as? AsyncOutputStream
    val rlenSet = this as? AsyncLengthStream
    val rlenGet = this as? AsyncGetLengthStream
    val closeable = this

    return object : AsyncStreamBase() {
        var expectedPosition: Long = 0L
        //val events = arrayListOf<String>()

        override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
            if (input == null) throw UnsupportedOperationException()
            //events += "before_read:actualPosition=$position,position=$expectedPosition"
            checkPosition(position)
            val read = input.read(buffer, offset, len)
            //events += "read:$read"
            if (read > 0) expectedPosition += read
            return read
        }

        override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
            if (output == null) throw UnsupportedOperationException()
            checkPosition(position)
            output.write(buffer, offset, len)
            expectedPosition += len
        }

        private fun checkPosition(position: Long) {
            if (position != expectedPosition) {
                throw SeekNotSupportedException()
            }
        }

        override suspend fun setLength(value: Long) = rlenSet?.setLength(value) ?: throw UnsupportedOperationException()
        override suspend fun getLength(): Long = rlenGet?.getLength() ?: throw UnsupportedOperationException()
        override suspend fun close() = closeable.close()
    }.toAsyncStream()
}
