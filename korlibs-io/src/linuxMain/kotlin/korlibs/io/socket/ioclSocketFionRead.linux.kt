@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.socket

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.cinterop.refTo
import platform.posix.FIONREAD
import platform.posix.ioctl

internal actual fun ioctlSocketFionRead(sockfd: Int): Int {
    val v = uintArrayOf(0u)
    ioctl(sockfd.convert(), FIONREAD.convert(), v.refTo(0))
    return v[0].toInt()
}
