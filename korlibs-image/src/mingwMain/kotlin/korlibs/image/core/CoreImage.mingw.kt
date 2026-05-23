@file:OptIn(ExperimentalForeignApi::class)

package korlibs.image.core

import kotlinx.atomicfu.*
import kotlinx.cinterop.*
import kotlinx.coroutines.*
import platform.gdiplus.*
import platform.posix.*
import platform.windows.*

actual val CoreImageFormatProvider_default: CoreImageFormatProvider = Win32CoreImageFormatProvider

@OptIn(ExperimentalForeignApi::class)
object Win32CoreImageFormatProvider : CoreImageFormatProvider {
    override suspend fun decode(data: ByteArray): CoreImage = withContext(Dispatchers.IO) {
        memScoped {
            initGdiPlusOnce()
            val premultiplied = true

            val width = alloc<FloatVar>()
            val height = alloc<FloatVar>()
            val pimage = allocArray<COpaquePointerVar>(1)

            data.usePinned { datap ->
                val pdata = datap.addressOf(0)
                val pstream = SHCreateMemStream(pdata.reinterpret(), data.size.convert())!!
                try {
                    if (GdipCreateBitmapFromStream(pstream, pimage).toInt() != 0) {
                        error("Can't decode image")
                    }
                } finally {
                    pstream.pointed.lpVtbl?.pointed?.Release?.invoke(pstream)
                }
            }

            GdipGetImageDimension(pimage[0], width.ptr, height.ptr)

            val rect = alloc<GpRect>().apply {
                X = 0
                Y = 0
                Width = width.value.toInt()
                Height = height.value.toInt()
            }
            val bmpData = alloc<BitmapData>()
            if (GdipBitmapLockBits(pimage[0], rect.ptr.reinterpret(), ImageLockModeRead, if (premultiplied) PixelFormat32bppPARGB else PixelFormat32bppARGB, bmpData.ptr.reinterpret()).toInt() != 0) {
                error("Can't decode image")
            }

            val bmpWidth = bmpData.Width.toInt()
            val bmpHeight = bmpData.Height.toInt()
            val out = IntArray((bmpWidth * bmpHeight).toInt())
            out.usePinned { outp ->
                val o = outp.addressOf(0)
                for (y in 0 until bmpHeight) {
                    val optr = (o.reinterpret<IntVar>() + bmpWidth * y)!!
                    val iptr = (bmpData.Scan0.toLong() + (bmpData.Stride * y)).toCPointer<IntVar>()!!
                    memcpy(optr, iptr, (bmpData.Width * 4.convert()).convert())
                    for (x in 0 until bmpWidth) optr[x] = argbToAbgr(optr[x])
                }
            }

            GdipBitmapUnlockBits(pimage[0], bmpData.ptr)
            GdipDisposeImage(pimage[0])

            //println(out.toList())
            CoreImage32(bmpWidth, bmpHeight, out)
        }
    }

    override suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Double): ByteArray = withContext(Dispatchers.IO) {
        return@withContext StbiCoreImageFormatProvider.encode(image, format, level)

        // https://learn.microsoft.com/es-es/windows/win32/api/gdiplusheaders/nf-gdiplusheaders-image-save(istream_constclsid_constencoderparameters)
        // https://learn.microsoft.com/en-us/windows/win32/api/gdiplusimagecodec/nf-gdiplusimagecodec-getimageencoders
        // https://learn.microsoft.com/en-us/windows/win32/api/gdiplusimagecodec/nf-gdiplusimagecodec-getimageencoderssize
        // https://learn.microsoft.com/en-us/previous-versions/ms534466(v=vs.85)
        memScoped {
            initGdiPlusOnce()

            val numEncoders = alloc<UINTVar>()
            val codecInfoSize = alloc<UINTVar>()

            if (GdipGetImageEncodersSize(numEncoders.ptr, codecInfoSize.ptr) != Ok) {
                error("Can't find image encoders")
            }

            val codecs = allocArray<ImageCodecInfo>(numEncoders.value.convert())

            GdipGetImageEncoders(numEncoders.value, sizeOf<ImageCodecInfo>().convert(), codecs)

            var selectedCodec: ImageCodecInfo? = null

            for (n in 0 until numEncoders.value.toInt()) {
                val codec = codecs[n]
                val mimeType = codec.MimeType?.toKStringFromUtf16() ?: continue
                if (CoreImageFormat.fromMimeType(mimeType) == format) {
                    selectedCodec = codec
                    break
                }
            }

            if (selectedCodec == null) {
                error("Can't find selected codec in image encoders (${numEncoders.value})")
            }

            // @TODO: Create Image
            // @TODO: Create Stream
            //GdipSaveImageToStream(null, stream, selectedCodec?.Clsid, null)

            TODO()
        }
    }
        //StbiCoreImageFormatProvider.encode(image, format, level)

    private var initializedGdiPlus = atomic(false)
    @OptIn(ExperimentalForeignApi::class)
    fun initGdiPlusOnce() {
        if (!initializedGdiPlus.compareAndSet(expect = false, update = true)) return
        memScoped {
            val ptoken = allocArray<ULONG_PTRVar>(1)
            val si = alloc<GdiplusStartupInput>().apply {
                GdiplusVersion = 1.convert()
                DebugEventCallback = null
                SuppressExternalCodecs = FALSE
                SuppressBackgroundThread = FALSE
            }
            GdiplusStartup(ptoken, si.ptr, null)
        }
    }

    // val r: Int get() = (value ushr 0) and 0xFF
    // val g: Int get() = (value ushr 8) and 0xFF
    // val b: Int get() = (value ushr 16) and 0xFF
    // val a: Int get() = (value ushr 24) and 0xFF
    fun argbToAbgr(col: Int): Int = (col and 0xFF00FF00.toInt()) or // GREEN + ALPHA are in place
        ((col and 0xFF) shl 16) or // Swap R
        ((col shr 16) and 0xFF) // Swap B
}
