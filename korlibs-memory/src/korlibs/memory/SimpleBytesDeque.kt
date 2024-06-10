package korlibs.memory

import korlibs.math.*

class SimpleBytesDeque(val initialBits: Int = 10, val allowGrow: Boolean = true) {
    private var ring = SimpleRingBuffer(initialBits)
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
        this.ring = SimpleRingBuffer(ilog2(minNewSize) + 2).also { it.write(ring) }
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
}
