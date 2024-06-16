package korlibs.time

import korlibs.time.fast.*
import kotlin.time.*

class Stopwatch(val nanosecondProvider: () -> Double = { PerformanceCounter.nanoseconds }) {
    constructor(timeProvider: TimeProvider) : this({ timeProvider.now().unixMillis.fastMilliseconds.nanoseconds })
    private var running = false
    private var startNano = 0.0
    private val currentNano get() = nanosecondProvider()
    private fun setStart() { startNano = currentNano }
    init {
        setStart()
    }
    fun start(): Stopwatch {
        setStart()
        running = true
        return this
    }
    fun restart(): Stopwatch = start()
    fun stop(): Stopwatch {
        startNano = elapsedNanoseconds
        running = false
        return this
    }
    val elapsedNanoseconds: Double get() = if (running) currentNano - startNano else startNano
    val elapsedMicroseconds: Double get() = elapsedNanoseconds * 1000

    val elapsed: Duration get() = elapsedNanoseconds.nanoseconds
    fun getElapsedAndRestart(): Duration = elapsed.also { restart() }

    val fastElapsed: FastDuration get() = elapsedNanoseconds.fastNanoseconds
    fun getFastElapsedAndRestart(): FastDuration = fastElapsed.also { restart() }
}
