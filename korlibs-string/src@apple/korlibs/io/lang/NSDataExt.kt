package korlibs.io.lang

import kotlinx.cinterop.*
import platform.Foundation.NSData
import platform.Foundation.NSMutableData
import platform.Foundation.appendBytes
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
internal fun NSData.toByteArray(): ByteArray {
    val result = ByteArray(length.toInt())
    if (result.isEmpty()) return result

    result.usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }

    return result
}

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
internal fun ByteArray.toNSData(): NSData = NSMutableData().apply {
    if (isEmpty()) return@apply
    this@toNSData.usePinned {
        appendBytes(it.addressOf(0), size.convert())
    }
}