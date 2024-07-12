package korlibs.io.compression.lzma

import korlibs.compression.lzma.*
import korlibs.io.compression.CompressionContext
import korlibs.io.compression.CompressionMethod
import korlibs.io.stream.*

/**
 * @TODO: Streaming! (right now loads the whole stream in-memory)
 */
@OptIn(ExperimentalStdlibApi::class)
object Lzma : CompressionMethod {
    override val name: String get() = "LZMA"

	override suspend fun uncompress(i: AsyncInputStream, o: AsyncOutputStream) {
		val input = i.readAll().openSync()
		val properties = input.readBytesExact(5)
		val decoder = SevenZip.LzmaDecoder()
		if (!decoder.setDecoderProperties(properties)) throw Exception("Incorrect stream properties")
		val outSize = input.readS64LE()

		o.writeBytes(MemorySyncStreamToByteArray {
			if (!decoder.code(input.toLzmaInput(), this.toLzmaOutput(), outSize)) throw Exception("Error in data stream")
		})
	}

	override suspend fun compress(i: AsyncInputStream, o: AsyncOutputStream, context: CompressionContext) {
		val algorithm = 2
		val matchFinder = 1
		val dictionarySize = 1 shl 23
		val lc = 3
		val lp = 0
		val pb = 2
		val fb = 128
		val eos = false

		val input = i.readAll()

		val out = MemorySyncStreamToByteArray {
			val output = this.toLzmaOutput()
			val encoder = SevenZip.LzmaEncoder()
			if (!encoder.setAlgorithm(algorithm)) throw Exception("Incorrect compression mode")
			if (!encoder.setDictionarySize(dictionarySize))
				throw Exception("Incorrect dictionary size")
			if (!encoder.setNumFastBytes(fb)) throw Exception("Incorrect -fb value")
			if (!encoder.setMatchFinder(matchFinder)) throw Exception("Incorrect -mf value")
			if (!encoder.setLcLpPb(lc, lp, pb)) throw Exception("Incorrect -lc or -lp or -pb value")
			encoder.setEndMarkerMode(eos)
			encoder.writeCoderProperties(output)
			val fileSize: Long = if (eos) -1 else input.size.toLong()
			this.write64LE(fileSize)
			encoder.code(input.openSync().toLzmaInput(), output, -1, -1, null)
		}

		o.writeBytes(out)
	}

	fun SyncInputStream.toLzmaInput(): SevenZip.LzmaInputStream = object : SevenZip.LzmaInputStream {
		override fun read(): Int = this@toLzmaInput.read()
		override fun read(bytes: ByteArray, offset: Int, size: Int): Int = this@toLzmaInput.read(bytes, offset, size)
	}
	fun SyncOutputStream.toLzmaOutput(): SevenZip.LzmaOutputStream = object : SevenZip.LzmaOutputStream {
		override fun flush() = this@toLzmaOutput.flush()
		override fun write8(value: Int) = this@toLzmaOutput.write8(value)
		override fun write(bytes: ByteArray, offset: Int, size: Int) = this@toLzmaOutput.write(bytes, offset, size)
	}
}
