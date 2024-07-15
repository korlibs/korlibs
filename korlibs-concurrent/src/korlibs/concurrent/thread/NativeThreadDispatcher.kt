package korlibs.concurrent.thread

import korlibs.concurrent.lock.*
import korlibs.time.*
import korlibs.time.core.*
import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.time.*

@OptIn(InternalCoroutinesApi::class)
class FixedPoolNativeThreadDispatcher(
    val numThreads: Int,
    val name: String = "FixedPoolNativeThreadDispatcher",
    val priority: NativeThreadPriority = NativeThreadPriority.NORMAL,
    val isDaemon: Boolean = true,
    val preciseTimings: Boolean = false,
) : CoroutineDispatcher(), AutoCloseable, Delay {
    init { check(numThreads >= 1) { "numThreads should be >= 1" } }
    val dispatchers = Array(numThreads) { NativeThreadDispatcher("$name-$it", priority, isDaemon, preciseTimings) }
    val threadIds = HashSet<Long>(dispatchers.map { it.thread.id })

    override fun close() {
        for (dispatcher in dispatchers) dispatcher.close()
    }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean = NativeThread.current.id !in threadIds

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatchers.minBy { it.numTasks }.dispatch(context, block)
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        dispatchers.minBy { it.numTimedTasks }.scheduleResumeAfterDelay(timeMillis, continuation)
    }
}

@OptIn(InternalCoroutinesApi::class, ExperimentalCoroutinesApi::class)
class NativeThreadDispatcher(
    val name: String = "NativeThreadDispatcher",
    val priority: NativeThreadPriority = NativeThreadPriority.NORMAL,
    val isDaemon: Boolean = true,
    val preciseTimings: Boolean = false,
) : CoroutineDispatcher(), AutoCloseable, Delay {
    var running = true
    private val notifyLock = Lock()
    class TimedTask(val time: FastDuration, val task: CancellableContinuation<Unit>) : Comparable<TimedTask> {
        override fun compareTo(other: TimedTask): Int = this.time.compareTo(time)
    }

    val timedTasksLock = Lock()
    val timedTasks = ArrayDeque<TimedTask>()
    val tasksLock = Lock()
    val tasks = ArrayDeque<Runnable>()
    val thread = NativeThread.start(name = name, priority = priority, isDaemon = isDaemon) {
        while (running) {
            try {
                notifyLock {
                    if (tasks.isEmpty()) {
                        val firstTask = timedTasksLock { timedTasks.firstOrNull() }
                        val time = if (firstTask != null) (firstTask.time - now()) else 10_000.fastMilliseconds
                        //if (firstTask == null) println("WAITING 10s")
                        //if (time > 10.fastMilliseconds) println("!!!!!!!!!!!! TIME=$time")
                        notifyLock.wait(time, precise = preciseTimings)
                    }
                }
            } catch (e: Throwable) {

            }
            if (!running) break
            try {
                val task = tasksLock { tasks.removeFirstOrNull() }
                //println("${NativeThread.current}: $task")
                task?.run()
                run {
                    while (true) {
                        val task = timedTasksLock {
                            val tryTask = timedTasks.firstOrNull()
                            if (tryTask != null && now() >= tryTask.time) timedTasks.removeFirst() else null
                        } ?: break
                        task.task.resume(Unit)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    val numTasks: Int get() = tasksLock { tasks.size }
    val numTimedTasks: Int get() = timedTasksLock { timedTasks.size }

    override fun isDispatchNeeded(context: CoroutineContext): Boolean {
        return NativeThread.current.id == thread.id
    }

    override fun close() {
        notifyLock {
            running = false
            notifyLock.notify()
        }
        //thread.interrupt()
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        notifyLock {
            tasksLock { tasks.add(block) }
            notifyLock.notify()
        }
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        val task = TimedTask(now() + timeMillis.fastMilliseconds, continuation)

        notifyLock {
            timedTasksLock {
                val firstTask = timedTasksLock { timedTasks.firstOrNull() }
                if (firstTask == null || task.time < firstTask.time) {
                    timedTasks.addFirst(task)
                } else {
                    timedTasks.addLast(task)
                    timedTasks.sort()
                }
            }
            notifyLock.notify()
        }
    }

    private val start = TimeSource.Monotonic.markNow()

    @OptIn(CoreTimeInternalApi::class)
    private fun now(): FastDuration = start.elapsedNow().fast
}
