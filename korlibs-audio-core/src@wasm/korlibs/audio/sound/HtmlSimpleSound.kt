package korlibs.audio.sound

import korlibs.io.lang.*
import korlibs.memory.*
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.fetch.*
import kotlin.coroutines.*

private external interface WindowExSetTimeout : JsAny {
    fun setTimeout(block: () -> Unit, time: Int): Int
}

private val windowExSetTimeout get() = window.unsafeCast<WindowExSetTimeout>()

class AudioBufferOrHTMLMediaElement(
    val audioBuffer: AudioBuffer?,
    val htmlAudioElement: HTMLAudioElement?,
) {
    constructor(audioBuffer: AudioBuffer?) : this(audioBuffer, null)
    constructor(htmlMediaElement: HTMLAudioElement?) : this(null, htmlMediaElement)

    val isNull get() = audioBuffer == null && htmlAudioElement == null
    val isNotNull get() = !isNull

    val duration: Double? get() = when {
        audioBuffer != null -> audioBuffer.duration
        htmlAudioElement != null -> htmlAudioElement.duration
        else -> null
    }
    val numberOfChannels: Int get() = audioBuffer?.numberOfChannels ?: 1
}

fun createAudioElement(
    src: String,
    currentTime: Double = 0.0,
    autoplay: Boolean = false,
    crossOrigin: String? = "anonymous"
): Audio {
    val out = Audio(src)
    out.crossOrigin = crossOrigin
    out.currentTime = currentTime
    out.autoplay = autoplay
    out.pause()
    return out
}

fun HTMLAudioElement.clone(): Audio =
    createAudioElement(this.src, this.currentTime, this.autoplay, this.crossOrigin)

external private class WindowWithGlobalAudioContext : JsAny {
    var globalAudioContext: AudioContext?
}

@JsName("Console")
internal open external class JsConsole {
    fun error(vararg msg: String)
    fun warn(vararg msg: String)
    fun info(vararg msg: String)
    fun log(vararg msg: String)
}

internal external val console: JsConsole /* compiled code */

object HtmlSimpleSound {
	val ctx: BaseAudioContext? = try {
        AudioContext().also {
            (window.unsafeCast<WindowWithGlobalAudioContext>()).globalAudioContext = it
        }
	} catch (e: Throwable) {
        console.error("$e")
        null
    }

	val available get() = ctx != null
	var unlocked = false
	private val unlockDeferred = CompletableDeferred<Unit>(Job())
	val unlock = unlockDeferred as Deferred<Unit>

    suspend fun getUnlockedContextOrThrow(): BaseAudioContext {
        if (ctx == null) error("Couldn't get AudioContext")
        unlock.await()
        return ctx
    }

