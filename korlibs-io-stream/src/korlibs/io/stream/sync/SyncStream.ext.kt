@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

val SyncStream.hasLength: Boolean get() = kotlin.runCatching { length }.isSuccess
val SyncStream.hasAvailable: Boolean get() = kotlin.runCatching { available }.isSuccess

fun SyncStream.toByteArray(): ByteArray {
    if (hasLength) {
        return this.sliceWithBounds(0L, length).readAll()
    } else {
        return this.clone().readAll()
    }
}

fun SyncStream.readAvailable(): ByteArray = readBytes(available.toInt())
fun SyncStream.readAll(): ByteArray = readBytes(available.toInt())

val SyncStream.eof: Boolean get () = this.available <= 0L
val SyncStream.hasMore: Boolean get () = this.available > 0L

fun SyncStream.skip(count: Int): SyncStream {
    position += count
    return this
}

fun SyncStream.skipToAlign(alignment: Int) {
    val nextPosition = position.nextAlignedTo(alignment.toLong())
    readBytes((nextPosition - position).toInt())
}

fun SyncStream.truncate() { length = position }

fun SyncStream.writeToAlign(alignment: Int, value: Int = 0) {
    val nextPosition = position.nextAlignedTo(alignment.toLong())
    val data = ByteArray((nextPosition - position).toInt())
    data.fill(value.toByte())
    writeBytes(data)
}

inline fun <T> SyncStream.keepPosition(callback: () -> T): T {
    val old = this.position
    try {
        return callback()
    } finally {
        this.position = old
    }
}

fun SyncStream.readStringVL(): String {
    val bytes = ByteArray(readU_VL())
    readExact(bytes, 0, bytes.size)
    return bytes.decodeToString()
}
