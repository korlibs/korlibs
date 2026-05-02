package korlibs.image.format.cg

import korlibs.image.bitmap.*
import korlibs.image.format.*

object CGNativeImageFormatProvider : BaseNativeImageFormatProvider() {
    override fun createBitmapNativeImage(bmp: Bitmap): CoreGraphicsNativeImage = CoreGraphicsNativeImage(bmp.toBMP32().premultipliedIfRequired())
}
