package korlibs.audio.core.node

import korlibs.audio.core.*
import korlibs.math.*
import korlibs.time.*

fun interface AudioNode {
    val rate: Int get() = 44100
    val nchannels: Int get() = 2
    val durationSamples: Long get() = Long.MAX_VALUE
    var currentSamples: Long
        get() = 0L
        set(value) { }

    fun processSize(nsamples: Int): Int = minOf(nsamples, remainingSamples.toIntClamp())
    fun process(data: AudioBuffer)
}

val AudioNode.durationTime: FastDuration get() = samplesToTime(durationSamples)
var AudioNode.currentTime: FastDuration
    get() = samplesToTime(currentSamples)
    set(value) { currentSamples = timeToSamples(value) }
val AudioNode.remainingSamples: Long get() = durationSamples - currentSamples
val AudioNode.remainingTime: FastDuration get() = durationTime - currentTime
fun AudioNode.samplesToTime(samples: Long): FastDuration = (samples.toDouble() / rate.toDouble()).fastSeconds
fun AudioNode.timeToSamples(time: FastDuration): Long = (time.seconds * rate).toLong()

class AudioNodes(val nodes: List<AudioNode>) : AudioNode {
    constructor(vararg nodes: AudioNode) : this(nodes.toList())

    override val rate: Int get() = nodes.maxOf { it.rate }
    override val nchannels: Int get() = nodes.maxOf { it.nchannels }

    override var currentSamples: Long
        get() = nodes.minOf { it.currentSamples }
        set(value) { for (node in nodes) node.currentSamples = value }

    override val durationSamples: Long get() = nodes.minOf { it.durationSamples }

    override fun processSize(nsamples: Int): Int = nodes.minOf { it.processSize(nsamples) }

    override fun process(data: AudioBuffer) {
        for (node in nodes) node.process(data)
    }
}

operator fun AudioNode.plus(other: AudioNode): AudioNodes {
    val currentNodes = if (this is AudioNodes) nodes else listOf(this)
    val otherNodes = if (other is AudioNodes) other.nodes else listOf(other)
    return AudioNodes(currentNodes + otherNodes)
}
