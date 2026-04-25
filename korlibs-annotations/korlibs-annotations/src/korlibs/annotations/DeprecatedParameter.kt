package korlibs.annotations

/**
 * Marks a parameter as deprecated.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class DeprecatedParameter(
    val reason: String
)
