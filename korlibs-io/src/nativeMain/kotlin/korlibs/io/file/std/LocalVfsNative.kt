@file:OptIn(ExperimentalForeignApi::class, ExperimentalStdlibApi::class)

package korlibs.io.file.std

import korlibs.io.async.CIO
import korlibs.io.file.VfsFile
import korlibs.io.file.VfsOpenMode
import korlibs.io.file.VfsProcessHandler
import korlibs.io.file.VfsStat
import korlibs.io.file.baseName
import korlibs.io.file.pathInfo
import korlibs.io.lang.DummyCloseable
import korlibs.io.lang.Environment
import korlibs.io.lang.FileNotFoundException
import korlibs.io.lang.IOException
import korlibs.io.lang.OutOfBoundsException
import korlibs.io.lang.tempPath
import korlibs.io.posix.posixChmod
import korlibs.io.posix.posixFclose
import korlibs.io.posix.posixFopen
import korlibs.io.posix.posixFread
import korlibs.io.posix.posixFseek
import korlibs.io.posix.posixFtell
import korlibs.io.posix.posixFwrite
import korlibs.io.posix.posixIsCaseSensitive
import korlibs.io.posix.posixMkdir
import korlibs.io.posix.posixRealpath
import korlibs.io.posix.posixStat
import korlibs.io.posix.posixTruncate
import korlibs.io.process.posixExec
import korlibs.io.stream.AsyncStream
import korlibs.io.stream.AsyncStreamBase
import korlibs.io.stream.toAsyncStream
import korlibs.time.DateTime
import kotlin.math.max
import kotlin.math.min
import kotlin.native.concurrent.ThreadLocal
import kotlin.time.measureTime
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import platform.posix.FILE
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.posix.closedir
import platform.posix.opendir
import platform.posix.posix_errno
import platform.posix.readdir
import platform.posix.strerror

@ThreadLocal
val tmpdir: String by lazy { Environment.tempPath }

@ThreadLocal
var customCwd: String? = null

@ThreadLocal
val nativeCwd by lazy { StandardPaths.cwd }
val cwd: String get() = customCwd ?: nativeCwd

//@ThreadLocal
//val cwdVfs: VfsFile by lazy { DynamicRootVfs(rootLocalVfsNative) { cwd } }
val cwdVfs: VfsFile get() = rootLocalVfsNative[cwd]

@ThreadLocal
actual val standardVfs: StandardVfs = object : StandardVfs() {
    override val resourcesVfs: VfsFile by lazy { applicationDataVfs.jail() }
    override val rootLocalVfs: VfsFile get() = applicationDataVfs
}

@ThreadLocal
actual val cacheVfs: VfsFile by lazy { MemoryVfs() }

@ThreadLocal
actual val tempVfs: VfsFile by lazy { jailedLocalVfs(tmpdir) }

actual val applicationVfs: VfsFile get() = rootLocalVfsNative[customCwd ?: StandardPaths.resourcesFolder]
actual val applicationDataVfs: VfsFile get() = applicationVfs
actual val externalStorageVfs: VfsFile get() = applicationVfs
actual val userHomeVfs: VfsFile get() = jailedLocalVfs(StandardPaths.userHome)

val rootLocalVfsNative by lazy { LocalVfsNative(async = true) }
val rootLocalVfsNativeSync by lazy { LocalVfsNative(async = false) }

actual fun localVfs(path: String, async: Boolean): VfsFile =
    (if (async) rootLocalVfsNative else rootLocalVfsNativeSync)[path]

expect open class LocalVfsNative(async: Boolean = true) : LocalVfsNativeBase

open class LocalVfsNativeBase(val async: Boolean = true) : LocalVfs() {
    val that get() = this
    override val absolutePath: String get() = ""

    override suspend fun isCaseSensitive(path: String): Boolean = posixIsCaseSensitive()

    fun resolveOrError(path: String): Pair<String, Exception?> {
        val rpath = path
        val pathExpected = rpath.pathInfo
        val pathResolved = posixRealpath(rpath).pathInfo
        if (pathExpected.baseName != pathResolved.baseName) {
            return rpath to IOException("File case not matched pathExpected=$pathExpected != pathResolved=$pathResolved")
        }
        return rpath to null
    }

    fun resolve(path: String): String {
        val (rpath, exception) = resolveOrError(path)
        if (exception != null) throw exception
        return rpath
    }

    suspend inline fun <R> doIo(crossinline func: () -> R): R =
        if (async) withContext(Dispatchers.CIO) { func() } else func()

    override suspend fun exec(
        path: String, cmdAndArgs: List<String>, env: Map<String, String>, handler: VfsProcessHandler
    ): Int {
        checkExecFolder(path, cmdAndArgs)
        return posixExec(path, cmdAndArgs, env, handler)
    }

    override suspend fun getAttributes(path: String): List<Attribute> {
        return listOf(UnixPermissions(posixStat(path)!!.mode))
    }

    override suspend fun chmod(path: String, mode: UnixPermissions) {
        posixChmod(path, mode.rbits)
    }

    override suspend fun readRange(path: String, range: LongRange): ByteArray {
        val rpath = resolve(path)

        return doIo {
            val fd = posixFopen(rpath, "rb")
            if (fd != null) {
                posixFseek(fd, 0L.convert(), SEEK_END)
                //val length = ftell(fd).toLong() // @TODO: Kotlin native bug?
                val length: Long = posixFtell(fd).convert()

                val start = min(range.start, length)
                val end = min(range.endInclusive, length - 1) + 1
                val totalRead = (end - start).toInt()

                //println("range=$range")
                //println("length=$length")
                //println("start=$start")
                //println("end=$end")
                //println("totalRead=$totalRead")

                val byteArray = ByteArray(totalRead)
                val finalRead = if (byteArray.isNotEmpty()) {
                    byteArray.usePinned { pin ->
                        posixFseek(fd, start.convert(), SEEK_SET)
                        posixFread(pin.addressOf(0), 1.convert(), totalRead.convert(), fd).toInt()
                    }
                } else {
                    0
                }

                //println("finalRead=$finalRead")

                posixFclose(fd)
                if (finalRead != totalRead) byteArray.copyOf(finalRead) else byteArray
            } else {
                null
            }
        } ?: throw FileNotFoundException("Can't open '${rpath}' for reading")
    }

