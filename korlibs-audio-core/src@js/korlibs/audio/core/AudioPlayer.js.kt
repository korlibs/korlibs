package korlibs.audio.core

import korlibs.audio.core.node.AudioNode
import korlibs.math.geom.*
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.w3c.dom.*
import org.w3c.dom.events.*

actual val defaultAudioSystem: AudioSystem = WebAudioAudioSystem

object WebAudioAudioSystem : AudioSystem() {
    val unlockDeferred = CompletableDeferred<Unit>()
    var unlocked = false

    val context = AudioContext().also { ctx ->
        var unlock: (e: Event) -> Unit = {}
        val _scratchBuffer = ctx.createBuffer(1, 1, 22050)

        unlock = {
            // Remove the touch start listener.
            document.removeEventListener("keydown", unlock, true)
            document.removeEventListener("touchstart", unlock, true)
            document.removeEventListener("touchend", unlock, true)
            document.removeEventListener("mousedown", unlock, true)

            // If already created the audio context, we try to resume it
            (window.asDynamic()).globalAudioContext.unsafeCast<BaseAudioContext?>()?.resume()

            val source = ctx.createBufferSource()

            source.buffer = _scratchBuffer
            source.connect(ctx.destination)
            source.start(0.0)
            if (jsTypeOf(ctx.asDynamic().resume) === "function") ctx.asDynamic().resume()
            source.onended = {
                source.disconnect(0)

                unlocked = true
                console.info("Web Audio was successfully unlocked")
                unlockDeferred.complete(Unit)
            }
        }
        document.addEventListener("keydown", unlock, true)
        document.addEventListener("touchstart", unlock, true)
        document.addEventListener("touchend", unlock, true)
        document.addEventListener("mousedown", unlock, true)
    }

    override fun createPlayer(device: AudioDevice): AudioPlayer = WebAudioAudioPlayer(device)
}

class WebAudioAudioPlayer(override val device: AudioDevice) : AudioPlayer() {
    private val ctx get() = WebAudioAudioSystem.context
    val clistener get() = ctx.listener

    override var listenerGain: Float
        get() = super.listenerGain
        set(value) {
        }
    override var listenerSpeed: Vector3 = Vector3.ZERO
    override var listenerPosition: Vector3 = Vector3.ZERO
        set(value) { field = value; clistener.setPosition(clistener.positionX, clistener.positionY, clistener.positionZ) }

    override var listenerOrientation: AudioOrientation = AudioOrientation()
        set(value) { field = value; clistener.setOrientation(clistener.forwardX, clistener.forwardY, clistener.forwardZ, clistener.upX, clistener.upY, clistener.upZ) }

    override fun close() {
        super.close()
    }

    override fun createSource(): AudioSource = WebAudioAudioSource(this)
}

// PITCH SHIFTING: https://github.com/ChenMachluf/pitch-shift-node
// https://github.com/mikolalysenko/pitch-shift/blob/master/pitchshift.js
class WebAudioAudioSource(override val player: AudioPlayer) : AudioSource() {
    private val ctx get() = WebAudioAudioSystem.context

    private val pannerNode = ctx.createPanner().also { it.connect(ctx.destination) }
    private val gainNode = ctx.createGain().also { it.connect(pannerNode) }
    var channelNode: AudioBufferSourceNode? = null
    var scriptNode: ScriptProcessorNode? = null

    override var position: Vector3 = Vector3.ZERO
        set(value) { field = value; pannerNode.positionX = value.x.toDouble(); pannerNode.positionY = value.y.toDouble(); pannerNode.positionZ = value.z.toDouble(); }
    override var direction: Vector3 = Vector3(1, 0, 0)
        set(value) { field = value; pannerNode.orientationX = value.x.toDouble(); pannerNode.orientationY = value.y.toDouble(); pannerNode.orientationZ = value.z.toDouble(); }
    override var coneOuterGain: Float = 0f
        set(value) { field = value; pannerNode.coneOuterGain = value.toDouble() }

    //override var pitch: Float = 1f
    //    set(value) { field = value; }
    override var volume: Float = 1f
        set(value) { field = value; gainNode.gain.value = value.toDouble() }
    override var looping: Boolean = false
        set(value) { field = value; channelNode?.loop = field }

    override var currentSamples: Long = 0L
        set(value) {
            field = value
            channelNode?.start(samplesToTime(value).seconds)
        }

    private fun cleanNodes() {
        channelNode?.disconnect()
        scriptNode?.disconnect()
        channelNode = null
        scriptNode = null
    }

    override fun setContent(buffer: AudioBuffer) {
        super.setContent(buffer)
        cleanNodes()
        val data = buffer.samples
        val jsbuffer = ctx.createBuffer(buffer.nchannels, buffer.nsamples, buffer.rate)
        for (ch in 0 until buffer.nchannels) data[ch].toFloatArray(jsbuffer.getChannelData(ch).unsafeCast<FloatArray>())
        channelNode = ctx.createBufferSource().also { it.connect(gainNode) }
        channelNode?.buffer = jsbuffer
        channelNode?.loop = looping
    }

    override fun setNode(samplesTotal: Long, rate: Int, nchannels: Int, node: AudioNode) {
        cleanNodes()
        val bufferSamples = 1024
        val samples = AudioBuffer(nchannels, bufferSamples, rate)
        scriptNode = ctx.createScriptProcessor(bufferSamples, nchannels, nchannels)
        scriptNode?.onaudioprocess = {
            if (state == AudioSourceState.PLAYING) {
                node.process(samples)
                for (ch in 0 until it.inputBuffer.numberOfChannels) {
                    samples.samples[ch].toFloatArray(it.outputBuffer.getChannelData(ch).unsafeCast<FloatArray>())
                }
            }
        }
    }

    override var state = AudioSourceState.INITIAL
        set(value) {
            val oldState = field
            field = value
            when (value) {
                AudioSourceState.INITIAL -> Unit
                AudioSourceState.PLAYING -> {
                    if (oldState == AudioSourceState.PAUSED) {
                        channelNode?.start()
                    } else {
                        channelNode?.start(0.0, samplesToTime(currentSamples).seconds)
                        scriptNode?.connect(gainNode)
                    }
                }
                AudioSourceState.PAUSED -> {
                    channelNode?.stop()
                }
                AudioSourceState.STOPPED -> {
                    channelNode?.stop()
                    scriptNode?.disconnect()
                }
            }
        }

    override fun close() {
        channelNode?.disconnect(); channelNode = null
        scriptNode?.disconnect(); scriptNode = null
        state = AudioSourceState.INITIAL
    }
}