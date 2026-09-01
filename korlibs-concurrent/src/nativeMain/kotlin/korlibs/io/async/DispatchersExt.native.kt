@file:OptIn(ExperimentalNativeApi::class)

package korlibs.io.async

import kotlin.experimental.ExperimentalNativeApi
import kotlinx.coroutines.Dispatchers

actual val Dispatchers.ConcurrencyLevel: Int get() = Platform.getAvailableProcessors()
