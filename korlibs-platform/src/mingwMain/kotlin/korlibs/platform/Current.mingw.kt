@file:OptIn(ExperimentalForeignApi::class)

package korlibs.platform

import kotlinx.cinterop.*
import platform.windows.*

internal actual val envs: Map<String, String> by lazy {
    val envs = GetEnvironmentStringsW() ?: return@lazy mapOf<String, String>()
    val lines = readStringsz(envs)
    FreeEnvironmentStringsW(envs)
    return@lazy lines.associate {
        val parts = it.split('=', limit = 2)
        parts[0] to parts.getOrElse(1) { parts[0] }
    }
}

private fun readStringsz(ptr: CPointer<WCHARVar>?): List<String> {
    if (ptr == null) return listOf()
    var n = 0
    var lastP = 0
    val out = arrayListOf<String>()
    while (true) {
        val c: Int = ptr[n++].toInt()
        if (c == 0) {
            val startPtr: CPointer<WCHARVar> = (ptr + lastP)!!
            val str = startPtr.toKString()
            if (str.isEmpty()) break
            out += str
            lastP = n
        }
    }
    return out
}

