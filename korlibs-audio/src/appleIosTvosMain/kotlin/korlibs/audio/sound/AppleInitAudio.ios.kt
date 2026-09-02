@file:OptIn(ExperimentalForeignApi::class)

package korlibs.audio.sound

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryAmbient
import platform.AVFAudio.setActive
import platform.Foundation.NSError

internal actual val appleInitAudioOnce: Unit by lazy {
    memScoped {
        val error = alloc<ObjCObjectVar<NSError?>>().ptr
        //AVAudioSession.sharedInstance().setCategory(AVAudioSessionCategoryPlayback, error) // Stops Music apps, etc.
        //AVAudioSession.sharedInstance().setCategory(AVAudioSessionCategorySoloAmbient, error)
        AVAudioSession.sharedInstance().setCategory(AVAudioSessionCategoryAmbient, error)
        //AVAudioSessionCategoryOptionMixWithOthers
        AVAudioSession.sharedInstance().setActive(true, error)
    }
    Unit
}
