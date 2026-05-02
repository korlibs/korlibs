package korlibs.platform

/** Operating System */
enum class Os {
    /** Unknown OS */
    UNKNOWN,
    /** Web Assembly */
    WASM,
    /** Linux */
    LINUX,
    /** Windows */
    WINDOWS,
    /** Android */
    ANDROID,
    /** Apple macOS */
    MACOSX,
    /** Apple iOS */
    IOS,
    /** Apple tvOS */
    TVOS,
    /** Apple watchOS */
    WATCHOS
    ;

    /** Whether this OS is [WINDOWS] */
    val isWindows: Boolean get() = this == WINDOWS
    /** Whether this OS is [ANDROID] */
    val isAndroid: Boolean get() = this == ANDROID
    /** Whether this OS is [LINUX] */
    val isLinux: Boolean get() = this == LINUX
    /** Whether this OS is [MACOSX] */
    val isMac: Boolean get() = this == MACOSX
    /** Whether this OS is [WASM] */
    val isIos: Boolean get() = this == IOS
    /** Whether this OS is [TVOS] */
    val isTvos: Boolean get() = this == TVOS
    /** Whether this OS is [WATCHOS] */
    val isWatchos: Boolean get() = this == WATCHOS
    /** Whether this OS is [IOS] or [TVOS] */
    val isAppleMobile: Boolean get() = isIos || isTvos
    /** Whether this OS is [LINUX] or [WINDOWS] or [MACOSX] */
    val isDesktop: Boolean get() = isLinux || isWindows || isMac
    /** Whether this OS is a mobile OS */
    val isMobile: Boolean get() = isAndroid || isAppleMobile
    /** Whether this OS is from Apple */
    val isApple: Boolean get() = isMac || isAppleMobile
    /** Whether this OS is a Unix-like OS (any OS but Windows) */
    val isPosix: Boolean get() = !isWindows

    companion object {
        /** All the values of the enum class */
        val VALUES = entries
        /** Current OS for the current platform */
        val CURRENT: Os get() = currentOs
    }
}
