@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.memory.*
import kotlin.math.*

fun String.openSync(): SyncStream = encodeToByteArray().openSync("r")
fun ByteArray.openSync(mode: String = "r"): SyncStream = MemorySyncStreamBase(ByteArrayBuilder(this)).toSyncStream(0L)

fun MemorySyncStream(data: ByteArray = EMPTY_BYTE_ARRAY) = MemorySyncStreamBase(ByteArrayBuilder(data)).toSyncStream()
fun MemorySyncStream(data: ByteArrayBuilder) = MemorySyncStreamBase(data).toSyncStream()
inline fun MemorySyncStreamToByteArray(initialCapacity: Int = 4096, callback: SyncStream.() -> Unit): ByteArray {
    val buffer = ByteArrayBuilder(initialCapacity)
    val s = MemorySyncStream(buffer)
    callback(s)
    return buffer.toByteArray()
}

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
