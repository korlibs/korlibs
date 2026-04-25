package korlibs.platform

/** Runtime */
enum class Runtime {
    /** JavaScript */
    JS,
    /** Java Virtual Machine */
    JVM,
    /** Android */
    ANDROID,
    /** Kotlin/Native */
    NATIVE,
    /** WebAssembly */
    WASM;

    /** Whether this is JavaScript */
    val isJs: Boolean get() = this == JS
    /** Whether this is Java Virtual Machine */
    val isJvm: Boolean get() = this == JVM
    /** Whether this is Android */
    val isAndroid: Boolean get() = this == ANDROID
    /** Whether this is Kotlin/Native */
    val isNative: Boolean get() = this == NATIVE
    /** Whether this is JVM or Android */
    val isJvmOrAndroid: Boolean get() = isJvm || isAndroid
    /** Whether this is WebAssembly */
    val isWasm: Boolean get() = this == WASM

    companion object {
        val CURRENT: Runtime get() = currentRuntime
    }
}
