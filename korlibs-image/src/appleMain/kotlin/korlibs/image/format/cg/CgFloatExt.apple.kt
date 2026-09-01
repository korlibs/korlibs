@file:OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)

package korlibs.image.format.cg

import kotlinx.cinterop.DoubleVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.FloatVar
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.set
import kotlinx.cinterop.sizeOf
import kotlinx.cinterop.value
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGFloatVar

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
