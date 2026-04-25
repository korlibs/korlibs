package korlibs.io.stream

import java.io.*

class FileSyncStreamBase(val file: java.io.File, val mode: String = "r") : SyncStreamBase() {
	val ra = RandomAccessFile(file, mode)

	override fun read(position: Long, buffer: ByteArray, offset: Int, len: Int): Int {
		ra.seek(position)
		return ra.read(buffer, offset, len)
	}

	override fun write(position: Long, buffer: ByteArray, offset: Int, len: Int) {
		ra.seek(position)
		ra.write(buffer, offset, len)
	}

	override var length: Long
		get() = ra.length()
		set(value) { ra.setLength(value) }

	override fun close() = ra.close()
}

fun File.openSync(mode: String = "r"): SyncStream = FileSyncStreamBase(this, mode).toSyncStream()

@Deprecated("Use toSyncInputStream instead", ReplaceWith("openSync(mode)"))
fun InputStream.toSyncStream(): SyncInputStream = toSyncInputStream()

fun SyncStream.toInputStream(): InputStream {
	val ss = this
	return object : InputStream() {
		override fun read(): Int = if (ss.eof) -1 else ss.readU8()
		override fun read(b: ByteArray, off: Int, len: Int): Int = ss.read(b, off, len)
		override fun available(): Int = ss.available.toInt()
		override fun close() = this@toInputStream.close()
	}
}
