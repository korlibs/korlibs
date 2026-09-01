package korlibs.audio.sound

import korlibs.audio.sound.backend.AWTNativeSoundProvider
import korlibs.audio.sound.backend.FFIALSANativeSoundProvider
import korlibs.audio.sound.backend.FFIJVMWaveOutNativeSoundProvider
import korlibs.audio.sound.backend.JVMCoreAudioNativeSoundProvider
import korlibs.platform.Platform

actual val nativeSoundProvider: NativeSoundProvider by lazy {
    try {
        when {
            Platform.isLinux -> FFIALSANativeSoundProvider
            Platform.isApple -> JVMCoreAudioNativeSoundProvider
            Platform.isWindows -> FFIJVMWaveOutNativeSoundProvider
            //else -> JnaOpenALNativeSoundProvider()
            else -> AWTNativeSoundProvider
        }
    } catch (e: UnsatisfiedLinkError) {
        DummyNativeSoundProvider
        //} catch (e: OpenALException) {
        //    logger.error { "OpenALException: ${e.message}" }
        //    DummyNativeSoundProvider
    } catch (e: Throwable) {
        e.printStackTrace()
        DummyNativeSoundProvider
    }
}
