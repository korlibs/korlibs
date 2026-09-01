package korlibs.image.format.cg

import korlibs.image.bitmap.Bitmap
import korlibs.image.format.BaseNativeImageFormatProvider

object CGNativeImageFormatProvider : BaseNativeImageFormatProvider() {
    override fun createBitmapNativeImage(bmp: Bitmap): CoreGraphicsNativeImage = CoreGraphicsNativeImage(bmp.toBMP32().premultipliedIfRequired())
}
