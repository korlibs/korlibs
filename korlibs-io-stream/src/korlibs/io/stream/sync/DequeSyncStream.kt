@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

import korlibs.memory.*

fun DequeSyncStream(): SyncStream = object : SyncStreamBase() {
    val deque: SimpleBytesDeque = SimpleBytesDeque()

    override val separateReadWrite: Boolean get() = true
    override val seekable get() = false

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        if (position != deque.read) error("Invalid DequeSyncStreamBase.position for reading $position != ${deque.read}")
        return deque.read(buffer, offset, len)
    }

    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
        if (position != deque.written) error("Invalid DequeSyncStreamBase.position for writting $position != ${deque.written}")
        deque.write(buffer, offset, len)
    }

    override var length: Long
        get() = deque.written
        set(value) {}

    override fun close() {
        deque.clear()
    }
}.toSyncStream()

//class DequeSyncStream : AutoCloseable, SyncInputStream, SyncOutputStream, SyncPositionStream, SyncLengthStream {
//    val deque = SimpleBytesDeque()
//    override var position: Long = 0L
//    override var length: Long = 0L
//
//    override fun write(buffer: ByteArray, offset: Int, len: Int) {
//        deque.write(buffer, offset, len)
//        length += len
//    }
//
//    override fun read(buffer: ByteArray, offset: Int, len: Int): Int {
//        return deque.read(buffer, offset, len).also {
//            position += it
//        }
//    }
//
//    override fun close() {
//        deque.clear()
//    }
//}
