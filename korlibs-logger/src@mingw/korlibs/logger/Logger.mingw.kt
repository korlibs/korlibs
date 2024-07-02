package korlibs.logger

import kotlinx.cinterop.*
import platform.windows.*

actual object Console : BaseConsole()

actual object DefaultLogOutput : Logger.Output {
    actual override fun output(logger: Logger, level: Logger.Level, msg: Any?) = Logger.ConsoleLogOutput.output(logger, level, msg)
}
