package korlibs.audio.sound

import android.content.*
import android.media.*
import android.os.*
import android.util.*
import korlibs.datastructure.pauseable.*
import korlibs.io.android.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

actual val nativeSoundProvider: NativeSoundProvider by lazy { AndroidNativeSoundProvider() }

class AndroidNativeSoundProvider : NativeSoundProvider() {
    override val target: String = "android"

    private val pauseable = SyncPauseable()
    override var paused: Boolean by pauseable::paused
    val UNSET_AUDIO_SESSION_ID = -2
    val INVALID_AUDIO_SESSION_ID = -1

    var audioSessionId: Int = UNSET_AUDIO_SESSION_ID
        private set

    fun ensureAudioManager(coroutineContext: CoroutineContext) {
        if (audioSessionId != UNSET_AUDIO_SESSION_ID) return

        val ctx = coroutineContext[AndroidCoroutineContext.Key]?.context
            ?: return run {
                if (audioSessionId == UNSET_AUDIO_SESSION_ID) {
                    Log.e("AndroidNSoundProvider", "Can't find the Android Context on the CoroutineContext. Must call withAndroidContext first")
                }
            }
        val audioManager = ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioSessionId = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> audioManager.generateAudioSessionId()
            else -> INVALID_AUDIO_SESSION_ID
        }
    }
    override fun createNewPlatformAudioOutput(coroutineContext: CoroutineContext, channels: Int, frequency: Int, gen: AudioPlatformOutputGen): AudioPlatformOutput {
        ensureAudioManager(coroutineContext)
        return AudioPlatformOutput(coroutineContext, channels, frequency, gen) {
            //val bufferSamples = 4096
            val bufferSamples = 1024

            val atChannelSize = Short.SIZE_BYTES * channels * bufferSamples
            val atChannel = if (channels >= 2) AudioFormat.CHANNEL_OUT_STEREO else AudioFormat.CHANNEL_OUT_MONO
            val atMode = AudioTrack.MODE_STREAM
            val at = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && audioSessionId >= 0) {
                AudioTrack(
                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).build(),
                    AudioFormat.Builder().setChannelMask(atChannel).setSampleRate(frequency).setEncoding(AudioFormat.ENCODING_PCM_16BIT).build(),
                    atChannelSize, atMode, audioSessionId
                )
            } else {
                @Suppress("DEPRECATION")
                AudioTrack(AudioManager.STREAM_MUSIC, frequency, atChannel, AudioFormat.ENCODING_PCM_16BIT, atChannelSize, atMode)
            }
            if (at.state == AudioTrack.STATE_UNINITIALIZED) {
                System.err.println("Audio track was not initialized correctly frequency=$frequency, bufferSamples=$bufferSamples")
            }

            val buffer = AudioSamplesInterleaved(channels, bufferSamples)
            at.play()

            var lastVolL = Float.NaN
            var lastVolR = Float.NaN

            try {
                while (running) {
                    pauseable.checkPaused()

                    if (this.paused) {
                        at.pause()
                        delay(20L)
                        continue
                    } else {
                        at.play()
                    }

                    when (at.state) {
                        AudioTrack.STATE_UNINITIALIZED -> {
                            delay(20L)
                        }
                        AudioTrack.STATE_INITIALIZED -> {
                            at.playbackRate = frequency
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                at.playbackParams.speed = this.pitch.toFloat()
                            }
                            val volL = this.volumeForChannel(0).toFloat()
                            val volR = this.volumeForChannel(1).toFloat()
                            if (lastVolL != volL || lastVolR != volR) {
                                lastVolL = volL
                                lastVolR = volR
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    at.setVolume(volL)
                                } else {
                                    at.setStereoVolume(volL, volR)
                                }
                            }

                            genSafe(buffer)
                            at.write(buffer.data.asShortArray(), 0, buffer.data.size)
                            delay(1L)
                        }
                    }
                }
            } finally {
                at.flush()
                at.stop()
                at.release()
            }

            //val temp = AudioSamplesInterleaved(2, bufferSamples)
        }
    }
}
