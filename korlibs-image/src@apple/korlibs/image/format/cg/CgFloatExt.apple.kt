@file:OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)

package korlibs.image.format.cg

import kotlinx.cinterop.*
import platform.CoreGraphics.*

// @TODO: K/N .convert() doesn't work to convert integers to doubles
fun Double.toCgFloat(): CGFloat = memScoped {
    val value = alloc<CGFloatVar>()
    if (sizeOf<CGFloatVar>().toInt() == 4) {
        value.ptr.reinterpret<FloatVar>()[0] = this@toCgFloat.toFloat()
    } else {
        value.ptr.reinterpret<DoubleVar>()[0] = this@toCgFloat.toDouble()
    }
    value.value
}

fun Float.toCgFloat(): CGFloat = toDouble().toCgFloat()
