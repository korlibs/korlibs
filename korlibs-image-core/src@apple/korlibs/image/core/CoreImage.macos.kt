package korlibs.image.core

import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.CoreFoundation.*
import platform.CoreGraphics.*
import platform.CoreServices.*
import platform.ImageIO.*
import platform.posix.*
import kotlin.math.*

actual val CoreImageFormatProvider_default: CoreImageFormatProvider = AppleCoreImageFormatProvider

// @TODO: Use Apple decoder
@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class, BetaInteropApi::class)
object AppleCoreImageFormatProvider : CoreImageFormatProvider {
    override suspend fun info(data: ByteArray): CoreImageInfo = withContext(Dispatchers.IO) {
        memScoped {
            autoreleasepool {
                val cfdata = data.usePinned { dataPin ->
                    CFDataCreate(null, dataPin.addressOf(0).reinterpret(), data.size.convert())
                }
                val imgSource = CGImageSourceCreateWithData(data = cfdata, options = null)
                val props = CGImageSourceCopyPropertiesAtIndex(imgSource, 0.convert(), null)
                    ?: error("Failed trying to read image in decodeHeaderInternal")

                try {
                    CoreImageInfo(
                        width = getIntFromDict(props, kCGImagePropertyPixelWidth),
                        height = getIntFromDict(props, kCGImagePropertyPixelHeight)
                    )
                } finally {
                    CFRelease(props)
                }
            }
        }
    }

    override suspend fun decode(data: ByteArray): CoreImage = withContext(Dispatchers.IO) {
        val premultiplied = true
        val maxSize: Int? = null
        memScoped {
            val maxSizePtr = alloc<IntVar>()
            autoreleasepool {
                val cfdata = data.usePinned { dataPin -> CFDataCreate(null, dataPin.addressOf(0).reinterpret(), data.size.convert()) }
                val imgSource = CGImageSourceCreateWithData(data = cfdata, options = null)

                val dict = CFDictionaryCreateMutable(null, 0, null, null)

                CFDictionaryAddValue(dict, kCGImageSourceShouldCache, kCFBooleanFalse)
                CFDictionaryAddValue(dict, kCGImageSourceCreateThumbnailWithTransform, kCFBooleanFalse)
                CFDictionaryAddValue(dict, kCGImageSourceCreateThumbnailFromImageAlways, kCFBooleanTrue)

                val cgImage: CGImageRef? = if (maxSize != null) {
                    maxSizePtr.value = maxSize
                    CFDictionaryAddValue(dict, kCGImageSourceThumbnailMaxPixelSize, CFNumberCreate(null, kCFNumberSInt32Type, maxSizePtr.ptr))
                    CGImageSourceCreateThumbnailAtIndex(imgSource, 0.convert(), dict)
                } else {
                    CGImageSourceCreateImageAtIndex(imgSource, 0.convert(), dict)
                }
                val iwidth = CGImageGetWidth(cgImage).toInt()
                val iheight = CGImageGetHeight(cgImage).toInt()

                if (iwidth == 0 && iheight == 0) error("Couldn't decode image with CG")

                val colorSpace = CGColorSpaceCreateDeviceRGB()
                try {
                    val alphaInfo = CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value

                    CoreImage32(iwidth, iheight, premultiplied = true).also { bmp ->
                        bmp.data.usePinned { pin ->
                            val context = CGBitmapContextCreate(
                                pin.addressOf(0), iwidth.convert(), iheight.convert(), 8.convert(),
                                (iwidth * 4).convert(), colorSpace, alphaInfo
                            )
                                ?: error("Couldn't create context for $iwidth, $iheight, premultiplied=$premultiplied")

                            try {
                                val rect = CGRectMakeExt(0, 0, iwidth, iheight)
                                CGContextDrawImage(context, rect, cgImage)
                                CGContextFlush(context)
                            } finally {
                                CGImageRelease(cgImage)
                                CGContextRelease(context)
                                CFRelease(imgSource)
                                CFRelease(cfdata)
                            }
                        }
                    }
                } finally {
                    CGColorSpaceRelease(colorSpace)
                }
            }
        }
    }

