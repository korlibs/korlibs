package korlibs.audio.core

import android.content.*
import android.media.*
import android.os.*
import korlibs.audio.core.impl.*
import korlibs.audio.core.impl.OpenALAudioSystem.OpenALAudioPlayer
import korlibs.audio.core.node.*
import korlibs.io.android.*
import kotlinx.coroutines.*
import kotlin.coroutines.*

actual val defaultAudioSystem: AudioSystem = AndroidAudioSystem

object AndroidAudioSystem : AudioSystem() {
    internal var audioSessionId: Int = -1 ; private set
    internal lateinit var audioManager: AudioManager

    override fun initialize(context: CoroutineContext) {
        if (::audioManager.isInitialized) return
        val ctx = context[AndroidCoroutineContext.Key]?.context ?: error("Can't find the Android Context on the CoroutineContext. Must call withAndroidContext first")
        audioManager = ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) audioSessionId = audioManager!!.generateAudioSessionId()
    }

    override fun createPlayer(device: AudioDevice): AudioPlayer = AndroidAudioPlayer(this, device)

    class AndroidAudioPlayer(val system: AndroidAudioSystem, override val device: AudioDevice) : AudioPlayer() {
        override fun createSource(): AudioSource {
            return object : SimpleAudioSource(this@AndroidAudioPlayer) {
                override suspend fun process(buffer: AudioBuffer): SimpleAudioGen {
                    val channels = buffer.nchannels
                    val nsamples = buffer.nsamples
                    val atChannelSize = Short.SIZE_BYTES * channels * nsamples
                    val atChannel = if (channels >= 2) AudioFormat.CHANNEL_OUT_STEREO else AudioFormat.CHANNEL_OUT_MONO
                    val atMode = AudioTrack.MODE_STREAM
                    val at = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && system.audioSessionId >= 0) {
                        AudioTrack(
                            //.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).build(),
                            AudioFormat.Builder().setChannelMask(atChannel).setSampleRate(rate).setEncoding(AudioFormat.ENCODING_PCM_16BIT).build(),
                            atChannelSize,
                            atMode,
                            system.audioSessionId
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        AudioTrack(AudioManager.STREAM_MUSIC, rate, atChannel, AudioFormat.ENCODING_PCM_16BIT, atChannelSize, atMode)
                    }
                    if (at.state == AudioTrack.STATE_UNINITIALIZED) {
                        System.err.println("Audio track was not initialized correctly frequency=$rate, channels=$channels, nsamples=$nsamples")
                    }
                    val interleaved = buffer.samples.interleaved()
                    at.play()

                    return SimpleAudioGen(
                        pauseResume = { if (it) at.pause() else at.play() },
                        queue = {
                            buffer.samples.interleaved(interleaved)

                            when (at.state) {
                                AudioTrack.STATE_UNINITIALIZED -> delay(20L)
                                AudioTrack.STATE_INITIALIZED -> {
                                    at.playbackRate = rate
                                    buffer.samples.interleaved(interleaved)
                                    at.write(interleaved.asShortArray(), 0, interleaved.size)
                                }
                            }
                        },
                        close = {
                            at.flush()
                            at.stop()
                            at.release()
                        }
                    )
                }
            }
        }
    }
}
