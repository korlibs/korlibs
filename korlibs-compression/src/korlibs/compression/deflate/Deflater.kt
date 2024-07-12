package korlibs.compression.deflate

import kotlin.math.*

// @TODO: Use sync API, suspend but do not use asynchronous stuff. Simplify interface for input and output
// @TODO: Act like a sequence of status (waiting for filling input, waiting for filling output, etc.)
// @TODO: Use = sequence<Int> { yield(NEED_MORE_INPUT) }
internal open class DeflaterPortable(val windowBits: Int) : IDeflaterInternal {
    override suspend fun compress(
        i: DeflaterBitReader,
        o: DeflaterAsyncOutputStream,
        level: Float
    ) {
        while (i.hasAvailable()) {
            val available = i.getAvailable()
            val chunkSize = min(available, 0xFFFFL).toInt()
            o.write8(if (chunkSize >= available) 1 else 0)
            o.write16LE(chunkSize)
            o.write16LE(chunkSize.inv())
            //for (n in 0 until chunkSize) o.write8(i.readU8())
            o.writeBytes(i.readBytesExact(chunkSize))
        }
    }

    override suspend fun uncompress(reader: DeflaterBitReader, out: DeflaterAsyncOutputStream) {
        //println("reader.bigChunkSize=${reader.bigChunkSize}, reader.readWithSize=${reader.readWithSize}")
        val sout = SlidingWindowWithOutput(SlidingWindow(windowBits), out, reader.bigChunkSize, reader.readWithSize)
        var lastBlock = false
        val tempTree = HuffmanTree()
        val tempDist = HuffmanTree()
        val codeLenCodeLen = IntArray(32)
        val lengths = IntArray(512)
        //println("uncompress[0]")
        while (!lastBlock) {
            sout.flushIfRequired()
            reader.prepareBigChunkIfRequired()
            //println("uncompress[1]")

            lastBlock = reader.sreadBit()
            val blockType = reader.readBits(2)
            if (blockType !in 0..2) error("invalid bit")

            //println("lastBlock=$lastBlock, btype=$blockType")

            if (blockType == 0) {
                //println("uncompress[2]")
                val len = reader.su16LE()
                val nlen = reader.su16LE()
                val nnlen = nlen.inv() and 0xFFFF
                if (len != nnlen) error("Invalid deflate stream: len($len) != ~nlen($nnlen) :: nlen=$nlen")
                val bytes = reader.abytes(len)
                sout.putOut(bytes, 0, len)
                sout.flushIfRequired()
            } else {
                //println("uncompress[3]")
                val tree: HuffmanTree
                val dist: HuffmanTree
                if (blockType == 1) {
                    tree = FIXED_TREE
                    dist = FIXED_DIST
                }
                else {
                    val hlit = reader.readBits(5) + 257
                    val hdist = reader.readBits(5) + 1
                    val hclen = reader.readBits(4) + 4
                    codeLenCodeLen.fill(0)
                    for (i in 0 until hclen) codeLenCodeLen[HCLENPOS[i]] = reader.readBits(3)
                    //console.info(codeLenCodeLen);
                    tempTree.setFromLengths(codeLenCodeLen)
                    val codeLen = tempTree
                    val hlithdist = hlit + hdist
                    var n = 0
                    lengths.fill(0)
                    while (n < hlithdist) {
                        val value = reader.read(codeLen)
                        if (value !in 0..18) error("Invalid")

                        val len = when (value) {
                            16 -> reader.readBits(2) + 3
                            17 -> reader.readBits(3) + 3
                            18 -> reader.readBits(7) + 11
                            else -> 1
                        }
                        val vv = when (value) {
                            16 -> lengths[n - 1]
                            17 -> 0
                            18 -> 0
                            else -> value
                        }

                        lengths.fill(vv, n, n + len)
                        n += len
                    }
                    tempTree.setFromLengths(lengths, 0, hlit)
                    tempDist.setFromLengths(lengths, hlit, hlithdist)
                    tree = tempTree
                    dist = tempDist
                }
                while (true) {
                    // @TODO: Read in parallel while decoding
                    reader.prepareBigChunkIfRequired()
                    val value = tree.read(reader)
                    if (value == 256) break
                    if (value < 256) {
                        sout.putOut(value.toByte())
                    } else {
                        val zlenof = value - 257
                        val lengthExtra = reader.readBits(LEN_EXTRA[zlenof])
                        val distanceData = reader.read(dist)
                        val distanceExtra = reader.readBits(DIST_EXTRA[distanceData])
                        val distance = DIST_BASE[distanceData] + distanceExtra
                        val length = LEN_BASE[zlenof] + lengthExtra
                        sout.getPutCopyOut(distance, length)
                    }
                    sout.flushIfRequired()
                }
            }
        }
        //println("uncompress[4]")
        sout.flushIfRequired(finish = true)
        //println("uncompress[5]")
    }

