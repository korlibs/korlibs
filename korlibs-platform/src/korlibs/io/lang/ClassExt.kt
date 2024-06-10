package korlibs.io.lang

import kotlin.reflect.*

expect val <T : Any> KClass<T>.portableSimpleName: String
