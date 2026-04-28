package korlibs.io.compression.deflate

import korlibs.compression.deflate.*

@OptIn(ExperimentalStdlibApi::class)
fun Deflate(windowBits: Int): IDeflater = DeflaterNative(windowBits)
fun DeflatePortable(windowBits: Int): IDeflater = DeflaterPortable(windowBits)
val DeflatePortable: IDeflater get() = DeflaterPortable

val Deflate: IDeflater by lazy { Deflate(15) }
