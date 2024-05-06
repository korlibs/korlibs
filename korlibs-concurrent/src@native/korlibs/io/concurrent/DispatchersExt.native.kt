@file:OptIn(ExperimentalNativeApi::class)

package korlibs.io.concurrent

import kotlin.experimental.*

actual val CONCURRENCY_COUNT: Int = Platform.getAvailableProcessors()

