package korlibs.image.format

import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.image.core.*
import korlibs.image.vector.*
import korlibs.io.async.*
import korlibs.io.file.*
import korlibs.io.stream.*
import korlibs.math.geom.*
import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.math.*

expect val nativeImageFormatProvider: NativeImageFormatProvider

data class NativeImageResult(
    val image: NativeImage,
    val originalWidth: Int = image.width,
    val originalHeight: Int = image.height,
)

internal fun CoreImage32.toBitmap(): Bitmap32 = Bitmap32(width, height, data, premultiplied)
internal fun CoreImage.toBitmap(): Bitmap = to32().toBitmap()

internal fun Bitmap32.toCoreImage(): CoreImage32 = CoreImage32(width, height, ints, premultiplied)
internal fun Bitmap.toCoreImage(): CoreImage = this.toBMP32().toCoreImage()

fun CoreImageInfo.toImageInfo(): ImageInfo = ImageInfo().also {
    it.width = width
    it.height = height
}

open class NativeImageFormatProvider : ImageFormatEncoderDecoder {
    protected suspend fun <T> executeIo(callback: suspend () -> T): T = when {
        coroutineContext.preferSyncIo -> callback()
        else -> withContext(Dispatchers.CIO) { callback() }
    }

    protected open suspend fun decodeHeaderInternal(data: ByteArray): ImageInfo {
        return CoreImage.info(data).toImageInfo()
        //val result = decodeInternal(data, ImageDecodingProps.DEFAULT)
        //return ImageInfo().also {
        //    it.width = result.originalWidth
        //    it.height = result.originalHeight
        //}
    }

    override suspend fun encodeSuspend(image: ImageDataContainer, props: ImageEncodingProps): ByteArray {
        return CoreImage.encode(image.mainBitmap.toCoreImage(), CoreImageFormat.fromMimeType(props.mimeType), props.quality)
    }

    protected open suspend fun decodeInternal(data: ByteArray, props: ImageDecodingProps): NativeImageResult {
        if (props.asumePremultiplied || props.premultiplied == false) {
            return RegisteredImageFormats.decode(data, props).toNativeImageResult(props)
        }
        val image = CoreImage.decodeBytes(data)
        return image.toNativeImage(props).toNativeImageResult(props)
    }

    protected fun CoreImage.toNativeImage(props: ImageDecodingProps): NativeImage {
        return convertCoreImageToNativeImage(this, props)
    }

    protected open fun convertCoreImageToNativeImage(image: CoreImage, props: ImageDecodingProps): NativeImage {
        return image.to32().toBitmap().toNativeImage(props)
    }

    protected open suspend fun decodeInternal(vfs: Vfs, path: String, props: ImageDecodingProps): NativeImageResult = decodeInternal(vfs.file(path).readBytes(), props)

    protected fun NativeImage.result(props: ImageDecodingProps): NativeImageResult {
        return NativeImageResult(when {
            props.asumePremultiplied -> this.asumePremultiplied()
            else -> this
        })
    }

    suspend fun decodeHeader(data: ByteArray): ImageInfo = decodeHeaderInternal(data)
    suspend fun decodeHeaderOrNull(data: ByteArray): ImageInfo? = try {
        decodeHeaderInternal(data)
    } catch (e: Throwable) {
        if (e is CancellationException) throw e
        null
    }

    suspend fun decode(vfs: Vfs, path: String, props: ImageDecodingProps): NativeImage = decodeInternal(vfs, path, props).image
    suspend fun decode(data: ByteArray, props: ImageDecodingProps = ImageDecodingProps.DEFAULT): NativeImage = decodeInternal(data, props).image
    override suspend fun decodeSuspend(data: ByteArray, props: ImageDecodingProps): NativeImage = decodeInternal(data, props).image
    suspend fun decode(file: FinalVfsFile, props: ImageDecodingProps): Bitmap = decodeInternal(file.vfs, file.path, props).image

