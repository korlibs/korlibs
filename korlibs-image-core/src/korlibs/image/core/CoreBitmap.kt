package korlibs.image.core

import korlibs.image.bitmap.*

// @TODO: We should probably provide Bitmap directly here

interface CoreBitmap {
    val width: Int
    val height: Int
    val bpp: Int
    val premultiplied: Boolean
    val backingArray: Any?
    fun toBMP32(): CoreBitmap32
}

interface CoreBitmapIndexed : CoreBitmap {
    val bytes: ByteArray
    val paletteInts: IntArray
}

interface CoreBitmap32 : CoreBitmap {
    // This array should be of `width * height` size where the index of each pixel can be retrieved
    // using `x + y * width`.
    // Each pixel is represented by an integer in RGBA packed format where each component is 1 byte:
    // (r) or (g shl 8) or (b shl 16) or (a shl 24)
    val ints: IntArray
}

interface CoreNativeImage : CoreBitmap, NativeImageRef {
    val data: Any?
}
