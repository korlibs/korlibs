package korlibs.image.format

actual val nativeImageFormatProvider: NativeImageFormatProvider get() = WatchosBaseNativeImageFormatProvider

open class WatchosBaseNativeImageFormatProvider : CoreImageNativeImageFormatProvider() {
    companion object : WatchosBaseNativeImageFormatProvider()
}