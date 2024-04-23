package korlibs.io.core

import korlibs.datastructure.closeable.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

expect val defaultSyncSystemIo: SyncSystemIo
expect val defaultSystemIo: SystemIo

object NullSyncSystemIo : SyncSystemIo() {
    override fun open(path: String, write: Boolean): SyncFileSystemIo? = TODO("Not yet implemented")
    override fun listdir(path: String): Sequence<String> = TODO("Not yet implemented")
    override fun mkdir(path: String): Boolean = TODO("Not yet implemented")
    override fun rmdir(path: String): Boolean = TODO("Not yet implemented")
    override fun unlink(path: String): Boolean = TODO("Not yet implemented")
    override fun stat(path: String): FileSystemIoStat? = TODO("Not yet implemented")
}
val NullSystemIo: SystemIo = NullSyncSystemIo.toAsync(Dispatchers.Unconfined)

abstract class SyncSystemIo {
    open fun getcwd(): String = "."

    abstract fun open(path: String, write: Boolean = false): SyncFileSystemIo?
    abstract fun listdir(path: String): Sequence<String>
    abstract fun mkdir(path: String): Boolean
    abstract fun rmdir(path: String): Boolean
    abstract fun unlink(path: String): Boolean
    abstract fun stat(path: String): FileSystemIoStat?

    fun exists(path: String): Boolean = stat(path) != null
    fun isFile(path: String): Boolean = stat(path)?.isDirectory == false
    fun isDirectory(path: String): Boolean = stat(path)?.isDirectory == true
}

abstract class SystemIo {
    abstract suspend fun open(path: String, write: Boolean = false): FileSystemIo?
    abstract suspend fun listdir(path: String): Flow<String>
    abstract suspend fun mkdir(path: String): Boolean
    abstract suspend fun rmdir(path: String): Boolean
    abstract suspend fun unlink(path: String): Boolean
    abstract suspend fun stat(path: String): FileSystemIoStat?

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

abstract class FileSystemIo : Closeable {
    abstract suspend fun getLength(): Long
    abstract suspend fun setLength(value: Long): Unit
    abstract suspend fun getPosition(): Long
    abstract suspend fun setPosition(value: Long): Unit
    abstract suspend fun read(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int
    abstract suspend fun write(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Unit
    abstract override fun close(): Unit
}

abstract class SyncFileSystemIo : Closeable {
    abstract fun getLength(): Long
    abstract fun setLength(value: Long): Unit
    abstract fun getPosition(): Long
    abstract fun setPosition(value: Long): Unit
    abstract fun read(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Int
    abstract fun write(data: ByteArray, offset: Int = 0, size: Int = data.size - offset): Unit
    abstract override fun close(): Unit
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
