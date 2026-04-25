@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.io.lang.*
import korlibs.memory.*
import kotlin.math.*

fun FastByteArrayInputStream.toSyncStream(): SyncStream = ReadonlySyncStreamBase(ba, start, end - start).open()
fun FastByteArrayInputStream.toAsyncStream(): AsyncStream = toSyncStream().toAsync()

fun SyncStream.readFastByteArrayInputStream(length: Int): FastByteArrayInputStream
    = FastByteArrayInputStream(readBytes(length))

private class ReadonlySyncStreamBase(var data: ByteArray, val offset: Int = 0, val size: Int = data.size - offset) : SyncStreamBase() {
    val ilength: Int get() = size

    override var length: Long
        get() = data.size.toLong()
        set(value) = unsupported()

    fun checkPosition(position: Long) { if (position < 0) invalidOp("Invalid position $position") }

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        checkPosition(position)
        val ipos = position.toInt()
        //if (position !in 0 until ilength) return -1
        if (position !in 0 until ilength) return 0
        val end = min(this.ilength, ipos + len)
        val actualLen = max((end - ipos), 0)
        arraycopy(this.data, ipos + this.offset, buffer, offset, actualLen)
        return actualLen
    }

    override fun close() = Unit

    override fun toString(): String = "ReadonlySyncStreamBase(${data.size})"
}
