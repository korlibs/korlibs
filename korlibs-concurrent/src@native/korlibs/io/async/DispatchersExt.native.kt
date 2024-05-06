@file:OptIn(ExperimentalNativeApi::class)

package korlibs.io.async

import korlibs.io.concurrent.*
import kotlinx.coroutines.*
import kotlin.experimental.*

actual val Dispatchers.ConcurrencyLevel: Int get() = Platform.getAvailableProcessors()