    private inline fun DeflaterBitReader.read(tree: HuffmanTree): Int = tree.read(this)

    internal class SlidingWindowWithOutput(
        val sliding: SlidingWindow,
        val out: DeflaterAsyncOutputStream,
        val flushSize: Int = FLUSH_SIZE,
        val extraSize: Int = EXTRA_SIZE
    ) {
        companion object {
            const val FLUSH_SIZE = 8 * 1024 * 1024
            const val EXTRA_SIZE = 128 * 1024
            //const val FLUSH_SIZE = 4 * 1024
            //const val EXTRA_SIZE = 4 * 1024
        }

        // @TODO: Optimize with buffering and copying
        val bab = FixedSizeByteArrayBuilder(flushSize + extraSize)
        //val bab = ByteArrayBuilder(flushSize + EXTRA_SIZE)

        val output get() = bab.size
        val mustFlush get() = bab.size >= flushSize

        fun getPutCopyOut(distance: Int, length: Int) {
            //print("LZ: distance=$distance, length=$length   :: ")
            for (n in 0 until length) {
                val v = sliding.getPut(distance)
                bab.appendFast(v.toByte())
                //print("$v,")
            }
            //println()
        }

        fun putOut(bytes: ByteArray, offset: Int, len: Int) {
            //print("BYTES: $len ::")
            bab.append(bytes, offset, len)
            sliding.putBytes(bytes, offset, len)
            //for (n in 0 until len) print("${bytes[offset + n].toUnsigned()},")
            //println()
        }

        fun putOut(byte: Byte) {
            //println("BYTE: $byte")
            bab.appendFast(byte)
            sliding.put(byte.unsigned)
        }

        suspend inline fun flush() {
            //print("FLUSH[$finish][${bab.size}]")
            //for (n in 0 until bab.size) print("${bab.data[n]},")
            //println()
            out.write(bab.data, 0, bab.size)
            bab.clear()
        }

        suspend inline fun flushIfRequired(finish: Boolean = false) {
            if (finish || mustFlush) flush()
        }
    }

    companion object : DeflaterPortable(15) {
        private val FIXED_TREE: HuffmanTree = HuffmanTree().setFromLengths(IntArray(288).apply {
            for (n in 0..143) this[n] = 8
            for (n in 144..255) this[n] = 9
            for (n in 256..279) this[n] = 7
            for (n in 280..287) this[n] = 8
        })
        private val FIXED_DIST: HuffmanTree = HuffmanTree().setFromLengths(IntArray(32) { 5 })

        private val LEN_EXTRA = intArrayOf(
            0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0, 0, 0
        )

        private val LEN_BASE = intArrayOf(
            3, 4, 5, 6, 7, 8, 9, 10, 11, 13,
            15, 17, 19, 23, 27, 31, 35, 43, 51, 59,
            67, 83, 99, 115, 131, 163, 195, 227, 258, 0, 0
        )

        private val DIST_EXTRA = intArrayOf(
            0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13
        )

        private val DIST_BASE = intArrayOf(
            1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193,
            257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577, 0, 0
        )

        private val HCLENPOS = intArrayOf(16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15)
    }

    internal class HuffmanTree {
        companion object {
            private const val INVALID_VALUE = -1
            private const val INCOMPLETE_VALUE = -2
            private const val NIL = 1023
            private const val MAX_LEN = 16
            private const val MAX_CODES = 288

            //private const val ENABLE_EXPERIMENTAL_FAST_READ = false
            private const val ENABLE_EXPERIMENTAL_FAST_READ = true
            private const val ENABLE_EXPERIMENTAL_FAST_READ_V2 = true
            //private const val ENABLE_EXPERIMENTAL_FAST_READ = false

            //private const val FAST_BITS = 9
            private const val FAST_BITS = 10
            //private const val FAST_BITS = 11
            //private const val FAST_BITS = 12
            //private const val FAST_BITS = 14
        }

        //private val data = IntArray(3 * 1024)

        private val value = IntArray(1024)
        private val left = IntArray(1024)
        private val right = IntArray(1024)

        private var nodeOffset = 0
        private var root: Int = NIL
        private var ncodes: Int = 0

