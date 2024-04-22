package korlibs.image.core

import kotlinx.browser.*
import kotlinx.dom.*
import org.w3c.dom.*
import kotlin.io.encoding.*

actual val systemCoreBitmapProvider: CoreBitmapProvider = BrowserCoreBitmapProvider

object BrowserCoreBitmapProvider : CoreBitmapProvider {
    override suspend fun decode(source: Any?, decodePixels: Boolean): CoreNativeImage {
        TODO()
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun encode(bitmap: CoreBitmap, format: String, quality: Float): ByteArray {
        val canvas = document.createElement("canvas").unsafeCast<HTMLCanvasElement>()
        // @TODO: Encode bitmap
        TODO()
        //val image = canvas.toDataURL("image/$format", quality.toDouble())
        //return Base64.decode(image.substringAfter(','))
    }
}
