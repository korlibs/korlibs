package korlibs.io.file.std

import korlibs.io.lang.Environment
import korlibs.io.lang.tempPath

open class StandardPathsJvmAndroid : StandardPathsBase {
    // File(".").absolutePath
    override val cwd: String get() = System.getProperty("user.dir")
    override val temp: String get() = Environment.tempPath
}
