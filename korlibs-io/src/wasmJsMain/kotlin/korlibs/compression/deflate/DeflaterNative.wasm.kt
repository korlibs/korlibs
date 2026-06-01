package korlibs.compression.deflate

actual fun DeflaterNative(windowBits: Int): IDeflater = DeflaterPortable(windowBits)
