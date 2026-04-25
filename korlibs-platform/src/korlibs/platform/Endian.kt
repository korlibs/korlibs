package korlibs.platform

/** Endian */
public enum class Endian {
    /** Little endian */
    LITTLE_ENDIAN,
    /** Big endian */
    BIG_ENDIAN
    ;

    /** Whether this is little endian */
    val isLittle: Boolean get() = this == LITTLE_ENDIAN
    /** Whether this is big endian */
    val isBig: Boolean get() = this == BIG_ENDIAN

    public companion object {
        /** Whether the current platform is little endian */
        val isLittleEndian: Boolean get() = currentIsLittleEndian
        /** Whether the current platform is big endian */
        val isBigEndian: Boolean get() = !currentIsLittleEndian

        /** Current endian for the current architecture */
        public val NATIVE: Endian = if (currentIsLittleEndian) LITTLE_ENDIAN else BIG_ENDIAN
    }
}
