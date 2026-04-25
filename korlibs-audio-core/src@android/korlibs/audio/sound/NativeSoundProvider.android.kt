package korlibs.audio.sound

import android.content.*
import android.media.*
import android.os.*
import android.util.*
import korlibs.datastructure.pauseable.*
import korlibs.io.android.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

actual val nativeSoundProvider: NativeSoundProvider = AndroidNativeSoundProvider

object AndroidNativeSoundProvider : NativeSoundProvider() {
    override val target: String = "android"

    private val pauseable = SyncPauseable()
    override var paused: Boolean by pauseable::paused
    val UNSET_AUDIO_SESSION_ID = -2
    val INVALID_AUDIO_SESSION_ID = -1

    var audioSessionId: Int = UNSET_AUDIO_SESSION_ID
        private set

    fun ensureAudioManager(coroutineContext: CoroutineContext) {
        if (audioSessionId != UNSET_AUDIO_SESSION_ID) return
        ensureAudioManager(coroutineContext[AndroidCoroutineContext.Key]?.context
            ?: return run {
                if (audioSessionId == UNSET_AUDIO_SESSION_ID) {
                    Log.e("AndroidNSoundProvider", "Can't find the Android Context on the CoroutineContext. Must call withAndroidContext first")
                }
            })
    }

    fun ensureAudioManager(ctx: Context) {
        if (audioSessionId != UNSET_AUDIO_SESSION_ID) return

        val audioManager = ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioSessionId = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> audioManager.generateAudioSessionId()
            else -> INVALID_AUDIO_SESSION_ID
        }
    }

    @ExperimentalStdlibApi
    override fun createNewPlatformAudioOutput(channels: Int, frequency: Int, gen: AudioPlatformOutputGen): AudioPlatformOutput {
        //ensureAudioManager(coroutineContext)

        val atBufferSamples = AudioPlatformOutput.DEFAULT_BLOCK_SIZE
        val atBufferSizeInBytes = Short.SIZE_BYTES * channels * atBufferSamples
        val atChannel = if (channels >= 2) AudioFormat.CHANNEL_OUT_STEREO else AudioFormat.CHANNEL_OUT_MONO
        val atMode = AudioTrack.MODE_STREAM
        val at = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && audioSessionId >= 0) {
            AudioTrack(
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).build(),
                AudioFormat.Builder().setChannelMask(atChannel).setSampleRate(frequency).setEncoding(AudioFormat.ENCODING_PCM_16BIT).build(),
                atBufferSizeInBytes, atMode, audioSessionId
            )
        } else {
            @Suppress("DEPRECATION")
            AudioTrack(AudioManager.STREAM_MUSIC, frequency, atChannel, AudioFormat.ENCODING_PCM_16BIT, atBufferSizeInBytes, atMode)
        }
        if (at.state == AudioTrack.STATE_UNINITIALIZED) {
            System.err.println("Audio track was not initialized correctly frequency=$frequency, bufferSamples=$atBufferSamples. Might need to call `AndroidNativeSoundProvider.ensureAudioManager(context)` method?")
        }

        //@TODO: event-based audio would work better
        //at.registerStreamEventCallback()

        return AudioPlatformOutput.simple(this, channels, frequency, gen) {
            AudioPlatformOutputSimple(
                init = { at.play() },
                paused = { if (it) at.pause() else at.play() },
                output = { buffer ->
                    when (at.state) {
                        AudioTrack.STATE_UNINITIALIZED -> {
                            delay(20L)
                        }
                        AudioTrack.STATE_INITIALIZED -> {
                            at.playbackRate = frequency
                            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) at.playbackParams.speed = this.pitch.toFloat()
                            at.write(buffer.data.asShortArray(), 0, buffer.data.size)
                            delay(1L)
                        }
                    }
                },
                close = {
                    at.flush()
                    at.stop()
                    at.release()
                },
            )
        }
    }
}
