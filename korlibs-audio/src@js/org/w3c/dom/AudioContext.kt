package org.w3c.dom

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Float32Array
import org.w3c.dom.events.*

external interface AudioParam {
    val defaultValue: Double
    val minValue: Double
    val maxValue: Double
    var value: Double
}

external interface GainNode : AudioNode {
    val gain: AudioParam
}

external interface StereoPannerNode : AudioNode {
    val pan: AudioParam
}

external interface PannerNode : AudioNode {
    fun setPosition(x: Double, y: Double, z: Double)
    fun setOrientation(x: Double, y: Double, z: Double)
}

open external class BaseAudioContext {
    fun createScriptProcessor(
        bufferSize: Int,
        numberOfInputChannels: Int,
        numberOfOutputChannels: Int
    ): ScriptProcessorNode

    fun decodeAudioData(ab: ArrayBuffer, successCallback: (AudioBuffer) -> Unit, errorCallback: () -> Unit): Unit

    fun createMediaElementSource(audio: HTMLAudioElement): MediaElementAudioSourceNode
    fun createBufferSource(): AudioBufferSourceNode
    fun createGain(): GainNode
    fun createPanner(): PannerNode
    fun createStereoPanner(): StereoPannerNode
    fun createBuffer(numOfchannels: Int, length: Int, rate: Int): AudioBuffer

    var currentTime: Double
    //var listener: AudioListener
    var sampleRate: Double
    var state: String // suspended, running, closed
    val destination: AudioDestinationNode

    fun resume()
    fun suspend()
}

external class AudioContext : BaseAudioContext
external class webkitAudioContext : BaseAudioContext

external interface MediaElementAudioSourceNode : AudioScheduledSourceNode {
    val mediaElement: HTMLMediaElement
}

external interface AudioScheduledSourceNode : AudioNode {
    var onended: () -> Unit
    fun start(whn: Double = definedExternally, offset: Double = definedExternally, duration: Double = definedExternally)
    fun stop(whn: Double = definedExternally)
}

external interface AudioBufferSourceNode : AudioScheduledSourceNode {
    var buffer: AudioBuffer?
    var detune: Int
    var loop: Boolean
    var loopEnd: Double
    var loopStart: Double
    var playbackRate: Double
}

external class AudioBuffer {
    val duration: Double
    val length: Int
    val numberOfChannels: Int
    val sampleRate: Int
    fun copyFromChannel(destination: Float32Array, channelNumber: Int, startInChannel: Double?): Unit
    fun copyToChannel(source: Float32Array, channelNumber: Int, startInChannel: Double?): Unit
    fun getChannelData(channel: Int): Float32Array
    //fun getChannelData(channel: Int): FloatArray
}

external interface AudioNode {
    val channelCount: Int
    //val channelCountMode: ChannelCountMode
    //val channelInterpretation: ChannelInterpretation
    val context: AudioContext
    val numberOfInputs: Int
    val numberOfOutputs: Int
    fun connect(destination: AudioNode, output: Int? = definedExternally, input: Int? = definedExternally): AudioNode
    //fun connect(destination: AudioParam, output: Int?): Unit
    fun disconnect(output: Int? = definedExternally): Unit

    fun disconnect(destination: AudioNode, output: Int? = definedExternally, input: Int? = definedExternally): Unit
    //fun disconnect(destination: AudioParam, output: Int?): Unit
}

external interface AudioDestinationNode : AudioNode {
    val maxChannelCount: Int
}

external class AudioProcessingEvent : Event {
    val inputBuffer: AudioBuffer
    val outputBuffer: AudioBuffer
    val playbackTime: Double
}

external interface ScriptProcessorNode : AudioNode {
    var onaudioprocess: (AudioProcessingEvent) -> Unit
}
