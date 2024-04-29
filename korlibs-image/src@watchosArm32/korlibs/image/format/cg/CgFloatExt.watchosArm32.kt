@file:OptIn(UnsafeNumber::class)

package korlibs.image.format.cg

import kotlinx.cinterop.*
import platform.CoreGraphics.*

// @TODO: K/N .convert() doesn't work to convert integers to doubles
@OptIn(UnsafeNumber::class)
actual inline fun Double.toCgFloat(): CGFloat = this.toFloat()
@OptIn(UnsafeNumber::class)
actual inline fun Float.toCgFloat(): CGFloat = this.toFloat()
