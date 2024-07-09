package korlibs.image.core

/**
 * An image representation that can be used to encode/decode images in different formats.
 */
interface CoreImage {
    /** [width] of the image */
    val width: Int
    /** [height] of the image */
    val height: Int
    /** Native object. Like IntArray for CoreImage32 or BufferedImage */
    val native: Any
    /** Bits per pixel */
    val bpp: Int
    /** Determine if the pixels are premultiplied by its alpha */
    val premultiplied: Boolean
    /** Returns a [CoreImage32] image */
    fun to32(): CoreImage32

    companion object
}

/**
 * A 32-bit image representation.
 *
 * [data] is in [CoreImage32Color] format
 */
class CoreImage32(
    override val width: Int,
    override val height: Int,
    val data: IntArray = IntArray(width * height),
    override val premultiplied: Boolean = true
) : CoreImage {
    override val native get() = data
    override val bpp: Int = 32
    override fun to32(): CoreImage32 = this
}

/**
 * 32-bit RGBA color format. Used in [CoreImage32]
 */
inline class CoreImage32Color(val value: Int) {
    constructor(red: UByte, green: UByte, blue: UByte, alpha: UByte = 255u) : this((red.toInt() shl RED_OFFSET) or (green.toInt() shl GREEN_OFFSET) or (blue.toInt() shl BLUE_OFFSET) or (alpha.toInt() shl ALPHA_OFFSET))
    constructor(red: Int, green: Int, blue: Int, alpha: Int = 255) : this(red.coerceIn(0, 255).toUByte(), green.coerceIn(0, 255).toUByte(), blue.coerceIn(0, 255).toUByte(), alpha.coerceIn(0, 255).toUByte())

    val red: Int get() = (value ushr RED_OFFSET) and 0xFF
    val green: Int get() = (value ushr GREEN_OFFSET) and 0xFF
    val blue: Int get() = (value ushr BLUE_OFFSET) and 0xFF
    val alpha: Int get() = (value ushr ALPHA_OFFSET) and 0xFF

    companion object {
        const val RED_OFFSET = 0
        const val GREEN_OFFSET = 8
        const val BLUE_OFFSET = 16
        const val ALPHA_OFFSET = 24
    }
}

/**
 * Provides information about an image.
 */
data class CoreImageInfo(
    /** [width] of the image */
    val width: Int,
    /** [height] of the image */
    val height: Int,
    /** Bits per pixel */
    val bpp: Int = 32,
    val format: CoreImageFormat? = null,
    val premultiplied: Boolean = true,
)

/**
 * Provides information about an image.
 */
fun CoreImage.info(): CoreImageInfo = CoreImageInfo(width, height, bpp, format = null, premultiplied = premultiplied)

/**
 * Image format: [PNG], [JPEG], [WEBP], [AVIF], etc.
 */
inline class CoreImageFormat(val name: String) {
    companion object {
        val PNG = CoreImageFormat("PNG")
        val JPEG = CoreImageFormat("JPEG")
        val WEBP = CoreImageFormat("WEBP")
        val AVIF = CoreImageFormat("AVIF")
    }
}

/**
 * Provides image encoding/decoding capabilities.
 */
interface CoreImageFormatProvider {
    /**
     * Gets the [CoreImageInfo] of a [data] ByteArray. Potentially without decoding the pixels.
     */
    suspend fun info(data: ByteArray): CoreImageInfo = decode(data).let {
        CoreImageInfo(width = it.width, height = it.height, bpp = it.bpp, format = null)
    }
    /**
     * Decodes a [data] ByteArray into a CoreImage
     */
    suspend fun decode(data: ByteArray): CoreImage
    /**
     * Encodes a [CoreImage] into a [ByteArray] in the specified [format] (PNG, JPEG, etc.)
     */
    suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Double = 1.0): ByteArray

    companion object
}

expect val CoreImageFormatProvider_default: CoreImageFormatProvider

private var _CoreImageFormatProvider_current: CoreImageFormatProvider? = null

/**
 * Current [CoreImageFormatProvider] used by [CoreImage] operations. By default, it uses the default one from the platform.
 */
var CoreImageFormatProvider.Companion.CURRENT: CoreImageFormatProvider
    get() = _CoreImageFormatProvider_current ?: CoreImageFormatProvider_default
    set(value) { _CoreImageFormatProvider_current = value }

/**
 * Gets the [CoreImageInfo] of a [data] ByteArray. Potentially without decoding the pixels.
 */
suspend fun CoreImage.Companion.info(data: ByteArray): CoreImageInfo =
    CoreImageFormatProvider.CURRENT.info(data)

/**
 * Decodes a [data] ByteArray into a CoreImage
 */
suspend fun CoreImage.Companion.decodeBytes(data: ByteArray): CoreImage =
    CoreImageFormatProvider.CURRENT.decode(data)

/**
 * Encodes a [CoreImage] into a [ByteArray] in the specified [format] (PNG, JPEG, etc.)
 */
suspend fun CoreImage.encodeBytes(format: CoreImageFormat, level: Double = 1.0): ByteArray =
    CoreImageFormatProvider.CURRENT.encode(this, format, level)
