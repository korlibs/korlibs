package korlibs.audio.core.impl

import korlibs.audio.core.*
import korlibs.audio.core.node.*
import korlibs.io.concurrent.*
import kotlinx.coroutines.*

class SimpleAudioPlayer(override val device: AudioDevice, val onInit: () -> Unit = {}, val onClose: () -> Unit = {}, val gen: (SimpleAudioPlayer) -> SimpleAudioSource) : AudioPlayer() {
    companion object {
        fun gen(
            device: AudioDevice,
            onInit: () -> Unit = {},
            onClose: () -> Unit = {},
            gen: SimpleAudioSource.(AudioBuffer) -> SimpleAudioGen
        ): SimpleAudioPlayer = SimpleAudioPlayer(device, onInit, onClose, gen = { player ->
            object : SimpleAudioSource(player) {
                override suspend fun process(buffer: AudioBuffer): SimpleAudioGen = gen(buffer)
            }
        })
    }

    override fun createSource(): AudioSource = gen(this)

    init {
        onInit()
    }

    override fun close() {
        onClose()
    }
}

class SimpleAudioGen(
    val implementedVolume: Boolean = false,
    val implementedPitch: Boolean = false,
    val implemented3D: Boolean = false,
    val pauseResume: suspend (Boolean) -> Unit = {},
    val queue: suspend SimpleAudioSource.(AudioBuffer) -> Unit,
    val close: suspend SimpleAudioSource.(AudioBuffer) -> Unit,
)

abstract class SimpleAudioSource(override val player: AudioPlayer, val dispatchers: CoroutineDispatcher = THREADS) : AudioSource() {
    var job: Job? = null

    var paused: Boolean
        get() = state == AudioSourceState.PAUSED
        set(value) {
            state = if (value) AudioSourceState.PLAYING else AudioSourceState.PAUSED
        }
    override var state: AudioSourceState = AudioSourceState.INITIAL
        set(value) {
            val oldState = field
            field = value
            when (value) {
                AudioSourceState.INITIAL -> Unit
                AudioSourceState.PLAYING -> {
                    if (oldState != AudioSourceState.PAUSED) {
                        job?.cancel()
                        job = node?.let { playJob(it) }
                    }
                }
                AudioSourceState.PAUSED -> Unit
                AudioSourceState.STOPPED -> job?.cancel()
            }
        }

    private fun playJob(node: AudioNode): Job {
        return CoroutineScope(dispatchers).launch {
            val buffer = AudioBuffer(nchannels, 2048, this@SimpleAudioSource.rate)
            val gen = process(buffer)
            var reportedPaused = false
            try {
                while (state != AudioSourceState.STOPPED) {
                    val isPaused = state == AudioSourceState.PAUSED
                    if (isPaused) {
                        if (isPaused != reportedPaused) {
                            reportedPaused = isPaused
                            gen.pauseResume(isPaused)
                        }
                        delay(10L)
                        continue
                    }
                    node.currentSamples = currentSamples
                    val samples = node.processSize(buffer.nsamples)
                    if (samples == 0) {
                        state = AudioSourceState.STOPPED
                        continue
                    }

                    node.process(buffer)
                    currentSamples += samples

                    if (!gen.implementedVolume) {
                        GainAudioNode(this@SimpleAudioSource.volume).process(buffer)
                    }
                    gen.queue(this@SimpleAudioSource, buffer)
                }
            } finally {
                gen.close(this@SimpleAudioSource, buffer)
            }
        }
    }

    protected abstract suspend fun process(buffer: AudioBuffer): SimpleAudioGen

    companion object {
        val THREADS = Dispatchers.createFixedThreadDispatcher("AudioPlayer", 16)
    }
}
