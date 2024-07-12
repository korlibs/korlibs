package korlibs.io.compression

import korlibs.datastructure.*
import korlibs.io.async.*
import korlibs.io.compression.util.BitReader
import korlibs.io.lang.unsupported
import korlibs.io.stream.*

interface CompressionMethod {
    val name: String get() = "UNKNOWN"

    val level: Int get() = 6

	suspend fun uncompress(i: AsyncInputStream, o: AsyncOutputStream): Unit = unsupported()

	suspend fun compress(
		i: AsyncInputStream,
		o: AsyncOutputStream,
		context: CompressionContext = CompressionContext(level = this.level)
	): Unit = unsupported()

	object Uncompressed : CompressionMethod {
        override val name: String get() = "STORE"

		override suspend fun uncompress(i: AsyncInputStream, o: AsyncOutputStream) { i.copyTo(o) }
		override suspend fun compress(i: AsyncInputStream, o: AsyncOutputStream, context: CompressionContext) { i.copyTo(o) }
	}
}

data class CompressionMethodWithConfig(val method: CompressionMethod, override val level: Int) : CompressionMethod by method, Extra by Extra.Mixin()

fun CompressionMethod.withLevel(level: Int): CompressionMethodWithConfig = CompressionMethodWithConfig(this, level)

suspend fun CompressionMethod.uncompress(i: AsyncInputStream, o: AsyncOutputStream): Unit = uncompress(BitReader.forInput(i), o)
suspend fun CompressionMethod.compress(i: AsyncInputStream, o: AsyncOutputStream, context: CompressionContext = CompressionContext(level = this.level)): Unit = compress(BitReader.forInput(i), o, context)

fun CompressionMethod.uncompress(i: SyncInputStream, o: SyncOutputStream) = runBlockingNoSuspensions {
	uncompress(i.toAsync(), o.toAsync())
}

fun CompressionMethod.compress(i: SyncInputStream, o: SyncOutputStream, context: CompressionContext = CompressionContext(level = this.level)) = runBlockingNoSuspensions {
	compress(i.toAsync(), o.toAsync(), context)
}

fun CompressionMethod.compress(bytes: ByteArray, context: CompressionContext = CompressionContext(level = this.level), outputSizeHint: Int = (bytes.size * 1.1).toInt()): ByteArray =
    MemorySyncStreamToByteArray(outputSizeHint) { this@compress.compress(bytes.openSync(), this, context) }
fun CompressionMethod.uncompress(bytes: ByteArray, outputSizeHint: Int = bytes.size * 2): ByteArray =
    MemorySyncStreamToByteArray(outputSizeHint) { this@uncompress.uncompress(bytes.openSync(), this) }

fun ByteArray.uncompress(method: CompressionMethod, outputSizeHint: Int = this.size * 2): ByteArray =
    method.uncompress(this, outputSizeHint)
fun ByteArray.compress(method: CompressionMethod, context: CompressionContext = CompressionContext(level = method.level), outputSizeHint: Int = (this.size * 1.1).toInt()): ByteArray =
	method.compress(this, context, outputSizeHint)

suspend fun CompressionMethod.uncompressStream(input: AsyncInputStream, bufferSize: Int = DEFAULT_MAX_SIZE): AsyncInputStream =
	//input.readAll().uncompress(this).openAsync()
	asyncStreamWriter(bufferSize, name = "uncompress:$this", lazy = false) { output -> uncompress(input, output) }
suspend fun CompressionMethod.compressStream(
	input: AsyncInputStream,
	context: CompressionContext = CompressionContext(),
	bufferSize: Int = DEFAULT_MAX_SIZE
): AsyncInputStream =
	//input.readAll().compress(this, context).openAsync()
	asyncStreamWriter(bufferSize, name = "compress:$this", lazy = false) { output -> compress(input, output, context) }

private const val DEFAULT_MAX_SIZE = 8 * 1024 * 1024

suspend fun AsyncInputStream.uncompressed(method: CompressionMethod, bufferSize: Int = DEFAULT_MAX_SIZE): AsyncInputStream = method.uncompressStream(this, bufferSize)
suspend fun AsyncInputStream.compressed(method: CompressionMethod, context: CompressionContext = CompressionContext(level = method.level), bufferSize: Int = DEFAULT_MAX_SIZE): AsyncInputStream = method.compressStream(this, context, bufferSize)
