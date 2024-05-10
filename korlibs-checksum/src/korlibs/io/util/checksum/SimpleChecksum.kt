package korlibs.io.util.checksum

interface SimpleChecksum {
	val initialValue: Int
	fun update(old: Int, data: ByteArray, offset: Int = 0, len: Int = data.size - offset): Int
}

fun SimpleChecksum.compute(data: ByteArray, offset: Int = 0, len: Int = data.size - offset) = update(initialValue, data, offset, len)

fun ByteArray.checksum(checksum: SimpleChecksum): Int = checksum.compute(this)

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
