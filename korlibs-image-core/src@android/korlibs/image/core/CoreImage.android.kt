package korlibs.image.core

import android.graphics.*
import android.os.*
import kotlinx.coroutines.*
import java.io.*

actual val CoreImageFormatProvider_default: CoreImageFormatProvider = AndroidCoreImageFormatProvider

object AndroidCoreImageFormatProvider : CoreImageFormatProvider {
    override suspend fun info(data: ByteArray): CoreImageInfo = withContext(Dispatchers.IO) {
        val options = BitmapFactory.Options().also { it.inJustDecodeBounds = true }
        Dispatchers.Default { BitmapFactory.decodeByteArray(data, 0, data.size, options) }
        CoreImageInfo(
            width = options.outWidth,
            height = options.outHeight,
            format = CoreImageFormat(options.outMimeType.substringAfterLast('/'))
        )
    }

    override suspend fun decode(data: ByteArray): CoreImage = withContext(Dispatchers.IO) {
        AndroidCoreImage(BitmapFactory.decodeByteArray(
            data, 0, data.size,
            BitmapFactory.Options().also {
                it.inPremultiplied = true
                it.inSampleSize = 1
                it.inMutable = true
            }
        ))
    }

    override suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Double): ByteArray = withContext(Dispatchers.IO) {
        val compressFormat = when (format.name.lowercase()) {
            "png" -> android.graphics.Bitmap.CompressFormat.PNG
            "jpeg", "jpg" -> android.graphics.Bitmap.CompressFormat.JPEG
            "webp" -> when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> android.graphics.Bitmap.CompressFormat.WEBP_LOSSY
                else -> android.graphics.Bitmap.CompressFormat.PNG
            }
            else -> android.graphics.Bitmap.CompressFormat.PNG
        }
        ByteArrayOutputStream().also { image.toAndroid().bitmap.compress(compressFormat, (level * 100).toInt(), it) }.toByteArray()
    }
}

fun CoreImage.toAndroid(): AndroidCoreImage {
    val bmp = this.to32()
    // @TODO: Should we convert RGBA/BGRA etc.?
    return AndroidCoreImage(Bitmap.createBitmap(bmp.data, 0, bmp.width, bmp.width, bmp.height, Bitmap.Config.ARGB_8888))
}

class AndroidCoreImage(val bitmap: Bitmap) : CoreImage {
    override val width: Int get() = bitmap.width
    override val height: Int get() = bitmap.height
    override val native: Any get() = bitmap
    override val bpp: Int get() = 32
    override val premultiplied: Boolean get() = bitmap.isPremultiplied
    override fun to32(): CoreImage32 = CoreImage32(width, height, IntArray(width * height)).also {
        // @TODO: Should we convert RGBA/BGRA etc.?
        bitmap.getPixels(it.data, 0, width, 0, 0, width, height)
    }
}