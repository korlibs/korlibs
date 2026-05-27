package korlibs.io.core

import korlibs.io.async.AsyncCloseable
import korlibs.io.lang.FileNotFoundException
import korlibs.io.stream.AsyncInputStream
import korlibs.io.stream.AsyncOutputStream
import korlibs.io.stream.SyncInputStream
import korlibs.io.stream.SyncOutputStream
import korlibs.io.stream.toAsync
import korlibs.math.toIntSafe
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal expect val defaultSyncSystemFS: SyncSystemFS
internal expect val defaultSystemFS: SystemFS

object NullSyncSystemFS : SyncSystemFS {
    override fun open(path: String, write: Boolean): SyncFileSystemFS? = TODO("Not yet implemented")
    override fun listdir(path: String): Sequence<String> = TODO("Not yet implemented")
    override fun mkdir(path: String): Boolean = TODO("Not yet implemented")
    override fun rmdir(path: String): Boolean = TODO("Not yet implemented")
    override fun unlink(path: String): Boolean = TODO("Not yet implemented")
    override fun stat(path: String): FileSystemFSStat? = TODO("Not yet implemented")
    override fun realpath(path: String): String = TODO("Not yet implemented")
    override fun readlink(path: String): String? = TODO("Not yet implemented")
    override fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SyncSystemFSProcess = TODO("Not yet implemented")
}
val NullSystemFS: SystemFS = NullSyncSystemFS.toAsync(Dispatchers.Unconfined)

interface SyncSystemFS {
    companion object : SyncSystemFS by defaultSyncSystemFS

    val fileSeparatorChar: Char get() = '/'
    val pathSeparatorChar: Char get() = ':'

    fun getcwd(): String = "."

    fun open(path: String, write: Boolean = false): SyncFileSystemFS?
    fun listdir(path: String): Sequence<String>
    fun mkdir(path: String): Boolean
    fun rmdir(path: String): Boolean
    fun unlink(path: String): Boolean
    fun stat(path: String): FileSystemFSStat?

    fun exists(path: String): Boolean = stat(path) != null
    fun isFile(path: String): Boolean = stat(path)?.isDirectory == false
    fun isDirectory(path: String): Boolean = stat(path)?.isDirectory == true

    fun realpath(path: String): String = TODO()
    fun readlink(path: String): String? = TODO()
    fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SyncSystemFSProcess = TODO()

    fun getResourceLength(path: String, clazz: KClass<*>? = null): Long = TODO()
    fun getResourceBytes(path: String, clazz: KClass<*>? = null): ByteArray = TODO()
}

fun SyncSystemFS.size(path: String): Long? = stat(path)?.size
fun SyncSystemFS.writeBytes(path: String, bytes: ByteArray) {
    val file = open(path, write = true) ?: error("Can't open '$path' for writing")
    file.use { it.write(bytes, 0, bytes.size) }
}
fun SyncSystemFS.readBytes(path: String): ByteArray {
    val file = open(path, write = false) ?: error("Can't open '$path' for reading")
    return file.use { fd ->
        ByteArray(fd.getLength().toIntSafe()).also { it.copyOf(fd.read(it)) }
    }
}

fun SyncSystemFS.checkExecFolder(path: String, cmdAndArgs: List<String>) {
    if (stat(path)?.isDirectory != true)
        throw FileNotFoundException("'$path' is not a directory, to execute '${cmdAndArgs.first()}'")
}

open class SyncSystemFSProcess(
    val stdin: SyncOutputStream,
    val stdout: SyncInputStream,
    val stderr: SyncInputStream,
) : AutoCloseable {
    open val exitCode: Int get() = Int.MIN_VALUE
    open fun destroy(): Unit = close()
    override fun close() = Unit
}

interface SystemFS {
    companion object : SystemFS by defaultSystemFS

    suspend fun open(path: String, write: Boolean = false): FileSystemFS?
    suspend fun listdir(path: String): Flow<String>
    suspend fun mkdir(path: String): Boolean
    suspend fun rmdir(path: String): Boolean
    suspend fun unlink(path: String): Boolean
    suspend fun stat(path: String): FileSystemFSStat?

    suspend fun exists(path: String): Boolean = stat(path) != null
    suspend fun isFile(path: String): Boolean = stat(path)?.isDirectory == false
    suspend fun isDirectory(path: String): Boolean = stat(path)?.isDirectory == true

    suspend fun realpath(path: String): String
    suspend fun readlink(path: String): String?
    suspend fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SystemFSProcess

    suspend fun getResourceLength(path: String, clazz: KClass<*>? = null): Long = TODO()
    suspend fun getResourceBytes(path: String, clazz: KClass<*>? = null): ByteArray = TODO()
}

