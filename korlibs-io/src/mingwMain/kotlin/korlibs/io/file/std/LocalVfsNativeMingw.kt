@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.file.std

import korlibs.io.async.CIO
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kotlinx.cinterop.wcstr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import platform.posix._wclosedir
import platform.posix._wopendir
import platform.posix._wreaddir

actual open class LocalVfsNative actual constructor(async: Boolean) : LocalVfsNativeBase(async) {
    override suspend fun listFlow(path: String) = flow {
        val dir = memScoped { _wopendir(resolve(path).wcstr) }
        if (dir != null) {
            try {
                while (true) {
                    val dent = _wreaddir(dir) ?: break
                    val name = dent.pointed.d_name.toKString()
                    if (name != "." && name != "..") {
                        emit(file("$path/$name"))
                    }
                }
            } finally {
                _wclosedir(dir)
            }
        }
    }.flowOn(Dispatchers.CIO)
}
