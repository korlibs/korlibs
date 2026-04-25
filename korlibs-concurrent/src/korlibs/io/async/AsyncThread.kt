package korlibs.io.async

import kotlinx.coroutines.*
import kotlin.coroutines.*

class AsyncThread() : AsyncInvokable {
    private var lastPromise: Deferred<*>? = null

    suspend fun await() {
        while (true) {
            val cpromise = lastPromise
            lastPromise?.await()
            if (cpromise == lastPromise) break
        }
    }

    fun cancel(): AsyncThread {
        lastPromise?.cancel()
        lastPromise = CompletableDeferred(Unit)
        return this
    }

    suspend fun <T> cancelAndQueue(func: suspend () -> T): T {
        cancel()
        return queue(func)
    }

    suspend fun <T> queue(func: suspend () -> T): T = invoke(func)

    override suspend operator fun <T> invoke(func: suspend () -> T): T {
        val task = sync(coroutineContext, func)
        return task.await()
    }

    suspend fun <T> sync(func: suspend () -> T): Deferred<T> = sync(coroutineContext, func)

    fun <T> sync(context: CoroutineContext, func: suspend () -> T): Deferred<T> {
        val oldPromise = lastPromise
        val promise = CoroutineScope(context).async {
            oldPromise?.await()
            func()
        }
        lastPromise = promise
        return promise
    }
}

///**
// * Creates a queue of processes that will be executed one after another by effectively preventing from executing
// * them at the same time.
// * This class is thread-safe.
// */
//class AsyncThread2 : AsyncInvokable {
//	private val lock = NonRecursiveLock()
//	private var lastPromise: Deferred<*> = CompletableDeferred(Unit)
//
//	suspend fun await() {
//		while (true) {
//			val cpromise = lock { lastPromise }
//			cpromise.await()
//			if (lock { cpromise == lastPromise }) break
//		}
//	}
//
//	fun cancel() = apply {
//		lock { lastPromise }.cancel()
//		lock { lastPromise = CompletableDeferred(Unit) }
//	}
//
//	override suspend operator fun <T> invoke(func: suspend () -> T): T {
//		val task = invoke(coroutineContext, func)
//        return task.await()
//	}
//
//	private operator fun <T> invoke(context: CoroutineContext, func: suspend () -> T): Deferred<T> = lock {
//		val oldPromise = lastPromise
//		CoroutineScope(context).async {
//			oldPromise.await()
//			func()
//		}.also { lastPromise = it }
//	}
//}

///**
// * Prevents a named invoke to happen at the same time (by effectively enqueuing by name).
// * This class is thread-safe.
// */
//class NamedAsyncThreads(val threadFactory: () -> AsyncInvokable = { AsyncThread2() }) {
//	class AsyncJob(val thread: AsyncInvokable) {
//		var count = 0
//	}
//	private val lock = NonRecursiveLock()
//	private val jobs = LinkedHashMap<String, AsyncJob>()
//
//	internal fun threadsCount() = jobs.size
//
//	suspend operator fun <T> invoke(name: String, func: suspend () -> T): T {
//		val job = lock {
//			jobs.getOrPut(name) { AsyncJob(threadFactory()) }.also { it.count++ }
//		}
//		try {
//			return job.thread.invoke(func)
//		} finally {
//			// Synchronization to prevent another thread from being added in the mean time, or a process queued.
//			lock {
//				job.count--
//				if (job.count == 0) {
//					jobs.remove(name)
//				}
//			}
//		}
//	}
//}
