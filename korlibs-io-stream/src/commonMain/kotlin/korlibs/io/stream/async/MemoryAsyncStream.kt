@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

import korlibs.memory.*
import kotlin.math.*

fun MemoryAsyncStream(data: korlibs.memory.ByteArrayBuilder): AsyncStream = MemoryAsyncStreamBase(data).toAsyncStream()
fun MemoryAsyncStream(initialCapacity: Int = 4096): AsyncStream = MemoryAsyncStreamBase(initialCapacity).toAsyncStream()

fun String.openAsync(): AsyncStream = encodeToByteArray().openAsync()
fun ByteArray.openAsync(mode: String = "r"): AsyncStream = MemoryAsyncStreamBase(ByteArrayBuilder(this, allowGrow = true)).toAsyncStream(0L)

class MemoryAsyncStreamBase(var data: ByteArrayBuilder) : AsyncStreamBase() {
    constructor(initialCapacity: Int = 4096) : this(ByteArrayBuilder(initialCapacity))

    var ilength: Int
        get() = data.size
        set(value) { data.size = value }

    override suspend fun setLength(value: Long) { ilength = value.toInt() }
    override suspend fun getLength(): Long = ilength.toLong()

    fun checkPosition(position: Long) { if (position < 0) throw IllegalArgumentException("Invalid position $position") }

    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        checkPosition(position)
        if (position !in 0 until ilength) return 0
        val end = min(this.ilength.toLong(), position + len)
        val actualLen = max((end - position).toInt(), 0)
        arraycopy(this.data.data, position.toInt(), buffer, offset, actualLen)
        return actualLen
    }

    override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
        checkPosition(position)
        data.size = max(data.size, (position + len).toInt())
        arraycopy(buffer, offset, this.data.data, position.toInt(), len)
    }

    override suspend fun close() = Unit

    override fun toString(): String = "MemoryAsyncStreamBase(${data.size})"
}
