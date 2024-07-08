package korlibs.image.core

import kotlinx.coroutines.*
import java.awt.image.*
import java.io.*
import javax.imageio.*

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

    override suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Double): ByteArray {
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
    else -> AwtCoreImage(this.toAwt())
}

fun CoreImage.toAwt(): BufferedImage = when (this) {
    is AwtCoreImage -> this.native
    else -> {
        val image = this.to32()
        BufferedImage(image.width, image.height, if (image.premultiplied) AWT_INTERNAL_IMAGE_TYPE_PRE else AWT_INTERNAL_IMAGE_TYPE).also {
            val data = (it.raster.dataBuffer as DataBufferInt).data
            image.data.copyInto(data)
            BGRAtoRGBA(data)
        }
    }
}

class AwtCoreImage(
    override val native: BufferedImage,
) : CoreImage {
    override fun toString() = "AwtCoreImage($width, $height)"

    override val width: Int get() = native.width
    override val height: Int get() = native.height
    override val bpp: Int get() = 32
    override val premultiplied: Boolean get() = native.type == BufferedImage.TYPE_INT_ARGB_PRE || native.type == BufferedImage.TYPE_4BYTE_ABGR_PRE

    override fun to32(): CoreImage32 {
        check((native.type == BufferedImage.TYPE_INT_ARGB_PRE) || (native.type == BufferedImage.TYPE_INT_ARGB))
        val dataBuffer = native.raster.dataBuffer as DataBufferInt
        val awtData = dataBuffer.data
        val data = awtData.copyOf()
        for (n in data.indices) data[n] = BGRAtoRGBA(data[n])
        return CoreImage32(width, height, data)
    }
}

private fun <T> ImageIOReader(s: InputStream, block: (ImageReader) -> T): T = ImageIO.createImageInputStream(s).use { input ->
    val readers = ImageIO.getImageReaders(input)
    if (!readers.hasNext()) error("Can't decode image")
    val reader = readers.next()
    try {
        block(reader.also { reader.input = input })
    } finally {
        reader.dispose()
    }
}

private fun ImageIOReadFormat(s: InputStream, type: Int = AWT_INTERNAL_IMAGE_TYPE_PRE): BufferedImage = ImageIOReader(s) { reader ->
    val availableTypes = reader.getImageTypes(0).asSequence().toList()
    val dtype = ImageTypeSpecifier.createFromBufferedImageType(type).takeIf { it in availableTypes }
        ?: ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_4BYTE_ABGR).takeIf { it in availableTypes }
        ?: availableTypes.first()
    reader.read(0, reader.defaultReadParam.also { it.destinationType = dtype }).let {
        if (it.type != type) it.convertToType(type) else it
    }
}

private fun BufferedImage.convertToType(type: Int = BufferedImage.TYPE_INT_ARGB_PRE): BufferedImage {
    if (this.type == type) return this
    return BufferedImage(this.width, this.height, type).also { it.graphics.drawImage(this, 0, 0, null) }
}

private const val AWT_INTERNAL_IMAGE_TYPE_PRE = BufferedImage.TYPE_INT_ARGB_PRE
private const val AWT_INTERNAL_IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB

internal fun BGRAtoRGBA(v: Int): Int = ((v shl 16) and 0x00FF0000) or ((v shr 16) and 0x000000FF) or (v and 0xFF00FF00.toInt())
internal fun BGRAtoRGBA(data: IntArray, start: Int = 0, end: Int = data.size) { for (n in start until end) data[n] = BGRAtoRGBA(data[n]) }
