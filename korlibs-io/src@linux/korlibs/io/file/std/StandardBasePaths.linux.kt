package korlibs.io.file.std

import korlibs.io.core.*
import korlibs.io.posix.*

actual object StandardPaths : StandardBasePathsNative(), StandardPathsBase {
    override val executableFile: String get() = LinuxSyncSystemFS.getCurrentExe() ?: "./a.out"
    override val executableFolder: String get() = LinuxSyncSystemFS.getCurrentExeFolder() ?: posixRealpath(".") ?: "."
}
