@file:OptIn(ExperimentalForeignApi::class)

package korlibs.platform

import kotlinx.cinterop.*

internal actual val envs: Map<String, String> by lazy {
    val out = LinkedHashMap<String, String>()
    val env = platform.posix.__environ
    var n = 0
    while (true) {
        val line = env?.get(n++)?.toKString()
        if (line.isNullOrBlank()) break
        val parts = line.split('=', limit = 2)
        out[parts[0]] = parts.getOrElse(1) { parts[0] }
    }
    return@lazy out
}
