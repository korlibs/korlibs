package korlibs.image.format

import korlibs.image.format.cg.CGNativeImageFormatProvider

actual val nativeImageFormatProvider: NativeImageFormatProvider = CGNativeImageFormatProvider
