package korlibs.compression.deflate

@ExperimentalStdlibApi
expect fun DeflaterNative(windowBits: Int): IDeflater

interface IDeflater {
    suspend fun uncompress(i: DeflaterBitReader, o: DeflaterAsyncOutputStream)
    suspend fun compress(i: DeflaterBitReader, o: DeflaterAsyncOutputStream, level: Float = 1f)
}
