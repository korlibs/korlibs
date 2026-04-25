package korlibs.image.format

import korlibs.image.format.cg.CGNativeImageFormatProvider

// https://developer.apple.com/library/archive/documentation/GraphicsImaging/Conceptual/drawingwithquartz2d/dq_context/dq_context.html#//apple_ref/doc/uid/TP30001066-CH203-BCIBHHBB
actual val nativeImageFormatProvider: NativeImageFormatProvider = CGNativeImageFormatProvider
