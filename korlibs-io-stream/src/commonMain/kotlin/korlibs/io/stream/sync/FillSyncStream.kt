@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.io.stream.internal.*
import korlibs.math.*
import kotlin.math.*

fun FillSyncStream(fillByte: Int = 0, length: Long = Long.MAX_VALUE) =
    FillSyncStreamBase(fillByte.toByte(), length).toSyncStream()

class FillSyncStreamBase(val fill: Byte, override var length: Long) : SyncStreamBase() {
    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        val end = min(length, position + len)
        val actualLen = (end - position).toIntSafe()
        buffer.fill(fill, offset, offset + actualLen)
        return actualLen
    }

    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) = Unit

    override fun close() = Unit
}
