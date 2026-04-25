package korlibs.io.lang

/** Exception representing an I/O error, that is a typealias in the case of the JVM */
expect open class IOException(msg: String) : Exception
/** Exception representing an EOF I/O error, that is a typealias in the case of the JVM */
expect open class EOFException(msg: String) : IOException
/** Exception representing a File Not Found I/O error, that is a typealias in the case of the JVM */
expect open class FileNotFoundException(msg: String) : IOException

/** Exception representing Malformed Input */
open class MalformedInputException(msg: String) : Exception(msg) {
    constructor(index: Int, msg: String = "") : this("At index=$index $msg".trim())
}

/** Exception representing that a File Already Exists */
class FileAlreadyExistsException(msg: String) : IOException(msg)

@OptIn(ExperimentalStdlibApi::class)
/** Exception representing an Internal error */
class InternalException(val code: Int) : Exception("Internal Exception with code $code (0x${code.toHexString()})")
/** Exception representing an Invalid Operation */
class InvalidOperationException(str: String = "Invalid Operation") : Exception(str)
/** Exception representing an Out of Bounds error */
class OutOfBoundsException(index: Int = -1, str: String = "Out Of Bounds") : Exception(str)
/** Exception representing a Key Not Found error */
class KeyNotFoundException(str: String = "Key Not Found") : Exception(str)
/** Exception representing a Value Not Found error */
class NotImplementedException(str: String = "Not Implemented") : Exception(str)
/** Typealias of IllegalArgumentException */
typealias InvalidArgumentException = IllegalArgumentException
/** Exception representing an Unreachable error */
class UnreachableException(str: String = "Unreachable") : Exception(str)
/** Exception representing a Reserved error */
class ReservedException(str: String = "Reserved") : Exception(str)
/** Exception representing a Must Validate Code error */
class MustValidateCodeException(str: String = "Must Validate Code") : Exception(str)
/** Exception representing a Must Override error */
class MustOverrideException(str: String = "Must Override") : Exception(str)
/** Exception representing a Must Implement error */
class DeprecatedException(str: String = "Deprecated") : Exception(str)
/** Exception representing a Unexpected error */
class UnexpectedException(str: String = "Unexpected") : Exception(str)
/** Exception representing a Cancel error */
class CancelException(str: String = "Cancel") : Exception(str)

/** Shortcut for throwing a [MustValidateCodeException] */
val deprecated: Nothing get() = throw MustValidateCodeException()
/** Shortcut for throwing a [MustValidateCodeException] */
val mustValidate: Nothing get() = throw NotImplementedException()
/** Shortcut for throwing a [InvalidOperationException] */
val noImpl: Nothing get() = throw NotImplementedException()
/** Shortcut for throwing a [InvalidOperationException] */
val invalidOp: Nothing get() = throw InvalidOperationException()
/** Shortcut for throwing a [InvalidArgumentException] */
val invalidArg: Nothing get() = throw InvalidArgumentException()
/** Shortcut for throwing a [UnreachableException] */
val unreachable: Nothing get() = throw UnreachableException()
/** Shortcut for throwing a [ReservedException] */
val reserved: Nothing get() = throw ReservedException()

/** Shortcut for throwing a [InternalException] with a provided [code] */
fun internalException(code: Int): Nothing = throw InternalException(code)
/** Shortcut for throwing a [DeprecatedException] with a provided [msg] */
fun deprecated(msg: String): Nothing = throw DeprecatedException(msg)
/** Shortcut for throwing a [MustValidateCodeException] with a provided [msg] */
fun mustValidate(msg: String): Nothing = throw MustValidateCodeException(msg)
/** Shortcut for throwing a [NotImplementedException] with a provided [msg] */
fun noImpl(msg: String): Nothing = throw NotImplementedException(msg)
/** Shortcut for throwing a [InvalidOperationException] with a provided [msg] */
fun invalidOp(msg: String): Nothing = throw InvalidOperationException(msg)
/** Shortcut for throwing a [InvalidArgumentException] with a provided [msg] */
fun invalidArg(msg: String): Nothing = throw InvalidArgumentException(msg)
/** Shortcut for throwing a [UnreachableException] with a provided [msg] */
fun unreachable(msg: String): Nothing = throw UnreachableException(msg)
/** Shortcut for throwing a [ReservedException] with a provided [msg] */
fun reserved(msg: String): Nothing = throw ReservedException(msg)
/** Shortcut for throwing a [CancelException] with a provided [msg] */
fun unsupported(msg: String = "unsupported"): Nothing = throw UnsupportedOperationException(msg)
/** Shortcut for throwing a [InvalidArgumentException] with a provided [msg] */
fun invalidArgument(msg: String): Nothing = throw InvalidArgumentException(msg)
/** Shortcut for throwing a [UnexpectedException] with a provided [msg] */
fun unexpected(msg: String): Nothing = throw UnexpectedException(msg)
/** Shortcut for throwing a [MalformedInputException] with a provided [msg] */
fun malformedInput(msg: String): Nothing = throw MalformedInputException(msg)

/**
 * Similar to [runCatching] but will return null if an exception is thrown.
 * There is an optional [show] parameter to also print a stacktrace in the case there is an exception, while still returning null.
 */
inline fun <R> runIgnoringExceptions(show: Boolean = false, action: () -> R): R? = try {
	action()
} catch (e: Throwable) {
	if (show) e.printStackTrace()
	null
}

/** Triggers an enter debugger. In JS it is debugger; in other places serves as a placeholder to put a breakpoint. */
expect fun enterDebugger(): Unit
