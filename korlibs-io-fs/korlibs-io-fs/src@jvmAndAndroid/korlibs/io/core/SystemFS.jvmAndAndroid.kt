package korlibs.io.core

import korlibs.io.core.internal.InternalSystemFSShellArgs
import korlibs.io.stream.DequeSyncStream
import korlibs.io.stream.SyncOutputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile
import java.net.*
import java.util.concurrent.CompletableFuture
import kotlin.io.path.pathString
import kotlin.io.path.readSymbolicLink
import kotlin.reflect.*

actual val defaultSyncSystemFS: SyncSystemFS = JvmSyncSystemFS
actual val defaultSystemFS: SystemFS by lazy { JvmSyncSystemFS.toAsync(Dispatchers.IO) }

object JvmSyncSystemFS : SyncSystemFS {
    override val fileSeparatorChar: Char get() = File.separatorChar
    override val pathSeparatorChar: Char get() = File.pathSeparatorChar
    override fun realpath(path: String): String =
        File(path).canonicalPath
        //File(path).toPath().toRealPath().pathString

    override fun readlink(path: String): String? =
        kotlin.runCatching { File(path).toPath().readSymbolicLink().pathString }.getOrNull()

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
        val cmdAndArgs = commands
        checkExecFolder(cwd, cmdAndArgs)
        val actualCmd = InternalSystemFSShellArgs.buildShellExecCommandLineArrayForProcessBuilder(cmdAndArgs)
        val pb = ProcessBuilder(actualCmd)
        pb.environment().putAll(envs)
        pb.directory(File(cwd).absoluteFile)
        val stdin = DequeSyncStream()
        val stdout = DequeSyncStream()
        val stderr = DequeSyncStream()

        val p = pb.start()

        val exitValue = CompletableFuture<Int>()

        CoroutineScope(Dispatchers.IO).launch {
            var closing = false
            val temp = ByteArray(1024)
            while (true) {
                if (!p.isAliveJre7) closing = true

                // Copy stdin
                stdin.read(temp, 0, minOf(temp.size.toLong(), stdin.availableRead).toInt()).also { readCount ->
                    if (readCount > 0) p.outputStream.write(temp, 0, readCount)
                }
                p.inputStream.copyAvailableChunk(stdout, temp, readRest = closing)
                p.errorStream.copyAvailableChunk(stderr, temp, readRest = closing)

                if (closing) break
                delay(1L)
            }
            p.waitFor()
            //handler.onCompleted(p.exitValue())
            exitValue.complete(p.exitValue())
        }

        return object : SyncSystemFSProcess(stdin, stdout, stderr) {
            override val exitCode: Int get() = exitValue.join()

            override fun close() {
                stdin.close()
                p.destroy()
            }
        }
    }

    private fun InputStream.copyAvailableChunk(out: SyncOutputStream, temp: ByteArray, readRest: Boolean): Int {
        var readCount = 0
        while (true) {
            val read = this.read(temp, 0, if (readRest) temp.size else maxOf(0, this.available()))
            if (read <= 0) break
            //println("copyAvailableChunk: read=$read, '${temp.copyOf(read).decodeToString()}', readRest=$readRest")
            out.write(temp, 0, read)
            readCount++
        }
        return readCount
    }

    override fun open(path: String, write: Boolean): SyncFileSystemFS? {
        val file = File(path).takeIf { write || it.exists() } ?: return null
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

    private fun getResourceURL(clazz: KClass<*>?, path: String): URL {
        return (clazz?.java?.classLoader ?: ClassLoader.getSystemClassLoader()).getResource(path) ?: error("Can't find resource '$path'")
    }

    override fun getResourceLength(path: String, clazz: KClass<*>?): Long {
        return getResourceURL(clazz, path).openStream().use { it.available().toLong() }
    }

    override fun getResourceBytes(path: String, clazz: KClass<*>?): ByteArray {
        return getResourceURL(clazz, path).readBytes()
    }
}

private val Process.isAliveJre7: Boolean
    get() = try {
        exitValue()
        false
    } catch (e: IllegalThreadStateException) {
        true
    }
