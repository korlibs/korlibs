package korlibs.concurrent.thread

import korlibs.concurrent.lock.*
import korlibs.time.*
import korlibs.time.core.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

@OptIn(InternalCoroutinesApi::class)
class FixedPoolNativeThreadDispatcher(
    val numThreads: Int,
    val name: String = "FixedPoolNativeThreadDispatcher",
    val priority: NativeThreadPriority = NativeThreadPriority.NORMAL,
    val isDaemon: Boolean = true,
) : CoroutineDispatcher(), AutoCloseable, Delay {
    init { check(numThreads >= 1) { "numThreads should be >= 1" } }
    val dispatchers = Array(numThreads) { NativeThreadDispatcher("$name-$it", priority, isDaemon) }
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
) : CoroutineDispatcher(), AutoCloseable, Delay {
    var running = true
    private val notifyLock = Lock()
    class TimedTask(val time: Long, val task: CancellableContinuation<Unit>) : Comparable<TimedTask> {
        override fun compareTo(other: TimedTask): Int = this.time.compareTo(time)
    }

    val timedTasksLock = Lock()
    val timedTasks = ArrayDeque<TimedTask>()
    val tasksLock = Lock()
    val tasks = ArrayDeque<Runnable>()
    val thread = NativeThread.start(name = name, priority = priority, isDaemon = isDaemon) {
        while (running) {
            if (tasksLock { tasks.isEmpty() }) {
                try {
                    notifyLock {
                        val firstTask = timedTasksLock { timedTasks.firstOrNull() }
                        val time = if (firstTask != null) (firstTask.time - now()).fastMilliseconds else 10000.fastMilliseconds
                        //if (firstTask == null) println("WAITING 10s")
                        notifyLock.wait(time)
                    }
                } catch (e: Throwable) {

                }
            }
            if (!running) break
            try {
                val task = tasksLock { tasks.removeFirstOrNull() }
                println("${NativeThread.current}: $task")
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
        running = false
        notifyLock { notifyLock.notify() }
        thread.interrupt()
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        tasksLock { tasks.add(block) }
        notifyLock { notifyLock.notify() }
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        val task = TimedTask(now() + timeMillis, continuation)
        timedTasksLock {
            val firstTask = timedTasks.firstOrNull()
            if (firstTask == null || task.time < firstTask.time) {
                notifyLock {
                    timedTasks.addFirst(task)
                    //println("ADDED TIMED TASK AND NOTIFY")
                    notifyLock.notify()
                }
            } else {
                notifyLock {
                    timedTasks.addLast(task)
                    timedTasks.sort()
                    notifyLock.notify()
                }
            }
        }
    }

    @OptIn(CoreTimeInternalApi::class)
    private fun now(): Long = CoreTime.currentTimeMillis()
}
