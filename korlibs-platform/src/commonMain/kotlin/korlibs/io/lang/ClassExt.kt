package korlibs.io.lang

import kotlin.reflect.*

/**
 * Like [KClass.simpleName] but guaranteed to work on all the targets.
 * Returns the simple name of the class in a portable way.
 **/
expect val <T : Any> KClass<T>.portableSimpleName: String
