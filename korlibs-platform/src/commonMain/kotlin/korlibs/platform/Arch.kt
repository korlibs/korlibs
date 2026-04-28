package korlibs.platform

/**
 * Architecture
 */
enum class Arch(val bits: Int, val isArm: Boolean = false, val isX86OrX64: Boolean = false, val isMips: Boolean = false, val isWasm: Boolean = false, val isPowerPC: Boolean = false) {
    UNKNOWN(-1),
    /** 32-bit x86 */
    X86(32, isX86OrX64 = true),
    /** 64-bit x86_64 */
    X64(64, isX86OrX64 = true),
    /** 32-bit ARM */
    ARM32(32, isArm = true),
    /** 64-bit ARM */
    ARM64(64, isArm = true),
    /** 32-bit MIPS */
    MIPS32(32, isMips = true),
    /** 32-bit MIPS Little Endian */
    MIPSEL32(32, isMips = true),
    /** 64-bit MIPS */
    MIPS64(64, isMips = true),
    /** 64-bit MIPS Little Endian */
    MIPSEL64(64, isMips = true),
    /** 32-bit WebAssembly */
    WASM32(32, isWasm = true),
    /** 64-bit PowerPC */
    POWERPC64(64, isPowerPC = true);

    /** Whether this architecture is 32-bit */
    val is32Bits: Boolean get() = bits == 32
    /** Whether this architecture is 64-bit */
    val is64Bits: Boolean get() = bits == 64

    /** Whether this architecture is x86 */
    val isX86: Boolean get() = this == X86
    /** Whether this architecture is x86_64 */
    val isX64: Boolean get() = this == X64

    /** Whether this architecture is ARM */
    val isArm32: Boolean get() = this == ARM32
    /** Whether this architecture is ARM64 */
    val isArm64: Boolean get() = this == ARM64

    /** Whether this architecture is MIPS */
    val isMIPS32: Boolean get() = this == MIPS32
    /** Whether this architecture is MIPS Little Endian */
    val isMIPSEL32: Boolean get() = this == MIPSEL32
    /** Whether this architecture is MIPS64 */
    val isMIPS64: Boolean get() = this == MIPS64
    /** Whether this architecture is MIPS Little Endian */
    val isMIPSEL64: Boolean get() = this == MIPSEL64

    /** Whether this architecture is WebAssembly */
    val isWASM32: Boolean get() = this == WASM32
    /** Whether this architecture is PowerPC */
    val isPOWERPC64: Boolean get() = this == POWERPC64

    companion object {
        val CURRENT: Arch get() = currentArch
    }
}
