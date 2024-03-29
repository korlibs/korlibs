package korlibs.time

import korlibs.time.core.*
import kotlin.time.*

/** Sleeps the thread during the specified time. Spinlocks on JS */
@OptIn(CoreTimeInternalApi::class)
@ExperimentalStdlibApi
fun blockingSleep(time: Duration): Unit = CoreTime.sleep(time)
