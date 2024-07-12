@file:OptIn(ExperimentalUnsignedTypes::class)

package korlibs.compression.deflate.sinfl

import korlibs.io.stream.*

data class Sinfl(
    var bits: Int = 0,
    var bitcnt: Int = 0,
    val lits: UIntArray = UIntArray(288),
    val dsts: UIntArray = UIntArray(32),
    val lens: UIntArray = UIntArray(19),
    var tlit: Int = 0,
    var tdist: Int = 0,
    var tlen: Int = 0
) {
    fun readBits(src: SyncInputStream, n: Int): Int {
        val s = this
        val v = s.bits and ((1 shl n) - 1)
        s.bits = s.bits ushr n
        s.bitcnt -= n
        s.bitcnt = if (s.bitcnt < 0) 0 else s.bitcnt
        while (s.bitcnt < 16) {
            val read = src.read()
            if (read < 0) error("EOF")
            s.bits = s.bits or (read shl s.bitcnt)
            s.bitcnt += 8
        }
        return v
    }


    fun decode(src: SyncInputStream, tree: UIntArray, max: Int): Int {
        val s = this
        var lo = 0
        var hi = max
        val search = (sinflRev16(s.bits) shl 16) or 0xffff
        // bsearch next prefix code
        while (lo < hi) {
            val guess = (lo + hi) / 2
            if (search < tree[guess].toInt()) hi = guess
            else lo = guess + 1
        }
        // pull out and check key
        val key = tree[lo - 1]
        readBits(src, key.toInt() and 0x0f)
        return (key.toInt() shr 4) and 0x0fff
    }
}

interface BitReader {
    fun readBits(n: Int): Int
}

val sinflMirror = IntArray(256).also { array ->
    var c = 0
    fun put(v: Int) { array[c++] = v }
    fun R2(n: Int) { put(n); put(n + 128); put(n + 64); put(n + 192) }
    fun R4(n: Int) { R2(n); R2(n + 32); R2(n + 16); R2(n + 48) }
    fun R6(n: Int) { R4(n); R4(n +  8); R4(n +  4); R4(n + 12) }
    R6(0); R6(2); R6(1); R6(3)
}
private fun sinflRev16(n: Int): Int = (sinflMirror[n and 0xff] shl 8) or sinflMirror[(n shr 8) and 0xff]

class HuffmanTree(val tree: UIntArray, val first: Int) {
    fun decode(reader: BitReader, max: Int): Int {
        val s = this
        var lo = 0
        var hi = max
        val search = (sinflRev16(s.bits) shl 16) or 0xffff
        // bsearch next prefix code
        while (lo < hi) {
            val guess = (lo + hi) / 2
            if (search < tree[guess].toInt()) hi = guess
            else lo = guess + 1
        }
        // pull out and check key
        val key = tree[lo - 1]
        reader.readBits(key.toInt() and 0x0f)
        return (key.toInt() shr 4) and 0x0fff
    }

    companion object {
        fun build(lens: UByteArray, symcnt: Int): HuffmanTree {
            val tree = UIntArray(symcnt)
            val cnt = IntArray(16)
            val first = IntArray(16)
            val codes = IntArray(16)
            cnt.fill(0)
            cnt[0] = 0
            first[0] = 0
            codes[0] = 0
            for (n in 0 until symcnt) cnt[lens[n].toInt()]++
            for (n in 1..15) {
                codes[n] = (codes[n - 1] + cnt[n - 1]) shl 1
                first[n] = first[n - 1] + cnt[n - 1]
            }
            for (n in 0 until symcnt) {
                val len = lens[n].toInt()
                if (len == 0) continue
                val code = codes[len]++
                val slot = first[len]++
                tree[slot] = ((code shl (32 - len)) or (n shl 4) or len).toUInt()
            }
            return HuffmanTree(tree, first[15])
        }
    }
}

private fun sinflBuild(tree: UIntArray, lens: UByteArray, symcnt: Int): Int {
    val cnt = IntArray(16)
    val first = IntArray(16)
    val codes = IntArray(16)
    cnt.fill(0)
    cnt[0] = 0
    first[0] = 0
    codes[0] = 0
    for (n in 0 until symcnt) cnt[lens[n].toInt()]++
    for (n in 1..15) {
        codes[n] = (codes[n - 1] + cnt[n - 1]) shl 1
        first[n] = first[n - 1] + cnt[n - 1]
    }
    for (n in 0 until symcnt) {
        val len = lens[n].toInt()
        if (len == 0) continue
        val code = codes[len]++
        val slot = first[len]++
        tree[slot] = ((code shl (32 - len)) or (n shl 4) or len).toUInt()
    }
    return first[15]
}

private enum class SinflStates { HDR, STORED, FIXED, DYN, BLK }

class SinflTree {
    val lits = UIntArray(288)
    val dsts = UIntArray(32)
    var tlit = 0
    var tdist = 0

