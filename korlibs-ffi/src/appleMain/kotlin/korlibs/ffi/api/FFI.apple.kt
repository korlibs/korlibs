@file:OptIn(ExperimentalForeignApi::class)
package korlibs.ffi.api

import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.rawValue
import kotlinx.cinterop.toCPointer

fun COpaquePointer?.toFFIPointer(): FFIPointer = FFIPointer(this.rawValue.toLong())

fun FFIPointer.toPointer(): COpaquePointer? = address.toCPointer()
