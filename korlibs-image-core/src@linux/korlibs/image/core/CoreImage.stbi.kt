package korlibs.image.core

import kotlinx.cinterop.*
import kotlinx.coroutines.*
import stb_image.*

@OptIn(ExperimentalForeignApi::class)
object StbiCoreImageFormatProvider : CoreImageFormatProvider {
    override suspend fun info(data: ByteArray): CoreImageInfo = withContext(Dispatchers.IO) {
        memScoped {
            val width = alloc<IntVar>()
            val height = alloc<IntVar>()
            val comp = alloc<IntVar>()
            data.usePinned {
                stbi_info_from_memory(it.addressOf(0).reinterpret(), data.size, width.ptr, height.ptr, comp.ptr)
            }
            CoreImageInfo(width.value, height.value, comp.value * 8)
        }
    }

    override suspend fun decode(data: ByteArray): CoreImage = withContext(Dispatchers.IO) {
        memScoped {
            val width = alloc<IntVar>()
            val height = alloc<IntVar>()
            val comp = alloc<IntVar>()
            val dataPtr = data.usePinned {
                stbi_load_from_memory(it.addressOf(0).reinterpret(), data.size, width.ptr, height.ptr, comp.ptr, 4)
            } ?: error("Couldn't decode image")
            val dataInts = dataPtr.reinterpret<IntVar>()
            val data = IntArray(width.value * height.value) { dataInts[it] }
            stbi_image_free(dataPtr)
            CoreImage32(width.value, height.value, data, premultiplied = false).premultiplied()
        }
    }

    override suspend fun encode(image: CoreImage, format: CoreImageFormat, level: Double): ByteArray = withContext(Dispatchers.IO) {
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

        val bmp = image.to32().depremultiplied()
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
                UInt
            } finally {
                outRef.dispose()
            }
        }
        out.toByteArray()
    }
}