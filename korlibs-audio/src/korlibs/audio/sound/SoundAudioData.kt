package korlibs.audio.sound

import kotlinx.coroutines.*
import kotlin.coroutines.*
import kotlin.time.*

@OptIn(ExperimentalCoroutinesApi::class)
class SoundAudioData(
    coroutineContext: CoroutineContext,
    val audioData: AudioData,
    var soundProvider: NativeSoundProvider,
    val closeStream: Boolean = false,
    override val name: String = "Unknown",
    val onComplete: (suspend () -> Unit)? = null
) : Sound(coroutineContext) {
    override suspend fun decode(maxSamples: Int): AudioData = audioData

    override fun play(coroutineContext: CoroutineContext, params: PlaybackParameters): SoundChannel {
        var pos = 0
        var paused = false
        var times = params.times
        var nas: NewPlatformAudioOutput? = null
        nas = soundProvider.createNewPlatformAudioOutput(coroutineContext, audioData.channels, audioData.rate) { it ->
            if (paused) {
                // @TODO: paused should not even call this right?
                for (ch in 0 until it.channels) {
                    audioData[ch].fill(0)
                }
                return@createNewPlatformAudioOutput
            }
            loop@for (ch in 0 until it.channels) {
                val audioDataCh = audioData[ch]
                for (n in 0 until it.totalSamples) {
                    val audioDataPos = pos + n
                    val sample = if (audioDataPos < audioDataCh.size) audioDataCh[audioDataPos] else 0
                    it[ch, n] = sample
                }
            }
            pos += it.totalSamples
            if (pos >= audioData.totalSamples) {
                pos = 0
                times = times.oneLess

                if (times == PlaybackTimes.ZERO) {
                    nas?.stop()
                }
            }
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
            override val state: SoundChannelState
                get() = when {
                  paused -> SoundChannelState.PAUSED
                  else -> super.state
                }

            override fun pause() {
                nas.paused = true
            }

            override fun resume() {
                nas.paused = false
            }

            override fun stop() {
                nas.stop()
            }
        }
    }
}