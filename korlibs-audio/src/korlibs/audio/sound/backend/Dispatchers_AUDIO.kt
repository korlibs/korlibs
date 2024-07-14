package korlibs.audio.sound.backend

import korlibs.io.concurrent.*
import korlibs.platform.*
import kotlinx.coroutines.*

val Dispatchers.AUDIO: CoroutineDispatcher by lazy {
    when {
        Platform.isJsOrWasm -> Dispatchers.Main
        else -> Dispatchers.createFixedThreadDispatcher("AUDIO", 16)
    }
}
