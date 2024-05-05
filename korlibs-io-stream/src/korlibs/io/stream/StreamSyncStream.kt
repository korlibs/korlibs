package korlibs.io.stream

import korlibs.datastructure.*
import korlibs.datastructure.closeable.*
import korlibs.io.lang.*

interface SyncInputStream : Closeable {
    fun read(buffer: ByteArray, offset: Int = 0, len: Int = buffer.size - offset): Int
    fun read(): Int = ByteArray(1).let { if (read(it, 0, 1) > 0) it[0].toInt() and 0xFF else -1 }
    fun skip(count: Int) {
        read(ByteArray(count))
    }
    override fun close() = Unit
}

interface MarkableSyncInputStream : SyncInputStream {
    fun mark(readlimit: Int)
    fun reset()
}

interface SyncOutputStream : Closeable {
    fun write(buffer: ByteArray, offset: Int = 0, len: Int = buffer.size - offset): Unit
    fun write(byte: Int) = ByteArray(1).let { it[0] = byte.toByte(); write(it, 0, 1) }
    fun flush() = Unit
    override fun close() = Unit
}

interface SyncPositionStream {
    var position: Long
}

interface SyncLengthStream {
    var length: Long
}

interface SyncRAInputStream {
    fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int
}

interface SyncRAOutputStream {
    fun write(position: Long, buffer: ByteArray, offset: Int, len: Int): Unit
    fun flush(): Unit = Unit
}

open class SyncStreamBase : Closeable, SyncRAInputStream, SyncRAOutputStream, SyncLengthStream {
    open val separateReadWrite: Boolean get() = false
    val smallTemp = ByteArray(16)
    open val seekable get() = true
    fun read(position: Long): Int = if (read(position, smallTemp, 0, 1) >= 1) smallTemp[0].toInt() and 0xFF else -1
    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int = unsupported()
    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int): Unit = unsupported()
    override var length: Long set(_) = unsupported(); get() = unsupported()
    override fun close() = Unit
    fun open(position: Long = 0L) = SyncStream(this, position)
}

class SyncStream constructor(
    val base: SyncStreamBase,
    position: Long = 0L
) : Extra by Extra.Mixin(), Closeable, SyncInputStream, SyncPositionStream, SyncOutputStream, SyncLengthStream, MarkableSyncInputStream {
    private val smallTemp = base.smallTemp
    private val separateReadWrite = base.separateReadWrite

    var positionRead: Long = position
        set(value) {
            if (separateReadWrite) field = value else position = value
            //println("SET positionRead=$value")
        }
        get() = if (separateReadWrite) field else position

    var positionWrite: Long = position
        set(value) {
            if (separateReadWrite) field = value else position = value
        }
        get() = if (separateReadWrite) field else position

    override var position: Long = position
        set(value) = if (separateReadWrite) positionRead = value else field = value
        get() = if (separateReadWrite) positionRead else field

    override fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        //println("read.positionRead[$this]=$positionRead")
        val read = base.read(positionRead, buffer, offset, len)
        positionRead += read
        //println("/read.positionRead[$this]=$positionRead")
        return read
    }

    override fun skip(count: Int) {
        positionRead += count
    }

    override fun read(): Int {
        val size = read(smallTemp, 0, 1)
        if (size <= 0) return -1
        return smallTemp[0].toInt() and 0xFF
    }

    override fun write(buffer: ByteArray, offset: Int, len: Int) {
        base.write(positionWrite, buffer, offset, len)
        positionWrite += len
    }

    override fun write(byte: Int) {
        smallTemp[0] = byte.toByte()
        write(smallTemp, 0, 1)
    }

    override var length: Long
        set(value) { base.length = value }
        get() = base.length

    val availableRead: Long get() = length - positionRead
    val availableWrite: Long get() = length - positionWrite

    val available: Long get() = availableRead

    override fun flush() {
        base.flush()
    }

    override fun close(): Unit = base.close()

    fun clone() = SyncStream(base, position)

    var markPos = 0L

    override fun mark(readlimit: Int) {
        if (!base.seekable) unsupported()
        markPos = positionRead
    }

    override fun reset() {
        positionRead = markPos
    }

    override fun toString(): String = "SyncStream($base, $position)"
}
