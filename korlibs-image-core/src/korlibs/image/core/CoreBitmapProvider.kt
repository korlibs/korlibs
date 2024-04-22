package korlibs.image.core

expect val systemCoreBitmapProvider: CoreBitmapProvider

object NullCoreBitmapProvider : CoreBitmapProvider {
}

data class CoreBitmapInfo(
    val width: Int,
    val height: Int,
    val bpp: Int,
    val format: String,
)

interface CoreBitmapProvider {
    suspend fun decode(source: Any?, decodePixels: Boolean = true): CoreNativeImage? = TODO()
    suspend fun encode(bitmap: CoreBitmap, format: String, quality: Float = 1f): ByteArray = TODO()
}
