@file:Suppress("NAME_SHADOWING", "ClassName")

package korlibs.crypto

object SHA3_224 : HasherFactory("SHA3-224", { SHA3(224 / Byte.SIZE_BITS) })
object SHA3_256 : HasherFactory("SHA3-256", { SHA3(256 / Byte.SIZE_BITS) })
object SHA3_384 : HasherFactory("SHA3-384", { SHA3(384 / Byte.SIZE_BITS) })
object SHA3_512 : HasherFactory("SHA3-512", { SHA3(512 / Byte.SIZE_BITS) })

// Based off of the following implementations:
//   - https://keccak.team/archives.html (Version 3.2 of the ``Reference and optimized code in C'')
//   - https://github.com/mjosaarinen/tiny_sha3
//   - https://github.com/brainhub/SHA3IUF
@OptIn(ExperimentalUnsignedTypes::class)
open class SHA3 internal constructor(digestSize: Int) : Hasher(
    chunkSize = 200 - 2 * digestSize,
    digestSize = digestSize,
    name = "SHA3-${digestSize * 8}"
) {
    private val buf = ULongArray(SHA3_KECCAK_SPONGE_WORDS)

    override fun coreReset() {
        buf.fill(0uL)
    }

    override fun corePadding(totalWritten: Long): ByteArray {
        val tail = (totalWritten % chunkSize).toInt()
        val padding = if (chunkSize - tail == 0) chunkSize else (chunkSize - tail)
        return ByteArray(padding).also {
            it[0] = 0x06
            it[it.size - 1] = (it[padding - 1].toInt() or 0x80).toByte()
        }
    }

    override fun coreUpdate(chunk: ByteArray) {
        for (i in 0 until chunkSize) {
            val wordIndex = i / 8
            val byteOffset = i % 8
            buf[wordIndex] = buf[wordIndex] xor ((chunk[i].toULong() and 0xFFuL) shl (8 * byteOffset))
        }
        keccakf(buf)
    }

    override fun coreDigest(out: ByteArray) {
        for (i in 0 until this@SHA3.digestSize) {
            val wordIndex = i / 8
            val byteOffset = i % 8
            out[i] = ((buf[wordIndex] shr (8 * byteOffset)) and 0xFFuL).toByte()
        }
    }

    companion object : HasherFactory("SHA3-256", { SHA3(32) }) {
        const val KECCAKF_ROUNDS = 24
        const val SHA3_KECCAK_SPONGE_WORDS = 1600 / Long.SIZE_BITS
        val KECCAKF_ROUND_CONSTANTS = ulongArrayOf(
            0x0000000000000001uL, 0x0000000000008082uL, 0x800000000000808AuL,
            0x8000000080008000uL, 0x000000000000808BuL, 0x0000000080000001uL,
            0x8000000080008081uL, 0x8000000000008009uL, 0x000000000000008AuL,
            0x0000000000000088uL, 0x0000000080008009uL, 0x000000008000000AuL,
            0x000000008000808BuL, 0x800000000000008BuL, 0x8000000000008089uL,
            0x8000000000008003uL, 0x8000000000008002uL, 0x8000000000000080uL,
            0x000000000000800AuL, 0x800000008000000AuL, 0x8000000080008081uL,
            0x8000000000008080uL, 0x0000000080000001uL, 0x8000000080008008uL
        )
        val KECCAKF_ROTATION_OFFSETS = intArrayOf(
            1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 2, 14,
            27, 41, 56, 8, 25, 43, 62, 18, 39, 61, 20, 44
        )
        val KECCAKF_PI_LANE = intArrayOf(
            10, 7, 11, 17, 18, 3, 5, 16, 8, 21, 24, 4,
            15, 23, 19, 13, 12, 2, 20, 14, 22, 9, 6, 1
        )

        private val MOD5 = intArrayOf(
            0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 5
        )

        private fun keccakf(state: ULongArray) {
            val bc = ULongArray(5)

            for (r in 0 until KECCAKF_ROUNDS) {
                // Theta
                for (i in 0 until 5)
                    bc[i] = state[i] xor state[i + 5] xor state[i + 10] xor state[i + 15] xor state[i + 20]

                for (i in 0 until 5) {
                    val t = bc[MOD5[i + 4]] xor bc[MOD5[i + 1]].rotateLeft(1)

                    var j = i
                    while (j < 25) { // for (j in i until 25 step 5)
                        state[j] = state[j] xor t
                        j += 5
                    }
                }

                // Rho Pi
                var t = state[1]
                for (i in 0 until (SHA3_KECCAK_SPONGE_WORDS - 1)) {
                    val j = KECCAKF_PI_LANE[i]
                    val temp = state[j]
                    state[j] = t.rotateLeft(KECCAKF_ROTATION_OFFSETS[i])
                    t = temp
                }

                // Chi
                var j = 0
                while (j < SHA3_KECCAK_SPONGE_WORDS) { // for (j in 0 until 25 step 5)
                    for (i in 0 until 5)
                        bc[i] = state[j + i]

                    for (i in 0 until 5)
                        state[j + i] = bc[i] xor ((bc[MOD5[i + 1]].inv()) and bc[MOD5[i + 2]])

                    j += 5
                }

                // Iota
                state[0] = state[0] xor KECCAKF_ROUND_CONSTANTS[r]
            }
        }
    }
}

fun ByteArray.sha3_224() = hash(SHA3_224)

fun ByteArray.sha3_256() = hash(SHA3_256)

fun ByteArray.sha3_384() = hash(SHA3_384)

fun ByteArray.sha3_512() = hash(SHA3_512)
