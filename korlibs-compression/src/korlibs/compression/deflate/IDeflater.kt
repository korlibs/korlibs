package korlibs.compression.deflate

import korlibs.io.compression.*
import korlibs.io.stream.*

internal interface IDeflaterInternal : IDeflater {
    suspend fun uncompress(i: DeflaterBitReader, o: DeflaterAsyncOutputStream)
    suspend fun compress(i: DeflaterBitReader, o: DeflaterAsyncOutputStream, level: Float = 1f)
    override suspend fun uncompress(i: AsyncInputStream, o: AsyncOutputStream) {
        this.uncompress(BitReader(i).toDeflater(), o.toDeflater())
    }

    override suspend fun compress(i: AsyncInputStream, o: AsyncOutputStream, context: CompressionContext) {
        this.compress(BitReader(i).toDeflater(), o.toDeflater(), context.level.toFloat() / 10f)
    }
}

interface IDeflater : CompressionMethod {
    override val name: String get() = "DEFLATE"
}
