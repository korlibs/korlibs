package korlibs.time

import korlibs.time.core.*

/** Sleeps the thread during the specified time. Spinlocks on JS */
@OptIn(CoreTimeInternalApi::class)
@ExperimentalStdlibApi
fun blockingSleep(time: TimeSpan): Unit = CoreTime.sleep(time)
