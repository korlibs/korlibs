@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.posix

import kotlinx.cinterop.*
import platform.posix.*
import platform.posix.FIONREAD
import platform.posix.ioctlsocket
import platform.windows.*

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
    //override fun posixRealpath(path: String): String = memScoped {
    //    val temp = allocArray<WCHARVar>(PATH_MAX + 1)
    //    val temp2 = alloc<WIN32_FIND_DATAW>()
    //    val temp3 = alloc<BY_HANDLE_FILE_INFORMATION>()
    //    temp3.name
    //    val res = FindFirstFileW(path, temp2.ptr)
    //    if (res != null) {
    //        println("RES: $res" + temp2.cFileName.toKString())
    //    } else {
    //        println("RES: null")
    //    }
    //    CloseHandle(res)
    //    val len = GetFullPathNameW(path, PATH_MAX.convert(), temp.reinterpret(), null)
    //    if (len == 0u) return@memScoped path
    //    temp.toKString().also {
    //        println("posixRealpath: path='$path' -> '$it'")
    //    }
    //}

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

