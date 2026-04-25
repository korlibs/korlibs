@file:OptIn(ExperimentalForeignApi::class)
package korlibs.ffi.api

import kotlinx.cinterop.*

internal actual class TestMathFFI_FFIImpl actual constructor() : korlibs.ffi.api.TestMathFFI {
    actual override fun cosf(v: Float): Float = __TestMathFFI_FFIImpl.cosf(v)
    actual override fun malloc(size: Int): FFIPointer = __TestMathFFI_FFIImpl.malloc(size).toFFIPointer()
    actual override fun free(ptr: FFIPointer): Unit = __TestMathFFI_FFIImpl.free(ptr.toPointer())
    actual override fun qsort(base: FFIPointer, number: Int, width: Int, compare: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>): Unit = __TestMathFFI_FFIImpl.qsort(base.toPointer(), number, width, alloc_Callback_qsort_compare(compare))
    actual override fun fopen(file: String, mode: String): FFIPointer = __TestMathFFI_FFIImpl.fopen(file.cstr, mode.cstr).toFFIPointer()
    actual override fun fclose(ptr: FFIPointer): Unit = __TestMathFFI_FFIImpl.fclose(ptr.toPointer())
    actual override fun remove(file: String): Unit = __TestMathFFI_FFIImpl.remove(file.cstr)
    actual override fun close() = Unit
}
fun COpaquePointer?.toFFIPointer(): FFIPointer = FFIPointer(this.rawValue.toLong())
fun FFIPointer.toPointer(): COpaquePointer? = address.toCPointer()
private object __TestMathFFI_FFIImpl {
    val __LIB__ = korlibs.ffi.api.FFIDLOpenPlatform(common = "libm.so.6", linux = "libm.so.6", macos = "/usr/lib/libSystem.dylib", windows = "msvcrt")
    val cosf by lazy { val funcName = "cosf"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(Float) -> Float>>() ?: error("Can't find $funcName") }
    val malloc by lazy { val funcName = "malloc"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(Int) -> COpaquePointer?>>() ?: error("Can't find $funcName") }
    val free by lazy { val funcName = "free"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(COpaquePointer?) -> Unit>>() ?: error("Can't find $funcName") }
    val qsort by lazy { val funcName = "qsort"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(COpaquePointer?, Int, Int, CPointer<CFunction<(COpaquePointer?, COpaquePointer?) -> Int>>) -> Unit>>() ?: error("Can't find $funcName") }
    val fopen by lazy { val funcName = "fopen"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(CValues<ByteVar>, CValues<ByteVar>) -> COpaquePointer?>>() ?: error("Can't find $funcName") }
    val fclose by lazy { val funcName = "fclose"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(COpaquePointer?) -> Unit>>() ?: error("Can't find $funcName") }
    val remove by lazy { val funcName = "remove"; korlibs.ffi.api.FFIDLSym(__LIB__, funcName)?.reinterpret<CFunction<(CValues<ByteVar>) -> Unit>>() ?: error("Can't find $funcName") }
}
private fun Callback_qsort_compare_0(__p0: COpaquePointer?, __p1: COpaquePointer?): Int = fun_Callback_qsort_compare[0]!!.func(__p0.toFFIPointer(), __p1.toFFIPointer())
private fun Callback_qsort_compare_1(__p0: COpaquePointer?, __p1: COpaquePointer?): Int = fun_Callback_qsort_compare[1]!!.func(__p0.toFFIPointer(), __p1.toFFIPointer())
private fun Callback_qsort_compare_2(__p0: COpaquePointer?, __p1: COpaquePointer?): Int = fun_Callback_qsort_compare[2]!!.func(__p0.toFFIPointer(), __p1.toFFIPointer())
private fun Callback_qsort_compare_3(__p0: COpaquePointer?, __p1: COpaquePointer?): Int = fun_Callback_qsort_compare[3]!!.func(__p0.toFFIPointer(), __p1.toFFIPointer())
private val fun_Callback_qsort_compare = kotlin.arrayOfNulls<FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>>(4)
private val ref_Callback_qsort_compare = listOf(staticCFunction(::Callback_qsort_compare_0), staticCFunction(::Callback_qsort_compare_1), staticCFunction(::Callback_qsort_compare_2), staticCFunction(::Callback_qsort_compare_3))
private fun alloc_Callback_qsort_compare(cfunc: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>) = ref_Callback_qsort_compare[cfunc.allocIn(fun_Callback_qsort_compare)]
