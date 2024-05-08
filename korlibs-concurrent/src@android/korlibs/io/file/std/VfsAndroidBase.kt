package korlibs.io.file.std

import android.content.*
import java.io.*

private var _vfsInitWithAndroidContextOnce: Boolean = false
var absoluteCwd = File(".").absolutePath
val tmpdir: String by lazy { System.getProperty("java.io.tmpdir") }

fun vfsInitWithAndroidContextOnce(context: Context) {
    if (_vfsInitWithAndroidContextOnce) return
    _vfsInitWithAndroidContextOnce = true
    absoluteCwd = context.applicationInfo.dataDir
}
