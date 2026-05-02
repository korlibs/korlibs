package korlibs.time

import kotlin.time.*

class Stopwatch(val nanosecondProvider: () -> Double = { PerformanceCounter.nanoseconds }) {
    constructor(timeProvider: TimeProvider) : this({ timeProvider.now().unixMillis.fastMilliseconds.nanoseconds })
    var running: Boolean = false
        private set
    private var startNano = 0.0
    private val currentNano get() = nanosecondProvider()
    private fun setStart() { startNano = currentNano }

    /**
     * Restarts the watch, making [elapsed] start from zero
     */
    fun start(): Stopwatch {
        setStart()
        running = true
        return this
    }

    /**
     * Restarts the watch, making [elapsed] start from zero
     */
    fun restart(): Stopwatch = start()

    /**
     * Stops the watch, keeping [elapsed] time until [restart] or [resume] is called
     */
    fun stop(): Stopwatch {
        startNano = elapsedNanoseconds
        running = false
        return this
    }

    /**
     * When stopped, resumes the stopwatch keeping accumulated [elapsed] time
     */
    fun resume(): Stopwatch {
        if (running) return this
        startNano = currentNano - startNano
        running = true
        return this
    }

    /**
     * Pauses the stopwatch, so it can be resumed later
     */
    fun pause(): Stopwatch {
        return stop()
    }

    var elapsedNanoseconds: Double
        get() = if (running) currentNano - startNano else startNano
        set(value) {
            startNano = when {
                running -> currentNano - value
                else -> value
            }
        }
    var elapsedMicroseconds: Double
        get() = elapsedNanoseconds * 1000
        set(value) { elapsedNanoseconds = value * 1000 }

    var elapsed: Duration get() = elapsedNanoseconds.nanoseconds
        set(value) {
            elapsedNanoseconds = value.nanoseconds
        }
    fun getElapsedAndRestart(): Duration = elapsed.also { restart() }

    var fastElapsed: FastDuration
        get() = elapsedNanoseconds.fastNanoseconds
        set(value) { elapsedNanoseconds = value.nanoseconds }
    fun getFastElapsedAndRestart(): FastDuration = fastElapsed.also { restart() }
}
