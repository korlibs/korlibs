package korlibs.image.format

import java.awt.image.BufferedImage
import korlibs.image.awt.AwtNativeImage
import korlibs.image.awt.awtShowImageAndWait
import korlibs.image.awt.toAwt
import korlibs.image.bitmap.Bitmap
import korlibs.image.bitmap.NativeImage
import korlibs.image.core.AwtCoreImage
import korlibs.image.core.CoreImage

actual val nativeImageFormatProvider: NativeImageFormatProvider = JvmNativeImageFormatProvider

object JvmNativeImageFormatProvider : NativeImageFormatProvider() {
    override fun create(width: Int, height: Int, premultiplied: Boolean?): NativeImage =
        AwtNativeImage(BufferedImage(maxOf(width, 1), maxOf(height, 1), if (premultiplied == false) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_ARGB_PRE))

    override fun copy(bmp: Bitmap): NativeImage = AwtNativeImage(bmp.toAwt())

    override suspend fun display(bitmap: Bitmap, kind: Int) {
        awtShowImageAndWait(bitmap)
    }

    override fun convertCoreImageToNativeImage(image: CoreImage, props: ImageDecodingProps): NativeImage = when (image) {
        is AwtCoreImage -> AwtNativeImage(image.native)
        else -> toNativeImageSure(image.to32().toBitmap())
    }

    override fun toNativeImageSure(bmp: Bitmap): NativeImage {
        return AwtNativeImage(bmp.toBMP32IfRequired().toAwt())
    }
}