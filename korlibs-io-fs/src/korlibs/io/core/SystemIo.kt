package korlibs.io.core

import korlibs.datastructure.closeable.*
import korlibs.io.async.*
import korlibs.io.stream.*
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
    override fun realpath(path: String): String = TODO("Not yet implemented")
    override fun readlink(path: String): String? = TODO("Not yet implemented")
    override fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SyncSystemIoProcess = TODO("Not yet implemented")
}
val NullSystemIo: SystemIo = NullSyncSystemIo.toAsync(Dispatchers.Unconfined)

abstract class SyncSystemIo {
    open val fileSeparatorChar: Char = '/'
    open val pathSeparatorChar: Char = ':'

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

    abstract fun realpath(path: String): String
    abstract fun readlink(path: String): String?
    abstract fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SyncSystemIoProcess
}

open class SyncSystemIoProcess(
    val stdin: SyncOutputStream,
    val stdout: SyncInputStream,
    val stderr: SyncInputStream,
) : Closeable {
    open val exitCode: Int get() = Int.MIN_VALUE
    open fun destroy(): Unit = close()
    override fun close() = Unit
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

    abstract suspend fun realpath(path: String): String
    abstract suspend fun readlink(path: String): String?
    abstract suspend fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SystemIoProcess
}

open class SystemIoProcess(
    val stdin: AsyncOutputStream,
    val stdout: AsyncInputStream,
    val stderr: AsyncInputStream,
) : AsyncCloseable {
    open suspend fun exitCode(): Int = Int.MIN_VALUE
    open suspend fun destroy(): Unit = close()
    override suspend fun close() = Unit
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

abstract class FileSystemIo : AsyncCloseable, AsyncInputStream, AsyncOutputStream {
    abstract suspend fun getLength(): Long
    abstract suspend fun setLength(value: Long): Unit
    abstract suspend fun getPosition(): Long
    abstract suspend fun setPosition(value: Long): Unit
    abstract override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int
    abstract override suspend fun write(buffer: ByteArray, offset: Int, len: Int): Unit
    abstract override suspend fun close(): Unit
}

abstract class SyncFileSystemIo : Closeable, SyncInputStream, SyncOutputStream {
    abstract fun getLength(): Long
    abstract fun setLength(value: Long): Unit
    abstract fun getPosition(): Long
    abstract fun setPosition(value: Long): Unit
    abstract override fun read(buffer: ByteArray, offset: Int, len: Int): Int
    abstract override fun write(buffer: ByteArray, offset: Int, len: Int): Unit
    abstract override fun close(): Unit
}

fun SyncSystemIo.toAsync(ioDispatcher: CoroutineDispatcher?): SystemIo {
    val sync = this@toAsync
    return object : SystemIo() {
        private suspend inline fun <T> doSyncIo(crossinline block: () -> T): T = doIo(ioDispatcher, block)

        override suspend fun open(path: String, write: Boolean): FileSystemIo? {
            val io = doSyncIo { sync.open(path, write) } ?: return null
            return object : FileSystemIo() {
                override suspend fun getLength(): Long = doSyncIo { io.getLength() }
                override suspend fun setLength(value: Long) = doSyncIo { io.setLength(value) }
                override suspend fun getPosition(): Long = doSyncIo { io.getPosition() }
                override suspend fun setPosition(value: Long) = doSyncIo { io.setPosition(value) }
                override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = doSyncIo { io.read(buffer, offset, len) }
                override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = doSyncIo { io.write(buffer, offset, len) }

                override suspend fun close() = doSyncIo { io.close() }
            }
        }

        override suspend fun listdir(path: String): Flow<String> = doSyncIo { sync.listdir(path).asFlow() }
        override suspend fun mkdir(path: String) = doSyncIo { sync.mkdir(path) }
        override suspend fun unlink(path: String) = doSyncIo { sync.unlink(path) }
        override suspend fun rmdir(path: String) = doSyncIo { sync.rmdir(path) }
        override suspend fun stat(path: String): FileSystemIoStat? = doSyncIo { sync.stat(path) }
        override suspend fun realpath(path: String): String = doSyncIo { sync.realpath(path) }
        override suspend fun readlink(path: String): String? = doSyncIo { sync.readlink(path) }
        override suspend fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SystemIoProcess = doSyncIo {
            val process = sync.exec(commands, envs, cwd)
            object : SystemIoProcess(process.stdin.toAsync(ioDispatcher), process.stdout.toAsync(ioDispatcher), process.stderr.toAsync(ioDispatcher)) {
                override suspend fun exitCode(): Int = doSyncIo { process.exitCode }
                override suspend fun destroy() = doIo(ioDispatcher) { process.destroy() }
                override suspend fun close() = doIo(ioDispatcher) { process.close() }
            }
        }
    }
}

private suspend inline fun <T> doIo(dispatcher: CoroutineDispatcher? = null, crossinline block: () -> T): T = when {
    dispatcher != null -> withContext(dispatcher) { block() }
    else -> block()
}

private fun SyncInputStream.toAsync(dispatcher: CoroutineDispatcher? = null): AsyncInputStream = object : AsyncInputStreamWithLength {
    val sync = this@toAsync
    private suspend inline fun <T> doIo(crossinline block: () -> T): T = doIo(dispatcher, block)
    override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = doIo { sync.read(buffer, offset, len) }
    override suspend fun close(): Unit = doIo { (sync as? Closeable)?.close() }
    override suspend fun getPosition(): Long = doIo { (sync as? SyncPositionStream)?.position } ?: super.getPosition()
    override suspend fun getLength(): Long = doIo { (sync as? SyncLengthStream)?.length } ?: super.getLength()
}

private fun SyncOutputStream.toAsync(dispatcher: CoroutineDispatcher? = null): AsyncOutputStream = object : AsyncOutputStream {
    val sync = this@toAsync
    private suspend inline fun <T> doIo(crossinline block: () -> T): T = doIo(dispatcher, block)
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = doIo { sync.write(buffer, offset, len) }
    override suspend fun close(): Unit = doIo { (sync as? Closeable)?.close() }
}
