@file:OptIn(UnsafeNumber::class)

package korlibs.image.format.cg

import kotlinx.cinterop.*
import platform.CoreGraphics.*

// @TODO: K/N .convert() doesn't work to convert integers to doubles
@OptIn(UnsafeNumber::class)
expect inline fun Double.toCgFloat(): CGFloat
@OptIn(UnsafeNumber::class)
expect inline fun Float.toCgFloat(): CGFloat