    /*
	class SimpleSoundChannel(
		val buffer: AudioBufferOrHTMLMediaElement,
		val ctx: BaseAudioContext,
        val params: PlaybackParameters,
        val coroutineContext: CoroutineContext
	) {
        var gainNode: GainNode? = null
        var pannerNode: PannerNode? = null
        var sourceNode: AudioScheduledSourceNode? = null
        var realHtmlAudioElement: HTMLAudioElement? = null

        fun createNode(startTime: Duration) {
            realHtmlAudioElement?.pause()
            sourceNode?.disconnect()

            val htmlAudioElement = buffer.htmlAudioElement
            val audioBuffer = buffer.audioBuffer

            ctx.destination.apply {
                pannerNode = panner {
                    gainNode = gain {
                        when {
                            htmlAudioElement != null -> {
                                realHtmlAudioElement = htmlAudioElement.clone()
                                sourceNode = source(realHtmlAudioElement!!)
                            }

                            audioBuffer != null -> {
                                sourceNode = source(audioBuffer)
                            }
                        }
                    }
                }
                updateNodes()
            }
            val realHtmlAudioElement = this.realHtmlAudioElement
            if (realHtmlAudioElement != null) {
                realHtmlAudioElement.currentTime = startTime.seconds
                realHtmlAudioElement.play()
            } else {
                sourceNode?.start(0.0, startTime.seconds)
            }
        }

        var startedAt = DateTime.now()
        var times = params.times

        fun createJobAt(startTime: Duration): Job {
            if (coroutineContext.job.isCompleted) {
                logger.warn { "Sound won't play because coroutineContext.job is completed" }
            }
            startedAt = DateTime.now()
            var startTime = startTime
            ctx?.resume()
            return CoroutineScope(coroutineContext).launch {
                try {
                    while (times.hasMore) {
                        //println("TIMES: $times, startTime=$startTime, buffer.duration.seconds=${buffer.duration.seconds}")
                        startedAt = DateTime.now()
                        createNode(startTime)
                        startTime = 0.seconds
                        val deferred = CompletableDeferred<Unit>()
                        //println("sourceNode: $sourceNode, ctx?.state=${ctx?.state}, buffer.duration=${buffer.duration}")
                        if (sourceNode == null || ctx?.state != "running") {
                            windowExSetTimeout.setTimeout(
                                {
                                    deferred.complete(Unit)
                                    null
                                },
                                ((buffer.duration ?: 0.0) * 1000).toInt()
                            )
                        } else {
                            sourceNode?.onended = {
                                deferred.complete(Unit)
                            }
                        }
                        //println("awaiting sound")
                        deferred.await()
                        times = times.oneLess
                        //println("sound awaited")
                        if (!times.hasMore) break
                    }
                } catch (e: CancellationException) {
                    params.onCancel?.invoke()
                } finally {
                    running = false
                    val realHtmlAudioElement = this@SimpleSoundChannel.realHtmlAudioElement
                    if (realHtmlAudioElement != null) {
                        realHtmlAudioElement.pause()
                        realHtmlAudioElement.currentTime = 0.0
                        //sourceNode?.stop()
                    } else {
                        sourceNode?.stop()
                    }
                    gainNode = null
                    pannerNode = null
                    sourceNode = null
                    params.onFinish?.invoke()
                }
            }
        }

        var currentTime: Duration
            get() = DateTime.now() - startedAt
            set(value) {
                job?.cancel()
                job = createJobAt(value)
            }
        var volume: Double = params.volume
            set(value) {
                field = value
                updateVolume()
            }
        var pitch: Double = params.pitch
            set(value) {
                field = value
                updatePitch()
            }
        var panning: Double = params.panning
            set(value) {
                field = value
                updatePanning()
            }


        private var running = true
        var pausedAt: Duration? = null

        fun updateNodes() {
            updateVolume()
            updatePitch()
            updatePanning()
        }

        fun updateVolume() {
            gainNode?.gain?.value = volume
        }

        fun updatePitch() {
        }

        fun updatePanning() {
            pannerNode?.setPosition(panning, 0.0, 0.0)
            pannerNode?.setOrientation(0.0, 1.0, 0.0)
        }

        //val playing get() = running && currentTime < buffer.duration
        val playing: Boolean
            get() = running.also {
                //println("playing: $running")
            }

        fun pause() {
            this.pausedAt = currentTime
            if (realHtmlAudioElement != null) {
                realHtmlAudioElement?.pause()
            } else {
                stop()
            }
        }

        fun resume() {
            if (realHtmlAudioElement != null) {
                realHtmlAudioElement?.play()
            } else {
                this.pausedAt?.let { currentTime = it }
            }
            this.pausedAt = null
        }

        fun stop() {
            job?.cancel()
        }

        fun play() {
            if (job != null && realHtmlAudioElement != null) {
                realHtmlAudioElement?.play()
            } else {
                stop()
                job = createJobAt(params.startTime)
            }
        }

        var job: Job? = null
    }

	fun AudioNode.panner(callback: PannerNode.() -> Unit = {}): PannerNode? {
		val ctx = ctx ?: return null
		val node = kotlin.runCatching { ctx.createPanner() }.getOrNull() ?: return null
		callback(node)
		node.connect(this)
		return node
	}

	fun AudioNode.gain(callback: GainNode.() -> Unit = {}): GainNode? {
		val ctx = ctx ?: return null
		val node = ctx.createGain()
		callback(node)
		node.connect(this)
		return node
	}

    fun AudioNode.sourceAny(buffer: AudioBufferOrHTMLMediaElement, callback: AudioScheduledSourceNode.() -> Unit = {}): AudioScheduledSourceNode? {
        val audioBuffer = buffer.audioBuffer
        val htmlAudioElement = buffer.htmlAudioElement
        return when {
            audioBuffer != null -> source(audioBuffer) { callback() }
            htmlAudioElement != null -> source(htmlAudioElement) { callback() }
            else -> error("Unexpected buffer $buffer")
        }
    }

	fun AudioNode.source(buffer: AudioBuffer, callback: AudioBufferSourceNode.() -> Unit = {}): AudioBufferSourceNode? {
		val ctx = ctx ?: return null
		val node = ctx.createBufferSource()
		node.buffer = buffer
		callback(node)
		node.connect(this)
		return node
	}

    fun AudioNode.source(buffer: HTMLAudioElement, callback: MediaElementAudioSourceNode.() -> Unit = {}): MediaElementAudioSourceNode? {
        val ctx = ctx ?: return null
        val node = ctx.createMediaElementSource(buffer)
        callback(node)
        node.connect(this)
        return node
    }

    fun playSound(buffer: AudioBufferOrHTMLMediaElement, params: PlaybackParameters, coroutineContext: CoroutineContext): SimpleSoundChannel? {
        return ctx?.let { SimpleSoundChannel(buffer, it, params, coroutineContext) }
    }

    fun stopSound(channel: AudioBufferSourceNode?) {
		channel?.disconnect(0)
		channel?.stop(0.0)
	}
     */

