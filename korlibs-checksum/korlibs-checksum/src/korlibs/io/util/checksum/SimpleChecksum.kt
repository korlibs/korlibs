package korlibs.io.util.checksum

/** Represents a 32-bit checksum interface, for checksums like [CRC32] or [Adler32] */
interface SimpleChecksum {
    /** Initial value for the checksum. */
	val initialValue: Int
    /** Returns an updated checksum starting with an [old] checksum, and then updating with a [data] ByteArray in the range [offset] and [len] */
	fun update(old: Int, data: ByteArray, offset: Int = 0, len: Int = data.size - offset): Int
}

/** Computes the checksum of the [data] determined in the range [offset] and [len]. */
fun SimpleChecksum.compute(data: ByteArray, offset: Int = 0, len: Int = data.size - offset) = update(initialValue, data, offset, len)

/** Computes the checksum of [this] ByteArray. */
fun ByteArray.checksum(checksum: SimpleChecksum): Int = checksum.compute(this)

/**
 * Utility to perform a checksum by small chunks of data provided by the [read] function.
 *
 * This is useful to avoid loading the whole data into memory.
 *
 * The read function should return the number of bytes read or -1 if there is no more data to read.
 */
inline fun SimpleChecksum.computeWithRead(initial: Int = this.initialValue, read: (buffer: ByteArray) -> Int): Int {
    var current = initial
    val temp = ByteArray(1024)
    while (true) {
        val readCount = read(temp)
        if (readCount <= 0) break
        current = update(current, temp, 0, readCount)
    }
    return current
}
