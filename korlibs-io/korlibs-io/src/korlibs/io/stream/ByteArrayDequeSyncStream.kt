package korlibs.io.stream

import korlibs.concurrent.thread.*
import korlibs.datastructure.*
import korlibs.time.*
import kotlinx.atomicfu.locks.*

class ByteArrayDequeSyncStream(val deque: ByteArrayDeque) : SyncStreamBase() {
    override val separateReadWrite: Boolean get() = true
    override val seekable: Boolean get() = false
    var closed = false
    private val lock = reentrantLock()
    private inline fun <T> lock(block: () -> T): T = lock.withLock(block)

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
        loop@while (true) {
            for (n in 0 until 4) {
                if (closed) return -1
                if (deque.availableRead > 0) break@loop
                NativeThread.sleep(n.toLong().milliseconds)
            }
        }
        return lock { deque.read(buffer, offset, len) }
    }

    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
        if (closed) error("Writing to a closed stream")
        lock { deque.write(buffer, offset, len) }
    }

    override var length: Long
        get() = TODO()
        set(value) = TODO()

    override fun close() {
        closed = true
    }
}

fun ByteArrayDeque.toSyncStream(): SyncStream = ByteArrayDequeSyncStream(this).toSyncStream()
