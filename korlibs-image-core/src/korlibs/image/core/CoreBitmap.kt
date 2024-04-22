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
    val ints: IntArray
}

interface CoreNativeImage : CoreBitmap, NativeImageRef {
    val data: Any?
}
