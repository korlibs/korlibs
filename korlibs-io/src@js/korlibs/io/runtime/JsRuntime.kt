package korlibs.io.runtime

import korlibs.io.*
import korlibs.io.file.*
import korlibs.io.file.std.*
import korlibs.io.lang.*
import korlibs.io.net.*
import korlibs.io.net.http.*
import korlibs.logger.*

abstract class JsRuntime {
    companion object {
        val logger = Logger("JsRuntime")
    }

    open val rawOsName: String = "unknown"

    open val rawPlatformName: String = when {
        isDenoJs -> "deno.js"
        isWeb -> "web.js"
        isNodeJs -> "node.js"
        isWorker -> "worker.js"
        isShell -> "shell.js"
        else -> "js"
    }
    open val isBrowser get() = false
    abstract fun existsSync(path: String): Boolean
    open fun currentDir(): String = "."

    open fun openVfs(path: String): VfsFile = TODO()
    open fun localStorage(): VfsFile = MemoryVfs()
    open fun tempVfs(): VfsFile = localVfs(Environment.tempPath)
    open suspend fun createClient(secure: Boolean): AsyncClient =
        error("AsyncClient is not supported on this JS runtime")
    open suspend fun createServer(port: Int, host: String, backlog: Int, secure: Boolean): AsyncServer =
        error("AsyncServer is not supported on JS browser")
    open fun createHttpClient(): HttpClient = TODO()
    open fun createHttpServer(): HttpServer = TODO()
}
