@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

import korlibs.memory.SimpleBytesDeque
import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock

fun DequeSyncStream(): SyncStream = object : SyncStreamBase() {
    var closed = false
    val deque: SimpleBytesDeque = SimpleBytesDeque()
    val lock = reentrantLock()

    override val separateReadWrite: Boolean get() = true
    override val seekable get() = false

    override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int = lock.withLock {
        if (position != deque.read) error("Invalid DequeSyncStreamBase.position for reading $position != ${deque.read}")
        val res = deque.read(buffer, offset, len)
        if (len > 0 && res <= 0 && closed) return@withLock -1
        res
    }

    override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int): Unit = lock.withLock {
        if (position != deque.written) error("Invalid DequeSyncStreamBase.position for writing $position != ${deque.written}")
        deque.write(buffer, offset, len)
    }

    override var length: Long
        get() = deque.written
        set(value) {}

    override fun close() = lock.withLock {
        closed = true
        deque.clear()
    }
}.toSyncStream()
