package korlibs.io.util

import java.io.File
import java.net.URL
import java.nio.file.Path
import korlibs.io.lang.Environment
import korlibs.platform.Platform
import kotlin.io.path.exists
import kotlin.io.path.toPath

private val LOGGER = korlibs.logger.Logger("JvmFsCaseSensitive")

internal fun URL.toFileOrNull(): File? {
    if (this.protocol != "file") return null
    return this.toURI().toPath().toFile()
}

fun URL.caseSensitiveOrNull(): URL? {
    if (this.protocol == "file") {
        val file = this.toURI().toPath().toFile()
        val matchesCaseSensitive = file.matchesCaseSensitive
        if (!matchesCaseSensitive) return null
    }
    return this
}

val File.matchesCaseSensitive: Boolean get() {
    if (!this.exists()) return true
    if (keepOsFsCaseSensitivity) return true
    if (Platform.isLinux) return true
    val canonicalFile = this.canonicalFile
    val matchesCase = canonicalFile.name == this.name
    if (!matchesCase) {
        LOGGER.info { "File $canonicalFile doesn't match $this" }
    }
    return matchesCase
}
fun File.caseSensitiveOrNull(): File? = this.takeIf { it.matchesCaseSensitive }
fun File.caseSensitiveOrThrow(): File = caseSensitiveOrNull() ?: throw NoSuchFileException(this)
fun File.existsCaseSensitive(): Boolean = this.exists() && this.matchesCaseSensitive

val Path.matchesCaseSensitive: Boolean get() = this.toFile().matchesCaseSensitive
fun Path.caseSensitiveOrNull(): Path? = this.toFile().caseSensitiveOrNull()?.toPath()
fun Path.caseSensitiveOrThrow(): Path? = this.toFile().caseSensitiveOrThrow().toPath()
fun Path.existsCaseSensitive(): Boolean = this.exists() && this.matchesCaseSensitive

val keepOsFsCaseSensitivity by lazy { Environment["KEEP_OS_FS_CASE_SENSITIVITY"] == "true" }
