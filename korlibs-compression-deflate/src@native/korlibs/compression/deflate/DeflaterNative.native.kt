@file:OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)

package korlibs.compression.deflate

import kotlinx.cinterop.*
import platform.posix.*
import platform.zlib.*
import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds

private const val CHUNK = 8 * 1024 * 1024

actual fun DeflaterNative(windowBits: Int): IDeflater = object : IDeflater {
    val DEBUG_DEFLATE = getenv("DEBUG_DEFLATE")?.toKStringFromUtf8() == "true"
    //val DEBUG_DEFLATE = true

    override suspend fun uncompress(input: DeflaterBitReader, output: DeflaterAsyncOutputStream) {
        memScoped {
            val strm: z_stream = alloc()

            var ret: Int

            val inpArray = ByteArray(CHUNK)
            val outArray = ByteArray(CHUNK)

            //val buffer = ByteArrayDeque(17)
            //suspend fun flush(force: Boolean) {
            //    if (force || buffer.availableRead >= CHUNK) {
            //        while (buffer.availableRead > 0) {
            //            val readCount = buffer.read(outArray, 0, outArray.size)
            //            if (readCount <= 0) break
            //            output.write(outArray, 0, readCount)
            //        }
            //    }
            //}

            var readTime = 0.milliseconds
            var writeTime = 0.milliseconds
            var inflateTime = 0.milliseconds
            var totalTime = 0.milliseconds
            var readCount = 0
            var writeCount = 0
            var inflateCount = 0

            try {
                totalTime = measureTime {
                    inpArray.usePinned { _inp ->
                        outArray.usePinned { _out ->
                            val inp = _inp.addressOf(0)
                            val out = _out.addressOf(0)
                            var tempInputSize = 0
                            memset(strm.ptr, 0, z_stream.size.convert())
                            ret = inflateInit2_(strm.ptr, -windowBits, zlibVersion()?.toKString(), sizeOf<z_stream>().toInt());
                            if (ret != Z_OK) error("Invalid inflateInit2_")

                            do {
                                //println("strm.avail_in: ${strm.avail_in}")
                                readTime += kotlin.time.measureTime {
                                    strm.avail_in = input.read(inpArray, 0, CHUNK).convert()
                                    readCount++
                                }
                                if (strm.avail_in == 0u || strm.avail_in > CHUNK.convert()) break
                                tempInputSize = strm.avail_in.convert()
                                strm.next_in = inp.reinterpret()

                                do {
                                    strm.avail_out = CHUNK.convert()
                                    strm.next_out = out.reinterpret()
                                    inflateCount++
                                    inflateTime += kotlin.time.measureTime {
                                        ret = platform.zlib.inflate(strm.ptr, platform.zlib.Z_NO_FLUSH)
                                    }
                                    check(ret != Z_STREAM_ERROR)
                                    when (ret) {
                                        Z_NEED_DICT -> ret = Z_DATA_ERROR
                                        Z_DATA_ERROR -> error("data error")
                                        Z_MEM_ERROR  -> error("mem error")
                                    }
                                    val have = CHUNK - strm.avail_out.toInt()
                                    //buffer.write(outArray, 0, have)
                                    //flush(false)
                                    writeTime += kotlin.time.measureTime {
                                        output.write(outArray, 0, have)
                                        writeCount++
                                    }
                                } while (strm.avail_out == 0u)
                            } while (ret != Z_STREAM_END)

                            // Return read bytes that were not consumed
                            val remaining = strm.avail_in.toInt()
                            if (remaining > 0) {
                                input.returnToBuffer(inpArray, tempInputSize - remaining, remaining)
                                //error("too much data in DeflateNative stream")
                            }

                            //flush(true)
                        }
                    }
                }
            } finally {
                inflateEnd(strm.ptr)
                if (DEBUG_DEFLATE) {
                    println("DeflateNative.uncompress: inflateCount=$inflateCount, inflateTime=$inflateTime, readCount=$readCount, readTime=$readTime, writeCount=$writeCount, writeTime=$writeTime, totalTime=$totalTime, CHUNK=$CHUNK, input=$input, output=$output")
                }
            }
        }
    }

    override suspend fun compress(
        input: DeflaterBitReader,
        output: DeflaterAsyncOutputStream,
        level: Float
    ) {
        memScoped {
            val strm: z_stream = alloc()

            var ret: Int

            val inpArray = ByteArray(CHUNK)
            val outArray = ByteArray(CHUNK)

            try {
                inpArray.usePinned { _inp ->
                    outArray.usePinned { _out ->
                        val inp = _inp.addressOf(0)
                        val out = _out.addressOf(0)
                        memset(strm.ptr, 0, z_stream.size.convert())
                        val Z_DEFLATED = 8
                        val MAX_MEM_LEVEL = 9
                        val Z_DEFAULT_STRATEGY = 0
                        ret = deflateInit2_(strm.ptr,
                            (level * 10).toInt(), Z_DEFLATED, -windowBits, MAX_MEM_LEVEL, Z_DEFAULT_STRATEGY, zlibVersion()?.toKString(), sizeOf<z_stream>().toInt());
                        if (ret != Z_OK) error("Invalid deflateInit2_")

                        var finalize = false
                        do {
                            val read = input.read(inpArray, 0, CHUNK)
                            if (read <= 0 || read > CHUNK) {
                                finalize = true
                            }
                            strm.avail_in = read.convert()
                            strm.next_in = inp.reinterpret()

                            do {
                                strm.avail_out = CHUNK.convert()
                                strm.next_out = out.reinterpret()
                                ret = deflate(strm.ptr, if (finalize) Z_FINISH else Z_NO_FLUSH)
                                check(ret != Z_STREAM_ERROR)
                                when (ret) {
                                    Z_NEED_DICT -> ret = Z_DATA_ERROR
                                    Z_DATA_ERROR -> error("data error")
                                    Z_MEM_ERROR  -> error("mem error")
                                }
                                val have = CHUNK - strm.avail_out.toInt()
                                output.write(outArray, 0, have)
                            } while (strm.avail_out == 0u)

                            check(strm.avail_in == 0u)

                        } while (!finalize && ret != Z_STREAM_END)
                    }
                }
            } finally {
                deflateEnd(strm.ptr)
            }
        }
    }
}