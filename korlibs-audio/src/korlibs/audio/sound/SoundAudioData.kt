package korlibs.audio.sound

import korlibs.io.async.*
import kotlin.coroutines.*
import kotlin.time.*

class SoundAudioData(
    coroutineContext: CoroutineContext,
    val audioData: AudioData,
    var soundProvider: NativeSoundProvider,
    override val name: String = audioData.name ?: "Unknown",
) : Sound(coroutineContext) {
    override suspend fun decode(maxSamples: Int): AudioData = audioData

    @OptIn(ExperimentalStdlibApi::class)
    override fun play(coroutineContext: CoroutineContext, params: PlaybackParameters): SoundChannel {
        var pos = 0
        var times = params.times
        lateinit var nas: AudioPlatformOutput
        nas = soundProvider.createNewPlatformAudioOutput(audioData.channels, audioData.rate) { buffer ->
            if (times == PlaybackTimes.ZERO) return@createNewPlatformAudioOutput

            for (n in 0 until buffer.totalSamples) {
                var audioDataPos = pos++
                if (audioDataPos >= audioData.totalSamples || times == PlaybackTimes.ZERO) {
                    pos = 0
                    audioDataPos = 0
                    times = times.oneLess
                    if (times == PlaybackTimes.ZERO) {
                        nas.stop()
                        break
                    }
                }
                for (ch in 0 until buffer.channels) {
                    val sample = when {
                        //completed -> AudioSample.ZERO
                        audioDataPos < audioData.totalSamples -> audioData[ch, audioDataPos]
                        else -> AudioSample.ZERO
                    }
                    buffer[ch, n] = sample
                }
            }
        }
        nas.copySoundPropsFromCombined(params, this)
        nas.start()
        coroutineContext.onCancel({ nas.running }) { nas.stop() }
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
            override val state: SoundChannelState
                get() = when {
                    !nas.running -> SoundChannelState.STOPPED
                    nas.paused -> SoundChannelState.PAUSED
                    else -> SoundChannelState.PLAYING
                }

            override fun pause() = run { nas.paused = true }
            override fun resume() = run { nas.paused = false }
            override fun stop() = run { nas.stop() }
        }
    }
}