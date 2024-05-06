package korlibs.io.concurrent

import kotlin.math.*

actual val CONCURRENCY_COUNT: Int = max(1, java.lang.Runtime.getRuntime().availableProcessors())