    override suspend fun decode(file: VfsFile, props: ImageDecodingProps): Bitmap = decode(file.getUnderlyingUnscapedFile(), props)

    suspend fun decode(vfs: Vfs, path: String, premultiplied: Boolean = true): NativeImage = decode(vfs, path, ImageDecodingProps.DEFAULT(premultiplied))
    suspend fun decode(data: ByteArray, premultiplied: Boolean): NativeImage = decode(data, ImageDecodingProps.DEFAULT(premultiplied))
    suspend fun decode(file: FinalVfsFile, premultiplied: Boolean = true): Bitmap = decode(file, ImageDecodingProps.DEFAULT(premultiplied))
    suspend fun decode(file: VfsFile, premultiplied: Boolean): Bitmap = decode(file, ImageDecodingProps.DEFAULT(premultiplied))

    open suspend fun display(bitmap: Bitmap, kind: Int): Unit {
        TODO()
    }

    open fun create(width: Int, height: Int, premultiplied: Boolean?): NativeImage {
        return BitmapNativeImage(Bitmap32(width, height, premultiplied = premultiplied ?: true))
    }

    open fun create(width: Int, height: Int, pixels: IntArray, premultiplied: Boolean? = null): NativeImage {
        val image = create(width, height, premultiplied)
        image.writePixelsUnsafe(0, 0, width, height, pixels)
        return image
    }
    //open fun create(width: Int, height: Int, premultiplied: Boolean): NativeImage = create(width, height)
	open fun copy(bmp: Bitmap): NativeImage = create(bmp.width, bmp.height, bmp.premultiplied).apply { context2d { drawImage(bmp, Point.ZERO) } }
	open fun mipmap(bmp: Bitmap, levels: Int): NativeImage = bmp.toBMP32().mipmap(levels).ensureNative()
	open fun mipmap(bmp: Bitmap): NativeImage {
        val out = NativeImage(ceil(bmp.width * 0.5).toInt(), ceil(bmp.height * 0.5).toInt())
        out.context2d(antialiased = true) {
            renderer.drawImage(bmp, Point.ZERO, Size(out.width, out.height))
        }
        return out
    }


    fun Bitmap.toNativeImage(props: ImageDecodingProps): NativeImage {
        if (this is NativeImage && !props.asumePremultiplied && (props.premultiplied == null || props.premultiplied == this.premultiplied)) {
            return this
        }
        val out = this.toBMP32IfRequired()
        when {
            props.asumePremultiplied -> out.asumePremultiplied()
            !this.premultiplied && props.premultiplied == false -> out.depremultiplyInplaceIfRequired()
            this.premultiplied && props.premultiplied == true -> out.premultiplyInplaceIfRequired()
        }
        return BitmapNativeImage(out)
    }

    fun Bitmap.toNativeImageResult(props: ImageDecodingProps): NativeImageResult = NativeImageResult(this.toNativeImage(props))
}

suspend fun BmpSlice.showImageAndWait(kind: Int = 0) = extract().showImageAndWait(kind)
suspend fun Bitmap.showImageAndWait(kind: Int = 0) = nativeImageFormatProvider.display(this, kind)
suspend fun ImageData.showImagesAndWait(kind: Int = 0) { for (frame in frames) frame.bitmap.showImageAndWait(kind) }
suspend fun List<Bitmap>.showImagesAndWait(kind: Int = 0) { for (bitmap in this) bitmap.showImageAndWait(kind) }
suspend fun SizedDrawable.showImageAndWait(kind: Int = 0) = this.render().toBMP32().showImageAndWait(kind)

open class RegisteredImageFormatsImageFormatProvider : BaseNativeImageFormatProvider() {
    override val formats: ImageFormatsMutable get() = RegisteredImageFormats

    override suspend fun decodeHeaderInternal(data: ByteArray): ImageInfo {
        return formats.decodeHeaderSuspend(data.openAsync()) ?: error("Unsupported format")
    }

