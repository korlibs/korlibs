package korlibs.io.core

import korlibs.datastructure.closeable.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

expect val defaultSyncSystemIo: SyncSystemIo
expect val defaultSystemIo: SystemIo

object NullSyncSystemIo : SyncSystemIo()
object NullSystemIo : SystemIo()

open class SyncSystemIo {
    open fun open(path: String, write: Boolean = false): SyncFileSystemIo? = TODO()
    open fun listdir(path: String): Sequence<String> = TODO()
    open fun mkdir(path: String): Boolean = TODO()
    open fun rmdir(path: String): Boolean = TODO()
    open fun unlink(path: String): Boolean = TODO()
    open fun stat(path: String): FileSystemIoStat? = TODO()

    open fun exists(path: String): Boolean = stat(path) != null
    open fun isFile(path: String): Boolean = stat(path)?.isDirectory == false
    open fun isDirectory(path: String): Boolean = stat(path)?.isDirectory == true
}

open class SystemIo {
    open suspend fun open(path: String, write: Boolean = false): FileSystemIo? = TODO()
    open suspend fun listdir(path: String): Flow<String> = TODO()
    open suspend fun mkdir(path: String): Boolean = TODO()
    open suspend fun rmdir(path: String): Boolean = TODO()
    open suspend fun unlink(path: String): Boolean = TODO()
    open suspend fun stat(path: String): FileSystemIoStat? = TODO()

    suspend fun exists(path: String): Boolean = stat(path) != null
    suspend fun isFile(path: String): Boolean = stat(path)?.isDirectory == false
    suspend fun isDirectory(path: String): Boolean = stat(path)?.isDirectory == true
}

data class FileSystemIoStat(
    val name: String,
    val size: Long = 0L,
    val mode: Int = 511, // 0o777
    val isDirectory: Boolean = false,
    val timeLastModification: Long = 0L,
    val timeCreation: Long = timeLastModification,
    val timeLastAccess: Long = timeLastModification,
    val inode: Long = 0L,
)

open class FileSystemIo : Closeable {
    open suspend fun getLength(): Long = TODO()
    open suspend fun setLength(value: Long): Unit = TODO()
    open suspend fun getPosition(): Long = TODO()
    open suspend fun setPosition(value: Long): Unit = TODO()
    open suspend fun read(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int = TODO()
    open suspend fun write(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Unit = TODO()
    override fun close(): Unit = TODO()
}

open class SyncFileSystemIo : Closeable {
    open fun getLength(): Long = TODO()
    open fun setLength(value: Long): Unit = TODO()
    open fun getPosition(): Long = TODO()
    open fun setPosition(value: Long): Unit = TODO()
    open fun read(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int = TODO()
    open fun write(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Unit = TODO()
    override fun close(): Unit = TODO()
}

fun SyncSystemIo.toAsync(ioDispatcher: CoroutineDispatcher): SystemIo {
    val sync = this@toAsync
    return object : SystemIo() {
        private suspend inline fun <T> doSyncIo(crossinline block: () -> T): T = withContext(ioDispatcher) { block() }

        override suspend fun open(path: String, write: Boolean): FileSystemIo? {
            val io = doSyncIo { sync.open(path, write) } ?: return null
            return object : FileSystemIo() {
                override suspend fun getLength(): Long = doSyncIo { io.getLength() }
                override suspend fun setLength(value: Long) = doSyncIo { io.setLength(value) }
                override suspend fun getPosition(): Long = doSyncIo { io.getPosition() }
                override suspend fun setPosition(value: Long) = doSyncIo { io.setPosition(value) }
                override suspend fun read(data: ByteArray, offset: Int, size: Int): Int = doSyncIo { io.read(data, offset, size) }
                override suspend fun write(data: ByteArray, offset: Int, size: Int) = doSyncIo { io.write(data, offset, size) }

                override fun close() = io.close()
            }
        }

        override suspend fun listdir(path: String): Flow<String> = doSyncIo { sync.listdir(path).asFlow() }
        override suspend fun mkdir(path: String) = doSyncIo { sync.mkdir(path) }
        override suspend fun unlink(path: String) = doSyncIo { sync.unlink(path) }
        override suspend fun rmdir(path: String) = doSyncIo { sync.rmdir(path) }
        override suspend fun stat(path: String): FileSystemIoStat? = doSyncIo { sync.stat(path) }
    }
}
