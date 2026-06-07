@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.socket

import kotlinx.cinterop.*

internal expect fun ioctlSocketFionRead(sockfd: Int): Int
