package korlibs.io.util.checksum

/**
 * Mutable checksum updater for [SimpleChecksum], with a provided [checksum]
 *
 * For example:
 * ```kotlin
 * val checksum = SimpleChecksumUpdater(CRC32)
 * checksum.reset()
 * checksum.update(byteArrayOf(1, 2, 3, 4), 1, 2)
 * checksum.current
 * ```
 */
class SimpleChecksumUpdater(val checksum: SimpleChecksum) {
    /** Current checksum value */
    var current: Int = checksum.initialValue

    /** Resets the checksum to the initial value */
    fun reset() {
        current = checksum.initialValue
    }

    /** Updates the checksum with the [data] ByteArray in the range [offset] and [len]. And returns the updated checksum. */
    fun update(data: ByteArray, offset: Int = 0, len: Int = data.size - offset): Int {
        current = checksum.update(current, data, offset, len)
        return current
    }

    /** Updates the checksum with the [read] function. And returns the updated checksum. */
    inline fun updateWithRead(read: (buffer: ByteArray) -> Int): Int {
        current = checksum.computeWithRead(current, read)
        return current
    }
}

/** Creates a [SimpleChecksumUpdater] for this [SimpleChecksum] */
fun SimpleChecksum.updater(): SimpleChecksumUpdater = SimpleChecksumUpdater(this)
