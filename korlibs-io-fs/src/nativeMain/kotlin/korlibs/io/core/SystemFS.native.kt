@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class, UnsafeNumber::class, ExperimentalForeignApi::class)

package korlibs.io.core

import kotlinx.cinterop.*
import platform.posix.*

@OptIn(ExperimentalForeignApi::class)
abstract class SyncSystemFSNativeBase : SyncSystemFS {
    override fun stat(path: String): FileSystemFSStat? = memScoped {
        val st = alloc<stat>()
        if (stat(path, st.ptr) != 0) return@memScoped null
        FileSystemFSStat(
            name = path.substringAfterLast('/'),
            size = st.st_size.toLong(),
            mode = st.st_mode.toInt(),
            isDirectory = S_ISDIR(st.st_mode.convert()),
            //timeLastModification = st.
        )
    }
    override fun unlink(path: String): Boolean = platform.posix.unlink(path) == 0
    override fun rmdir(path: String): Boolean = platform.posix.rmdir(path) == 0
    override fun listdir(path: String): Sequence<String> = sequence {
        val dir = platform.posix.opendir(path)
        while (true) {
            val res = readdir(dir) ?: break
            yield(res.pointed.d_name.toKStringFromUtf8())
        }
        closedir(dir)
    }
    override fun open(path: String, write: Boolean): SyncFileSystemFS? {
        val file = fopen(path, if (write) "r+b" else "rb") ?: return null
        return createSyncFileSystemFS(file)
    }

    abstract fun createSyncFileSystemFS(file: CPointer<FILE>?): SyncFileSystemFSNativeBase

    private fun S_ISDIR(m: Int): Boolean = (((m) and S_IFMT) == S_IFDIR)
}

abstract class SyncFileSystemFSNativeBase(val file: CPointer<FILE>?) : SyncFileSystemFS() {
    val fd = fileno(file)

    abstract fun ftruncate64(len: Long)
    abstract fun ftell64(): Long
    abstract fun fseek64(pos: Long, type: Int): Long

    override fun getLength(): Long {
        val cur = ftell64()
        val len = fseek64(0L, SEEK_END)
        fseek64(cur, SEEK_SET)
        return len
    }

    override fun setLength(value: Long): Unit = run { ftruncate64(value.convert()) }
    override fun getPosition(): Long = ftell64()
    override fun setPosition(value: Long): Unit = run { fseek64(value.convert(), SEEK_SET) }
    override fun read(buffer: ByteArray, offset: Int, len: Int): Int = buffer.usePinned { fread(it.startAddressOf + offset, 1.convert(), len.convert(), file).toInt() }
    override fun write(buffer: ByteArray, offset: Int, len: Int): Unit = buffer.usePinned {
        fwrite(it.startAddressOf + offset, 1.convert(), len.convert(), file).toInt()
        Unit
    }

    override fun close(): Unit = run { fclose(file) }

    protected val Pinned<ByteArray>.startAddressOf: CPointer<ByteVar> get() = if (this.get().isNotEmpty()) this.addressOf(0) else emptyAddressByte
}

private val emptyAddressByte = ByteArray(1).pin().addressOf(0)
