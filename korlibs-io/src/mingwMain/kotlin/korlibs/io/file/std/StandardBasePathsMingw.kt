@file:OptIn(ExperimentalForeignApi::class)

package korlibs.io.file.std

import kotlinx.cinterop.*

actual object StandardPaths : StandardBasePathsNative(), StandardPathsBase {
    override val executableFile: String get() = kotlinx.cinterop.memScoped {
        val maxSize = 4096
        val data = allocArray<kotlinx.cinterop.UShortVar>(maxSize + 1)
        platform.windows.GetModuleFileNameW(null, data.reinterpret(), maxSize.convert())
        data.toKString()
    }.replace('\\', '/')
}
