package korlibs.io.async

import kotlin.contracts.*

interface AsyncCloseable {
	suspend fun close()

	companion object {
		val DUMMY = object : AsyncCloseable {
			override suspend fun close() = Unit
		}
	}
}

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

@OptIn(ExperimentalContracts::class)
@Deprecated("", ReplaceWith("use(block)"))
suspend inline fun <T : AsyncCloseable?, TR> T.useIt(block: (T) -> TR): TR {
	contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
	return use(block)
}

@OptIn(ExperimentalContracts::class)
suspend inline fun <T : AsyncCloseable?, TR> T.useThis(block: T.() -> TR): TR {
	contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
	return use(block)
}
