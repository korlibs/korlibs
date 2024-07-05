package korlibs.image.core

import kotlinx.coroutines.*
import java.awt.image.*
import java.io.*
import javax.imageio.*

actual val CoreImageFormatProvider_default: CoreImageFormatProvider = AwtCoreImageFormatProvider

object AwtCoreImageFormatProvider : CoreImageFormatProvider {
    var dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun info(data: ByteArray): CoreImageInfo = withContext(dispatcher) {
        ImageIOReader(data.inputStream()) {
            CoreImageInfo(width = it.getWidth(0), height = it.getHeight(0), bpp = 32, format = CoreImageFormat(it.formatName))
        }
    }

    override suspend fun decode(data: ByteArray): CoreImage = withContext(dispatcher) {
        AwtCoreImage(ImageIOReadFormat(data.inputStream()))
    }

    override suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Float): ByteArray {
        val imageWriter = ImageIO.getImageWritersByFormatName(format.name).next()
        return ByteArrayOutputStream().use { bao ->
            ImageIO.createImageOutputStream(bao).use { ios ->
                imageWriter.output = ios
                imageWriter.write(image.toAwtCoreImage().native)
            }
            bao.toByteArray()
        }
    }
}

fun CoreImage.toAwtCoreImage(): AwtCoreImage = when (this) {
    is AwtCoreImage -> this
    else -> AwtCoreImage(toAwt())
}

class AwtCoreImage(
    override val native: BufferedImage,
) : CoreImage {
    override val width: Int get() = native.width
    override val height: Int get() = native.height
    override val bpp: Int get() = 32
    override val premultiplied: Boolean get() = native.type == BufferedImage.TYPE_INT_ARGB_PRE || native.type == BufferedImage.TYPE_4BYTE_ABGR_PRE

    override fun to32(): CoreImage32 {
        check((native.type == BufferedImage.TYPE_INT_ARGB_PRE) || (native.type == BufferedImage.TYPE_INT_ARGB))
        val dataBuffer = native.raster.dataBuffer as DataBufferInt
        val awtData = dataBuffer.data
        val data = awtData.copyOf()
        for (n in data.indices) data[n] = conv(data[n])
        return CoreImage32(width, height, data)
    }

    companion object {
        fun conv(v: Int): Int = ((v shl 16) and 0x00FF0000) or ((v shr 16) and 0x000000FF) or (v and 0xFF00FF00.toInt())
    }
}

private fun <T> ImageIOReader(s: InputStream, block: (ImageReader) -> T): T = ImageIO.createImageInputStream(s).use { input ->
    val readers = ImageIO.getImageReaders(input)
    if (!readers.hasNext()) error("Can't decode image")
    val reader = readers.next()
    try {
        block(reader.also { reader.input = s })
    } finally {
        reader.dispose()
    }
}

private fun ImageIOReadFormat(s: InputStream, type: Int = AWT_INTERNAL_IMAGE_TYPE_PRE): BufferedImage = ImageIOReader(s) { reader ->
    val availableTypes = reader.getImageTypes(0).asSequence().toList()
    val dtype = ImageTypeSpecifier.createFromBufferedImageType(type).takeIf { it in availableTypes }
        ?: ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR).takeIf { it in availableTypes }
        ?: availableTypes.first()
    reader.read(0, reader.defaultReadParam.also { it.destinationType = dtype })
}

private const val AWT_INTERNAL_IMAGE_TYPE_PRE = BufferedImage.TYPE_INT_ARGB_PRE
private const val AWT_INTERNAL_IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB
