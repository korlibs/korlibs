package korlibs.concurrent.lock

import korlibs.time.*
import kotlinx.atomicfu.*
import kotlinx.cinterop.*
import platform.posix.*
import kotlin.time.*

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
actual class Lock actual constructor() : BaseLockWithNotifyAndWait {
    actual companion object {}

    private val nrlock = NonRecursiveLock()
    private val nlocks = atomic(0)
    private var arena: Arena? = null
    private var mutex: pthread_mutex_t? = null
    private var cond: pthread_cond_t? = null

    actual override fun lock() {
        val mut = nrlock {
            //val mut = run {
            if (arena == null) {
                arena = Arena()
            }
            if (cond == null) {
                cond = arena!!.alloc<pthread_cond_t>().also {
                    pthread_cond_init(it.ptr, null)
                    //pthread_cond_destroy(cond.ptr)
                }
            }
            if (mutex == null) {
                mutex = arena!!.alloc<pthread_mutex_t>().also {
                    pthread_mutex_init(it.ptr, null)
                    //pthread_mutex_destroy(mutex.ptr)
                }
            }
            nlocks.incrementAndGet()
            mutex!!
        }
        pthread_mutex_lock(mut.ptr)
    }

    actual override fun unlock() {
        nrlock {
            //run {
            pthread_mutex_unlock(mutex!!.ptr)

            if (nlocks.decrementAndGet() == 0) {
                if (cond != null) pthread_cond_destroy(cond?.ptr)
                if (mutex != null) pthread_mutex_destroy(mutex?.ptr)
                arena?.clear()
                cond = null
                mutex = null
                arena = null
            }
        }
    }

    actual override fun notify(unit: Unit) {
        //println("SIGNALING...")
        pthread_cond_signal(cond!!.ptr)
        //println(" --> SIGNALED")
        //pthread_cond_broadcast() // notifyall
    }

    fun timespec.toDuration(): Duration = (tv_sec.toLong().seconds + tv_nsec.toLong().nanoseconds)
    fun Duration.toTimespec(out: timespec): timespec {
        val nanos = inWholeNanoseconds
        val s: Long = (nanos / 1_000_000_000)
        val n: Long = (nanos % 1_000_000_000)
        out.tv_sec = s.convert()
        out.tv_nsec = n.convert()
        return out
    }

    actual override fun wait(time: FastDuration): Boolean {
        val time = time.slow

        return memScoped {
            var tspec: timespec = alloc<timespec>()
            clock_gettime(CLOCK_REALTIME.convert(), tspec.ptr)
            val clockTime = tspec.toDuration()
            //val waitTime = (clockTime + time - 10.milliseconds)
            val waitTime = (clockTime + time)
            tspec = waitTime.toTimespec(tspec)
            //println("WAITING... $time")
            //val startTime = CoreTime.currentTimeMillisDouble()
            (pthread_cond_timedwait(cond!!.ptr, mutex!!.ptr, tspec.ptr)).also {
                //val elapsedTime = (CoreTime.currentTimeMillisDouble() - startTime).milliseconds
                //println(" --> WAITED it=$it : timedout=${it == ETIMEDOUT}, elapsedTime=$elapsedTime, clockTime=$clockTime, waitTime=$waitTime -- ${waitTime - clockTime}, requested=${time}")
            } == ETIMEDOUT
        }
    }
}
