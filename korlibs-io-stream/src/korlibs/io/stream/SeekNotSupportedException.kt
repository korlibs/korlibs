package korlibs.io.stream

open class SeekNotSupportedException(message: String = "Seeking not supported!") : UnsupportedOperationException(message)