@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

import korlibs.io.stream.*
import korlibs.memory.*
import kotlinx.coroutines.sync.*
import kotlin.math.*

fun AsyncStream.buffered(blockSize: Int = 2048, blocksToRead: Int = 0x10): AsyncStream = BufferedStreamBase(this.base, blockSize, blocksToRead).toAsyncStream(this.position)

private class BufferedStreamBase(val base: AsyncStreamBase, val blockSize: Int = 2048, val blocksToRead: Int = 0x10) : AsyncStreamBase() {
    private val bsize = blockSize * blocksToRead

    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int = _read(position, buffer, offset, len)

    var cachedData = byteArrayOf()
    var cachedSector = -1L

    override fun toString(): String = "Buffered[$blocksToRead*$blockSize]:$base"

    suspend fun _read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        if (base.hasLength() && position >= base.getLength()) return -1
        val sector = position / bsize
        if (cachedSector != sector) {
            val pos = sector * bsize
            val len = if (base.hasLength()) minOf(bsize.toLong(), base.getLength() - pos).toInt() else bsize
            //println("$base, position=$position, offset=$offset, len=$len, base.getLength()=${base.getLength()}\n")
            cachedData = base.readBytes(pos, len)
            cachedSector = sector
        }
        val soffset = (position % bsize).toInt()
        val available = cachedData.size - soffset
        val toRead = min(available, len)
        arraycopy(cachedData, soffset, buffer, offset, toRead)
        return toRead
    }

    override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
        base.write(position, buffer, offset, len)
    }

    override suspend fun setLength(value: Long) = base.setLength(value)
    override suspend fun getLength(): Long = base.getLength()
    override suspend fun close() = base.close()
}

suspend fun AsyncBufferedInputStream.readBufferedLine(limit: Int = 0x1000) =
    readUntil('\n'.code.toByte(), including = false, limit = limit).decodeToString()

fun AsyncInputStream.bufferedInput(bufferSize: Int = 0x2000): AsyncBufferedInputStream =
    AsyncBufferedInputStream(this, bufferSize)

class AsyncBufferedInputStream(val base: AsyncInputStream, val initialBits: Int = 10) : AsyncInputStream {
    private val buf = SimpleBytesDeque(initialBits)

    private val queue = Mutex()
    private val temp = buf.tempBuffer

    suspend fun require(len: Int = 1) = queue.withLock {
        while (buf.availableRead < len) {
            val read = base.read(temp, 0, temp.size)
            if (read <= 0) break
            buf.write(temp, 0, read)
        }
    }

    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        if (buf.availableRead < len) require()
        return buf.read(buffer, offset, len)
    }

    override suspend fun read(): Int {
        if (buf.availableRead < 1) require()
        return buf.readByte()
    }

    suspend fun readUntil(end: Byte, including: Boolean = true, limit: Int = 0x1000): ByteArray {
        val out = ByteArrayBuilder()
        loop@while (true) {
            require()
            if (buf.availableRead == 0) break@loop
            while (buf.availableRead > 0) {
                val byteInt = buf.readByte()
                if (byteInt < 0) break@loop
                val byte = byteInt.toByte()
                //println("chunk: $chunk, ${chunk.size}")
                if (including || byte != end) {
                    out.append(byte)
                }
                if (byte == end || out.size >= limit) break@loop
            }
        }
        return out.toByteArray()
    }

    override suspend fun close() {
        base.close()
    }
}
