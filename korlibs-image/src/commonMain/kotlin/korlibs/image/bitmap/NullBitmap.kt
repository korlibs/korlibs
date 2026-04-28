package korlibs.image.bitmap

import korlibs.image.color.*

open class NullBitmap(
    width: Int,
    height: Int,
    premultiplied: Boolean = true
) : Bitmap(width, height, 32, premultiplied, null) {
    companion object : NullBitmap(1, 1)

    override fun setRgbaRaw(x: Int, y: Int, v: RGBA) = Unit
    override fun getRgbaRaw(x: Int, y: Int): RGBA = RGBA(0)
}
