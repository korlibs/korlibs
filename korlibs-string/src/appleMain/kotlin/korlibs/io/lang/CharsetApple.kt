package korlibs.io.lang

import korlibs.memory.ByteArrayBuilder
import korlibs.platform.toByteArray
import korlibs.platform.toNSData
import korlibs.platform.useCFStringRef
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import platform.CoreFoundation.CFStringConvertEncodingToNSStringEncoding
import platform.CoreFoundation.CFStringConvertIANACharSetNameToEncoding
import platform.CoreFoundation.kCFStringEncodingInvalidId
import platform.Foundation.NSString
import platform.Foundation.NSStringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding

@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
class CharsetApple(name: String) : Charset(name) {
    private val encoding: NSStringEncoding = getEncoding(name)

    private fun getEncoding(name: String): NSStringEncoding {
        val encoding = name.uppercase().useCFStringRef { CFStringConvertEncodingToNSStringEncoding(CFStringConvertIANACharSetNameToEncoding(it)) }
        if (encoding.toUInt() != kCFStringEncodingInvalidId) {
            return encoding
        } else {
            throw IllegalArgumentException("Charset $name is not supported by apple.")
        }
    }


    override fun decode(out: StringBuilder, src: ByteArray, start: Int, end: Int): Int {
        if (end == 0) return 0

        val data = src.copyOfRange(start, end).toNSData()

        val content = NSString.create(data, this.encoding) as? String
            ?: throw MalformedInputException("Failed to convert Bytes to String using $name")

        out.append(content)

        return src.size
    }

    override fun encode(out: ByteArrayBuilder, src: CharSequence, start: Int, end: Int) {
        val content = src.substring(start, end) as? NSString ?: error("Failed to convert input to NSString.")
        val data = content.dataUsingEncoding(this.encoding)?.toByteArray()
            ?: throw MalformedInputException("Failed to convert String to Bytes using $name")

        out.append(data)
    }
}
