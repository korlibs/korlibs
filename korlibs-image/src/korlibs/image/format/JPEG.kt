package korlibs.image.format

import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.io.lang.*
import korlibs.io.stream.*
import korlibs.memory.*
import kotlin.math.*

object JPEG : ImageFormat("jpg", "jpeg") {
    override fun decodeHeader(s: SyncStream, props: ImageDecodingProps): ImageInfo? = try {
        val info = JPEGDecoder.decodeInfo(s.readAll())
        ImageInfo().apply {
            this.width = info.width
            this.height = info.height
            this.bitsPerPixel = 24
        }
    } catch (e: Throwable) {
        null
    }

    override fun readImage(s: SyncStream, props: ImageDecodingProps): ImageData {
        return ImageData(listOf(ImageFrame(JPEGDecoder.decode(s.readAll()))))
    }

    override fun writeImage(image: ImageData, s: SyncStream, props: ImageEncodingProps) {
        s.writeBytes(JPEGEncoder.encode(image.mainBitmap.toBMP32(), quality = (props.quality * 100).toInt()))
    }
}

// https://github.com/eugeneware/jpeg-js/blob/652bfced3ead53808285b1b5fa9c0b589d00bbf0/lib/decoder.js

/*
   Copyright 2011 notmasteryet

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

// - The JPEG specification can be found in the ITU CCITT Recommendation T.81
//   (www.w3.org/Graphics/JPEG/itu-t81.pdf)
// - The JFIF specification can be found in the JPEG File Interchange Format
//   (www.w3.org/Graphics/JPEG/jfif3.pdf)
// - The Adobe Application-Specific JPEG markers in the Supporting the DCT Filters
//   in PostScript Level 2, Technical Note #5116
//   (partners.adobe.com/public/developer/en/ps/sdk/5116.DCT_Filter.pdf)

class JPEGDecoder {
    private var dctZigZag = intArrayOf(
        0,
        1, 8,
        16, 9, 2,
        3, 10, 17, 24,
        32, 25, 18, 11, 4,
        5, 12, 19, 26, 33, 40,
        48, 41, 34, 27, 20, 13, 6,
        7, 14, 21, 28, 35, 42, 49, 56,
        57, 50, 43, 36, 29, 22, 15,
        23, 30, 37, 44, 51, 58,
        59, 52, 45, 38, 31,
        39, 46, 53, 60,
        61, 54, 47,
        55, 62,
        63
    )

    private var dctCos1 = 4017     // cos(pi/16)
    private var dctSin1 = 799      // sin(pi/16)
    private var dctCos3 = 3406     // cos(3*pi/16)
    private var dctSin3 = 2276     // sin(3*pi/16)
    private var dctCos6 = 1567     // cos(6*pi/16)
    private var dctSin6 = 3784     // sin(6*pi/16)
    private var dctSqrt2 = 5793    // sqrt(2)
    private var dctSqrt1d2 = 2896  // sqrt(2) / 2

    @Suppress("UNUSED_PARAMETER")
    class Jfif(
        versionMajor: Int,
        versionMinor: Int,
        densityUnits: Int,
        xDensity: Int,
        yDensity: Int,
        thumbWidth: Int,
        thumbHeight: Int,
        thumbData: UByteArrayInt
    )

    @Suppress("unused")
    class Adobe(
        val version: Int,
        val flags0: Int,
        val flags1: Int,
        val transformCode: Boolean
    )

    private var width: Int = 0
    private var height: Int = 0
    private var jfif: Jfif? = null
    private var adobe: Adobe? = null
    private var components = arrayListOf<Component>()
    private var colorTransform: Boolean? = null

    private fun mceil(v: Float): Int = ceil(v).toInt()

    data class HuffmanNode(var children: ArrayList<Any> = arrayListOf(), var index: Int = 0) {
        fun setChildAt(index: Int, value: Any) {
            while (children.size <= index) children.add(Unit) // Todo use other thing?
            children[index] = value
        }
    }

    private fun buildHuffmanTable(codeLengths: UByteArrayInt, values: UByteArrayInt): List<Any> {
        var k = 0
        val code = arrayListOf<HuffmanNode>()
        var length = 16
        while (length > 0 && (0 == codeLengths[length - 1])) length--
        code.add(HuffmanNode())
        var p: HuffmanNode = code[0]
        var q: HuffmanNode
        for (i in 0 until length) {
            for (j in 0 until codeLengths[i]) {
                p = code.removeAt(code.size - 1)
                p.setChildAt(p.index, values[k])
                while (p.index > 0) {
                    p = code.removeAt(code.size - 1)
                }
                p.index++
                code.add(p)
                while (code.size <= i) {
                    q = HuffmanNode()
                    code.add(q)
                    p.setChildAt(p.index, q.children)
                    p = q
                }
                k++
            }
            if (i + 1 < length) {
                // p here points to last code
                q = HuffmanNode()
                code.add(q)
                p.setChildAt(p.index, q.children)
                p = q
            }
        }
        return code[0].children
    }

    private fun decodeScan(
        data: UByteArrayInt, offset: Int,
        frame: Frame, components: List<FrameComponent>, resetInterval: Int,
        spectralStart: Int, spectralEnd: Int,
        successivePrev: Int, successive: Int
    ): Int {
        @Suppress("NAME_SHADOWING")
        var resetInterval = resetInterval
        @Suppress("NAME_SHADOWING")
        var offset = offset
        val mcusPerLine = frame.mcusPerLine
        val progressive = frame.progressive
        //var precision = frame.precision
        //var samplesPerLine = frame.samplesPerLine
        //var scanLines = frame.scanLines
        //var maxH = frame.maxH
        //var maxV = frame.maxV

        val startOffset = offset
        var bitsData = 0
        var bitsCount = 0

        fun readBit(): Int {
            if (bitsCount > 0) {
                bitsCount--
                return (bitsData shr bitsCount) and 1
            }
            bitsData = data[offset++]
            if (bitsData == 0xFF) {
                val nextByte = data[offset++]
                if (nextByte != 0) {
                    invalidOp("unexpected marker: " + ((bitsData shl 8) or nextByte).toString(16))
                }
                // unstuff 0
            }
            bitsCount = 7
            return bitsData ushr 7
        }

        fun decodeHuffman(tree: List<Any>): Int {
            var node: List<Any> = tree
            while (true) {
                val bit = readBit()
                val res = node[bit]
                @Suppress("UNCHECKED_CAST")
                when (res) {
                    is Int -> return res
                    is List<*> -> node = res as List<Any>
                    else -> invalidOp("invalid huffman sequence")
                }
            }
        }

        fun receive(length: Int): Int {
            var len = length
            var n = 0
            while (len > 0) {
                val bit = readBit()
                n = (n shl 1) or bit
                len--
            }
            return n
        }

        fun receiveAndExtend(length: Int): Int {
            val n = receive(length)
            if (n >= (1 shl (length - 1))) return n
            return n + (-1 shl length) + 1
        }

        fun decodeBaseline(component: FrameComponent, zz: IntArray) {
            val t = decodeHuffman(component.huffmanTableDC)
            val diff = if (t == 0) 0 else receiveAndExtend(t)
            component.pred += diff
            zz[0] = component.pred
            var k = 1
            while (k < 64) {
                val rs = decodeHuffman(component.huffmanTableAC)
                val s = rs and 15
                val r = rs shr 4
                if (s == 0) {
                    if (r < 15) break
                    k += 16
                    continue
                }
                k += r
                val z = dctZigZag[k]
                zz[z] = receiveAndExtend(s)
                k++
            }
        }

        fun decodeDCFirst(component: FrameComponent, zz: IntArray) {
            val t = decodeHuffman(component.huffmanTableDC)
            val diff = if (t == 0) 0 else (receiveAndExtend(t) shl successive)
            component.pred += diff
            zz[0] = component.pred
        }

        fun decodeDCSuccessive(@Suppress("UNUSED_PARAMETER") component: FrameComponent, zz: IntArray) {
            zz[0] = zz[0] or (readBit() shl successive)
        }

        var eobrun = 0
        fun decodeACFirst(component: FrameComponent, zz: IntArray) {
            if (eobrun > 0) {
                eobrun--
                return
            }
            var k = spectralStart
            @Suppress("UnnecessaryVariable")
            val e = spectralEnd
            while (k <= e) {
                val rs = decodeHuffman(component.huffmanTableAC)
                val s = rs and 15
                val r = rs shr 4
                if (s == 0) {
                    if (r < 15) {
                        eobrun = receive(r) + (1 shl r) - 1
                        break
                    }
                    k += 16
                    continue
                }
                k += r
                val z = dctZigZag[k]
                zz[z] = receiveAndExtend(s) * (1 shl successive)
                k++
            }
        }

        var successiveACState = 0
        var successiveACNextValue = 0
        fun decodeACSuccessive(component: FrameComponent, zz: IntArray) {
            var k = spectralStart
            @Suppress("UnnecessaryVariable")
            val e = spectralEnd
            var r = 0
            loop@ while (k <= e) {
                val z = dctZigZag[k]
                val direction = if (zz[z] < 0) -1 else 1
                when (successiveACState) {
                    0 -> {// initial state
                        val rs = decodeHuffman(component.huffmanTableAC)
                        val s = rs and 15
                        r = rs shr 4
                        if (s == 0) {
                            if (r < 15) {
                                eobrun = receive(r) + (1 shl r)
                                successiveACState = 4
                            } else {
                                r = 16
                                successiveACState = 1
                            }
                        } else {
                            if (s != 1) invalidOp("invalid ACn encoding")
                            successiveACNextValue = receiveAndExtend(s)
                            successiveACState = if (r != 0) 2 else 3
                        }
                        continue@loop
                    }
                    1, 2 -> { // skipping r zero items
                        if (zz[z] != 0)
                            zz[z] += (readBit() shl successive) * direction
                        else {
                            r--
                            if (r == 0) successiveACState = if (successiveACState == 2) 3 else 0
                        }
                    }
                    3 -> { // set value for a zero item
                        if (zz[z] != 0)
                            zz[z] += (readBit() shl successive) * direction
                        else {
                            zz[z] = successiveACNextValue shl successive
                            successiveACState = 0
                        }
                    }
                    4 -> { // eob
                        if (zz[z] != 0)
                            zz[z] += (readBit() shl successive) * direction
                    }
                }
                k++
            }
            if (successiveACState == 4) {
                eobrun--
                if (eobrun == 0)
                    successiveACState = 0
            }
        }

        fun decodeMcu(
            component: FrameComponent,
            decode: (FrameComponent, IntArray) -> Unit,
            mcu: Int,
            row: Int,
            col: Int
        ) {
            val mcuRow = (mcu / mcusPerLine) or 0
            val mcuCol = mcu % mcusPerLine
            val blockRow = mcuRow * component.v + row
            val blockCol = mcuCol * component.h + col
            decode(component, component.blocks[blockRow][blockCol])
        }

        fun decodeBlock(component: FrameComponent, decode: (FrameComponent, IntArray) -> Unit, mcu: Int) {
            val blockRow = (mcu / component.blocksPerLine) or 0
            val blockCol = mcu % component.blocksPerLine
            decode(component, component.blocks[blockRow][blockCol])
        }

        val componentsLength = components.size
        var component: FrameComponent
        val decodeFn = if (progressive) {
            if (spectralStart == 0)
                if (successivePrev == 0) ::decodeDCFirst else ::decodeDCSuccessive
            else
                if (successivePrev == 0) ::decodeACFirst else ::decodeACSuccessive
        } else {
            ::decodeBaseline
        }

        var mcu = 0
        val mcuExpected = if (componentsLength == 1) {
            components[0].blocksPerLine * components[0].blocksPerColumn
        } else {
            mcusPerLine * frame.mcusPerColumn
        }
        if (resetInterval == 0) {
            resetInterval = mcuExpected
        }

        var h: Int
        var v: Int
        while (mcu < mcuExpected) {
            // reset interval stuff
            for (i in 0 until componentsLength) components[i].pred = 0
            eobrun = 0

            if (componentsLength == 1) {
                component = components[0]
                for (n in 0 until resetInterval) {
                    decodeBlock(component, decodeFn, mcu)
                    mcu++
                }
            } else {
                for (n in 0 until resetInterval) {
                    for (i in 0 until componentsLength) {
                        component = components[i]
                        h = component.h
                        v = component.v
                        for (j in 0 until v) {
                            for (k in 0 until h) {
                                decodeMcu(component, decodeFn, mcu, j, k)
                            }
                        }
                    }
                    mcu++

                    // If we've reached our expected MCU's, stop decoding
                    if (mcu == mcuExpected) break
                }
            }

            // find marker
            bitsCount = 0
            val marker = (data[offset] shl 8) or data[offset + 1]
            if (marker < 0xFF00) {
                invalidOp("marker was not found")
            }

            if (marker in 0xFFD0..0xFFD7) { // RSTx
                offset += 2
            } else {
                break
            }
        }

        return offset - startOffset
    }

    private fun buildComponentData(
        @Suppress("UNUSED_PARAMETER") frame: Frame,
        component: FrameComponent
    ): List<UByteArrayInt> {
        val lines = arrayListOf<UByteArrayInt>()
        val blocksPerLine = component.blocksPerLine
        val blocksPerColumn = component.blocksPerColumn
        val samplesPerLine = blocksPerLine shl 3
        val rr = IntArray(64)
        val r = UByteArrayInt(64)

        // A port of poppler's IDCT method which in turn is taken from:
        //   Christoph Loeffler, Adriaan Ligtenberg, George S. Moschytz,
        //   "Practical Fast 1-D DCT Algorithms with 11 Multiplications",
        //   IEEE Intl. Conf. on Acoustics, Speech and Signal Processing, 1989,
        //   988-991.
        fun quantizeAndInverse(zz: IntArray, dataOut: UByteArrayInt, dataIn: IntArray) {
            val qt = component.quantizationTable
            @Suppress("UnnecessaryVariable")
            val p = dataIn

            // dequant
            for (i in 0 until 64) p[i] = zz[i] * qt[i]

            // inverse DCT on rows
            for (i in 0 until 8) {
                val row = 8 * i

                // check for all-zero AC coefficients
                if (p[1 + row] == 0 && p[2 + row] == 0 && p[3 + row] == 0 &&
                    p[4 + row] == 0 && p[5 + row] == 0 && p[6 + row] == 0 &&
                    p[7 + row] == 0
                ) {
                    val t = (dctSqrt2 * p[0 + row] + 512) shr 10
                    p[0 + row] = t
                    p[1 + row] = t
                    p[2 + row] = t
                    p[3 + row] = t
                    p[4 + row] = t
                    p[5 + row] = t
                    p[6 + row] = t
                    p[7 + row] = t
                    continue
                }

                // stage 4
                var v0 = (dctSqrt2 * p[0 + row] + 128) shr 8
                var v1 = (dctSqrt2 * p[4 + row] + 128) shr 8
                var v2 = p[2 + row]
                var v3 = p[6 + row]
                var v4 = (dctSqrt1d2 * (p[1 + row] - p[7 + row]) + 128) shr 8
                var v7 = (dctSqrt1d2 * (p[1 + row] + p[7 + row]) + 128) shr 8
                var v5 = p[3 + row] shl 4
                var v6 = p[5 + row] shl 4

                // stage 3
                var t = (v0 - v1 + 1) shr 1
                v0 = (v0 + v1 + 1) shr 1
                v1 = t
                t = (v2 * dctSin6 + v3 * dctCos6 + 128) shr 8
                v2 = (v2 * dctCos6 - v3 * dctSin6 + 128) shr 8
                v3 = t
                t = (v4 - v6 + 1) shr 1
                v4 = (v4 + v6 + 1) shr 1
                v6 = t
                t = (v7 + v5 + 1) shr 1
                v5 = (v7 - v5 + 1) shr 1
                v7 = t

                // stage 2
                t = (v0 - v3 + 1) shr 1
                v0 = (v0 + v3 + 1) shr 1
                v3 = t
                t = (v1 - v2 + 1) shr 1
                v1 = (v1 + v2 + 1) shr 1
                v2 = t
                t = (v4 * dctSin3 + v7 * dctCos3 + 2048) shr 12
                v4 = (v4 * dctCos3 - v7 * dctSin3 + 2048) shr 12
                v7 = t
                t = (v5 * dctSin1 + v6 * dctCos1 + 2048) shr 12
                v5 = (v5 * dctCos1 - v6 * dctSin1 + 2048) shr 12
                v6 = t

                // stage 1
                p[0 + row] = v0 + v7
                p[7 + row] = v0 - v7
                p[1 + row] = v1 + v6
                p[6 + row] = v1 - v6
                p[2 + row] = v2 + v5
                p[5 + row] = v2 - v5
                p[3 + row] = v3 + v4
                p[4 + row] = v3 - v4
            }

            // inverse DCT on columns
            for (col in 0 until 8) {
                // check for all-zero AC coefficients
                if (p[1 * 8 + col] == 0 && p[2 * 8 + col] == 0 && p[3 * 8 + col] == 0 &&
                    p[4 * 8 + col] == 0 && p[5 * 8 + col] == 0 && p[6 * 8 + col] == 0 &&
                    p[7 * 8 + col] == 0
                ) {
                    val t = (dctSqrt2 * dataIn[col + 0] + 8192) shr 14
                    p[0 * 8 + col] = t
                    p[1 * 8 + col] = t
                    p[2 * 8 + col] = t
                    p[3 * 8 + col] = t
                    p[4 * 8 + col] = t
                    p[5 * 8 + col] = t
                    p[6 * 8 + col] = t
                    p[7 * 8 + col] = t
                    continue
                }

                // stage 4
                var v0 = (dctSqrt2 * p[0 * 8 + col] + 2048) shr 12
                var v1 = (dctSqrt2 * p[4 * 8 + col] + 2048) shr 12
                var v2 = p[2 * 8 + col]
                var v3 = p[6 * 8 + col]
                var v4 = (dctSqrt1d2 * (p[1 * 8 + col] - p[7 * 8 + col]) + 2048) shr 12
                var v7 = (dctSqrt1d2 * (p[1 * 8 + col] + p[7 * 8 + col]) + 2048) shr 12
                var v5 = p[3 * 8 + col]
                var v6 = p[5 * 8 + col]

                // stage 3
                var t = (v0 - v1 + 1) shr 1
                v0 = (v0 + v1 + 1) shr 1
                v1 = t
                t = (v2 * dctSin6 + v3 * dctCos6 + 2048) shr 12
                v2 = (v2 * dctCos6 - v3 * dctSin6 + 2048) shr 12
                v3 = t
                t = (v4 - v6 + 1) shr 1
                v4 = (v4 + v6 + 1) shr 1
                v6 = t
                t = (v7 + v5 + 1) shr 1
                v5 = (v7 - v5 + 1) shr 1
                v7 = t

                // stage 2
                t = (v0 - v3 + 1) shr 1
                v0 = (v0 + v3 + 1) shr 1
                v3 = t
                t = (v1 - v2 + 1) shr 1
                v1 = (v1 + v2 + 1) shr 1
                v2 = t
                t = (v4 * dctSin3 + v7 * dctCos3 + 2048) shr 12
                v4 = (v4 * dctCos3 - v7 * dctSin3 + 2048) shr 12
                v7 = t
                t = (v5 * dctSin1 + v6 * dctCos1 + 2048) shr 12
                v5 = (v5 * dctCos1 - v6 * dctSin1 + 2048) shr 12
                v6 = t

                // stage 1
                p[0 * 8 + col] = v0 + v7
                p[7 * 8 + col] = v0 - v7
                p[1 * 8 + col] = v1 + v6
                p[6 * 8 + col] = v1 - v6
                p[2 * 8 + col] = v2 + v5
                p[5 * 8 + col] = v2 - v5
                p[3 * 8 + col] = v3 + v4
                p[4 * 8 + col] = v3 - v4
            }

            // convert to 8-bit integers
            for (i in 0 until 64) {
                val sample = 128 + ((p[i] + 8) shr 4)
                dataOut[i] = if (sample < 0) 0 else if (sample > 0xFF) 0xFF else sample
            }
        }

        for (blockRow in 0 until blocksPerColumn) {
            val scanLine = blockRow shl 3
            for (i in 0 until 8)
                lines.add(UByteArrayInt(samplesPerLine))
            for (blockCol in 0 until blocksPerLine) {
                quantizeAndInverse(component.blocks[blockRow][blockCol], r, rr)

                var offset = 0
                val sample = blockCol shl 3
                for (j in 0 until 8) {
                    val line = lines[scanLine + j]
                    for (i in 0 until 8)
                        line[sample + i] = r[offset++]
                }
            }
        }
        return lines
    }

    private fun clampTo8bit(a: Int): Int = if (a < 0) 0 else if (a > 255) 255 else a
    //private fun clampTo8bit(a: Float): Float = if (a < 0f) 0f else if (a > 255f) 255f else a

    private fun UByteArrayInt.subarray(from: Int, to: Int) = UByteArrayInt(this.asByteArray().copyOfRange(from, to))

    class FrameComponent(
        val h: Int,
        val v: Int,
        var quantizationIdx: Int
    ) {
        var huffmanTableDC: List<Any> = emptyList()
        var huffmanTableAC: List<Any> = emptyList()
        var quantizationTable: IntArray = IntArray(0)
        var pred: Int = 0
        var blocksPerLine: Int = 0
        var blocksPerColumn: Int = 0
        var blocks = emptyList<List<IntArray>>()
    }

    @Suppress("unused")
    class Frame(
        var extended: Boolean,
        var progressive: Boolean,
        var precision: Int,
        var scanLines: Int,
        var samplesPerLine: Int,
        var components: ArrayList<FrameComponent>,
        var componentsOrder: ArrayList<Int>,
        var maxH: Int = 0,
        var maxV: Int = 0
    ) {
        var mcusPerLine: Int = 0
        var mcusPerColumn: Int = 0
    }

    fun parse(data: UByteArrayInt) {
        var offset = 0
        //var length = data.size
        fun readUint16(): Int {
            val value = (data[offset] shl 8) or data[offset + 1]
            offset += 2
            return value
        }

        fun readDataBlock(): UByteArrayInt {
            val len = readUint16()
            val array = UByteArrayInt(data.asByteArray().copyOfRange(offset, offset + len - 2))
            offset += array.size
            return array
        }

        fun prepareComponents(frame: Frame) {
            var maxH = 0
            var maxV = 0
            for (component in frame.components) {
                if (maxH < component.h) maxH = component.h
                if (maxV < component.v) maxV = component.v
            }
            val mcusPerLine = mceil(frame.samplesPerLine.toFloat() / 8f / maxH.toFloat())
            val mcusPerColumn = mceil(frame.scanLines.toFloat() / 8f / maxV.toFloat())
            for (component in frame.components) {
                val blocksPerLine =
                    mceil(mceil(frame.samplesPerLine.toFloat() / 8f) * component.h.toFloat() / maxH.toFloat())
                val blocksPerColumn =
                    mceil(mceil(frame.scanLines.toFloat() / 8f) * component.v.toFloat() / maxV.toFloat())
                val blocksPerLineForMcu = mcusPerLine * component.h
                val blocksPerColumnForMcu = mcusPerColumn * component.v
                val blocks = arrayListOf<ArrayList<IntArray>>()
                for (i in 0 until blocksPerColumnForMcu) {
                    val row = arrayListOf<IntArray>()
                    for (j in 0 until blocksPerLineForMcu)
                        row.add(IntArray(64))
                    blocks.add(row)
                }
                component.blocksPerLine = blocksPerLine
                component.blocksPerColumn = blocksPerColumn
                component.blocks = blocks
            }
            frame.maxH = maxH
            frame.maxV = maxV
            frame.mcusPerLine = mcusPerLine
            frame.mcusPerColumn = mcusPerColumn
        }

        var jfif: Jfif? = null
        var adobe: Adobe? = null
        //var pixels = null
        lateinit var frame: Frame
        var resetInterval = 0
        val quantizationTables = ArrayList((0 until 16).map { IntArray(0) })
        val frames = arrayListOf<Frame>()
        val huffmanTablesAC = ArrayList((0 until 16).map { emptyList<Any>() })
        val huffmanTablesDC = ArrayList((0 until 16).map { emptyList<Any>() })
        var fileMarker = readUint16()
        if (fileMarker != 0xFFD8) { // SOI (Start of Image)
            invalidOp("SOI not found")
        }

        fileMarker = readUint16()
        while (fileMarker != 0xFFD9) { // EOI (End of image)
            when (fileMarker) {
                0xFF00 -> Unit
                // APP0-15 (Application Specific), COM (Comment)
                0xFFE0, 0xFFE1, 0xFFE2, 0xFFE3, 0xFFE4, 0xFFE5, 0xFFE6, 0xFFE7, 0xFFE8,
                0xFFE9, 0xFFEA, 0xFFEB, 0xFFEC, 0xFFED, 0xFFEE, 0xFFEF, 0xFFFE -> {
                    val appData = readDataBlock()

                    if (fileMarker == 0xFFE0) {
                        if (appData[0] == 0x4A && appData[1] == 0x46 && appData[2] == 0x49 &&
                            appData[3] == 0x46 && appData[4] == 0
                        ) { // 'JFIF\x00'
                            jfif = Jfif(
                                versionMajor = appData[5],
                                versionMinor = appData[6],
                                densityUnits = appData[7],
                                xDensity = (appData[8] shl 8) or appData[9],
                                yDensity = (appData[10] shl 8) or appData[11],
                                thumbWidth = appData[12],
                                thumbHeight = appData[13],
                                thumbData = appData.subarray(14, 14 + 3 * appData[12] * appData[13])
                            )
                        }
                    }
                    // TODO APP1 - Exif
                    if (fileMarker == 0xFFEE) {
                        if (appData[0] == 0x41 && appData[1] == 0x64 && appData[2] == 0x6F &&
                            appData[3] == 0x62 && appData[4] == 0x65 && appData[5] == 0
                        ) { // 'Adobe\x00'
                            adobe = Adobe(
                                version = appData[6],
                                flags0 = (appData[7] shl 8) or appData[8],
                                flags1 = (appData[9] shl 8) or appData[10],
                                transformCode = appData[11] != 0
                            )
                        }
                    }
                }
                0xFFDB -> { // DQT (Define Quantization Tables)
                    val quantizationTablesLength = readUint16()
                    val quantizationTablesEnd = quantizationTablesLength + offset - 2
                    while (offset < quantizationTablesEnd) {
                        val quantizationTableSpec = data[offset++]
                        val tableData = IntArray(64)
                        when (quantizationTableSpec shr 4) {
                            0 -> for (j in 0 until 64) tableData[dctZigZag[j]] = data[offset++] // 8 bit values
                            1 -> for (j in 0 until 64) tableData[dctZigZag[j]] = readUint16() //16 bit
                            else -> invalidOp("DQT: invalid table spec")
                        }
                        quantizationTables[quantizationTableSpec and 15] = tableData
                    }
                }
                0xFFC0, 0xFFC1, 0xFFC2 -> {
                    // SOF0 (Start of Frame, Baseline DCT), SOF1 (Start of Frame, Extended DCT), SOF2 (Start of Frame, Progressive DCT)
                    readUint16() // skip data length
                    frame = Frame(
                        extended = (fileMarker == 0xFFC1),
                        progressive = (fileMarker == 0xFFC2),
                        precision = data[offset++],
                        scanLines = readUint16(),
                        samplesPerLine = readUint16(),
                        components = ArrayList(),
                        componentsOrder = arrayListOf()
                    )
                    val componentsCount = data[offset++]
                    var componentId: Int
                    //var maxH = 0
                    //var maxV = 0
                    for (i in 0 until componentsCount) {
                        componentId = data[offset]
                        val h = data[offset + 1] shr 4
                        val v = data[offset + 1] and 15
                        val qId = data[offset + 2]
                        frame.componentsOrder.add(componentId)
                        while (frame.components.size <= componentId) frame.components.add(FrameComponent(0, 0, 0))
                        frame.components[componentId] = FrameComponent(h = h, v = v, quantizationIdx = qId)
                        offset += 3
                    }
                    prepareComponents(frame)
                    frames.add(frame)
                }
                0xFFC4 -> { // DHT (Define Huffman Tables)
                    val huffmanLength = readUint16()
                    var i = 2
                    while (i < huffmanLength) {
                        val huffmanTableSpec = data[offset++]
                        val codeLengths = UByteArrayInt(16)
                        var codeLengthSum = 0
                        for (j in 0 until 16) {
                            codeLengths[j] = data[offset]
                            codeLengthSum += codeLengths[j]
                            offset++
                        }
                        val huffmanValues = UByteArrayInt(codeLengthSum)
                        for (j in 0 until codeLengthSum) {
                            huffmanValues[j] = data[offset]
                            offset++
                        }

                        i += 17 + codeLengthSum

                        val table = if ((huffmanTableSpec shr 4) == 0) huffmanTablesDC else huffmanTablesAC
                        table[huffmanTableSpec and 15] = buildHuffmanTable(codeLengths, huffmanValues)
                    }
                }

                0xFFDD -> { // DRI (Define Restart Interval)
                    readUint16() // skip data length
                    resetInterval = readUint16()
                }
                0xFFDA -> { // SOS (Start of Scan)
                    @Suppress("UNUSED_VARIABLE")
                    var scanLength = readUint16()
                    val selectorsCount = data[offset++]
                    val components = arrayListOf<FrameComponent>()
                    var component: FrameComponent
                    for (i in 0 until selectorsCount) {
                        component = frame.components[data[offset++]]
                        val tableSpec = data[offset++]
                        component.huffmanTableDC = huffmanTablesDC[tableSpec shr 4]
                        component.huffmanTableAC = huffmanTablesAC[tableSpec and 15]
                        components.add(component)
                    }
                    val spectralStart = data[offset++]
                    val spectralEnd = data[offset++]
                    val successiveApproximation = data[offset++]
                    val processed = decodeScan(
                        data, offset,
                        frame, components, resetInterval,
                        spectralStart, spectralEnd,
                        successiveApproximation shr 4,
                        successiveApproximation and 15
                    )
                    offset += processed
                }
                else -> {
                    if (data[offset - 3] == 0xFF &&
                        data[offset - 2] >= 0xC0 && data[offset - 2] <= 0xFE
                    ) {
                        // could be incorrect encoding -- last 0xFF byte of the previous
                        // block was eaten by the encoder
                        offset -= 3
                    } else {
                        invalidOp("unknown JPEG marker " + fileMarker.toString(16))
                    }
                }
            }
            fileMarker = readUint16()
        }

        if (frames.size != 1) invalidOp("only single frame JPEGs supported")

        // set each frame's components quantization table
        for (i in 0 until frames.size) {
            val cp = frames[i].components
            for (c in cp) {
                c.quantizationTable = quantizationTables[c.quantizationIdx]
                c.quantizationIdx = -1
            }
        }

        this.width = frame.samplesPerLine
        this.height = frame.scanLines
        this.jfif = jfif
        this.adobe = adobe
        this.components = arrayListOf()
        for (i in 0 until frame.componentsOrder.size) {
            val component = frame.components[frame.componentsOrder[i]]
            this.components.add(
                Component(
                    lines = buildComponentData(frame, component),
                    scaleX = component.h.toFloat() / frame.maxH.toFloat(),
                    scaleY = component.v.toFloat() / frame.maxV.toFloat()
                )
            )
        }
    }

    class Component(val lines: List<UByteArrayInt>, val scaleX: Float, val scaleY: Float)

    private fun getData(width: Int, height: Int): UByteArrayInt {
        val scaleX = this.width / width
        val scaleY = this.height / height

        var offset = 0
        val dataLength = width * height * this.components.size
        val data = UByteArrayInt(dataLength)

        when (this.components.size) {
            1 -> {
                val component1 = this.components[0]
                for (y in 0 until height) {
                    val component1Line = component1.lines[((y * component1.scaleY * scaleY).toInt())]
                    for (x in 0 until width) {
                        data[offset++] = component1Line[((x * component1.scaleX * scaleX).toInt())]
                    }
                }
            }
            2 -> {
                // PDF might compress two component data in custom colorspace
                val component1 = this.components[0]
                val component2 = this.components[1]
                for (y in 0 until height) {
                    val component1Line = component1.lines[((y * component1.scaleY * scaleY).toInt())]
                    val component2Line = component2.lines[((y * component2.scaleY * scaleY).toInt())]
                    for (x in 0 until width) {
                        data[offset++] = component1Line[((x * component1.scaleX * scaleX).toInt())]
                        data[offset++] = component2Line[((x * component2.scaleX * scaleX).toInt())]
                    }
                }
            }
            3 -> {
                // The adobe transform marker overrides any previous setting
                val colorTransform = when {
                    this.adobe?.transformCode == true -> true
                    else -> this.colorTransform ?: true
                }

                val component1 = this.components[0]
                val component2 = this.components[1]
                val component3 = this.components[2]
                for (y in 0 until height) {
                    val component1Line = component1.lines[((y * component1.scaleY * scaleY).toInt())]
                    val component2Line = component2.lines[((y * component2.scaleY * scaleY).toInt())]
                    val component3Line = component3.lines[((y * component3.scaleY * scaleY).toInt())]

                    if (!colorTransform) {
                        for (x in 0 until width) {
                            data[offset++] = component1Line[((x * component1.scaleX * scaleX).toInt())]
                            data[offset++] = component2Line[((x * component2.scaleX * scaleX).toInt())]
                            data[offset++] = component3Line[((x * component3.scaleX * scaleX).toInt())]
                        }
                    } else {
                        for (x in 0 until width) {
                            val yy = component1Line[((x * component1.scaleX * scaleX).toInt())]
                            val cb = component2Line[((x * component2.scaleX * scaleX).toInt())]
                            val cr = component3Line[((x * component3.scaleX * scaleX).toInt())]
                            data[offset++] = clampTo8bit((yy + 1.402f * (cr - 128f)).toInt())
                            data[offset++] =
                                clampTo8bit((yy - 0.3441363f * (cb - 128f) - 0.71413636f * (cr - 128f)).toInt())
                            data[offset++] = clampTo8bit((yy + 1.772f * (cb - 128f)).toInt())
                        }
                    }
                }
            }
            4 -> {
                if (this.adobe == null) invalidOp("Unsupported color mode (4 components)")
                // The default transform for four components is false
                // The adobe transform marker overrides any previous setting
                val colorTransform = when {
                    this.adobe?.transformCode == true -> true
                    else -> this.colorTransform ?: false
                }
                val component1 = this.components[0]
                val component2 = this.components[1]
                val component3 = this.components[2]
                val component4 = this.components[3]
                for (y in 0 until height) {
                    val component1Line = component1.lines[(y * component1.scaleY * scaleY).toInt()]
                    val component2Line = component2.lines[(y * component2.scaleY * scaleY).toInt()]
                    val component3Line = component3.lines[(y * component3.scaleY * scaleY).toInt()]
                    val component4Line = component4.lines[(y * component4.scaleY * scaleY).toInt()]
                    for (x in 0 until width) {
                        val c: Int
                        val m: Int
                        val ye: Int
                        val k: Int

                        if (!colorTransform) {
                            c = component1Line[(x * component1.scaleX * scaleX).toInt()]
                            m = component2Line[(x * component2.scaleX * scaleX).toInt()]
                            ye = component3Line[(x * component3.scaleX * scaleX).toInt()]
                            k = component4Line[(x * component4.scaleX * scaleX).toInt()]
                        } else {
                            val yy = component1Line[(x * component1.scaleX * scaleX).toInt()]
                            val cb = component2Line[(x * component2.scaleX * scaleX).toInt()]
                            val cr = component3Line[(x * component3.scaleX * scaleX).toInt()]

                            k = component4Line[(x * component4.scaleX * scaleX).toInt()]

                            c = 255 - clampTo8bit((yy + 1.402 * (cr - 128)).toInt())
                            m = 255 - clampTo8bit((yy - 0.3441363 * (cb - 128) - 0.71413636 * (cr - 128)).toInt())
                            ye = 255 - clampTo8bit((yy + 1.772 * (cb - 128)).toInt())
                        }
                        data[offset++] = 255 - c
                        data[offset++] = 255 - m
                        data[offset++] = 255 - ye
                        data[offset++] = 255 - k
                    }
                }
            }
            else -> invalidOp("Unsupported color mode")
        }
        return data
    }

    fun copyToImageData(imageData: ImageData) {
        val width = imageData.width
        val height = imageData.height
        val imageDataArray = imageData.data
        val data = this.getData(width, height)
        var i = 0
        var j = 0
        when (this.components.size) {
            1 -> {
                for (n in 0 until width * height) {
                    val yy = data[i++]
                    imageDataArray[j++] = yy
                    imageDataArray[j++] = yy
                    imageDataArray[j++] = yy
                    imageDataArray[j++] = 255
                }
            }
            3 -> {
                for (n in 0 until width * height) {
                    imageDataArray[j++] = data[i++]
                    imageDataArray[j++] = data[i++]
                    imageDataArray[j++] = data[i++]
                    imageDataArray[j++] = 255
                }
            }
            4 -> {
                for (n in 0 until width * height) {
                    val c = data[i++]
                    val m = data[i++]
                    val y = data[i++]
                    val k = data[i++]

                    imageDataArray[j++] = 255 - clampTo8bit(c * (1 - k / 255) + k)
                    imageDataArray[j++] = 255 - clampTo8bit(m * (1 - k / 255) + k)
                    imageDataArray[j++] = 255 - clampTo8bit(y * (1 - k / 255) + k)
                    imageDataArray[j++] = 255
                }
            }
            else -> invalidOp("Unsupported color mode")
        }
    }

    data class ImageInfo(val width: Int, val height: Int)
    class ImageData(val width: Int, val height: Int, val data: UByteArrayInt)

    companion object {
        fun decodeInfo(jpegData: ByteArray): ImageInfo {
            val arr = UByteArrayInt(jpegData)
            val decoder = JPEGDecoder()
            decoder.parse(arr)
            return JPEGDecoder.ImageInfo(decoder.width, decoder.height)
        }

        fun decode(jpegData: ByteArray): Bitmap32 {
            val data = decodeToData(jpegData)
            return RGBA.decodeToBitmap32(data.width, data.height, data.data.asByteArray())
        }

        private fun decodeToData(jpegData: ByteArray): ImageData {
            val arr = UByteArrayInt(jpegData)
            val decoder = JPEGDecoder()
            decoder.parse(arr)

            val image = ImageData(
                width = decoder.width,
                height = decoder.height,
                data = UByteArrayInt(decoder.width * decoder.height * 4)
            )

            decoder.copyToImageData(image)

            return image
        }
    }
}

/*
  Copyright (c) 2008, Adobe Systems Incorporated
  All rights reserved.

  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are
  met:

  * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

  * Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.

  * Neither the name of Adobe Systems Incorporated nor the names of its
    contributors may be used to endorse or promote products derived from
    this software without specific prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
/*
JPEG encoder ported to JavaScript and optimized by Andreas Ritter, www.bytestrom.eu, 11/2009
JPEG encoder ported to Kotlin by soywiz

Basic GUI blocking jpeg encoder
*/

