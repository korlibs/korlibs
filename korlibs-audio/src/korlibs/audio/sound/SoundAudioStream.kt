package korlibs.audio.sound

import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.time.*

class SoundAudioData(
    coroutineContext: CoroutineContext,
    val audioData: AudioData,
    var soundProvider: NativeSoundProvider,
    override val name: String = "Unknown",
    val onComplete: (() -> Unit)? = null
) : Sound(coroutineContext) {
    override suspend fun decode(maxSamples: Int): AudioData = audioData

    override fun play(coroutineContext: CoroutineContext, params: PlaybackParameters): SoundChannel {
        var pos = 0
        var times = params.times
        val nas = soundProvider.createPlatformAudioOutput(coroutineContext, audioData.channels, audioData.frequency) { it ->
            if (paused) {
                // @TODO: paused should not even call this right?
                for (ch in 0 until it.channels) {
                    audioData[ch].fill(0)
                }
                return@createPlatformAudioOutput 0
            }
            val available = audioData[0].size - pos
            loop@for (ch in 0 until it.channels) {
                val audioDataCh = audioData[ch]
                for (n in 0 until it.totalSamples) {
                    val audioDataPos = pos + n
                    val sample = if (audioDataPos < audioDataCh.size) audioDataCh[audioDataPos] else 0
                    it[ch, n] = sample
                }
            }
            val readCount = minOf(available, it.totalSamples)
            pos += it.totalSamples
            //println("GEN: $it, it.totalSamples=${it.totalSamples}, pos=$pos, times=$times, paused=$paused")

            if (pos >= audioData.totalSamples) {
                times = times.oneLess

                if (times == PlaybackTimes.ZERO) {
                    stop()
                    pos = audioData.totalSamples
                    onComplete?.invoke()
                } else {
                    pos = 0
                }
            }
            readCount
            //println("GEN: $it, it.totalSamples=${it.totalSamples}, pos=$pos, times=$times, paused=$paused, audioData.totalTime=${audioData.totalTime}")
        }
        nas.copySoundPropsFromCombined(params, this)
        nas.start()
        return object : SoundChannel(this) {
            override var volume: Double by nas::volume
            override var pitch: Double by nas::pitch
            override var panning: Double by nas::panning
            override var current: Duration
                get() = audioData.timeAtSample(pos)
                set(value) {
                    pos = audioData.sampleAtTime(value)
                }
            override val total: Duration get() = audioData.totalTime
            override val state: SoundChannelState get() = when {
                nas.paused -> SoundChannelState.PAUSED
                else -> super.state
            }
            override fun pause() { nas.paused = true }
            override fun resume() { nas.paused = false }
            override fun stop() { nas.stop() }
        }
    }
}

class SoundAudioStream(
    coroutineContext: CoroutineContext,
    val stream: AudioStream,
    var soundProvider: NativeSoundProvider,
    val closeStream: Boolean = false,
    override val name: String = "Unknown",
    val onComplete: (suspend () -> Unit)? = null
) : Sound(coroutineContext) {
    override val length: Duration get() = stream.totalLength
    override suspend fun decode(maxSamples: Int): AudioData = stream.toData(maxSamples)
    override suspend fun toStream(): AudioStream = stream.clone()
    override val nchannels: Int get() = stream.channels

    override fun play(coroutineContext: CoroutineContext, params: PlaybackParameters): SoundChannel {
        val deque = AudioSamplesDeque(stream.channels)
        var currentPos = 0L

        val job = CoroutineScope(coroutineContext).launch {
            try {
                while (true) {
                    val temp = AudioSamples(stream.channels, 1024)
                    val read = stream.read(temp, 0, temp.totalSamples)
                    if (read <= 0) break
                    deque.write(temp, 0, read)
                    while (deque.availableRead >= 32 * 1024) delay(1L)
                }
            } finally {
                if (closeStream) {
                    stream.close()
                }
                onComplete?.invoke()
            }
        }

        val output = soundProvider.createPlatformAudioOutput(coroutineContext, stream.channels, stream.frequency) { samples ->
            if (paused) {
                0
            } else {
                deque.read(samples).also {
                    currentPos += samples.totalSamples
                }
            }
        }

        return object : SoundChannel(this), SoundProps by output {
            override var current: Duration = stream.estimateTimeFromSamples(currentPos)
            override val total: Duration = stream.totalLength
            override val state: SoundChannelState = SoundChannelState.PLAYING
            override fun pause() { output.paused = true }
            override fun resume() { output.paused = false }
            override fun stop() { output.stop(); job.cancel() }
        }
    }
}
