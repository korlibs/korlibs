@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

suspend fun AsyncStream.sliceWithSize(start: Long, length: Long, closeParent: Boolean = false): AsyncStream = sliceWithBounds(start, start + length, closeParent)
suspend fun AsyncStream.sliceWithSize(start: Int, length: Int, closeParent: Boolean = false): AsyncStream =
    sliceWithBounds(start.toLong(), (start + length).toLong(), closeParent)

suspend fun AsyncStream.slice(range: IntRange, closeParent: Boolean = false): AsyncStream =
    sliceWithBounds(range.start.toLong(), (range.endInclusive.toLong() + 1), closeParent)

suspend fun AsyncStream.slice(range: LongRange, closeParent: Boolean = false): AsyncStream = sliceWithBounds(range.start, (range.endInclusive + 1), closeParent)

suspend fun AsyncStream.sliceWithBounds(start: Long, end: Long, closeParent: Boolean = false): AsyncStream {
    val rlen = if (this.hasLength()) this.getLength() else end
    val len = if (rlen >= 0L) rlen else end
    val clampedStart = start.coerceIn(0, len)
    val clampedEnd = end.coerceIn(0, len)

    val base = this.base
    return when (base) {
        is SliceAsyncStreamBase -> {
            SliceAsyncStreamBase(
                base.base,
                base.baseStart + clampedStart,
                base.baseStart + clampedEnd,
                closeParent
            )
        }
        else -> {
            SliceAsyncStreamBase(this.base, clampedStart, clampedEnd, closeParent)
        }
    }.toAsyncStream()
}

suspend fun AsyncStream.sliceStart(start: Long = 0L, closeParent: Boolean = false): AsyncStream = sliceWithBounds(start, this.getLength(), closeParent)
suspend fun AsyncStream.sliceHere(closeParent: Boolean = false): AsyncStream = this.sliceWithSize(position, this.getLength(), closeParent)

suspend fun AsyncStream.readSlice(length: Long): AsyncStream {
    val start = getPosition()
    val out = this.sliceWithSize(start, length)
    setPosition(start + length)
    return out
}

suspend fun AsyncStream.readStream(length: Int): AsyncStream = readSlice(length.toLong())
suspend fun AsyncStream.readStream(length: Long): AsyncStream = readSlice(length)

class SliceAsyncStreamBase(
    internal val base: AsyncStreamBase,
    internal val baseStart: Long,
    internal val baseEnd: Long,
    internal val closeParent: Boolean
) : AsyncStreamBase() {
    init {
        //check(baseStart < 0) { "baseStart negative: $baseStart" }
        //check(baseEnd < baseStart) { "Invalid baseEnd=$baseEnd" }
    }
    //init {
    //	base.refCount++
    //}

    internal val baseLength = baseEnd - baseStart

    private fun clampPosition(position: Long) = position.coerceIn(baseStart, baseEnd)

    private fun clampPositionLen(position: Long, len: Int): Pair<Long, Int> {
        if (position < 0L) throw IllegalArgumentException("Invalid position")
        val targetStartPosition = clampPosition(this.baseStart + position)
        val targetEndPosition = clampPosition(targetStartPosition + len)
        val targetLen = (targetEndPosition - targetStartPosition).toInt()
        return Pair(targetStartPosition, targetLen)
    }

    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        val (targetStartPosition, targetLen) = clampPositionLen(position, len)
        return base.read(targetStartPosition, buffer, offset, targetLen)
    }

    override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
        val (targetStartPosition, targetLen) = clampPositionLen(position, len)
        return base.write(targetStartPosition, buffer, offset, targetLen)
    }

    override suspend fun getLength(): Long = baseLength

    override suspend fun close() {
        if (closeParent) {
            base.close()
        }
    }

    override fun toString(): String = "SliceAsyncStreamBase($base, $baseStart, $baseEnd)"
}
