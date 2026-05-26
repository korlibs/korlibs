@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.posix

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import kotlinx.cinterop.wcstr
import platform.posix.FILE
import platform.posix.FIONREAD
import platform.posix.PATH_MAX
import platform.posix.getcwd
import platform.posix.ioctlsocket

actual val POSIX: BasePosix = PosixMingw

object PosixMingw : BasePosix() {
    override fun isCaseSensitive(): Boolean = false

    override fun posixFopen(filename: String, mode: String): CPointer<FILE>? {
        return memScoped {
            //setlocale(LC_ALL, ".UTF-8") // On Windows 10 : https://docs.microsoft.com/en-us/cpp/c-runtime-library/reference/setlocale-wsetlocale?redirectedfrom=MSDN&view=msvc-160#utf-8-support
            platform.posix._wfopen(filename.wcstr, mode.wcstr)
        }
    }

    override fun posixReadlink(path: String): String? = null

    override fun posixRealpath(path: String): String = path

    override fun posixGetcwd(): String = memScoped {
        val temp = allocArray<ByteVar>(PATH_MAX + 1)
        getcwd(temp, PATH_MAX)
        temp.toKString()
    }

    override fun posixMkdir(path: String, attr: Int): Int {
        return platform.posix.mkdir(path)
    }

    override fun ioctlSocketFionRead(sockfd: Int): Int {
        val v = uintArrayOf(0u)
        ioctlsocket(sockfd.convert(), FIONREAD, v.refTo(0))
        return v[0].toInt()
    }
}