    override suspend fun decodeInternal(data: ByteArray, props: ImageDecodingProps): NativeImageResult {
        return formats.decodeSuspend(data, props).toNativeImageResult(props)
    }

    override suspend fun encodeSuspend(image: ImageDataContainer, props: ImageEncodingProps): ByteArray {
        val format = formats.formatByExtOrNull(props.filename.pathInfo.extensionLC) ?: formats.formats.last()
        return format.encode(image.default)
        //return PNG.encode(image.default.mainBitmap)
    }

}

open class BaseNativeImageFormatProvider : NativeImageFormatProvider() {
    open val formats: ImageFormat get() = RegisteredImageFormats

    override suspend fun decodeHeaderInternal(data: ByteArray): ImageInfo {
        return CoreImage.info(data).toImageInfo()
    }

    override suspend fun decodeInternal(data: ByteArray, props: ImageDecodingProps): NativeImageResult {
        return wrapNative(CoreImage.decodeBytes(data).toBitmap(), props)
    }

    override suspend fun encodeSuspend(image: ImageDataContainer, props: ImageEncodingProps): ByteArray {
        return CoreImage.encode(image.mainBitmap.toCoreImage(), CoreImageFormat.fromMimeType(props.mimeType), props.quality)
    }

    protected open fun createBitmapNativeImage(bmp: Bitmap): BitmapNativeImage = BitmapNativeImage(bmp)
    protected open fun wrapNative(bmp: Bitmap, props: ImageDecodingProps): NativeImageResult {
        val bmp32: Bitmap32 = bmp.toBMP32IfRequired()
        //bmp32.premultiplyInPlace()
        //return BitmapNativeImage(bmp32)
        return NativeImageResult(createBitmapNativeImage(
            when {
                props.asumePremultiplied -> bmp32.asumePremultiplied()
                props.premultipliedSure -> bmp32.premultipliedIfRequired()
                else -> bmp32.depremultipliedIfRequired()
            }
        ))
    }
    protected fun Bitmap.wrapNativeExt(props: ImageDecodingProps = ImageDecodingProps.DEFAULT_PREMULT) = wrapNative(this, props)

    override fun create(width: Int, height: Int, premultiplied: Boolean?): NativeImage = createBitmapNativeImage(Bitmap32(width, height, premultiplied = premultiplied ?: true))
    override fun copy(bmp: Bitmap): NativeImage = createBitmapNativeImage(bmp)
    override suspend fun display(bitmap: Bitmap, kind: Int) {
        println("TODO: NativeNativeImageFormatProvider.display(bitmap=$bitmap, kind=$kind)")
    }
    override fun mipmap(bmp: Bitmap, levels: Int): NativeImage = createBitmapNativeImage(bmp)
    override fun mipmap(bmp: Bitmap): NativeImage = createBitmapNativeImage(bmp)
}

open class BitmapNativeImage(val bitmap: Bitmap32) : NativeImage(bitmap.width, bitmap.height, bitmap, bitmap.premultiplied) {
    override val name: String get() = "BitmapNativeImage"
    @Suppress("unused")
    val intData: IntArray = bitmap.ints
    constructor(bitmap: Bitmap) : this(bitmap.toBMP32IfRequired())
    override fun getContext2d(antialiasing: Boolean): Context2d = bitmap.getContext2d(antialiasing)
    override fun toBMP32(): Bitmap32 = bitmap
    override fun readPixelsUnsafe(x: Int, y: Int, width: Int, height: Int, out: IntArray, offset: Int) = bitmap.readPixelsUnsafe(x, y, width, height, out, offset)
    override fun writePixelsUnsafe(x: Int, y: Int, width: Int, height: Int, out: IntArray, offset: Int) = bitmap.writePixelsUnsafe(x, y, width, height, out, offset)
    override fun setRgbaRaw(x: Int, y: Int, v: RGBA) = bitmap.setRgbaRaw(x, y, v)
    override fun getRgbaRaw(x: Int, y: Int): RGBA = bitmap.getRgbaRaw(x, y)
}
