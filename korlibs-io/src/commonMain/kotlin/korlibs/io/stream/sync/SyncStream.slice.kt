@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

class SliceSyncStreamBase(internal val base: SyncStreamBase, internal val baseStart: Long, internal val baseEnd: Long) : SyncStreamBase() {
    override val separateReadWrite: Boolean get() = base.separateReadWrite
    internal val baseLength: Long = baseEnd - baseStart

    override var length: Long
        set(value) = throw UnsupportedOperationException()
        get() = baseLength

    private fun clampPosition(position: Long) = position.coerceIn(baseStart, baseEnd)

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

fun SyncStream.sliceStart(start: Long = 0L): SyncStream = sliceWithBounds(start, this.length)
fun SyncStream.sliceHere(): SyncStream = SyncStream(SliceSyncStreamBase(this.base, position, length))

fun SyncStream.slice(range: IntRange): SyncStream =
    sliceWithBounds(range.start.toLong(), (range.endInclusive.toLong() + 1))

fun SyncStream.slice(range: LongRange): SyncStream = sliceWithBounds(range.start, (range.endInclusive + 1))

fun SyncStream.sliceWithBounds(start: Long, end: Long): SyncStream {
    val len = this.length
    val clampedStart = start.coerceIn(0, len)
    val clampedEnd = end.coerceIn(0, len)
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
