@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.io.socket

import kotlinx.cinterop.*
import platform.posix.*

internal actual fun ioctlSocketFionRead(sockfd: Int): Int {
    val v = uintArrayOf(0u)
    ioctl(sockfd.convert(), FIONREAD.convert(), v.refTo(0))
    return v[0].toInt()
}

