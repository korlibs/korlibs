@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.util

inline operator fun ByteArray.set(o: Int, v: Int) { this[o] = v.toByte() }
inline operator fun ByteArray.set(o: Int, v: Long) { this[o] = v.toByte() }