        // Low half-word contains the value, High half-word contains the len
        val FAST_INFO = IntArray(1 shl FAST_BITS) { INVALID_VALUE }
        val FAST_NODE = IntArray(1 shl FAST_BITS) { 0 }

        //var fastReadCount = 0
        //var slowReadCount = 0

        fun read(reader: DeflaterBitReader): Int {
            if (ENABLE_EXPERIMENTAL_FAST_READ) reader.ensureBits(FAST_BITS)
            var node = this.root
            if (ENABLE_EXPERIMENTAL_FAST_READ && reader.bitsavailable >= FAST_BITS) {
                //println("${reader.bitsavailable} >= $FAST_BITS")
                val bits = reader.peekBits(FAST_BITS)
                val raw = FAST_INFO[bits]
                val value = raw.toShort().toInt()
                val len = raw shr 16
                if (len > 0) {
                    //println("BITS1[raw=${raw.hex}]: len=$len, value=$value")
                    reader.skipBits(len)
                    if (value == INCOMPLETE_VALUE) {
                        node = FAST_NODE[bits]
                    } else {
                        //fastReadCount++
                        return value
                    }
                }
            }
            do {
                node = if (reader.sreadBit()) node.right else node.left
            } while (node != NIL && node.value == INVALID_VALUE)
            //println("BITS2: ${node.value}")
            //slowReadCount++
            return node.value
        }

        private fun resetAlloc() {
            nodeOffset = 0
        }

        private fun alloc(value: Int, left: Int, right: Int): Int {
            return (nodeOffset++).apply {
                //this@HuffmanTree.data[0 + this] = value
                //this@HuffmanTree.data[1024 + this] = left
                //this@HuffmanTree.data[2048 + this] = right
                this@HuffmanTree.value[this] = value
                this@HuffmanTree.left[this] = left
                this@HuffmanTree.right[this] = right
            }
        }

        //private inline val Int.value get() = this@HuffmanTree.data[0 + this]
        //private inline val Int.left get() = this@HuffmanTree.data[1024 + this]
        //private inline val Int.right get() = this@HuffmanTree.data[2048 + this]

        private inline val Int.value get() = this@HuffmanTree.value[this]
        private inline val Int.left get() = this@HuffmanTree.left[this]
        private inline val Int.right get() = this@HuffmanTree.right[this]

        private fun allocLeaf(value: Int): Int = alloc(value, NIL, NIL)
        private fun allocNode(left: Int, right: Int): Int = alloc(INVALID_VALUE, left, right)

        private val COUNTS = IntArray(MAX_LEN + 1)
        private val OFFSETS = IntArray(MAX_LEN + 1)
        private val COFFSET = IntArray(MAX_LEN + 1)
        private val CODES = IntArray(MAX_CODES)

        fun setFromLengths(codeLengths: IntArray, start: Int = 0, end: Int = codeLengths.size): HuffmanTree {
            var oldOffset = 0
            var oldCount = 0
            val ncodes = end - start

            //println("fastReadCount=$fastReadCount, slowReadCount=$slowReadCount")
            //fastReadCount = 0
            //slowReadCount = 0

            resetAlloc()

            COUNTS.fill(0)

            // Compute the count of codes per length
            for (n in start until end) {
                val codeLen = codeLengths[n]
                if (codeLen !in 0..MAX_LEN) error("Invalid HuffmanTree.codeLengths $codeLen")
                COUNTS[codeLen]++
            }

            // Compute the disposition using the counts per length
            var currentOffset = 0
            for (n in 0 until MAX_LEN) {
                val count = COUNTS[n]
                OFFSETS[n] = currentOffset
                COFFSET[n] = currentOffset
                currentOffset += count
            }

            // Place elements in the computed disposition
            for (n in start until end) {
                val codeLen = codeLengths[n]
                CODES[COFFSET[codeLen]++] = n - start
            }

            for (i in MAX_LEN downTo 1) {
                val newOffset = nodeOffset

                val OFFSET = OFFSETS[i]
                val SIZE = COUNTS[i]
                for (j in 0 until SIZE) allocLeaf(CODES[OFFSET + j])
                for (j in 0 until oldCount step 2) allocNode(oldOffset + j, oldOffset + j + 1)

                oldOffset = newOffset
                oldCount = SIZE + oldCount / 2
                if (oldCount >= 2 && oldCount % 2 != 0) {
                    error("This canonical code does not represent a Huffman code tree: $oldCount")
                }
            }
            if (oldCount != 2) {
                error("This canonical code does not represent a Huffman code tree")
            }

            this.root = allocNode(nodeOffset - 2, nodeOffset - 1)
            this.ncodes = ncodes

            if (ENABLE_EXPERIMENTAL_FAST_READ) {
                computeFastLookup()
            }

            return this
        }

