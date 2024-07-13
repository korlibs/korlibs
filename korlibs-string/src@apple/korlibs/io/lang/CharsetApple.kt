package korlibs.io.lang

import korlibs.memory.ByteArrayBuilder
import kotlinx.cinterop.UnsafeNumber
import platform.Foundation.*

@OptIn(UnsafeNumber::class)
class CharsetApple(name: String) : Charset(name) {
    companion object {
        val encodingMap = mapOf(
            "UTF-8" to NSUTF8StringEncoding,
            "ISO-8859-1" to NSISOLatin1StringEncoding,
            "UTF-16" to NSUTF16StringEncoding,
            "UTF-16BE" to NSUTF16BigEndianStringEncoding,
            "UTF-16LE" to NSUTF16LittleEndianStringEncoding,
            "UTF-32" to NSUTF32StringEncoding,
            "UTF-32BE" to NSUTF32BigEndianStringEncoding,
            "UTF-32LE" to NSUTF32LittleEndianStringEncoding,
            "ASCII" to NSASCIIStringEncoding,
            "NEXTSTEP" to NSNEXTSTEPStringEncoding,
            "JAPANESE_EUC" to NSJapaneseEUCStringEncoding,
            "EUC-JP" to NSJapaneseEUCStringEncoding,
            "LATIN1" to NSISOLatin1StringEncoding,
        )

        fun isSupported(name: String) = encodingMap.containsKey(name.uppercase())
    }

    private val encoding: NSStringEncoding =
        encodingMap[name.uppercase()] ?: throw IllegalArgumentException("Charset $name is not supported by apple.")


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
