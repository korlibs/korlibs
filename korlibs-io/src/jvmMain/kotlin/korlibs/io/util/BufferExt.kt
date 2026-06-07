package korlibs.io.util

import java.nio.ByteBuffer

fun ByteBuffer.toByteArray(): ByteArray {
	val out = ByteArray(this.limit())
	this.get(out, 0, out.size)
	return out
}
