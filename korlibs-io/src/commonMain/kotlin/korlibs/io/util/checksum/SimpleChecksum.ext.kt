package korlibs.io.util.checksum

import korlibs.io.async.use
import korlibs.io.stream.*

suspend fun AsyncInputOpenable.checksum(checksum: SimpleChecksum) = this.openRead().use { it.checksum(checksum) }

fun SyncInputStream.checksum(checksum: SimpleChecksum): Int =
	checksum.computeWithRead { this.read(it) }

suspend fun AsyncInputStream.checksum(checksum: SimpleChecksum): Int =
	checksum.computeWithRead { this.read(it) }

fun AsyncOutputStream.withChecksumUpdater(checksum: SimpleChecksumUpdater): AsyncOutputStream = object : AsyncOutputStream by this@withChecksumUpdater {
    override suspend fun write(buffer: ByteArray, offset: Int, len: Int) {
        checksum.update(buffer, offset, len)
        this@withChecksumUpdater.write(buffer, offset, len)
    }
}
