@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.util

operator fun ByteArray.set(o: Int, v: Int) { this[o] = v.toByte() }
operator fun ByteArray.set(o: Int, v: Long) { this[o] = v.toByte() }
