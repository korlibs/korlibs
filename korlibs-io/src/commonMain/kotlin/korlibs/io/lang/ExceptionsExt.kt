package korlibs.io.lang

import korlibs.logger.Console

fun currentStackTrace(msg: String = "printStackTrace"): String = Exception(msg).stackTraceToString()

fun Throwable.printStackTraceWithExtraMessage(msg: String) {
    Console.error(msg)
    Console.error(stackTraceToString())
}

fun printStackTrace(msg: String = "printStackTrace") {
    Console.error(currentStackTrace(msg))
}
