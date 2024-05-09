package korlibs.io.stream

import korlibs.datastructure.*
import korlibs.math.*
import korlibs.memory.*
import kotlin.math.*

interface SyncInputStream : AutoCloseable {
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

interface SyncOutputStream : AutoCloseable {
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

open class SyncStreamBase : AutoCloseable, SyncRAInputStream, SyncRAOutputStream, SyncLengthStream {
    open val separateReadWrite: Boolean get() = false
    val smallTemp = ByteArray(16)
    open val seekable get() = true
    fun read(position: Long): Int = if (read(position, smallTemp, 0, 1) >= 1) smallTemp[0].toInt() and 0xFF else -1
    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int = throw UnsupportedOperationException()
    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int): Unit = throw UnsupportedOperationException()
    override var length: Long set(_) = throw UnsupportedOperationException(); get() = throw UnsupportedOperationException()
    override fun close() = Unit
    fun open(position: Long = 0L) = SyncStream(this, position)
}

fun SyncStreamBase.toSyncStream(position: Long = 0L) = SyncStream(this, position)

class SyncStream(
    val base: SyncStreamBase,
    position: Long = 0L
) : Extra by Extra.Mixin(), AutoCloseable, SyncInputStream, SyncPositionStream, SyncOutputStream, SyncLengthStream, MarkableSyncInputStream {
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
        if (!base.seekable) throw UnsupportedOperationException()
        markPos = positionRead
    }

    override fun reset() {
        positionRead = markPos
    }

    override fun toString(): String = "SyncStream($base, $position)"
}

fun ByteArray.openSync(mode: String = "r"): SyncStream = MemorySyncStreamBase(ByteArrayBuilder(this)).toSyncStream(0L)

val SyncStream.hasLength: Boolean get() = kotlin.runCatching { length }.isSuccess
val SyncStream.hasAvailable: Boolean get() = kotlin.runCatching { available }.isSuccess

fun SyncStream.toByteArray(): ByteArray {
    if (hasLength) {
        return this.sliceWithBounds(0L, length).readAll()
    } else {
        return this.clone().readAll()
    }
}

fun MemorySyncStream(data: ByteArray = EMPTY_BYTE_ARRAY) = MemorySyncStreamBase(ByteArrayBuilder(data)).toSyncStream()
fun MemorySyncStream(data: ByteArrayBuilder) = MemorySyncStreamBase(data).toSyncStream()

class MemorySyncStreamBase(var data: ByteArrayBuilder) : SyncStreamBase() {
    constructor(initialCapacity: Int = 4096) : this(ByteArrayBuilder(initialCapacity))

    var ilength: Int
        get() = data.size
        set(value) { data.size = value }

    override var length: Long
        get() = data.size.toLong()
        set(value) { data.size = value.toInt() }

    fun checkPosition(position: Long) { if (position < 0) throw IllegalArgumentException("Invalid position $position") }

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        checkPosition(position)
        val ipos = position.toInt()
        //if (position !in 0 until ilength) return -1
        if (position !in 0 until ilength) return 0
        val end = min(this.ilength, ipos + len)
        val actualLen = max((end - ipos), 0)
        arraycopy(this.data.data, ipos, buffer, offset, actualLen)
        return actualLen
    }

    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
        checkPosition(position)
        data.size = max(data.size, (position + len).toInt())
        arraycopy(buffer, offset, this.data.data, position.toInt(), len)
    }

    override fun close() = Unit

    override fun toString(): String = "MemorySyncStreamBase(${data.size})"
}

fun SyncStream.readAvailable(): ByteArray = readBytes(available.toInt())
fun SyncStream.readAll(): ByteArray = readBytes(available.toInt())

fun SyncInputStream.readBytes(len: Int): ByteArray {
    val bytes = ByteArray(len)
    val out = read(bytes, 0, len)
    return if (out != len) bytes.copyOf(out) else bytes
}

fun SyncStream.sliceStart(start: Long = 0L): SyncStream = sliceWithBounds(start, this.length)
fun SyncStream.sliceHere(): SyncStream = SyncStream(SliceSyncStreamBase(this.base, position, length))

fun SyncStream.slice(range: IntRange): SyncStream =
    sliceWithBounds(range.start.toLong(), (range.endInclusive.toLong() + 1))

fun SyncStream.slice(range: LongRange): SyncStream = sliceWithBounds(range.start, (range.endInclusive + 1))

fun SyncStream.sliceWithBounds(start: Long, end: Long): SyncStream {
    val len = this.length
    val clampedStart = start.clamp(0, len)
    val clampedEnd = end.clamp(0, len)
    val base = this.base
    if (base is SliceSyncStreamBase) {
        return SliceSyncStreamBase(
            base.base,
            base.baseStart + clampedStart,
            base.baseStart + clampedEnd
        ).toSyncStream()
    } else {
        return SliceSyncStreamBase(base, clampedStart, clampedEnd).toSyncStream()
    }
}

fun SyncStream.sliceWithSize(position: Long, length: Long): SyncStream = sliceWithBounds(position, position + length)
fun SyncStream.sliceWithSize(position: Int, length: Int): SyncStream =
    sliceWithBounds(position.toLong(), (position + length).toLong())

fun SyncStream.readSlice(length: Long): SyncStream = sliceWithSize(position, length).apply {
    this@readSlice.position += length
}

fun SyncStream.readStream(length: Int): SyncStream = readSlice(length.toLong())
fun SyncStream.readStream(length: Long): SyncStream = readSlice(length)

class SliceSyncStreamBase(internal val base: SyncStreamBase, internal val baseStart: Long, internal val baseEnd: Long) :
    SyncStreamBase() {
    override val separateReadWrite: Boolean get() = base.separateReadWrite
    internal val baseLength: Long = baseEnd - baseStart

    override var length: Long
        set(value) = throw UnsupportedOperationException()
        get() = baseLength

    private fun clampPosition(position: Long) = position.clamp(baseStart, baseEnd)

    private fun clampPositionLen(position: Long, len: Int): Pair<Long, Int> {
        if (position < 0L) throw IllegalArgumentException("Invalid position")
        val targetStartPosition = clampPosition(this.baseStart + position)
        val targetEndPosition = clampPosition(targetStartPosition + len)
        val targetLen = (targetEndPosition - targetStartPosition).toInt()
        return Pair(targetStartPosition, targetLen)
    }

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        val (targetStartPosition, targetLen) = clampPositionLen(position, len)
        return base.read(targetStartPosition, buffer, offset, targetLen)
    }

    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
        val (targetStartPosition, targetLen) = clampPositionLen(position, len)
        return base.write(targetStartPosition, buffer, offset, targetLen)
    }

    override fun close() = Unit

    override fun toString(): String = "SliceAsyncStreamBase($base, $baseStart, $baseEnd)"
}

class DequeSyncStream : AutoCloseable, SyncInputStream, SyncOutputStream, SyncPositionStream, SyncLengthStream {
    val deque = SimpleBytesDeque()
    override var position: Long = 0L
    override var length: Long = 0L

    override fun write(buffer: ByteArray, offset: Int, len: Int) {
        deque.write(buffer, offset, len)
        length += len
    }

    override fun read(buffer: ByteArray, offset: Int, len: Int): Int {
        return deque.read(buffer, offset, len).also {
            position += it
        }
    }

    override fun close() {
        deque.clear()
    }
}
