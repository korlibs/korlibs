package korlibs.io.async

import kotlinx.coroutines.*
import kotlin.coroutines.*

//class AsyncQueue(val context: CoroutineContext) {
class AsyncQueue {
	//constructor() : AsyncQueue(CoroutineContext())

	val thread = AsyncThread()
    //val thread = AsyncThread2()

	//companion object {
	//	suspend operator fun invoke() = AsyncQueue(getCoroutineContext())
	//}

	suspend operator fun invoke(func: suspend () -> Unit): AsyncQueue = invoke(coroutineContext, func)

	operator fun invoke(context: CoroutineContext, func: suspend () -> Unit): AsyncQueue {
		thread.sync(context) {
            //try {
                func()
            //} catch (e: Throwable) {
            //    Console.error("AsyncQueue.invoke.catch")
            //    e.printStackTrace()
            //}
        }
		return this
	}

	suspend fun await(func: suspend () -> Unit) {
		invoke(func)
		await()
	}

	suspend fun await() {
		thread.await()
	}
}

fun AsyncQueue.withContext(ctx: CoroutineContext) = AsyncQueueWithContext(this, ctx)
suspend fun AsyncQueue.withContext() = AsyncQueueWithContext(this, coroutineContext)

class AsyncQueueWithContext(val queue: AsyncQueue, val context: CoroutineContext) {
	operator fun invoke(func: suspend () -> Unit): AsyncQueue = queue.invoke(context, func)
	suspend fun await(func: suspend () -> Unit) = queue.await(func)
	suspend fun await() = queue.await()
}
