package korlibs.audio.sound

import korlibs.concurrent.thread.*
import korlibs.io.concurrent.*
import korlibs.platform.*
import kotlinx.coroutines.*

val Dispatchers.AUDIO: CoroutineDispatcher by lazy {
    when {
        Platform.isJsOrWasm -> Dispatchers.Main
        else -> {
            //Dispatchers.createFixedThreadDispatcher("AUDIO", 16)
            FixedPoolNativeThreadDispatcher(16, "AUDIO", NativeThreadPriority.HIGHER, isDaemon = true)
        }
    }
}
