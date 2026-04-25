package korlibs.logger

import platform.Foundation.*

actual object Console : BaseConsole() {
    override fun logInternal(kind: Kind, vararg msg: Any?) {
        NSLog("%s", logToString(kind, *msg))
    }

    override fun logToString(kind: Kind, vararg msg: Any?): String {
        return msg.joinToString(", ")
    }
}

actual object DefaultLogOutput : Logger.Output {
    actual override fun output(logger: Logger, level: Logger.Level, msg: Any?) = Logger.ConsoleLogOutput.output(logger, level, msg)
}
