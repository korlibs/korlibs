package korlibs.audio.sound.backend

import korlibs.io.concurrent.*
import kotlinx.coroutines.*

val Dispatchers.AUDIO by lazy {
    Dispatchers.createFixedThreadDispatcher("AUDIO", 16)
}
