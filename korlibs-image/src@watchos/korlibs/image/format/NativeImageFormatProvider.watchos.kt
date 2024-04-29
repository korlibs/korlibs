package korlibs.image.format

actual val nativeImageFormatProvider: NativeImageFormatProvider get() = WatchosBaseNativeImageFormatProvider

open class WatchosBaseNativeImageFormatProvider : StbImageNativeImageFormatProvider() {
    companion object : WatchosBaseNativeImageFormatProvider()
}