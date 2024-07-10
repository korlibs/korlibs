package korlibs.io.file.std

import android.content.*
import korlibs.io.lang.*
import java.io.*

private var _vfsInitWithAndroidContextOnce: Boolean = false
var absoluteCwd = File(".").absolutePath
val tmpdir: String by lazy { Environment.tempPath }

fun vfsInitWithAndroidContextOnce(context: Context) {
    if (_vfsInitWithAndroidContextOnce) return
    _vfsInitWithAndroidContextOnce = true
    absoluteCwd = context.applicationInfo.dataDir
}