    fun setFixedTree() {
        val lens = ByteArray(288 + 32).also { lens ->
            for (n in 0..143) lens[n] = 8
            for (n in 144..255) lens[n] = 9
            for (n in 256..279) lens[n] = 7
            for (n in 280..287) lens[n] = 8
            for (n in 0..31) lens[288 + n] = 5
        }

        tlit = sinflBuild(lits, lens.toUByteArray(), 288)
        tdist = sinflBuild(dsts, lens.copyOfRange(288, lens.size).toUByteArray(), 32)
    }

    fun copyToState(s: Sinfl) {
        lits.copyInto(s.lits)
        dsts.copyInto(s.dsts)
        tlit = s.tlit
        tdist = s.tdist
    }

    fun fromLengths(lens: ByteArray) {
    }

    companion object {
        val FIXED by lazy { SinflTree().apply { setFixedTree() }
    }
}

private val order = byteArrayOf(16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15)
private val dbase = shortArrayOf(1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577)
private val dbits = byteArrayOf(0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 0, 0)
private val lbase = shortArrayOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258, 0, 0)
private val lbits = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0, 0, 0)

fun sinflate(out: ByteArray, ii: SyncInputStream, size: Int): Int {
    val o = out.copyOf()

    var state = SinflStates.HDR
    val s = Sinfl()
    var last = 0

    s.readBits(ii, 0)
    while (s.bitcnt != 0) {
        when (state) {
            SinflStates.HDR -> {
                var type = 0
                last = s.readBits(ii, 1)
                type = s.readBits(ii, 2)
                state = when (type) {
                    0x00 -> SinflStates.STORED
                    0x01 -> SinflStates.FIXED
                    0x02 -> SinflStates.DYN
                    else -> return out.indexOf(o)
                }
            }
            SinflStates.STORED -> {
                var len: Int
                var nlen: Int
                s.readBits(ii, s.bitcnt and 7)
                len = s.readBits(ii, 16)
                nlen = s.readBits(ii, 16)
                ii[0] = ii[0] - 2
                s.bitcnt = 0

                if (len > e - ii[0] || len == 0) return out.indexOf(o)
                out.copyInto(ii, ii[0], 0, len)
                ii[0] = ii[0] + len
                state = SinflStates.HDR
            }
            SinflStates.FIXED -> {
                SinflTree.FIXED.copyToState(s)
                state = SinflStates.BLK
            }
            SinflStates.DYN -> {
                val nlens = ByteArray(19)
                val lens = ByteArray(288 + 32)
                val nlit = 257 + s.readBits(ii, 5)
                val ndist = 1 + s.readBits(ii, 5)
                val nlen = 4 + s.readBits(ii, 4)
                for (n in 0 until nlen) nlens[order[n].toInt()] = s.readBits(ii, 3).toByte()
                s.tlen = sinflBuild(s.lens, nlens.toUByteArray(), 19)

                var n = 0
                while (n < nlit + ndist) {
                    val sym = s.decode(ii, s.lens, s.tlen)
                    when (sym) {
                        in 0..15 -> lens[n++] = sym.toByte()
                        16 -> {
                            var i = 3 + s.readBits(ii, 2)
                            while (i > 0) {
                                lens[n] = lens[n - 1]
                                i--
                                n++
                            }
                        }
                        17 -> {
                            var i = 3 + s.readBits(ii, 3)
                            while (i > 0) {
                                lens[n] = 0
                                i--
                                n++
                            }
                        }
                        18 -> {
                            var i = 11 + s.readBits(ii, 7)
                            while (i > 0) {
                                lens[n] = 0
                                i--
                                n++
                            }
                        }
                    }
                }
                s.tlit = sinflBuild(s.lits, lens.toUByteArray(), nlit)
                s.tdist = sinflBuild(s.dsts, lens.copyOfRange(nlit, lens.size).toUByteArray(), ndist)
                state = SinflStates.BLK
            }
            SinflStates.BLK -> {
                val sym = s.decode(ii, s.lits, s.tlit)
                if (sym > 256) {
                    var sym = sym - 257
                    val len = s.readBits(ii, lbits[sym].toInt()) + lbase[sym]
                    val dsym = s.decode(ii, s.dsts, s.tdist)
                    val offs = s.readBits(ii, dbits[dsym].toInt()) + dbase[dsym]
                    if (offs > out.indexOf(o)) return out.indexOf(o)
                    while (len > 0) {
                        out[out.indexOf(o)] = out[out.indexOf(o) - offs]
                        o + 1
                    }
                } else if (sym == 256) {
                    if (last != 0) return out.indexOf(o)
                    state = SinflStates.HDR
                } else {
                    out[out.indexOf(o)] = sym.toByte()
                    o + 1
                }
            }
        }
    }
    return out.indexOf(o)
}