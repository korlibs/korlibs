package korlibs.image.format

actual val nativeImageFormatProvider: NativeImageFormatProvider get() = LinuxBaseNativeImageFormatProvider

object LinuxBaseNativeImageFormatProvider : CoreImageNativeImageFormatProvider()
