package korlibs.platform

/** Build variant */
enum class BuildVariant {
    /** Debug variant */
    DEBUG,
    /** Release variant */
    RELEASE;

    /** Whether this is a debug variant */
    val isDebug: Boolean get() = this == DEBUG
    /** Whether this is a release variant */
    val isRelease: Boolean get() = this == RELEASE

    companion object {
        /** Current build variant */
        val CURRENT: BuildVariant get() = currentBuildVariant
    }
}
