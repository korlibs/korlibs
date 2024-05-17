package korlibs.io.core

import kotlinx.coroutines.*
import java.io.*

actual val defaultSyncSystemFS: SyncSystemFS = JvmSyncSystemFS
actual val defaultSystemFS: SystemFS = SyncSystemFS.toAsync(Dispatchers.IO)

object JvmSyncSystemFS : SyncSystemFS {
    override val fileSeparatorChar: Char get() = File.separatorChar
    override val pathSeparatorChar: Char get() = File.pathSeparatorChar
    override fun realpath(path: String): String {
        TODO("Not yet implemented")
    }

    override fun readlink(path: String): String? {
        TODO("Not yet implemented")
    }

    override fun mkdir(path: String) = File(path).mkdir()
    override fun rmdir(path: String) = File(path).takeIf { it.isDirectory }?.delete() == true
    override fun unlink(path: String) = File(path).takeIf { !it.isDirectory }?.delete() == true
    override fun listdir(path: String): Sequence<String> = (File(path).list() ?: emptyArray<String>()).asSequence()
    override fun stat(path: String): FileSystemFSStat? {
        val file = File(path).takeIf { it.exists() } ?: return null
        return FileSystemFSStat(
            name = file.name,
            size = file.length(),
            timeLastModification = file.lastModified(),
            isDirectory = file.isDirectory,
        )
    }

    override fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SyncSystemFSProcess {
        TODO("Not yet implemented")
    }

    override fun open(path: String, write: Boolean): SyncFileSystemFS? {
        val file = File(path).takeIf { it.exists() } ?: return null
        val s = RandomAccessFile(file, if (write) "rw" else "r")
        return object : SyncFileSystemFS() {
            override fun getLength(): Long = s.length()
            override fun setLength(value: Long) = s.setLength(value)
            override fun getPosition(): Long = s.filePointer
            override fun setPosition(value: Long) = s.seek(value)
            override fun read(buffer: ByteArray, offset: Int, len: Int): Int = s.read(buffer, offset, len)
            override fun write(buffer: ByteArray, offset: Int, len: Int): Unit = s.write(buffer, offset, len)
            override fun close() = s.close()
        }
    }
}
