package korlibs.io.compression.deflate

import korlibs.compression.deflate.DeflaterNative
import korlibs.compression.deflate.DeflaterPortable
import korlibs.compression.deflate.IDeflater

@OptIn(ExperimentalStdlibApi::class)
fun Deflate(windowBits: Int): IDeflater = DeflaterNative(windowBits)
fun DeflatePortable(windowBits: Int): IDeflater = DeflaterPortable(windowBits)
val DeflatePortable: IDeflater get() = DeflaterPortable

val Deflate: IDeflater by lazy { Deflate(15) }