    fun ensureUnlockStart() {
        unlock
    }

	suspend fun waitUnlocked(): BaseAudioContext? {
        if (!unlock.isCompleted) {
            console.warn("Waiting for key or mouse down to start sound...")
        }
		unlock.await()
		return ctx
	}

	fun callOnUnlocked(callback: (Unit) -> Unit): Cancellable {
		var cancelled = false
		unlock.invokeOnCompletion { if (!cancelled) callback(Unit) }
		return Cancellable { cancelled = true }
	}

	suspend fun loadSound(data: ArrayBuffer, url: String): AudioBuffer? {
		if (ctx == null) return null
		return suspendCoroutine<AudioBuffer> { c ->
			ctx.decodeAudioData(
				data,
				{ data -> c.resume(data) },
				{ c.resumeWithException(Exception("error decoding $url")) }
			)
		}
	}

	fun loadSoundBuffer(url: String): HTMLAudioElement? {
		if (ctx == null) return null
		return createAudioElement(url)
	}

    /*
	suspend fun playSoundBuffer(buffer: HTMLAudioElement?) {
		if (ctx != null) {
			buffer?.audio?.play()
			buffer?.node?.connect(ctx.destination)
		}
	}

	suspend fun stopSoundBuffer(buffer: HTMLAudioElement?) {
		if (ctx != null) {
			buffer?.audio?.pause()
			buffer?.audio?.currentTime = 0.0
			buffer?.node?.disconnect(ctx.destination)
		}
	}
    */

	suspend fun loadSound(data: ByteArray): AudioBuffer? = loadSound(data.toInt8Array().buffer, "ByteArray")

	suspend fun loadSound(url: String): AudioBuffer? =
        loadSound(window.fetch(url).await<Response>().arrayBuffer().await<ArrayBuffer>().toByteArray())

	init {
		val _scratchBuffer = ctx?.createBuffer(1, 1, 22050)
		lateinit var unlock: (e: Event) -> Unit
        unlock = {
            // Remove the touch start listener.
            document.removeEventListener("keydown", unlock, true)
            document.removeEventListener("touchstart", unlock, true)
            document.removeEventListener("touchend", unlock, true)
            document.removeEventListener("mousedown", unlock, true)

            if (ctx != null) {
                // If already created the audio context, we try to resume it
                (window.unsafeCast<WindowWithGlobalAudioContext>()).globalAudioContext?.unsafeCast<BaseAudioContext>()?.resume()

                val source = ctx.createBufferSource()

                source.buffer = _scratchBuffer
                source.connect(ctx.destination)
                source.start(0.0)
                ctx.resume()
                source.onended = {
                    source.disconnect(0)

                    unlocked = true
                    console.info("Web Audio was successfully unlocked")
                    unlockDeferred.complete(Unit)
                }
            }
        }

		document.addEventListener("keydown", unlock, true)
		document.addEventListener("touchstart", unlock, true)
		document.addEventListener("touchend", unlock, true)
		document.addEventListener("mousedown", unlock, true)
	}
}

private fun ByteArray.toInt8Array(): Int8Array {
    //val tout = this.asDynamic()
    //if (tout is Int8Array) {
    //    return tout.unsafeCast<Int8Array>()
    //} else {
    val out = Int8Array(this.size)
    for (n in 0 until out.length) out[n] = this[n]
    return out
    //}
}
