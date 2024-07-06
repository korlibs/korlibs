package korlibs.image.core

internal val CoreImageIsMAC = System.getProperty("os.name").let {
    it.contains("mac", ignoreCase = true) || it.contains("darwin", ignoreCase = true)
}

actual val CoreImageFormatProvider_default: CoreImageFormatProvider by lazy {
     when {
        CoreImageIsMAC -> CoreGraphicsCoreImageFormatProvider // In MacOS native decoder is 2x~5x faster than the one in the JVM
        else -> AwtCoreImageFormatProvider
    }
}
