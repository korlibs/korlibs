package korlibs.audio.core

import korlibs.audio.core.impl.*
import korlibs.platform.*

actual val defaultAudioSystem: AudioSystem by lazy {
    when {
        Platform.isWindows -> Win32AudioSystem
        else -> NullAudioSystem
    }
}
