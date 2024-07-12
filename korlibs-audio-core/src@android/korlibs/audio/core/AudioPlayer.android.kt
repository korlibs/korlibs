package korlibs.audio.core

import android.content.*
import android.media.*
import android.os.*
import korlibs.audio.core.impl.*
import korlibs.audio.core.node.*
import korlibs.io.android.*
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

    override fun createPlayer(device: AudioDevice): AudioPlayer = SoftAudioPlayer(device, AndroidAudioStreamPlayer(this))
}

class AndroidAudioStreamPlayer(val system: AndroidAudioSystem) : AudioStreamPlayer {
    override fun playNode(source: AudioSource, rate: Int, channels: Int, node: AudioNode): AudioSimpleStream {
        var paused = false
        val thread = korlibs.concurrent.thread.nativeThread(isDaemon = true) { thread ->
            //val bufferSamples = 4096
            val bufferSamples = 1024

            val atChannelSize = Short.SIZE_BYTES * channels * bufferSamples
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
                System.err.println("Audio track was not initialized correctly frequency=$rate, bufferSamples=$bufferSamples")
            }

            val buffer = AudioBuffer(channels, bufferSamples, rate)
            val interleaved = buffer.samples.interleaved()
            at.play()

            try {
                while (thread.threadSuggestRunning) {
                    if (paused) {
                        at.pause()
                        Thread.sleep(20L)
                        continue
                    } else {
                        at.play()
                    }

                    when (at.state) {
                        AudioTrack.STATE_UNINITIALIZED -> Thread.sleep(20L)
                        AudioTrack.STATE_INITIALIZED -> {
                            at.playbackRate = rate
                            node.process(buffer)
                            buffer.samples.interleaved(interleaved)
                            at.write(interleaved.asShortArray(), 0, interleaved.size)
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

        return AudioSimpleStream(
            onPausedChange = { paused = it },
            onClosed = { thread.threadSuggestRunning = false }
        )
    }
}
