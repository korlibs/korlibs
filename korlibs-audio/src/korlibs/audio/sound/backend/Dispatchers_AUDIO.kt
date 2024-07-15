package korlibs.audio.sound.backend

import korlibs.concurrent.thread.*
import korlibs.platform.*
import kotlinx.coroutines.*

val Dispatchers.AUDIO: CoroutineDispatcher by lazy {
    when {
        Platform.isJsOrWasm -> Dispatchers.Main
        else -> {
            FixedPoolNativeThreadDispatcher(16, "AUDIO", NativeThreadPriority.HIGHER, isDaemon = true)
        }
    }
}
