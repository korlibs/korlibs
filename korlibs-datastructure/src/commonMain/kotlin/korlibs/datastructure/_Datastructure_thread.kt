package korlibs.datastructure

import korlibs.concurrent.thread.NativeThread
import korlibs.concurrent.thread.sleep
import korlibs.time.DateTime

//val NativeThread.extra: Extra get() {
//    if (this.userData == null) {
//        this.userData = Extra.Mixin()
//    }
//    return this.userData as Extra
//}

// Extension from DateTime
fun NativeThread.Companion.sleepUntil(date: DateTime, exact: Boolean = true) {
    sleep(date - DateTime.now(), exact)
}

