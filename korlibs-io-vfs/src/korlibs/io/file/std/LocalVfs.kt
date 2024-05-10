package korlibs.io.file.std

import korlibs.io.file.*

abstract class LocalVfs : Vfs() {
    companion object {}

    override suspend fun getAttributes(path: String): List<Attribute> {
        val stat = stat(path)
        if (!stat.exists) return emptyList()
        return listOf(UnixPermissions(stat.mode))
    }

    override fun toString(): String = "LocalVfs"
}
