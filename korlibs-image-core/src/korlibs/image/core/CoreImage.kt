package korlibs.image.core

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

/** In RGBA format */
class CoreImage32(
    override val width: Int,
    override val height: Int,
    val data: IntArray,
    override val premultiplied: Boolean = true
) : CoreImage {
    override val native get() = data
    override val bpp: Int = 32
    override fun to32(): CoreImage32 = this
}

data class CoreImageInfo(
    val width: Int,
    val height: Int,
    val bpp: Int = 32,
    val format: CoreImageFormat? = null,
    val premultiplied: Boolean = true,
)

fun CoreImage.info(): CoreImageInfo = CoreImageInfo(width, height, bpp, format = null, premultiplied = premultiplied)

inline class CoreImageFormat(val name: String) {
    companion object {
        val PNG = CoreImageFormat("PNG")
        val JPEG = CoreImageFormat("JPEG")
        val WEBP = CoreImageFormat("WEBP")
        val AVIF = CoreImageFormat("AVIF")
    }
}

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
}

expect val CoreImageFormatProvider_default: CoreImageFormatProvider

private var _CoreImageFormatProvider_current: CoreImageFormatProvider? = null

var CoreImageFormatProvider_current: CoreImageFormatProvider
    get() = _CoreImageFormatProvider_current ?: CoreImageFormatProvider_default
    set(value) { _CoreImageFormatProvider_current = value }

suspend fun CoreImage.Companion.info(data: ByteArray): CoreImageInfo =
    CoreImageFormatProvider_current.info(data)

suspend fun CoreImage.Companion.decodeBytes(data: ByteArray): CoreImage =
    CoreImageFormatProvider_current.decode(data)

suspend fun CoreImage.encodeBytes(format: CoreImageFormat, level: Double = 1.0): ByteArray =
    CoreImageFormatProvider_current.encode(this, format, level)
