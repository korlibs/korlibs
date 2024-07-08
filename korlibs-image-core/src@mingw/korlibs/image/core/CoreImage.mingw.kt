@file:OptIn(ExperimentalForeignApi::class)

package korlibs.image.core

import kotlinx.atomicfu.*
import kotlinx.cinterop.*
import platform.gdiplus.*
import platform.posix.*
import platform.windows.*
import stb_image.*

actual val CoreImageFormatProvider_default: CoreImageFormatProvider = Win32CoreImageFormatProvider

@OptIn(ExperimentalForeignApi::class)
object Win32CoreImageFormatProvider : CoreImageFormatProvider {
    override suspend fun decode(data: ByteArray): CoreImage = memScoped {
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

    override suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Float): ByteArray {
        class ByteArrayBuilder {
            private val chunks = ArrayList<ByteArray>()
            fun append(data: ByteArray) { chunks += data }
            fun toByteArray(): ByteArray = ByteArray(chunks.sumOf { it.size }).also {
                var offset = 0
                for (chunk in chunks) {
                    chunk.copyInto(it, destinationOffset = offset)
                    offset += chunk.size
                }
            }
        }

        val bmp = image.to32()
        val out = ByteArrayBuilder()

        bmp.data.usePinned {
            val outRef = StableRef.create(out)
            try {
                val outPtr = outRef.asCPointer()
                val stride = bmp.width * 4
                val callback = staticCFunction { context: COpaquePointer?, data: COpaquePointer?, size: Int ->
                    val out = context?.let { context.asStableRef<ByteArrayBuilder>().get() } ?: return@staticCFunction
                    out.append(data?.readBytes(size) ?: byteArrayOf())
                }
                when (format.name.lowercase()) {
                    "png" -> stbi_write_png_to_func(callback, outPtr, bmp.width, bmp.height, 4, it.addressOf(0), stride)
                    "bmp" -> stbi_write_bmp_to_func(callback, outPtr, bmp.width, bmp.height, 4, it.addressOf(0))
                    "jpg", "jpeg" -> stbi_write_jpg_to_func(callback, outPtr, bmp.width, bmp.height, 4, it.addressOf(0), (level * 100).toInt().coerceIn(0, 100))
                    "tga" -> stbi_write_tga_to_func(callback, outPtr, bmp.width, bmp.height, 4, it.addressOf(0))
                }
                Unit
            } finally {
                outRef.dispose()
            }
        }

        return out.toByteArray()
    }

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
    fun argbToAbgr(col: Int): Int {
        return (col and 0xFF00FF00.toInt()) or // GREEN + ALPHA are in place
            ((col and 0xFF) shl 16) or // Swap R
            ((col shr 16) and 0xFF) // Swap B
    }

}
