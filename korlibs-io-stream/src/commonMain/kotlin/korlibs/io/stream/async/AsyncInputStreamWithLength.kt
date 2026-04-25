@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

operator fun AsyncInputStreamWithLength.plus(other: AsyncInputStreamWithLength): AsyncInputStreamWithLength = listOf(this, other).combine()

suspend fun AsyncInputStreamWithLength.getAvailable(): Long = this.getLength() - this.getPosition()
suspend fun AsyncInputStreamWithLength.hasAvailable(): Boolean = getAvailable() > 0
suspend fun AsyncInputStreamWithLength.supportsAvailable() = kotlin.runCatching { hasAvailable() }.isSuccess

fun AsyncInputStream.withLength(length: Long): AsyncInputStream {
    val base = this
    var currentPos = 0L
    return object : AsyncInputStream by base, AsyncGetLengthStream, AsyncGetPositionStream {
        override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
            val read = base.read(buffer, offset, len)
            if (read >= 0) currentPos += read
            return read
        }

        override suspend fun getPosition(): Long = currentPos
        override suspend fun getLength(): Long = length
    }
}

fun List<AsyncInputStreamWithLength>.combine(): AsyncInputStreamWithLength {
    val list = this
    return object : AsyncInputStreamWithLength {
        override suspend fun getPosition(): Long = list.map { it.getPosition() }.sum()
        override suspend fun getLength(): Long = list.map { it.getLength() }.sum()

        override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int {
            list.fastForEach { i ->
                val read = i.read(buffer, offset, len)
                if (read > 0) return read
            }
            return -1
        }

        override suspend fun close() {
            list.fastForEach { i ->
                i.close()
            }
        }
    }
}

private inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}
