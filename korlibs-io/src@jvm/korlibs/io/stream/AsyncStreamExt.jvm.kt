package korlibs.io.stream

import korlibs.io.async.*
import korlibs.io.lang.*
import kotlinx.coroutines.*
import java.io.*
import kotlin.coroutines.*

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
