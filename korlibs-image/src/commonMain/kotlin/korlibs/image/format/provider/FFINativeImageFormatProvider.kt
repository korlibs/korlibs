package korlibs.image.format.provider

import korlibs.image.format.BaseNativeImageFormatProvider
import korlibs.platform.Platform

val FFINativeImageFormatProviderOpt: BaseNativeImageFormatProvider? by lazy {
    when {
        Platform.isMac -> FFICoreGraphicsImageFormatProvider
        Platform.isWindows -> FFIGdiNativeImageFormatProvider
        Platform.isLinux -> FFISDLImageNativeImageFormatProvider
        else -> null
    }
}

val FFINativeImageFormatProvider get() = FFINativeImageFormatProviderOpt
    ?: error("Unsupported platform '${Platform.os}' for FFINativeImageFormatProvider")
