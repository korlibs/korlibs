package korlibs.image.format

import korlibs.image.awt.*
import korlibs.image.bitmap.*
import korlibs.image.core.*
import java.awt.image.*

actual val nativeImageFormatProvider: NativeImageFormatProvider = JvmNativeImageFormatProvider

object JvmNativeImageFormatProvider : NativeImageFormatProvider() {
    override fun create(width: Int, height: Int, premultiplied: Boolean?): NativeImage =
        AwtNativeImage(BufferedImage(maxOf(width, 1), maxOf(height, 1), if (premultiplied == false) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_ARGB_PRE))

    override fun copy(bmp: Bitmap): NativeImage = AwtNativeImage(bmp.toAwt())

    override suspend fun display(bitmap: Bitmap, kind: Int) {
        awtShowImageAndWait(bitmap)
    }

    override fun convertCoreImageToNativeImage(image: CoreImage, props: ImageDecodingProps): NativeImage {
        if (image is AwtCoreImage) return AwtNativeImage(image.native)
        return super.convertCoreImageToNativeImage(image, props)
    }
}