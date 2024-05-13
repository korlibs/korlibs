package korlibs.io.core

import korlibs.io.lang.*
import korlibs.io.stream.*
import korlibs.js.*
import korlibs.memory.*
import kotlinx.browser.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.khronos.webgl.*
import org.w3c.fetch.*

actual val defaultSyncSystemFs: SyncSystemFs = DenoSyncSystemFs()
actual val defaultSystemFs: SystemFs = DenoSystemFs()

class DenoSystemFs : SystemFs {
    override suspend fun open(path: String, write: Boolean): FileSystemIo? = TODO()
    override suspend fun listdir(path: String): Flow<String> = TODO("Not yet implemented")
    override suspend fun mkdir(path: String): Boolean = TODO("Not yet implemented")
    override suspend fun rmdir(path: String): Boolean = TODO("Not yet implemented")
    override suspend fun unlink(path: String): Boolean = TODO("Not yet implemented")
    override suspend fun stat(path: String): FileSystemIoStat? = TODO()
    override suspend fun realpath(path: String): String = path
    override suspend fun readlink(path: String): String? = path
    override suspend fun exec(commands: List<String>, envs: Map<String, String>, cwd: String): SystemIoProcess = TODO("Not yet implemented")
}

class DenoSyncSystemFs : SyncSystemFs {
    override fun open(path: String, write: Boolean): SyncFileSystemIo? = TODO("Not yet implemented")
    override fun listdir(path: String): Sequence<String> = TODO("Not yet implemented")
    override fun mkdir(path: String): Boolean = runCatching { Deno.mkdirSync(path) }.isSuccess
    override fun rmdir(path: String): Boolean = runCatching { Deno.removeSync(path) }.isSuccess
    override fun unlink(path: String): Boolean = runCatching { Deno.removeSync(path) }.isSuccess
    override fun stat(path: String): FileSystemIoStat? = TODO("Not yet implemented")
}
