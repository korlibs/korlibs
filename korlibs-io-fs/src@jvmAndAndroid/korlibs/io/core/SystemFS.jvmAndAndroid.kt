package korlibs.io.core

import korlibs.io.stream.DequeSyncStream
import korlibs.io.stream.SyncOutputStream
import korlibs.platform.Platform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile
import java.util.concurrent.CompletableFuture

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
        val cmdAndArgs = commands
        checkExecFolder(cwd, cmdAndArgs)
        val actualCmd = ShellArgs.buildShellExecCommandLineArrayForProcessBuilder(cmdAndArgs)
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


// @TODO: DRY, try to use buildShellExecCommandLineArray
// @TODO: NodeJS fails on windows with special characters like & echo &
private object ShellArgs {
    fun buildShellExecCommandLineForPopen(cmdAndArgs: List<String>): String = buildShellExecCommandLine(cmdAndArgs)
    fun buildShellExecCommandLineArrayForProcessBuilder(cmdAndArgs: List<String>): List<String> = buildShellExecCommandLineArray(cmdAndArgs)
    fun buildShellExecCommandLineArrayForExecl(cmdAndArgs: List<String>): List<String> = buildShellExecCommandLineArray(cmdAndArgs)
    fun buildShellExecCommandLineArrayForNodeSpawn(cmdAndArgs: List<String>): List<String> = (cmdAndArgs)

    fun buildShellExecCommandLineArray(cmdAndArgs: List<String>): List<String> = when {
        Platform.isWindows -> listOf("cmd", "/c", ShellArgs.escapeshellCommandWin(cmdAndArgs))
        Platform.isLinux -> listOf("/bin/sh", "-c", cmdAndArgs.joinToString(" ") { ShellArgs.escapeshellargUnix(it) })
        //OS.isLinux -> listOf("/bin/sh", "-c", "\"" + cmdAndArgs.joinToString(" ") { ShellArgs.escapeshellargUnix(it) } + "\"")
        //OS.isLinux -> listOf("/bin/sh", "-c", "'" + cmdAndArgs.joinToString(" ") { ShellArgs.escapeshellargUnix(it) }.replace("'", "'\"'\"'") + "'")
        else -> cmdAndArgs
    }

    fun buildShellExecCommandLine(cmdAndArgs: List<String>): String = when {
        Platform.isWindows -> cmdAndArgs.joinToString(" ") { ShellArgs.escapeshellargWin(it) }
        else -> "/bin/sh -c '" + cmdAndArgs.joinToString(" ") { ShellArgs.escapeshellargUnix(it) }.replace("'", "'\"'\"'") + "'"
    }

    fun escapeshellCommandUnix(args: List<String>): String {
        return escapeshellargUnix(args.joinToString(" ") { escapeshellargUnix(it) })
    }

    fun escapeshellargUnix(str: String): String {
        return buildString {
            append("'")
            for (c in str) {
                when (c) {
                    '\n' -> append("\\n")
                    '\r' -> append("\\r")
                    '\t' -> append("\\t")
                    '\\' -> append("\\\\")
                    '\'' -> append("'\"'\"'") // https://stackoverflow.com/questions/1250079/how-to-escape-single-quotes-within-single-quoted-strings
                    else -> append(c)
                }
            }
            append("'")
        }
    }

    fun escapeshellCommandWin(args: List<String>): String {
        return "\"" + args.joinToString(" ") { escapeshellargWin(it) } + "\""
    }

    // https://sourcedaddy.com/windows-7/escaping-special-characters.html
    // https://stackoverflow.com/questions/17319224/escaping-illegal-characters-in-params
    fun escapeshellargWin(str: String): String {
        return buildString {
            for (c in str) {
                when (c) {
                    '<', '>', '(', ')', '&', '|', ',', ';', '^', '"', '\'', ' ', '\n', '\r', '\t' -> append('^')
                }
                append(c)
            }
        }
    }
}

private val Process.isAliveJre7: Boolean
    get() = try {
        exitValue()
        false
    } catch (e: IllegalThreadStateException) {
        true
    }
