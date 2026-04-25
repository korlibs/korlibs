package korlibs.annotations

/**
 * Annotate a class, function, or property to keep it in the output even if it's not used.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class Keep

/**
 * Annotate a class, function, or property to keep its names in the output even if it's not used.
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class KeepNames