// Based on: https://github.com/eugeneware/jpeg-js/blob/652bfced3ead53808285b1b5fa9c0b589d00bbf0/lib/encoder.js
class JPEGEncoder(quality: Int = 50) {
    private var yTable = IntArray(64)
    private var uvTable = IntArray(64)
    private var fdtblY = FloatArray(64)
    private var fdtblUV = FloatArray(64)
    private var ydcHt: Array<IntArray> = emptyArray()
    private var uvdcHt: Array<IntArray> = emptyArray()
    private var yavHt: Array<IntArray> = emptyArray()
    private var uvacHt: Array<IntArray> = emptyArray()

    private var bitcode = Array(0xFFFF) { IntArray(2) }
    private var category = IntArray(0xFFFF)
    private var outputfDCTQuant = IntArray(64)
    private var du = IntArray(64)
    private var byteOut = ByteArrayBuilder()
    private var byteNew = 0
    private var bytePos = 7

    private var ydu = FloatArray(64)
    private var udu = FloatArray(64)
    private var vdu = FloatArray(64)
    private var rgbYuvTable = IntArray(2048)
    private var currentQuality: Int = 0

    private fun initQuantTables(sf: Int) {
        for (i in 0 until 64) {
            var t = floor((YQT_TABLE[i] * sf + 50).toFloat() / 100f).toInt()
            if (t < 1) {
                t = 1
            } else if (t > 255) {
                t = 255
            }
            yTable[ZIG_ZAG[i]] = t
        }

        for (j in 0 until 64) {
            var u = floor((UVQT_TABLE[j] * sf + 50f) / 100f).toInt()
            if (u < 1) {
                u = 1
            } else if (u > 255) {
                u = 255
            }
            uvTable[ZIG_ZAG[j]] = u
        }

        var k = 0
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                fdtblY[k] = (1.0f / (yTable[ZIG_ZAG[k]].toFloat() * AASF_TABLE[row] * AASF_TABLE[col] * 8.0f))
                fdtblUV[k] = (1.0f / (uvTable[ZIG_ZAG[k]].toFloat() * AASF_TABLE[row] * AASF_TABLE[col] * 8.0f))
                k++
            }
        }
    }

    private fun computeHuffmanTbl(nrCodes: IntArray, std_table: IntArray): Array<IntArray> {
        var codeValue = 0
        var posInTable = 0
        val ht = Array((std_table.max() ?: 0) + 1) { IntArray(2) }
        for (k in 1..16) {
            for (j in 1..nrCodes[k]) {
                ht[std_table[posInTable]] = IntArray(2)
                ht[std_table[posInTable]][0] = codeValue
                ht[std_table[posInTable]][1] = k
                posInTable++
                codeValue++
            }
            codeValue *= 2
        }
        return ht
    }

    private fun initHuffmanTbl() {
        ydcHt = computeHuffmanTbl(STD_DC_LUMINANCE_NRCODES, STD_DC_LUMINANCE_VALUES)
        uvdcHt = computeHuffmanTbl(STD_DC_CHROMIANCE_NRCODES, STD_DC_CHROMIANCE_VALUES)
        yavHt = computeHuffmanTbl(STD_AC_LUMINANCE_NRCODES, STD_AC_LUMINANCE_VALUES)
        uvacHt = computeHuffmanTbl(STD_AC_CHROMIANCE_NRCODES, STD_AC_CHROMIANCE_VALUES)
    }

    private fun initCategoryNumber() {
        var nrlower = 1
        var nrupper = 2
        for (cat in 1..15) {
            //Positive numbers
            for (nr in nrlower until nrupper) {
                category[32767 + nr] = cat
                bitcode[32767 + nr] = IntArray(2)
                bitcode[32767 + nr][1] = cat
                bitcode[32767 + nr][0] = nr
            }
            //Negative numbers
            for (nrneg in (-(nrupper - 1))..(-nrlower)) {
                category[32767 + nrneg] = cat
                bitcode[32767 + nrneg] = IntArray(2)
                bitcode[32767 + nrneg][1] = cat
                bitcode[32767 + nrneg][0] = nrupper - 1 + nrneg
            }
            nrlower = nrlower shl 1
            nrupper = nrupper shl 1
        }
    }

    private fun initRGBYUVTable() {
        for (i in 0 until 256) {
            rgbYuvTable[(i + 0)] = 19595 * i
            rgbYuvTable[(i + 256)] = 38470 * i
            rgbYuvTable[(i + 512)] = 7471 * i + 0x8000
            rgbYuvTable[(i + 768)] = -11059 * i
            rgbYuvTable[(i + 1024)] = -21709 * i
            rgbYuvTable[(i + 1280)] = 32768 * i + 0x807FFF
            rgbYuvTable[(i + 1536)] = -27439 * i
            rgbYuvTable[(i + 1792)] = -5329 * i
        }
    }

    // IO functions
    private fun writeBits(bs: IntArray) {
        val value = bs[0]
        var posval = bs[1] - 1
        while (posval >= 0) {
            if ((value and (1 shl posval)) != 0) {
                byteNew = byteNew or (1 shl bytePos)
            }
            posval--
            bytePos--
            if (bytePos < 0) {
                if (byteNew == 0xFF) {
                    writeByte(0xFF)
                    writeByte(0)
                } else {
                    writeByte(byteNew)
                }
                bytePos = 7
                byteNew = 0
            }
        }
    }

    private fun writeByte(value: Int) {
        byteOut.append(value.toByte())
    }

    private fun writeWord(value: Int) {
        writeByte((value ushr 8) and 0xFF)
        writeByte((value) and 0xFF)
    }

    // DCT & quantization core
    private fun fDCTQuant(data: FloatArray, fdtbl: FloatArray): IntArray {
        var d0: Float
        var d1: Float
        var d2: Float
        var d3: Float
        var d4: Float
        var d5: Float
        var d6: Float
        var d7: Float
        /* Pass 1: process rows. */
        var dataOff = 0
        val i8 = 8
        val i64 = 64
        for (i in 0 until i8) {
            d0 = data[dataOff + 0]
            d1 = data[dataOff + 1]
            d2 = data[dataOff + 2]
            d3 = data[dataOff + 3]
            d4 = data[dataOff + 4]
            d5 = data[dataOff + 5]
            d6 = data[dataOff + 6]
            d7 = data[dataOff + 7]

            val tmp0 = d0 + d7
            val tmp7 = d0 - d7
            val tmp1 = d1 + d6
            val tmp6 = d1 - d6
            val tmp2 = d2 + d5
            val tmp5 = d2 - d5
            val tmp3 = d3 + d4
            val tmp4 = d3 - d4

            /* Even part */
            var tmp10 = tmp0 + tmp3    /* phase 2 */
            val tmp13 = tmp0 - tmp3
            var tmp11 = tmp1 + tmp2
            var tmp12 = tmp1 - tmp2

            data[dataOff] = tmp10 + tmp11 /* phase 3 */
            data[dataOff + 4] = tmp10 - tmp11

            val z1 = (tmp12 + tmp13) * 0.707106781f /* c4 */
            data[dataOff + 2] = tmp13 + z1 /* phase 5 */
            data[dataOff + 6] = tmp13 - z1

            /* Odd part */
            tmp10 = tmp4 + tmp5 /* phase 2 */
            tmp11 = tmp5 + tmp6
            tmp12 = tmp6 + tmp7

            /* The rotator is modified from fig 4-8 to avoid extra negations. */
            val z5 = (tmp10 - tmp12) * 0.382683433f /* c6 */
            val z2 = 0.541196100f * tmp10 + z5 /* c2-c6 */
            val z4 = 1.306562965f * tmp12 + z5 /* c2+c6 */
            val z3 = tmp11 * 0.707106781f /* c4 */

            val z11 = tmp7 + z3    /* phase 5 */
            val z13 = tmp7 - z3

            data[dataOff + 5] = z13 + z2    /* phase 6 */
            data[dataOff + 3] = z13 - z2
            data[dataOff + 1] = z11 + z4
            data[dataOff + 7] = z11 - z4

            dataOff += 8 /* advance pointer to next row */
        }

        /* Pass 2: process columns. */
        dataOff = 0
        for (i in 0 until i8) {
            d0 = data[dataOff]
            d1 = data[dataOff + 8]
            d2 = data[dataOff + 16]
            d3 = data[dataOff + 24]
            d4 = data[dataOff + 32]
            d5 = data[dataOff + 40]
            d6 = data[dataOff + 48]
            d7 = data[dataOff + 56]

            val tmp0p2 = d0 + d7
            val tmp7p2 = d0 - d7
            val tmp1p2 = d1 + d6
            val tmp6p2 = d1 - d6
            val tmp2p2 = d2 + d5
            val tmp5p2 = d2 - d5
            val tmp3p2 = d3 + d4
            val tmp4p2 = d3 - d4

            /* Even part */
            var tmp10p2 = tmp0p2 + tmp3p2    /* phase 2 */
            val tmp13p2 = tmp0p2 - tmp3p2
            var tmp11p2 = tmp1p2 + tmp2p2
            var tmp12p2 = tmp1p2 - tmp2p2

            data[dataOff] = tmp10p2 + tmp11p2 /* phase 3 */
            data[dataOff + 32] = tmp10p2 - tmp11p2

            val z1p2 = (tmp12p2 + tmp13p2) * 0.707106781f /* c4 */
            data[dataOff + 16] = tmp13p2 + z1p2 /* phase 5 */
            data[dataOff + 48] = tmp13p2 - z1p2

            /* Odd part */
            tmp10p2 = tmp4p2 + tmp5p2 /* phase 2 */
            tmp11p2 = tmp5p2 + tmp6p2
            tmp12p2 = tmp6p2 + tmp7p2

            /* The rotator is modified from fig 4-8 to avoid extra negations. */
            val z5p2 = (tmp10p2 - tmp12p2) * 0.382683433f /* c6 */
            val z2p2 = 0.541196100f * tmp10p2 + z5p2 /* c2-c6 */
            val z4p2 = 1.306562965f * tmp12p2 + z5p2 /* c2+c6 */
            val z3p2 = tmp11p2 * 0.707106781f /* c4 */

            val z11p2 = tmp7p2 + z3p2    /* phase 5 */
            val z13p2 = tmp7p2 - z3p2

            data[dataOff + 40] = z13p2 + z2p2 /* phase 6 */
            data[dataOff + 24] = z13p2 - z2p2
            data[dataOff + 8] = z11p2 + z4p2
            data[dataOff + 56] = z11p2 - z4p2

            dataOff++ /* advance pointer to next column */
        }

        // Quantize/descale the coefficients
        var fDCTQuant: Float
        for (i in 0 until i64) {
            // Apply the quantization and scaling factor & Round to nearest integer
            fDCTQuant = data[i] * fdtbl[i]
            outputfDCTQuant[i] = if (fDCTQuant > 0.0f) ((fDCTQuant + 0.5f).toInt()) else ((fDCTQuant - 0.5f).toInt())
            //outputfDCTQuant[i] = fround(fDCTQuant);

        }
        return outputfDCTQuant
    }

    private fun writeAPP0() {
        writeWord(0xFFE0) // marker
        writeWord(16) // length
        writeByte(0x4A) // J
        writeByte(0x46) // F
        writeByte(0x49) // I
        writeByte(0x46) // F
        writeByte(0) // = "JFIF",'\0'
        writeByte(1) // versionhi
        writeByte(1) // versionlo
        writeByte(0) // xyunits
        writeWord(1) // xdensity
        writeWord(1) // ydensity
        writeByte(0) // thumbnwidth
        writeByte(0) // thumbnheight
    }

    private fun writeSOF0(width: Int, height: Int) {
        writeWord(0xFFC0) // marker
        writeWord(17)   // length, truecolor YUV JPG
        writeByte(8)    // precision
        writeWord(height)
        writeWord(width)
        writeByte(3)    // nrofcomponents
        writeByte(1)    // IdY
        writeByte(0x11) // HVY
        writeByte(0)    // QTY
        writeByte(2)    // IdU
        writeByte(0x11) // HVU
        writeByte(1)    // QTU
        writeByte(3)    // IdV
        writeByte(0x11) // HVV
        writeByte(1)    // QTV
    }

    private fun writeDQT() {
        writeWord(0xFFDB) // marker
        writeWord(132)       // length
        writeByte(0)
        for (i in 0 until 64) {
            writeByte(yTable[i])
        }
        writeByte(1)
        for (j in 0 until 64) {
            writeByte(uvTable[j])
        }
    }

    private fun writeDHT() {
        writeWord(0xFFC4) // marker
        writeWord(0x01A2) // length

        writeByte(0) // HTYDCinfo
        for (i in 0 until 16) {
            writeByte(STD_DC_LUMINANCE_NRCODES[i + 1])
        }
        for (j in 0..11) {
            writeByte(STD_DC_LUMINANCE_VALUES[j])
        }

        writeByte(0x10) // HTYACinfo
        for (k in 0 until 16) {
            writeByte(STD_AC_LUMINANCE_NRCODES[k + 1])
        }
        for (l in 0..161) {
            writeByte(STD_AC_LUMINANCE_VALUES[l])
        }

        writeByte(1) // HTUDCinfo
        for (m in 0 until 16) {
            writeByte(STD_DC_CHROMIANCE_NRCODES[m + 1])
        }
        for (n in 0..11) {
            writeByte(STD_DC_CHROMIANCE_VALUES[n])
        }

        writeByte(0x11) // HTUACinfo
        for (o in 0 until 16) {
            writeByte(STD_AC_CHROMIANCE_NRCODES[o + 1])
        }
        for (p in 0..161) {
            writeByte(STD_AC_CHROMIANCE_VALUES[p])
        }
    }

    private fun writeSOS() {
        writeWord(0xFFDA) // marker
        writeWord(12) // length
        writeByte(3) // nrofcomponents
        writeByte(1) // IdY
        writeByte(0) // HTY
        writeByte(2) // IdU
        writeByte(0x11) // HTU
        writeByte(3) // IdV
        writeByte(0x11) // HTV
        writeByte(0) // Ss
        writeByte(0x3f) // Se
        writeByte(0) // Bf
    }

    private fun processDU(
        CDU: FloatArray,
        fdtbl: FloatArray,
        DC: Int,
        HTDC: Array<IntArray>,
        HTAC: Array<IntArray>
    ): Int {
        @Suppress("LocalVariableName", "NAME_SHADOWING")
        var dc = DC
        val eob = HTAC[0x00]
        val m16zeroes = HTAC[0xF0]
        var pos: Int
        val i16 = 16
        val i63 = 63
        val i64 = 64
        val duDct = fDCTQuant(CDU, fdtbl)
        //ZigZag reorder
        for (j in 0 until i64) {
            du[ZIG_ZAG[j]] = duDct[j]
        }
        val diff = du[0] - dc
        dc = du[0]
        //Encode DC
        if (diff == 0) {
            writeBits(HTDC[0]) // Diff might be 0
        } else {
            pos = 32767 + diff
            writeBits(HTDC[category[pos]])
            writeBits(bitcode[pos])
        }
        //Encode ACs
        var end0pos = 63 // was const... which is crazy
        while ((end0pos > 0) && (du[end0pos] == 0)) end0pos--
        //end0pos = first element in reverse order !=0
        if (end0pos == 0) {
            writeBits(eob)
            return dc
        }
        var i = 1
        var lng: Int
        while (i <= end0pos) {
            val startpos = i
            while ((du[i] == 0) && (i <= end0pos)) i++
            var nrzeroes = i - startpos
            if (nrzeroes >= i16) {
                lng = nrzeroes shr 4
                for (nrmarker in 1..lng)
                    writeBits(m16zeroes)
                nrzeroes = nrzeroes and 0xF
            }
            pos = 32767 + du[i]
            writeBits(HTAC[(nrzeroes shl 4) + category[pos]])
            writeBits(bitcode[pos])
            i++
        }
        if (end0pos != i63) {
            writeBits(eob)
        }
        return dc
    }

    // image data object
    fun encode(bmp: Bitmap32, quality: Int? = null): ByteArray = encode(
        JPEGEncoder.ImageData(
            bmp.extractBytes(),
            bmp.width,
            bmp.height
        ), quality
    )

    private fun encode(image: ImageData, quality: Int? = null): ByteArray {
        //var time_start = Klock.currentTimeMillis()

        if (quality != null) setQuality(quality)

        // Initialize bit writer
        byteOut = ByteArrayBuilder()
        byteNew = 0
        bytePos = 7

        // Add JPEG headers
        writeWord(0xFFD8) // SOI
        writeAPP0()
        writeDQT()
        writeSOF0(image.width, image.height)
        writeDHT()
        writeSOS()

        // Encode 8x8 macroblocks
        var dcy = 0
        var dcu = 0
        var dcv = 0

        byteNew = 0
        bytePos = 7

        val imageData = image.data
        val width = image.width
        val height = image.height

        val quadWidth = width * 4

        var x: Int
        var y = 0
        var r: Int
        var g: Int
        var b: Int
        var start: Int
        while (y < height) {
            x = 0
            while (x < quadWidth) {
                start = quadWidth * y + x

                @Suppress("UNUSED_CHANGED_VALUE")
                for (pos in 0 until 64) {
                    val row = pos shr 3// /8
                    val col = (pos and 7) * 4 // %8
                    var p = start + (row * quadWidth) + col

                    if (y + row >= height) { // padding bottom
                        p -= (quadWidth * (y + 1 + row - height))
                    }

                    if (x + col >= quadWidth) { // padding right
                        p -= ((x + col) - quadWidth + 4)
                    }

                    r = imageData[p++].toInt() and 0xFF
                    g = imageData[p++].toInt() and 0xFF
                    b = imageData[p++].toInt() and 0xFF


                    /* // calculate YUV values dynamically
                    YDU[pos]=((( 0.29900)*r+( 0.58700)*g+( 0.11400)*b))-128; //-0x80
                    UDU[pos]=(((-0.16874)*r+(-0.33126)*g+( 0.50000)*b));
                    VDU[pos]=((( 0.50000)*r+(-0.41869)*g+(-0.08131)*b));
                    */

                    // use lookup table (slightly faster)
                    ydu[pos] =
                        (((rgbYuvTable[(r + 0)] + rgbYuvTable[(g + 256)] + rgbYuvTable[(b + 512)]) shr 16) - 128).toFloat()
                    udu[pos] =
                        (((rgbYuvTable[(r + 768)] + rgbYuvTable[(g + 1024)] + rgbYuvTable[(b + 1280)]) shr 16) - 128).toFloat()
                    vdu[pos] =
                        (((rgbYuvTable[(r + 1280)] + rgbYuvTable[(g + 1536)] + rgbYuvTable[(b + 1792)]) shr 16) - 128).toFloat()

                }

                dcy = processDU(ydu, fdtblY, dcy, ydcHt, yavHt)
                dcu = processDU(udu, fdtblUV, dcu, uvdcHt, uvacHt)
                dcv = processDU(vdu, fdtblUV, dcv, uvdcHt, uvacHt)
                x += 32
            }
            y += 8
        }


        ////////////////////////////////////////////////////////////////

        // Do the bit alignment of the EOI marker
        if (bytePos >= 0) {
            val fillbits = IntArray(2)
            fillbits[1] = bytePos + 1
            fillbits[0] = (1 shl (bytePos + 1)) - 1
            writeBits(fillbits)
        }

        writeWord(0xFFD9) //EOI

        return byteOut.toByteArray()
    }

    private fun setQuality(quality: Int) {
        @Suppress("NAME_SHADOWING")
        var quality = quality
        if (quality <= 0) {
            quality = 1
        }
        if (quality > 100) {
            quality = 100
        }

        if (currentQuality == quality) return // don't recalc if unchanged

        initQuantTables(if (quality < 50) (5000 / quality) else (200 - quality * 2))
        currentQuality = quality
        //console.log('Quality set to: '+quality +'%');
    }

    init {
        initHuffmanTbl()
        initCategoryNumber()
        initRGBYUVTable()
        setQuality(quality)
    }

    private class ImageData(val data: ByteArray, val width: Int, val height: Int)

    companion object {
        private val ZIG_ZAG = intArrayOf(
            0, 1, 5, 6, 14, 15, 27, 28,
            2, 4, 7, 13, 16, 26, 29, 42,
            3, 8, 12, 17, 25, 30, 41, 43,
            9, 11, 18, 24, 31, 40, 44, 53,
            10, 19, 23, 32, 39, 45, 52, 54,
            20, 22, 33, 38, 46, 51, 55, 60,
            21, 34, 37, 47, 50, 56, 59, 61,
            35, 36, 48, 49, 57, 58, 62, 63
        )

        private var STD_DC_LUMINANCE_NRCODES = intArrayOf(0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0)
        private var STD_DC_LUMINANCE_VALUES = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        private var STD_AC_LUMINANCE_NRCODES = intArrayOf(0, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 0x7d)
        private var STD_AC_LUMINANCE_VALUES = intArrayOf(
            0x01, 0x02, 0x03, 0x00, 0x04, 0x11, 0x05, 0x12,
            0x21, 0x31, 0x41, 0x06, 0x13, 0x51, 0x61, 0x07,
            0x22, 0x71, 0x14, 0x32, 0x81, 0x91, 0xa1, 0x08,
            0x23, 0x42, 0xb1, 0xc1, 0x15, 0x52, 0xd1, 0xf0,
            0x24, 0x33, 0x62, 0x72, 0x82, 0x09, 0x0a, 0x16,
            0x17, 0x18, 0x19, 0x1a, 0x25, 0x26, 0x27, 0x28,
            0x29, 0x2a, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39,
            0x3a, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49,
            0x4a, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59,
            0x5a, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69,
            0x6a, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79,
            0x7a, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89,
            0x8a, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98,
            0x99, 0x9a, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7,
            0xa8, 0xa9, 0xaa, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6,
            0xb7, 0xb8, 0xb9, 0xba, 0xc2, 0xc3, 0xc4, 0xc5,
            0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xd2, 0xd3, 0xd4,
            0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xe1, 0xe2,
            0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea,
            0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
            0xf9, 0xfa
        )

        private var STD_DC_CHROMIANCE_NRCODES = intArrayOf(0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0)
        private var STD_DC_CHROMIANCE_VALUES = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        private var STD_AC_CHROMIANCE_NRCODES = intArrayOf(0, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 0x77)
        private var STD_AC_CHROMIANCE_VALUES = intArrayOf(
            0x00, 0x01, 0x02, 0x03, 0x11, 0x04, 0x05, 0x21,
            0x31, 0x06, 0x12, 0x41, 0x51, 0x07, 0x61, 0x71,
            0x13, 0x22, 0x32, 0x81, 0x08, 0x14, 0x42, 0x91,
            0xa1, 0xb1, 0xc1, 0x09, 0x23, 0x33, 0x52, 0xf0,
            0x15, 0x62, 0x72, 0xd1, 0x0a, 0x16, 0x24, 0x34,
            0xe1, 0x25, 0xf1, 0x17, 0x18, 0x19, 0x1a, 0x26,
            0x27, 0x28, 0x29, 0x2a, 0x35, 0x36, 0x37, 0x38,
            0x39, 0x3a, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48,
            0x49, 0x4a, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58,
            0x59, 0x5a, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68,
            0x69, 0x6a, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78,
            0x79, 0x7a, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87,
            0x88, 0x89, 0x8a, 0x92, 0x93, 0x94, 0x95, 0x96,
            0x97, 0x98, 0x99, 0x9a, 0xa2, 0xa3, 0xa4, 0xa5,
            0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xb2, 0xb3, 0xb4,
            0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xc2, 0xc3,
            0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xd2,
            0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda,
            0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9,
            0xea, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
            0xf9, 0xfa
        )

        private val YQT_TABLE = intArrayOf(
            16, 11, 10, 16, 24, 40, 51, 61,
            12, 12, 14, 19, 26, 58, 60, 55,
            14, 13, 16, 24, 40, 57, 69, 56,
            14, 17, 22, 29, 51, 87, 80, 62,
            18, 22, 37, 56, 68, 109, 103, 77,
            24, 35, 55, 64, 81, 104, 113, 92,
            49, 64, 78, 87, 103, 121, 120, 101,
            72, 92, 95, 98, 112, 100, 103, 99
        )

        private val UVQT_TABLE = intArrayOf(
            17, 18, 24, 47, 99, 99, 99, 99,
            18, 21, 26, 66, 99, 99, 99, 99,
            24, 26, 56, 99, 99, 99, 99, 99,
            47, 66, 99, 99, 99, 99, 99, 99,
            99, 99, 99, 99, 99, 99, 99, 99,
            99, 99, 99, 99, 99, 99, 99, 99,
            99, 99, 99, 99, 99, 99, 99, 99,
            99, 99, 99, 99, 99, 99, 99, 99
        )

        private val AASF_TABLE = floatArrayOf(
            1.0f, 1.387039845f, 1.306562965f, 1.175875602f,
            1.0f, 0.785694958f, 0.541196100f, 0.275899379f
        )

        fun encode(bmp: Bitmap32, quality: Int = 50): ByteArray {
            return JPEGEncoder(quality).encode(bmp)
        }
    }
}