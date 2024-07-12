package korlibs.compression.deflate

import korlibs.io.compression.*
import korlibs.io.compression.util.*
import korlibs.io.stream.*


interface IDeflater : CompressionMethod {
    override val name: String get() = "DEFLATE"

    suspend fun uncompress(i: DeflaterBitReader, o: DeflaterAsyncOutputStream)
    suspend fun compress(i: DeflaterBitReader, o: DeflaterAsyncOutputStream, level: Float = 1f)

    override suspend fun uncompress(i: AsyncInputStream, o: AsyncOutputStream) {
        uncompress(BitReader(i).toDeflater(), o.toDeflater())
    }

    override suspend fun compress(i: AsyncInputStream, o: AsyncOutputStream, context: CompressionContext) {
        compress(BitReader(i).toDeflater(), o.toDeflater(), context.level.toFloat() / 10f)
    }
}

internal fun BitReader.toDeflater(): DeflaterBitReader = object : DeflaterBitReader {
    override val bigChunkSize: Int get() = this@toDeflater.bigChunkSize
    override val readWithSize: Int get() = this@toDeflater.readWithSize
    override val bitsavailable: Int get() = this@toDeflater.bitsavailable
    override fun ensureBits(bits: Int) = this@toDeflater.ensureBits(bits)
    override suspend fun hasAvailable(): Boolean = this@toDeflater.hasAvailable()
    override suspend fun getAvailable(): Long  = this@toDeflater.getAvailable()
    override suspend fun abytes(count: Int): ByteArray = this@toDeflater.abytes(count)
    override fun su16LE(): Int = this@toDeflater.su16LE()
    override fun sreadBit(): Boolean = this@toDeflater.sreadBit()
    override fun skipBits(bits: Int)  = this@toDeflater.skipBits(bits)
    override fun peekBits(count: Int): Int  = this@toDeflater.peekBits(count)
    override fun readBits(count: Int): Int  = this@toDeflater.readBits(count)
    override fun returnToBuffer(data: ByteArray, offset: Int, size: Int) = this@toDeflater.returnToBuffer(data, offset, size)
    override suspend fun read(data: ByteArray, offset: Int, size: Int): Int = this@toDeflater.read(data, offset, size)
    override suspend fun readBytesExact(count: Int): ByteArray = this@toDeflater.readBytesExact(count)
    override suspend fun prepareBigChunkIfRequired() = this@toDeflater.prepareBigChunkIfRequired()
}

internal fun AsyncOutputStream.toDeflater(): DeflaterAsyncOutputStream = object : DeflaterAsyncOutputStream {
    override suspend fun write(bytes: ByteArray, offset: Int, size: Int) = this@toDeflater.write(bytes, offset, size)
    override suspend fun write8(value: Int) = this@toDeflater.write8(value)
    override suspend fun write16LE(value: Int) = this@toDeflater.write16LE(value)
    override suspend fun writeBytes(bytes: ByteArray) = this@toDeflater.writeBytes(bytes)
}
