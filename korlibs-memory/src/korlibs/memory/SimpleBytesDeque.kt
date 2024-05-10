package korlibs.memory

import kotlin.jvm.*
import kotlin.math.*

class SimpleBytesDeque(val initialBits: Int = 10, val allowGrow: Boolean = true) {
    private var ring = RingBuffer(initialBits)
    val tempBuffer = ByteArray(1024)

    var written: Long = 0; private set
    var read: Long = 0; private set
    val availableWriteWithoutAllocating get() = ring.availableWrite
    val availableRead get() = ring.availableRead
    val bufferSize get() = ring.totalSize

    fun writeHead(buffer: ByteArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        ensureWrite(size)
        val out = ring.writeHead(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    fun write(buffer: ByteArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        ensureWrite(size)
        val out = ring.write(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    fun read(buffer: ByteArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ring.read(buffer, offset, size)
        if (out > 0) read += out
        return out
    }

    fun skip(count: Int): Int {
        return ring.skip(count)
    }

    fun peek(buffer: ByteArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        return ring.peek(buffer, offset, size)
    }

    fun readByte(): Int = ring.readByte()
    fun writeByte(v: Int): Boolean {
        ensureWrite(1)
        return ring.writeByte(v)
    }

    private fun ensureWrite(count: Int) {
        if (count <= ring.availableWrite) return
        if (!allowGrow) {
            val message = "Can't grow ByteArrayDeque. Need to write $count, but only ${ring.availableWrite} is available"
            println("ERROR: $message")
            error(message)
        }
        val minNewSize = ring.availableRead + count
        this.ring = RingBuffer(ilog2(minNewSize) + 2).also { it.write(ring) }
    }

    fun clear() {
        ring.clear()
    }

    val hasMoreToWrite get() = ring.availableWrite > 0
    val hasMoreToRead get() = ring.availableRead > 0
    fun readOne(): Byte {
        read(tempBuffer, 0, 1)
        return tempBuffer[0]
    }
    fun writeOne(value: Byte) {
        tempBuffer[0] = value
        write(tempBuffer, 0, 1)
    }

    override fun hashCode(): Int = ring.contentHashCode()
    override fun equals(other: Any?): Boolean = (other is SimpleBytesDeque) && this.ring == other.ring

    private class RingBuffer(val bits: Int) {
        val totalSize = 1 shl bits
        private val mask = totalSize - 1
        private val buffer = ByteArray(totalSize)
        private var readPos = 0
        private var writePos = 0
        var availableWrite = totalSize; private set
        var availableRead = 0; private set
        val internalBuffer get() = buffer
        val internalReadPos get() = readPos and mask
        val internalWritePos get() = writePos and mask
        fun internalWriteSkip(count: Int) {
            if (count < 0 || count > availableWrite) error("Try to write more than available")
            writePos += count
            availableRead += count
            availableWrite -= count
        }
        fun internalReadSkip(count: Int) {
            if (count < 0 || count > availableRead) error("Try to write more than available")
            readPos += count
            availableRead -= count
            availableWrite += count
        }

        val availableReadBeforeWrap: Int get() = min(availableRead, (totalSize - (readPos and mask)))
        val availableWriteBeforeWrap: Int get() = min(availableWrite, (totalSize - (writePos and mask)))

        fun write(consume: RingBuffer) {
            while (consume.availableRead > 0) {
                val copySize = min(consume.availableReadBeforeWrap, this.availableWriteBeforeWrap)
                arraycopy(
                    consume.internalBuffer,
                    consume.internalReadPos,
                    this.internalBuffer,
                    this.internalWritePos,
                    copySize
                )
                consume.internalReadSkip(copySize)
                this.internalWriteSkip(copySize)
            }
        }

        fun writeHead(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int {
            val toWrite = min(availableWrite, size)
            for (n in 0 until toWrite) {
                readPos = (readPos - 1) and mask
                buffer[readPos] = data[offset + size - n - 1]
            }
            availableRead += toWrite
            availableWrite -= toWrite
            return toWrite
        }

        fun write(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int {
            var remaining = min(availableWrite, size)
            var coffset = offset
            var totalWrite = 0
            while (remaining > 0) {
                val chunkSize = min(remaining, availableWriteBeforeWrap)
                if (chunkSize <= 0) break
                arraycopy(data, coffset, buffer, internalWritePos, chunkSize)
                internalWriteSkip(chunkSize)
                coffset += chunkSize
                remaining -= chunkSize
                totalWrite += chunkSize
            }
            return totalWrite
        }

        fun read(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int = skip(peek(data, offset, size))

        fun skip(size: Int): Int {
            val toRead = min(availableRead, size)
            readPos = (readPos + toRead) and mask
            availableWrite += toRead
            availableRead -= toRead
            return toRead
        }

        fun peek(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int {
            var toRead = min(availableRead, size)
            var readCount = 0
            val buffer = buffer
            val mask = mask
            var coffset = offset
            var lReadPos = readPos

            while (true) {
                val toReadChunk = min(toRead, availableReadBeforeWrap)
                if (toReadChunk <= 0) break
                arraycopy(buffer, lReadPos and mask, data, coffset, toReadChunk)
                toRead -= toReadChunk
                coffset += toReadChunk
                lReadPos += toReadChunk
                readCount += toReadChunk
            }
            return readCount
        }

        fun readBytes(count: Int): ByteArray {
            val out = ByteArray(count)
            return out.copyOf(read(out))
        }

        fun readByte(): Int {
            if (availableRead <= 0) return -1
            val out = buffer[readPos].toInt() and 0xFF
            readPos = (readPos + 1) and mask
            availableRead--
            availableWrite++
            return out
        }

        fun writeByte(v: Int): Boolean {
            if (availableWrite <= 0) return false
            buffer[writePos] = v.toByte()
            writePos = (writePos + 1) and mask
            availableWrite--
            availableRead++
            return true
        }

        fun clear() {
            readPos = 0
            writePos = 0
            availableRead = 0
            availableWrite = totalSize
        }

        fun peek(offset: Int = 0) = buffer[(readPos + offset) and mask]
        override fun equals(other: Any?): Boolean = (other is RingBuffer) && this.availableRead == other.availableRead && equaler(availableRead) { this.peek(it) == other.peek(it) }
        override fun hashCode(): Int = contentHashCode()
        fun contentHashCode(): Int = hashCoder(availableRead) { peek(it).toInt() }

        inline fun hashCoder(count: Int, gen: (index: Int) -> Int): Int {
            var out = 0
            for (n in 0 until count) {
                out *= 7
                out += gen(n)
            }
            return out
        }

        inline fun equaler(count: Int, gen: (index: Int) -> Boolean): Boolean {
            for (n in 0 until count) if (!gen(n)) return false
            return true
        }

    }
}