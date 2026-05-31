package korlibs.time

import korlibs.time.core.CoreTime
import korlibs.time.core.CoreTimeInternalApi
import kotlin.time.Duration

/** Sleeps the thread during the specified time. Spinlocks on JS */
@OptIn(CoreTimeInternalApi::class)
@ExperimentalStdlibApi
fun blockingSleep(time: Duration): Unit = CoreTime.sleep(time)
