package korlibs.io.file.std

import korlibs.io.core.SystemFS
import korlibs.io.file.*
import korlibs.io.stream.AsyncInputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

abstract class LocalVfs : Vfs() {
    companion object {}

    override suspend fun getAttributes(path: String): List<Attribute> {
        val stat = stat(path)
        if (!stat.exists) return emptyList()
        return listOf(UnixPermissions(stat.mode))
    }

    override suspend fun exec(
        path: String,
        cmdAndArgs: List<String>,
        env: Map<String, String>,
        handler: VfsProcessHandler
    ): Int {
        val res = SystemFS.exec(cmdAndArgs, env, path)
        var completed = false

        val pipeJob = CoroutineScope(coroutineContext).launch {
            val temp = ByteArray(1024)

            suspend fun pipeChunk(stream: AsyncInputStream, out: suspend (ByteArray) -> Unit): Boolean {
                val read = stream.read(temp, 0, temp.size)
                if (read > 0) out(temp.copyOf(read))
                return read > 0
            }

            while (true) {
                val stdout = pipeChunk(res.stdout) { handler.onOut(it) }
                val stderr = pipeChunk(res.stderr) { handler.onErr(it) }
                if (!stdout && !stderr && completed) break
                delay(1L)
            }
        }
        return res.exitCode().also {
            completed = true
            pipeJob.join()
        }
    }

    override fun toString(): String = "LocalVfs"
}
