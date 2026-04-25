package korlibs.platform

import kotlinx.cinterop.*
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringRef
import platform.Foundation.*
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