        // @TODO: Optimize this
        private fun computeFastLookup() {
            if (ENABLE_EXPERIMENTAL_FAST_READ_V2) {
                FAST_INFO.fill(INVALID_VALUE)
                computeEncodedValues(root, 0, 0)
            } else {
                //println("computeEncodedValues: " + computeEncodedValues(root, 0, 0))
                //println("FAST_INFO.size=${FAST_INFO.size}")
                for (value in FAST_INFO.indices) {
                    var node = this.root
                    var bitcount = 0
                    do {
                        node = if (value.extractBool(bitcount++)) node.right else node.left
                    } while (node != NIL && node.value == INVALID_VALUE)
                    FAST_INFO[value] = if (bitcount > FAST_BITS) -1 else node.value or (bitcount shl 16)
                }
            }
        }

        private fun computeEncodedValues(node: Int, encoded: Int, encodedBits: Int) {
            if (node.value == INVALID_VALUE) {
                if (encodedBits < FAST_BITS) {
                    computeEncodedValues(node.left, encoded, encodedBits + 1)
                    computeEncodedValues(node.right, encoded or (1 shl encodedBits), encodedBits + 1)
                } else {
                    writeVariants(encoded, encodedBits, node, INCOMPLETE_VALUE)
                }
            } else {
                writeVariants(encoded, encodedBits, node, node.value)
                //println("encoded=$encoded, encodedBits=$encodedBits, nvalue=$nvalue, rangeCount=$rangeCount")
            }
        }

        private fun writeVariants(encoded: Int, encodedBits: Int, node: Int, nvalue: Int) {
            val encodedInfo = (nvalue and 0xFFFF) or (encodedBits shl 16)
            val rangeCount = 1 shl (FAST_BITS - encodedBits)
            for (n in 0 until rangeCount) {
                val i = encoded or (n shl encodedBits)
                FAST_INFO[i] = encodedInfo
                FAST_NODE[i] = node
            }
        }
    }

    internal class SlidingWindow(val nbits: Int) {
        val data = ByteArray(1 shl nbits)
        val mask = data.size - 1
        var pos = 0

        fun get(offset: Int): Int {
            return data[(pos - offset) and mask].toInt() and 0xFF
        }

        fun getPut(offset: Int): Int = put(get(offset))

        fun put(value: Int): Int {
            data[pos] = value.toByte()
            pos = (pos + 1) and mask
            return value
        }

        // @TODO: Optimize?
        fun putBytes(bytes: ByteArray, offset: Int, len: Int) {
            for (n in 0 until len) put(bytes[offset + n].unsigned)
        }
    }

    internal class FixedSizeByteArrayBuilder(public val data: ByteArray) {
        public val capacity: Int get() = data.size
        public var size: Int = 0
            private set
        public constructor(size: Int) : this(ByteArray(size))

        public fun clear() {
            size = 0
        }

        public fun append(array: ByteArray, offset: Int = 0, len: Int = array.size - offset) {
            arraycopy(array, offset, this.data, size, len)
            this.size += len
        }

        public fun appendFast(v: Byte) {
            data[size++] = v
        }

        public inline fun append(v: Byte): FixedSizeByteArrayBuilder {
            appendFast(v)
            return this
        }

        public fun append(vararg v: Byte): Unit = append(v)
        public fun append(vararg v: Int) {
            for (n in 0 until v.size) this.data[this.size + n] = v[n].toByte()
            this.size += v.size
        }

        public fun toByteArray(): ByteArray = data.copyOf(size)
    }
}

private inline fun Int.extractBool(offset: Int): Boolean = extract1(offset) != 0
private inline fun Int.extract1(offset: Int): Int = (this ushr offset) and 0b1
private val Byte.unsigned: Int get() = this.toInt() and 0xFF
private fun Int.signExtend(bits: Int): Int = (this shl (32 - bits)) shr (32 - bits) // Int.SIZE_BITS
private inline val Int.unsigned: Long get() = this.toLong() and 0xFFFFFFFFL
private fun arraycopy(src: ByteArray, srcPos: Int, dst: ByteArray, dstPos: Int, size: Int) {
    src.copyInto(dst, dstPos, srcPos, srcPos + size)
}
