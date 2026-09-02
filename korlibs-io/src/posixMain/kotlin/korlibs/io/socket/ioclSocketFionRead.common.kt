@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.socket

import kotlinx.cinterop.ExperimentalForeignApi

internal expect fun ioctlSocketFionRead(sockfd: Int): Int
