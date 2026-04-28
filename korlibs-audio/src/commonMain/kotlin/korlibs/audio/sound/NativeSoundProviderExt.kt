package korlibs.audio.sound

import korlibs.audio.format.*
import korlibs.io.file.*
import korlibs.io.stream.*
import kotlin.coroutines.*


//suspend fun createNewPlatformAudioOutput(nchannels: Int, freq: Int = 44100, gen: AudioPlatformOutputGen): AudioPlatformOutput = createNewPlatformAudioOutput(coroutineContextKt, nchannels, freq, gen)

suspend fun NativeSoundProvider.createSound(data: ByteArray, streaming: Boolean = false, props: AudioDecodingProps = AudioDecodingProps.DEFAULT, name: String = "Unknown"): Sound {
    val format = props.formats ?: audioFormats
    val stream = format.decodeStreamOrError(data.openAsync(), props)
    return if (streaming) {
        createStreamingSound(stream, closeStream = true, name = name)
    } else {
        createNonStreamingSound(stream.toData(), name = name)
    }
}

val NativeSoundProvider.audioFormats: AudioFormats by lazy { defaultAudioFormats }
//open val audioFormats: AudioFormats = AudioFormats(WAV, MP3Decoder, OGG)

suspend fun NativeSoundProvider.createSound(vfs: Vfs, path: String, streaming: Boolean = false, props: AudioDecodingProps = AudioDecodingProps.DEFAULT): Sound {
    //println("createSound.coroutineContext: $coroutineContextKt")
    return if (streaming) {
        //val stream = vfs.file(path).open()
        //createStreamingSound(audioFormats.decodeStreamOrError(stream, props)) {
        val vfsFile = vfs.file(path)
        val stream: AsyncStream = if (props.readInMemory) vfsFile.readAll().openAsync() else vfsFile.open()

        createStreamingSound((props.formats ?: audioFormats).decodeStreamOrError(stream, props), name = vfsFile.baseName) {
            stream.close()
        }
    } else {
        createSound(vfs.file(path).read(), streaming, props, name = vfs[path].baseName)
    }
}

suspend fun NativeSoundProvider.createSound(file: FinalVfsFile, streaming: Boolean = false, props: AudioDecodingProps = AudioDecodingProps.DEFAULT): Sound = createSound(file.vfs, file.path, streaming, props)
suspend fun NativeSoundProvider.createSound(file: VfsFile, streaming: Boolean = false, props: AudioDecodingProps = AudioDecodingProps.DEFAULT): Sound = createSound(file.getUnderlyingUnscapedFile(), streaming, props)

suspend fun NativeSoundProvider.createNonStreamingSound(
    data: AudioData,
    name: String = "Unknown"
    //): Sound = createStreamingSound(data.toStream(), true, name)
): Sound = SoundAudioData(coroutineContext, data, this, data.name ?: name)

suspend fun NativeSoundProvider.createSound(
    data: AudioData,
    formats: AudioFormats = defaultAudioFormats,
    streaming: Boolean = false,
    name: String = "Unknown"
): Sound = createSound(WAV.encodeToByteArray(data), streaming, name = name)

suspend fun NativeSoundProvider.createStreamingSound(stream: AudioStream, closeStream: Boolean = false, name: String = "Unknown", onComplete: (suspend () -> Unit)? = null): Sound =
    SoundAudioStream(kotlin.coroutines.coroutineContext, stream, this, closeStream, name, onComplete)

suspend fun NativeSoundProvider.playAndWait(stream: AudioStream, params: PlaybackParameters = PlaybackParameters.DEFAULT) = createStreamingSound(stream).playAndWait(params)