    override suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Double): ByteArray = withContext(Dispatchers.IO) {
        memScoped {
            val data = CFDataCreateMutable(null, 0)
            try {
                val destination = CGImageDestinationCreateWithData(
                    data, when (format.name.lowercase()) {
                        "jpg", "jpeg" -> kUTTypeJPEG
                        "tiff" -> kUTTypeTIFF
                        "bmp" -> kUTTypeBMP
                        "gif" -> kUTTypeGIF
                        //"heif", "heic" -> UTTypeHEIF
                        else -> kUTTypePNG
                    }, 1.convert(), null
                )
                    ?: error("Failed to create CGImageDestination")

                val imageProperties = CFDictionaryCreateMutable(null, 0, null, null)
                val ref = alloc<DoubleVar>()
                ref.value = level
                val num = CFNumberCreate(null, kCFNumberDoubleType, ref.ptr)
                CFDictionaryAddValue(imageProperties, kCGImageDestinationLossyCompressionQuality, num)

                //println("CGNativeImageFormatProvider.encodeSuspend")
                val cgImage = image.to32().toCGImage()

                try {
                    CGImageDestinationAddImage(destination, cgImage, imageProperties)
                    if (!CGImageDestinationFinalize(destination)) error("Can't write image")
                } finally {
                    CGImageRelease(cgImage)
                    CFRelease(imageProperties)
                    CFRelease(num)
                    CFRelease(destination)
                }
                val length: Int = CFDataGetLength(data).convert()
                CFDataGetMutableBytePtr(data)?.readBytes(length.convert())
                    ?: error("Can't write image")
            } finally {
                CFRelease(data)
            }
        }
    }

    private fun getIntFromDict(props: CFDictionaryRef?, key: CFStringRef?): Int = memScoped {
        alloc<IntVar>().also {
            CFNumberGetValue(CFDictionaryGetValue(props, key)?.reinterpret(), kCFNumberIntType, it.ptr)
        }.value
    }


    private fun swapRB(value: Int): Int = (value and 0xFF00FF00.toInt()) or ((value and 0xFF) shl 16) or ((value ushr 16) and 0xFF)

    fun swapRB(ptr: CPointer<IntVar>?, count: Int) {
        if (ptr == null) return
        for (n in 0 until count) ptr[n] = swapRB(ptr[n])
    }

    fun transferBitmap32CGContext(bmp: CoreImage32, ctx: CGContextRef?, toBitmap: Boolean) {
        val ctxBytesPerRow = CGBitmapContextGetBytesPerRow(ctx).toInt()
        val ctxWidth = CGBitmapContextGetWidth(ctx).toInt()
        val ctxHeight = CGBitmapContextGetHeight(ctx).toInt()
        val pixels = CGBitmapContextGetData(ctx)?.reinterpret<IntVar>() ?: error("Can't get pixels!")
        val minWidth = min(ctxWidth, bmp.width)
        val minHeight = min(ctxHeight, bmp.height)
        val out = bmp.data
        out.usePinned { outPin ->
            val outStart: CPointer<IntVarOf<Int>> = outPin.addressOf(0)
            val widthInBytes: size_t = (minWidth * 4).convert()
            for (n in 0 until minHeight) {
                val bmpPtr = outStart + ctxWidth * n
                val ctxPtr = pixels.reinterpret<ByteVar>() + ctxBytesPerRow * n
                when {
                    toBitmap -> {
                        memcpy(bmpPtr, ctxPtr, widthInBytes)
                        swapRB(bmpPtr?.reinterpret(), minWidth)
                    }
                    else -> {
                        //swapRB(bmpPtr?.reinterpret(), minWidth)
                        memcpy(ctxPtr, bmpPtr, widthInBytes)
                        //swapRB(bmpPtr?.reinterpret(), minWidth) // Reverse since we cannot write directly without memcopy to ctxPtr
                        //swapRB(ctxPtr?.reinterpret(), minWidth)
                    }
                }
            }
        }
    }

    /**
     * Returned image must be deallocated with [CGImageRelease]
     */
    fun transferBitmap32ToCGImage(bmp: CoreImage32, colorSpace: CGColorSpaceRef? = null): CGImageRef? {
        val allocColorSpace = if (colorSpace == null) CGColorSpaceCreateDeviceRGB() else null

        val imageCtx: CGContextRef? = CGBitmapContextCreate(
            null, bmp.width.convert(), bmp.height.convert(),
            8.convert(), 0.convert(), colorSpace ?: allocColorSpace,
            CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value
        )
        try {
            transferBitmap32CGContext(bmp, imageCtx, toBitmap = false)
            return CGBitmapContextCreateImage(imageCtx)
        } finally {
            CGContextRelease(imageCtx)
            if (allocColorSpace != null) CGColorSpaceRelease(allocColorSpace)
        }
    }

}

@OptIn(ExperimentalForeignApi::class)
fun CoreImage32.toCGImage(): CGImageRef? {
    return AppleCoreImageFormatProvider.transferBitmap32ToCGImage(this, null)
}

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
fun CGImageRef.toBitmap32(): CoreImage32 {
    val image = this
    val width = CGImageGetWidth(image).toInt()
    val height = CGImageGetHeight(image).toInt()
    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val ctx = CGBitmapContextCreate(
        null, width.convert(), height.convert(),
        8.convert(), 0.convert(), colorSpace,
        CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value
    )
    CGContextDrawImage(ctx, CGRectMakeExt(0, 0, width, height), image)
    val out = CoreImage32(width, height, premultiplied = true)
    AppleCoreImageFormatProvider.transferBitmap32CGContext(out, ctx, toBitmap = true)
    return out
}

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
private fun CGRectMakeExt(x: Int, y: Int, width: Int, height: Int): CValue<CGRect> = CGRectMake(x.convert(), y.convert(), width.convert(), height.convert())
