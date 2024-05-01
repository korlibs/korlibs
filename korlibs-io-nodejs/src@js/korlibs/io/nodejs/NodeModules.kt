package korlibs.io.nodejs

@JsModule("node:http")
@JsNonModule
internal external object NodeHTTP {
}

@JsModule("node:url")
@JsNonModule
internal external object NodeURL {
}

@JsModule("node:net")
@JsNonModule
internal external object NodeNet {
}

@JsModule("node:child_process")
@JsNonModule
internal external object NodeChildProcess {
}

@JsModule("node:path")
@JsNonModule
internal external object NodePath {
    fun resolve(path: String): String
}

@JsModule("node:fs")
@JsNonModule
internal external object NodeFS {
    fun realpathSync(path: String): String
    fun existsSync(path: String): Boolean

    fun mkdir(path: String, mode: Int, callback: (Error?) -> Unit)
    fun rename(src: String, dst: String, callback: (Error?) -> Unit)
    fun unlink(path: String, callback: (Error?) -> Unit)
    fun rmdir(path: String, callback: (Error?) -> Unit)
    fun stat(path: String, callback: (Error?, NodeFileStat) -> Unit)
    fun chmod(path: String, value: Int, callback: (Error?) -> Unit)
    fun open(path: String, cmode: String, callback: (Error?, NodeFD?) -> Unit)
    fun read(fd: NodeFD?, buffer: NodeJsBuffer, offset: Int, len: Int, position: Double, callback: (Error?, Int, NodeJsBuffer) -> Unit)
    fun readdir(path: String, callback: (err: Error?, files: Array<String>) -> Unit)
    fun write(fd: NodeFD?, buffer: NodeJsBuffer, offset: Int, len: Int, position: Double, callback: (Error?, Int, NodeJsBuffer) -> Unit)
    fun ftruncate(fd: NodeFD?, length: Double, callback: (Error?) -> Unit)
    fun fstat(fd: NodeFD?, callback: (Error?, NodeFileStat) -> Unit)
    fun close(fd: NodeFD?, callback: (Error?) -> Unit)
}

internal external interface NodeFD

internal external interface NodeFileStat {
    val dev: Double
    val ino: Double
    val mode: Double
    val nlinks: Double
    val uid: Double
    val gid: Double
    val rdev: Double
    val size: Double
    val blkSize: Int
    val blocks: Double
    val atimeMs: Double
    val mtimeMs: Double
    val ctimeMs: Double
    fun isDirectory(): Boolean
    fun isFile(): Boolean
    fun isSocket(): Boolean
    fun isSymbolicLink(): Boolean
}
