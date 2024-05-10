package korlibs.io.stream

import kotlinx.coroutines.*
import kotlin.coroutines.*

val EMPTY_BYTE_ARRAY = ByteArray(0)

class SyncAsyncStreamBase(val sync: SyncStreamBase, val dispatcher: CoroutineDispatcher? = null) : AsyncStreamBase() {
    private suspend inline fun <T> doIo(crossinline block: () -> T): T = when {
        dispatcher != null -> withContext(dispatcher) { block() }
        else -> block()
    }
    override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int = doIo { sync.read(position, buffer, offset, len) }
    override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) = doIo { sync.write(position, buffer, offset, len) }
    override suspend fun setLength(value: Long) = doIo { sync.length = value }
    override suspend fun getLength(): Long = doIo { sync.length }
}

suspend inline fun AsyncOutputStream.writeSync(hintSize: Int = 4096, callback: SyncStream.() -> Unit) {
    writeBytes(MemorySyncStreamToByteArray(hintSize) {
        callback()
    })
}

fun AsyncStreamBase.toSyncOrNull(): SyncStreamBase? = (this as? SyncAsyncStreamBase?)?.sync
    ?: (this as? MemoryAsyncStreamBase?)?.let { MemorySyncStreamBase(it.data) }

fun AsyncStream.toSyncOrNull(): SyncStream? = this.base.toSyncOrNull()?.let { SyncStream(it, this.position) }

fun SyncStream.toAsync(dispatcher: CoroutineDispatcher? = null): AsyncStream = this.base.toAsync(dispatcher).toAsyncStream(this.position)
fun SyncStreamBase.toAsync(dispatcher: CoroutineDispatcher? = null): AsyncStreamBase = when (this) {
    is MemorySyncStreamBase -> MemoryAsyncStreamBase(this.data)
    else -> SyncAsyncStreamBase(this, dispatcher)
}

fun SyncInputStream.toAsync(dispatcher: CoroutineDispatcher? = null): AsyncInputStream = object : AsyncInputStreamWithLength {
    val sync = this@toAsync
    private suspend inline fun <T> doIo(crossinline block: () -> T): T = doIo(dispatcher, block)
    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = doIo { sync.read(buffer, offset, len) }
    override suspend fun close(): Unit = launchIo(dispatcher) { (sync as? AutoCloseable)?.close() }
    override suspend fun getPosition(): Long = doIo { (sync as? SyncPositionStream)?.position } ?: super.getPosition()
    override suspend fun getLength(): Long = doIo { (sync as? SyncLengthStream)?.length } ?: super.getLength()
}

fun SyncOutputStream.toAsync(dispatcher: CoroutineDispatcher? = null): AsyncOutputStream = object : AsyncOutputStream {
    val sync = this@toAsync
    private suspend inline fun <T> doIo(crossinline block: () -> T): T = doIo(dispatcher, block)
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = doIo { sync.write(buffer, offset, len) }
    override suspend fun close(): Unit = launchIo(dispatcher) { (sync as? AutoCloseable)?.close() }
}

private suspend inline fun <T> doIo(dispatcher: CoroutineDispatcher? = null, crossinline block: () -> T): T = when {
    dispatcher != null -> withContext(dispatcher) { block() }
    else -> block()
}

private inline fun launchIo(dispatcher: CoroutineContext?, crossinline block: () -> Unit): Unit {
    if (dispatcher != null) {
        CoroutineScope(dispatcher).launch {
            block()
        }
    } else {
        block()
    }
}
