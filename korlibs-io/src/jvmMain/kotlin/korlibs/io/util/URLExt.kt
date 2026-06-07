package korlibs.io.util

import java.net.URL
import korlibs.io.file.PathInfo
import korlibs.io.file.baseName

val URL.basename: String get() = PathInfo(this.file).baseName
