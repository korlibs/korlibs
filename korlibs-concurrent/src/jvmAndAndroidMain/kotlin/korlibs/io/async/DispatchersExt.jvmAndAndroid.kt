package korlibs.io.async

import kotlinx.coroutines.Dispatchers

actual val Dispatchers.ConcurrencyLevel: Int get() = maxOf(1, java.lang.Runtime.getRuntime().availableProcessors())
