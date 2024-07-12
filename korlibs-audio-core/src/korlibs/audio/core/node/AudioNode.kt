package korlibs.audio.core.node

import korlibs.audio.core.*
import korlibs.time.*

fun interface AudioNode {
    fun seek(position: Long) = Unit
    fun processSize(nsamples: Int): Int = nsamples
    fun process(data: AudioBuffer)
}

interface AudioNodeWithRate : AudioNode {
    val rate: Int
}

interface AudioNodeWithDuration : AudioNodeWithRate {
    val durationSamples: Long
    val durationTime: FastDuration get() = samplesToTime(durationSamples)

    fun samplesToTime(samples: Long): FastDuration = (samples.toDouble() / rate.toDouble()).fastSeconds
    fun timeToSamples(time: FastDuration): Long = (time.seconds * rate).toLong()
}

interface AudioNodeWithCurrentAndDuration : AudioNodeWithDuration {
    var currentSamples: Long
    var currentTime: FastDuration
        get() = samplesToTime(currentSamples)
        set(value) { currentSamples = timeToSamples(value) }

    val remainingSamples: Long get() = durationSamples - currentSamples
    val remainingTime: FastDuration get() = durationTime - currentTime
}

class AudioNodes(val nodes: List<AudioNode>) : AudioNode {
    constructor(vararg nodes: AudioNode) : this(nodes.toList())

    override fun seek(position: Long) {
        for (node in nodes) node.seek(position)
    }

    override fun process(data: AudioBuffer) {
        for (node in nodes) node.process(data)
    }
}

operator fun AudioNode.plus(other: AudioNode): AudioNodes {
    val currentNodes = if (this is AudioNodes) nodes else listOf(this)
    val otherNodes = if (other is AudioNodes) other.nodes else listOf(other)
    return AudioNodes(currentNodes + otherNodes)
}
