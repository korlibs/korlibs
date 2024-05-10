@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.stream

import korlibs.memory.extract

class ByteArrayBitReader(val data: ByteArray) {
    private var dataPos: Int = 0
    private var currentBits: Int = 0
    private var availableBits: Int = 0
    private val availableBytes: Int get() = data.size - dataPos
    val hasMoreBits: Boolean get() = availableBits > 0 || availableBytes > 0

    private fun feedByte() {
        currentBits = currentBits shl 8
        currentBits = currentBits or ((data[dataPos++].toInt() and 0xFF))
        availableBits += 8
    }

    // @TODO: Cover edge cases
    fun readIntBits(nbits: Int): Int {
        check(availableBits <= 32)
        while (availableBits < nbits && availableBytes > 0) {
            if (availableBits > 24) TODO()
            feedByte()
        }
        availableBits -= nbits
        return currentBits.extract(availableBits, nbits)
    }
}
