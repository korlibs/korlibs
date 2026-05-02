package korlibs.io.async

import kotlin.contracts.*

/** An interface that allows to close resources asynchronously. */
interface AsyncCloseable {
	/** Closes this resource. */
	suspend fun close()

	companion object {
		/** A dummy [AsyncCloseable] that does nothing. */
		val DUMMY = object : AsyncCloseable {
			override suspend fun close() = Unit
		}
	}
}

/** An base [AsyncCloseable] that provides a default implementation for the close, so it is not mandatory overriding it. */
interface OptionalAsyncCloseable : AsyncCloseable {
	override suspend fun close(): Unit = Unit
}

// @TODO: Bug in Kotlin.JS related to inline
// https://youtrack.jetbrains.com/issue/KT-29120
//inline suspend fun <T : AsyncCloseable, R> T.use(callback: T.() -> R): R { // FAILS
//	try {
//		return callback()
//	} finally {
//		close()
//	}
//}

/**
 * Executes the [block] with the [AsyncCloseable] as parameter, and closes the resource once done.
 */
@OptIn(ExperimentalContracts::class)
suspend inline fun <T : AsyncCloseable?, TR> T.use(block: (T) -> TR): TR {
	contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
	var error: Throwable? = null
	val result = try {
		block(this)
	} catch (e: Throwable) {
		error = e
		null
	}
	this?.close()
	if (error != null) throw error
	return result as TR
}

/**
 * Executes the [block] with the [AsyncCloseable] as parameter, and closes the resource once done.
 */
@OptIn(ExperimentalContracts::class)
@Deprecated("", ReplaceWith("use(block)"))
suspend inline fun <T : AsyncCloseable?, TR> T.useIt(block: (T) -> TR): TR {
	contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
	return use(block)
}

/**
 * Executes the [block] with the [AsyncCloseable] as receiver, and closes the resource once done.
 */
@OptIn(ExperimentalContracts::class)
suspend inline fun <T : AsyncCloseable?, TR> T.useThis(block: T.() -> TR): TR {
	contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
	return use(block)
}
