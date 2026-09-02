package korlibs.audio.sound

import korlibs.concurrent.thread.FixedPoolNativeThreadDispatcher
import korlibs.concurrent.thread.NativeThreadPriority
import korlibs.platform.Platform
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val Dispatchers.AUDIO: CoroutineDispatcher by lazy {
    when {
        Platform.isJsOrWasm -> Dispatchers.Main
        else -> {
            //Dispatchers.createFixedThreadDispatcher("AUDIO", 16)
            FixedPoolNativeThreadDispatcher(16, "AUDIO", NativeThreadPriority.HIGHER, isDaemon = true)
        }
    }
}
