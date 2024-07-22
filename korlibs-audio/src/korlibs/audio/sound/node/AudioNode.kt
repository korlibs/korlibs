package korlibs.audio.sound.node

import korlibs.audio.sound.*

// @TODO: Enable this later
interface AudioNode {
    fun seek(pos: Long) = Unit
    fun generate(audioData: AudioData): Int
}