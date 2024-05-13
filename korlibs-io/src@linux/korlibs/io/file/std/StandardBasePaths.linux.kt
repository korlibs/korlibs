package korlibs.io.file.std

import korlibs.io.core.*
import korlibs.io.posix.*

actual object StandardPaths : StandardBasePathsNative(), StandardPathsBase {
    override val executableFile: String get() = LinuxSyncSystemFs.getCurrentExe() ?: "./a.out"
    override val executableFolder: String get() = LinuxSyncSystemFs.getCurrentExeFolder() ?: posixRealpath(".") ?: "."
}
