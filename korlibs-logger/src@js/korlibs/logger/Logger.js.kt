package korlibs.logger

actual object Console : BaseConsole() {
    override fun logInternal(kind: Kind, vararg msg: Any?) {
        when (kind) {
            Kind.ERROR -> console.error(*msg)
            Kind.WARN -> console.warn(*msg)
            Kind.INFO -> console.info(*msg)
            Kind.DEBUG -> console.log(*msg)
            Kind.TRACE -> console.log(*msg)
            Kind.LOG -> console.log(*msg)
        }
    }
}

actual object DefaultLogOutput : Logger.Output {
    actual override fun output(logger: Logger, level: Logger.Level, msg: Any?) = Logger.ConsoleLogOutput.output(logger, level, msg)
}
