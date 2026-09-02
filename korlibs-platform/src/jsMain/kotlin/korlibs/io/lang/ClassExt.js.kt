package korlibs.io.lang

import kotlin.reflect.KClass

actual val <T : Any> KClass<T>.portableSimpleName: String get() = simpleName ?: "unknown"
