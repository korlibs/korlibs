package korlibs.io.async

import korlibs.io.concurrent.*
import kotlinx.coroutines.*
import kotlin.math.*

actual val Dispatchers.ConcurrencyLevel: Int get() = maxOf(1, java.lang.Runtime.getRuntime().availableProcessors())