open class SystemFSProcess(
    val stdin: AsyncOutputStream,
    val stdout: AsyncInputStream,
    val stderr: AsyncInputStream,
) : AsyncCloseable {
    open suspend fun exitCode(): Int = Int.MIN_VALUE
    open suspend fun destroy(): Unit = close()
    override suspend fun close() = Unit
}

data class FileSystemFSStat(
    val name: String,
    val size: Long = 0L,
    val mode: Int = 511, // 0o777
    val isDirectory: Boolean = false,
    val timeLastModification: Long = 0L,
    val timeCreation: Long = timeLastModification,
    val timeLastAccess: Long = timeLastModification,
    val inode: Long = 0L,
)

abstract class FileSystemFS : AsyncCloseable, AsyncInputStream, AsyncOutputStream {
    abstract suspend fun getLength(): Long
    abstract suspend fun setLength(value: Long): Unit
    abstract suspend fun getPosition(): Long
    abstract suspend fun setPosition(value: Long): Unit
    abstract override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int
    abstract override suspend fun write(buffer: ByteArray, offset: Int, len: Int): Unit
    abstract override suspend fun close(): Unit
}

abstract class SyncFileSystemFS : AutoCloseable, SyncInputStream, SyncOutputStream {
    abstract fun getLength(): Long
    abstract fun setLength(value: Long): Unit
    abstract fun getPosition(): Long
    abstract fun setPosition(value: Long): Unit
    abstract override fun read(buffer: ByteArray, offset: Int, len: Int): Int
    abstract override fun write(buffer: ByteArray, offset: Int, len: Int): Unit
    abstract override fun close(): Unit
}

fun SyncSystemFS.toAsync(ioDispatcher: CoroutineDispatcher?): SystemFS {
    val sync = this@toAsync
    return object : SystemFS {
        private suspend inline fun <T> doSyncIo(crossinline block: () -> T): T = doIo(ioDispatcher, block)

        override suspend fun open(path: String, write: Boolean): FileSystemFS? {
            val coroutineContext = coroutineContext
            val io = doSyncIo { sync.open(path, write) } ?: return null
            return object : FileSystemFS() {
                override suspend fun getLength(): Long = doSyncIo { io.getLength() }
                override suspend fun setLength(value: Long) = doSyncIo { io.setLength(value) }
                override suspend fun getPosition(): Long = doSyncIo { io.getPosition() }
                override suspend fun setPosition(value: Long) = doSyncIo { io.setPosition(value) }
                override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = doSyncIo { io.read(buffer, offset, len) }
                override suspend fun write(buffer: ByteArray, offset: Int, len: Int) = doSyncIo { io.write(buffer, offset, len) }
                override suspend fun close(): Unit { CoroutineScope(coroutineContext).launch { io.close() } }
            }
        }

        override suspend fun listdir(path: String): Flow<String> = doSyncIo { sync.listdir(path).asFlow() }
        override suspend fun mkdir(path: String) = doSyncIo { sync.mkdir(path) }
        override suspend fun unlink(path: String) = doSyncIo { sync.unlink(path) }
        override suspend fun rmdir(path: String) = doSyncIo { sync.rmdir(path) }
        override suspend fun stat(path: String): FileSystemFSStat? = doSyncIo { sync.stat(path) }
        override suspend fun realpath(path: String): String = doSyncIo { sync.realpath(path) }
        override suspend fun readlink(path: String): String? = doSyncIo { sync.readlink(path) }
        override suspend fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SystemFSProcess = doSyncIo {
            val process = sync.exec(commands, envs, cwd)
            object : SystemFSProcess(process.stdin.toAsync(ioDispatcher), process.stdout.toAsync(ioDispatcher), process.stderr.toAsync(ioDispatcher)) {
                override suspend fun exitCode(): Int = doSyncIo { process.exitCode }
                override suspend fun destroy() = doIo(ioDispatcher) { process.destroy() }
                override suspend fun close() = doIo(ioDispatcher) { process.close() }
            }
        }

        override suspend fun getResourceLength(path: String, clazz: KClass<*>?): Long = doSyncIo { sync.getResourceLength(path, clazz) }
        override suspend fun getResourceBytes(path: String, clazz: KClass<*>?): ByteArray = doSyncIo { sync.getResourceBytes(path, clazz) }
    }
}

private suspend inline fun <T> doIo(dispatcher: CoroutineDispatcher? = null, crossinline block: () -> T): T = when {
    dispatcher != null -> withContext(dispatcher) { block() }
    else -> block()
}
private inline fun launchIo(dispatcher: CoroutineDispatcher?, crossinline block: () -> Unit): Unit {
    when {
        dispatcher != null -> CoroutineScope(dispatcher).launch { block() }
        else -> block()
    }
}
