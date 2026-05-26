package korlibs.io.stream

import java.io.InputStream
import korlibs.io.async.withContextNullable
import korlibs.io.lang.unsupported
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

fun InputStream.toAsync(context: CoroutineContext? = Dispatchers.IO, length: Long? = null): AsyncInputStream {
	val syncIS = this
	return when {
		length != null -> object : AsyncInputStream, AsyncLengthStream {
			override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = withContextNullable(context) { syncIS.read(buffer, offset, len) }
			override suspend fun read(): Int = withContextNullable(context) { syncIS.read() }
			override suspend fun close() = withContextNullable(context) { syncIS.close() }
			override suspend fun setLength(value: Long): Unit = unsupported("Can't set length")
			override suspend fun getLength(): Long = length
			override suspend fun hasLength(): Boolean = true
		}
		else -> object : AsyncInputStream {
			override suspend fun read(buffer: ByteArray, offset: Int, len: Int): Int = withContextNullable(context) { syncIS.read(buffer, offset, len) }
			override suspend fun read(): Int = withContextNullable(context) { syncIS.read() }
			override suspend fun close(): Unit = withContextNullable(context) { syncIS.close() }
		}
	}
}
