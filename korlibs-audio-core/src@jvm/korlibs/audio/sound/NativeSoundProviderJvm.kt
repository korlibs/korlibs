package korlibs.audio.sound

import korlibs.audio.sound.backend.*
import korlibs.concurrent.thread.*
import korlibs.platform.*

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
