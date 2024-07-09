package korlibs.image.format

import korlibs.image.bitmap.*
import korlibs.io.stream.*

/**
 * Apple Icon Image Format
 * <https://en.wikipedia.org/wiki/Apple_Icon_Image_format>
 */
class ICNS : ImageFormat("icns", mimeType = "image/x-icns") {
    override fun readImageContainer(s: SyncStream, props: ImageDecodingProps): ImageDataContainer {
        check(s.readStringz(4) == "icns") { "Not an ICNS file" }
        //val fileLen = s.readU32BE()
        val images = arrayListOf<Bitmap>()
        while (!s.eof) {
            val type = s.readStringz(4)
            val len = s.readU32BE()
            val data = s.readBytes(len.toInt() - 8)
            val bmp = when (type) {
                "icp4" -> PNG.decode(data, props)
                "icp5" -> PNG.decode(data, props)
                "icp6" -> PNG.decode(data, props)
                "ic07" -> PNG.decode(data, props)
                "ic08" -> PNG.decode(data, props)
                "ic09" -> PNG.decode(data, props)
                "ic10" -> PNG.decode(data, props)
                "ic11" -> PNG.decode(data, props)
                "ic12" -> PNG.decode(data, props)
                "ic13" -> PNG.decode(data, props)
                "ic14" -> PNG.decode(data, props)
                "icp0" -> PNG.decode(data, props)
                "icp1" -> PNG.decode(data, props)
                "icp2" -> PNG.decode(data, props)
                "icp3" -> PNG.decode(data, props)
                else -> null
            }
            if (bmp != null) images += bmp
        }
        return ImageDataContainer(images)
    }
}