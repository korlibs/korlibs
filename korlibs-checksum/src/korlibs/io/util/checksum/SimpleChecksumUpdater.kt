package korlibs.io.util.checksum

class SimpleChecksumUpdater(val checksum: SimpleChecksum) {
    var current: Int = checksum.initialValue

    fun update(data: ByteArray, offset: Int = 0, len: Int = data.size - offset): Int {
        current = checksum.update(current, data, offset, len)
        return current
    }

    inline fun updateWithRead(read: (buffer: ByteArray) -> Int): Int {
        current = checksum.computeWithRead(current, read)
        return current
    }
}

fun SimpleChecksum.updater(): SimpleChecksumUpdater = SimpleChecksumUpdater(this)