    override suspend fun open(path: String, mode: VfsOpenMode): AsyncStream {
        val rpath = resolve(path)

        val (initialFd, initialFileLength) = doIo {
            val fd = posixFopen(rpath, mode.cmode)
            val len = if (fd != null) {
                posixFseek(fd, 0L.convert(), SEEK_END)
                val end = posixFtell(fd)
                posixFseek(fd, 0L.convert(), SEEK_SET)
                end.toLong()
            } else {
                0L
            }
            Pair(fd, len)
        }
        val errno = posix_errno()
        //if (initialFd == null || errno != 0) {
        if (initialFd == null) {
            val errstr = strerror(errno)?.toKString()
            throw FileNotFoundException("Can't open '$rpath' with mode '${mode.cmode}' errno=$errno, errstr=$errstr")
        }

        return object : AsyncStreamBase() {
            var fd: CPointer<FILE>? = initialFd
            var currentFileLength: Long = initialFileLength

            fun checkFd() {
                if (fd == null) error("Error with file '$rpath'")
            }

            internal suspend fun fileTransfer(
                fd: CPointer<FILE>,
                position: Long,
                buffer: ByteArray,
                offset: Int,
                len: Int,
                write: Boolean
            ): Long {
                if (len <= 0) return 0L
                if (offset + len > buffer.size) throw OutOfBoundsException(offset + len)
                var transferredCount: Long = 0L
                //fileWrite(fd, position, buffer.copyOfRange(offset, offset + len))
                val time = measureTime {
                    transferredCount = buffer.usePinned { bufferPin ->
                        doIo {
                            posixFseek(fd, position.convert(), SEEK_SET)
                            if (write) {
                                //platform.posix.fwrite(buffer, 1.convert(), len.convert(), fd).toLong()
                                posixFwrite(
                                    bufferPin.addressOf(offset),
                                    len.convert(),
                                    1.convert(),
                                    fd
                                ).toLong() // Write it at once (len, 1)
                                len.toLong()
                            } else {
                                posixFread(
                                    bufferPin.addressOf(offset),
                                    1.convert(),
                                    len.convert(),
                                    fd
                                ).toLong() // Allow to read less values (1, len)
                            }
                        }
                    }
                }
                //println("fileTransfer: len=$len, write=$write, transferred=$transferredCount, async=$async, time=$time")
                return transferredCount
            }

            override suspend fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
                checkFd()
                return fileTransfer(fd!!, position, buffer, offset, len, write = false).toInt()
            }

            override suspend fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
                checkFd()
                currentFileLength = max(currentFileLength, position + len)
                fileTransfer(fd!!, position, buffer, offset, len, write = true)
            }

            override suspend fun setLength(value: Long) {
                checkFd()
                doIo {
                    posixTruncate(rpath, value.convert())
                    Unit
                }
                currentFileLength = value
            }

            override suspend fun getLength(): Long = currentFileLength
            override suspend fun close() {
                if (fd != null) {
                    doIo {
                        posixFclose(fd)
                        Unit
                    }
                }
                fd = null
                currentFileLength = 0L
            }

            override fun toString(): String = "$that($path)"
        }.toAsyncStream()
    }

    override suspend fun setSize(path: String, size: Long) {
        posixTruncate(resolve(path), size.convert())
    }

    override suspend fun stat(path: String): VfsStat {
        val (rpath, exception) = resolveOrError(path)
        if (exception == null) {
            val statInfo = posixStat(rpath)
            if (statInfo != null) {
                return createExistsStat(
                    rpath, statInfo.isDirectory, statInfo.size, mode = statInfo.mode,
                    createTime = statInfo.timeCreated,
                    modifiedTime = statInfo.timeModified,
                    lastAccessTime = statInfo.timeLastAccess,
                )
            }
        }
        return createNonExistsStat(rpath, exception = exception)
    }

    override suspend fun listFlow(path: String) = flow {
        val dir = opendir(resolve(path))
        if (dir != null) {
            try {
                while (true) {
                    val dent = readdir(dir) ?: break
                    val name = dent.pointed.d_name.toKString()
                    if (name != "." && name != "..") {
                        emit(file("$path/$name"))
                    }
                }
            } finally {
                closedir(dir)
            }
        }
    }.flowOn(Dispatchers.CIO)

    override suspend fun mkdir(path: String, attributes: List<Attribute>): Boolean =
        posixMkdir(resolve(path), "0777".toInt(8).convert()) == 0

    override suspend fun touch(path: String, time: DateTime, atime: DateTime) {
        // @TODO:
        println("TODO:LocalVfsNative.touch")
    }

    override suspend fun delete(path: String): Boolean = platform.posix.unlink(resolve(path)) == 0
    override suspend fun rmdir(path: String): Boolean = platform.posix.rmdir(resolve(path)) == 0

    override suspend fun rename(src: String, dst: String): Boolean = run {
        platform.posix.rename(resolve(src), resolve(dst)) == 0
    }

    override suspend fun watch(path: String, handler: (FileEvent) -> Unit): AutoCloseable {
        // @TODO:
        println("TODO:LocalVfsNative.watch")
        return DummyCloseable
    }

    override fun toString(): String = "LocalVfs"
}
