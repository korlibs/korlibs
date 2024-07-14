package korlibs.audio.sound

@Deprecated("Use nodes")
interface AudioStreamable {
    suspend fun toStream(): AudioStream
}
