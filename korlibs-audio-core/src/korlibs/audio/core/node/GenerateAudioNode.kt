package korlibs.audio.core.node

import korlibs.audio.core.*

class GenerateAudioNode(
    override val rate: Int = 44100, // @TODO: resampling
    val gen: (position: Long, data: AudioBuffer) -> Int
) : AudioNodeWithRate {
    var position = 0L
    override fun process(data: AudioBuffer) {
        position += gen(position, data)
    }
}