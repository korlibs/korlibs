package korlibs.audio.sound.node

import korlibs.audio.sound.AudioData

// @TODO: Enable this later
internal interface AudioNode {
    fun seek(pos: Long) = Unit
    fun generate(audioData: AudioData): Int
}