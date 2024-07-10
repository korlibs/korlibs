package korlibs.audio.core

import korlibs.audio.core.impl.*
import korlibs.platform.*

actual val defaultAudioSystem: AudioSystem by lazy {
    when {
        Platform.isWindows -> Win32AudioSystem
        //Platform.isWindows -> OpenALAudioSystem
        Platform.isLinux -> ALSAAudioSystem
        //else -> OpenALAudioSystem
        else -> NullAudioSystem
    }
}
