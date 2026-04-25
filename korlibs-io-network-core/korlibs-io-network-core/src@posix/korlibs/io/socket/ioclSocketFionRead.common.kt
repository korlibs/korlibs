@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.socket

import kotlinx.cinterop.*
import platform.posix.*

internal expect fun ioctlSocketFionRead(sockfd: Int): Int

