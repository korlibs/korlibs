@file:Suppress("PackageDirectoryMismatch")
package korlibs.io.stream

import korlibs.memory.*

fun sequenceSyncStream(block: suspend SequenceScope<ByteArray>.() -> Unit): SyncStream {
    return object : SyncStreamBase() {
        override val seekable: Boolean = false
        val iterator = sequence { block() }.iterator()
        val deque = SimpleBytesDeque()

        override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
            while (deque.availableRead < len) {
                if (!iterator.hasNext()) break
                deque.write(iterator.next())
            }
            return deque.read(buffer, offset, len)
        }
    }.toSyncStream()
}
