package korlibs.audio.sound

import korlibs.audio.sound.backend.*
import korlibs.concurrent.thread.*
import korlibs.platform.*

//private val logger = Logger("NativeSoundProviderJvm")

private val nativeSoundProviderDeferred: NativeSoundProvider by lazy {
    try {
        when {
            //Platform.isLinux -> FFIALSANativeSoundProvider
            Platform.isApple -> jvmCoreAudioNativeSoundProvider
            Platform.isWindows -> jvmWaveOutNativeSoundProvider
            //else -> JnaOpenALNativeSoundProvider()
            else -> AwtNativeSoundProvider
        } ?: AwtNativeSoundProvider
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

actual val nativeSoundProvider: NativeSoundProvider by lazy {
    nativeThread(isDaemon = true, start = true) { nativeSoundProviderDeferred }
    LazyNativeSoundProvider { nativeSoundProviderDeferred }
}
//actual val nativeSoundProvider: NativeSoundProvider by lazy { JogampNativeSoundProvider() }
//actual val nativeSoundProvider: NativeSoundProvider by lazy { AwtNativeSoundProvider() }
