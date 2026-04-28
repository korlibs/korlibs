package korlibs.platform

/**
 * Platform interface that provides information about the current platform.
 */
interface Platform {
    /** Endian-ness */
    val endian: Endian
    /** Architecture */
    val arch: Arch
    /** Operating System */
    val os: Os
    /** Runtime */
    val runtime: Runtime
    /** Raw platform name */
    val rawPlatformName: String
    /** Raw OS name */
    val rawOsName: String
    /** Build variant */
    val buildVariant: BuildVariant

    /**
     * JVM, Android & K/N: true
     * Android: true
     * JS, WASM_JS: false <-- workers have different heaps
     **/
    val hasMultithreadedSharedHeap: Boolean

    /** Whether this is a little endian platform */
    val isLittleEndian: Boolean get() = endian == Endian.LITTLE_ENDIAN
    /** Whether this is a big endian platform */
    val isBigEndian: Boolean get() = endian == Endian.BIG_ENDIAN
    /** Whether this is a debug build */
    val isDebug: Boolean get() = buildVariant == BuildVariant.DEBUG
    /** Whether this is a release build */
    val isRelease: Boolean get() = buildVariant == BuildVariant.RELEASE

    /** Whether this is a Windows platform */
    val isWindows: Boolean get() = os.isWindows
    /** Whether this is a Unix platform */
    val isUnix: Boolean get() = os.isPosix
    /** Whether this is a Posix platform */
    val isPosix: Boolean get() = os.isPosix
    /** Whether this is a Linux platform */
    val isLinux: Boolean get() = os.isLinux
    /** Whether this is a Mac platform */
    val isMac: Boolean get() = os.isMac
    /** Whether this is an Apple device */
    val isApple: Boolean get() = os.isApple
    /** Whether this is a mobile platform */
    val isAppleMobile: Boolean get() = os.isAppleMobile

    /** Whether this is an iOS platform */
    val isIos: Boolean get() = os.isIos
    /** Whether this is an Android platform */
    val isAndroid: Boolean get() = os.isAndroid
    /** Whether this is a tvOS platform */
    val isTvos: Boolean get() = os.isTvos
    /** Whether this is a watchOS platform */
    val isWatchos: Boolean get() = os.isWatchos

    /** Whether this is running in JS */
    val isJs: Boolean get() = runtime.isJs
    /** Whether this is running with Kotlin/Native */
    val isNative: Boolean get() = runtime.isNative
    /** Whether this is running with Kotlin/Native in a desktop environment */
    val isNativeDesktop: Boolean get() = isNative && os.isDesktop
    /** Whether this is running in a JVM */
    val isJvm: Boolean get() = runtime.isJvm
    /** Whether this is running in WASM */
    val isWasm: Boolean get() = runtime.isWasm
    /** Whether this is running in a JS or a WASM runtime */
    val isJsOrWasm: Boolean get() = isJs || isWasm

    /** Whether this is running in a JS or a WASM runtime */
    // @TODO: Fix this
    val isInsideBrowser: Boolean get() = (isJs || isWasm)// && (rawPlatformName.contains("web") || rawPlatformName.contains("worker"))

    /** Whether this is running in a JS shell */
    val isJsShell: Boolean get() = rawPlatformName == "js-shell" || rawPlatformName == "wasm-shell"
    /** Whether this is running in a JS Node.js environment */
    val isJsNodeJs: Boolean get() = rawPlatformName == "js-node" || rawPlatformName == "wasm-node"
    /** Whether this is running in a JS Deno environment */
    val isJsDenoJs: Boolean get() = rawPlatformName == "js-deno" || rawPlatformName == "wasm-deno"
    /** Whether this is running in a JS browser environment */
    val isJsBrowser: Boolean get() = rawPlatformName == "js-web" || rawPlatformName == "wasm-web"
    /** Whether this is running in a JS worker environment */
    val isJsWorker: Boolean get() = rawPlatformName == "js-worker" || rawPlatformName == "wasm-worker"
    /** Whether this is running in a JS browser or worker environment */
    val isJsBrowserOrWorker: Boolean get() = isJsBrowser || isJsWorker

    /** Current Platform */
    companion object : Platform {
        override val endian: Endian get() = Endian.NATIVE
        override val isLittleEndian: Boolean get() = currentIsLittleEndian
        override val isBigEndian: Boolean get() = !currentIsLittleEndian
        override val arch: Arch get() = Arch.CURRENT
        override val os: Os get() = Os.CURRENT
        override val runtime: Runtime get() = Runtime.CURRENT
        override val rawPlatformName: String get() = currentRawPlatformName
        override val rawOsName: String get() = currentRawOsName
        override val buildVariant: BuildVariant get() = BuildVariant.CURRENT
        override val isDebug: Boolean get() = currentIsDebug
        override val isRelease: Boolean get() = !currentIsDebug
        override val hasMultithreadedSharedHeap: Boolean get() = multithreadedSharedHeap

        val envs: Map<String, String> by lazy { korlibs.platform.envs }
        val envsUC: Map<String, String> by lazy { envs.mapKeys { it.key.uppercase() } }
        val languagesRaw: List<String> get() = korlibs.platform.languages

        operator fun invoke(
            endian: Endian = Endian.LITTLE_ENDIAN,
            arch: Arch = Arch.UNKNOWN,
            os: Os = Os.UNKNOWN,
            runtime: Runtime = Runtime.JVM,
            buildVariant: BuildVariant = BuildVariant.DEBUG,
            rawPlatformName: String = "unknown",
            rawOsName: String = "unknown",
            hasMultithreadedSharedHeap: Boolean = false,
        ): Platform = Impl(endian, arch, os, runtime, buildVariant, rawPlatformName, rawOsName, hasMultithreadedSharedHeap)
    }

    data class Impl(
        override val endian: Endian,
        override val arch: Arch,
        override val os: Os,
        override val runtime: Runtime,
        override val buildVariant: BuildVariant,
        override val rawPlatformName: String,
        override val rawOsName: String,
        override val hasMultithreadedSharedHeap: Boolean,
    ) : Platform
}
