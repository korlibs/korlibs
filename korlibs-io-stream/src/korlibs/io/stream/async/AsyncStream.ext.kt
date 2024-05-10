@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.memory.*

fun AsyncStream.skip(count: Int): AsyncStream {
    position += count
    return this
}
fun AsyncStream.skipToAlign(alignment: Int) { position = position.nextAlignedTo(alignment.toLong()) }
fun AsyncStream.skipToAlign(alignment: Int, offset: Int) { position = (position + offset).nextAlignedTo(alignment.toLong()) - offset }

suspend fun AsyncStream.truncate() = setLength(position)

suspend fun AsyncStreamBase.readBytes(position: Long, count: Int): ByteArray {
    val out = ByteArray(count)
    val readLen = read(position, out, 0, out.size)
    return out.copyOf(readLen)
}

inline fun <T> AsyncStream.keepPosition(callback: () -> T): T {
    val old = this.position
    try {
        return callback()
    } finally {
        this.position = old
    }
}

suspend fun AsyncStream.writeToAlign(alignment: Int, value: Int = 0) {
    val nextPosition = getPosition().nextAlignedTo(alignment.toLong())
    val data = ByteArray((nextPosition - getPosition()).toInt())
    data.fill(value.toByte())
    writeBytes(data)
}


suspend fun AsyncStream.readAllAsFastStream(offset: Int = 0): FastByteArrayInputStream = this.readAll().openFastStream()

inline fun AsyncStream.getWrittenRange(callback: () -> Unit): LongRange {
    val start = position
    callback()
    val end = position
    return start until end
}
