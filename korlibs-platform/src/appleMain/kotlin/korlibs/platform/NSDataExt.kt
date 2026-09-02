package korlibs.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringRef
import platform.Foundation.CFBridgingRetain
import platform.Foundation.NSData
import platform.Foundation.NSMutableData
import platform.Foundation.NSString
import platform.Foundation.appendBytes
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
fun NSData.toByteArray(): ByteArray {
    val result = ByteArray(length.toInt())
    if (result.isEmpty()) return result

    result.usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }

    return result
}

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
fun ByteArray.toNSData(): NSData = NSMutableData().apply {
    if (isEmpty()) return@apply
    this@toNSData.usePinned {
        appendBytes(it.addressOf(0), size.convert())
    }
}


@OptIn(ExperimentalForeignApi::class)
inline fun <T> String.useCFStringRef(block: (CFStringRef) -> T): T {
    val ref = CFBridgingRetain(this as NSString) as CFStringRef
    return block(ref).also { CFRelease(ref) }
}