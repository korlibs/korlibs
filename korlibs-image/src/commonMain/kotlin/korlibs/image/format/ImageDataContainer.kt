package korlibs.image.format

import korlibs.image.bitmap.*

open class ImageDataContainer(
    val imageDatas: List<ImageData>
) {
    companion object {
        operator fun invoke(bitmaps: List<Bitmap>): ImageDataContainer = ImageDataContainer(bitmaps.map { ImageData(it) })
        operator fun invoke(vararg bitmaps: Bitmap): ImageDataContainer = ImageDataContainer(bitmaps.toList())
    }
    constructor(vararg imageDatas: ImageData) : this(imageDatas.toList())

    val imageDatasByName: Map<String?, ImageData> by lazy { imageDatas.associateBy { it.name } }
    val default: ImageData by lazy { imageDatasByName[null] ?: imageDatas.first() }
    val mainBitmap: Bitmap get() = default.mainBitmap

    operator fun get(name: String?): ImageData? = imageDatasByName[name]
}
