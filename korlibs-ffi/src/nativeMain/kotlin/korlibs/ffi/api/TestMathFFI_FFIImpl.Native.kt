@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package korlibs.ffi.api

import kotlinx.cinterop.*

// Checked-in native actual — KSP is not wired for native targets to avoid cross-platform
// inconsistencies (e.g. iOS targets being compiled on Linux where iOS KSP cannot run).
// If new @FFI interfaces are added, regenerate this file by running kspKotlinLinuxX64
// locally and copying the output here, then removing the generated file.

private fun COpaquePointer?.toFFIPointer(): FFIPointer = FFIPointer(this.rawValue.toLong())
private fun FFIPointer.toPointer(): COpaquePointer? = address.toCPointer()

private object __TestMathFFI_FFIImpl {
    val __LIB__ = FFIDLOpenPlatform(
        common  = "libm.so.6",
        linux   = "libm.so.6",
        macos   = "/usr/lib/libSystem.dylib",
        windows = "msvcrt",
    )

    val cosf by lazy {
        val n = "cosf"
        FFIDLSym(__LIB__, n)?.reinterpret<CFunction<(Float) -> Float>>()
            ?: error("Can't find $n")
    }
    val malloc by lazy {
        val n = "malloc"
        FFIDLSym(__LIB__, n)?.reinterpret<CFunction<(Int) -> COpaquePointer?>>()
            ?: error("Can't find $n")
    }
    val free by lazy {
        val n = "free"
        FFIDLSym(__LIB__, n)?.reinterpret<CFunction<(COpaquePointer?) -> Unit>>()
            ?: error("Can't find $n")
    }
    val qsort by lazy {
        val n = "qsort"
        FFIDLSym(__LIB__, n)
            ?.reinterpret<CFunction<(COpaquePointer?, Int, Int, CPointer<CFunction<(COpaquePointer?, COpaquePointer?) -> Int>>) -> Unit>>()
            ?: error("Can't find $n")
    }
    val fopen by lazy {
        val n = "fopen"
        FFIDLSym(__LIB__, n)?.reinterpret<CFunction<(CValues<ByteVar>, CValues<ByteVar>) -> COpaquePointer?>>()
            ?: error("Can't find $n")
    }
    val fclose by lazy {
        val n = "fclose"
        FFIDLSym(__LIB__, n)?.reinterpret<CFunction<(COpaquePointer?) -> Unit>>()
            ?: error("Can't find $n")
    }
    val remove by lazy {
        val n = "remove"
        FFIDLSym(__LIB__, n)?.reinterpret<CFunction<(CValues<ByteVar>) -> Unit>>()
            ?: error("Can't find $n")
    }
}

private fun Callback_qsort_compare_0(p0: COpaquePointer?, p1: COpaquePointer?): Int = fun_Callback_qsort_compare[0]!!.func(p0.toFFIPointer(), p1.toFFIPointer())
private fun Callback_qsort_compare_1(p0: COpaquePointer?, p1: COpaquePointer?): Int = fun_Callback_qsort_compare[1]!!.func(p0.toFFIPointer(), p1.toFFIPointer())
private fun Callback_qsort_compare_2(p0: COpaquePointer?, p1: COpaquePointer?): Int = fun_Callback_qsort_compare[2]!!.func(p0.toFFIPointer(), p1.toFFIPointer())
private fun Callback_qsort_compare_3(p0: COpaquePointer?, p1: COpaquePointer?): Int = fun_Callback_qsort_compare[3]!!.func(p0.toFFIPointer(), p1.toFFIPointer())

private val fun_Callback_qsort_compare = kotlin.arrayOfNulls<FFIFunctionRef<(FFIPointer, FFIPointer) -> Int>>(4)
private val ref_Callback_qsort_compare = listOf(
    staticCFunction(::Callback_qsort_compare_0),
    staticCFunction(::Callback_qsort_compare_1),
    staticCFunction(::Callback_qsort_compare_2),
    staticCFunction(::Callback_qsort_compare_3),
)
private fun alloc_Callback_qsort_compare(cfunc: FFIFunctionRef<(FFIPointer, FFIPointer) -> Int>) =
    ref_Callback_qsort_compare[cfunc.allocIn(fun_Callback_qsort_compare)]

internal actual class TestMathFFI_FFIImpl actual constructor() : TestMathFFI {
    actual override fun cosf(v: Float): Float =
        __TestMathFFI_FFIImpl.cosf(v)
    actual override fun malloc(size: Int): FFIPointer =
        __TestMathFFI_FFIImpl.malloc(size).toFFIPointer()
    actual override fun free(ptr: FFIPointer): Unit =
        __TestMathFFI_FFIImpl.free(ptr.toPointer())
    actual override fun qsort(base: FFIPointer, number: Int, width: Int, compare: FFIFunctionRef<(FFIPointer, FFIPointer) -> Int>): Unit =
        __TestMathFFI_FFIImpl.qsort(base.toPointer(), number, width, alloc_Callback_qsort_compare(compare))
    actual override fun fopen(file: String, mode: String): FFIPointer =
        __TestMathFFI_FFIImpl.fopen(file.cstr, mode.cstr).toFFIPointer()
    actual override fun fclose(ptr: FFIPointer): Unit =
        __TestMathFFI_FFIImpl.fclose(ptr.toPointer())
    actual override fun remove(file: String): Unit =
        __TestMathFFI_FFIImpl.remove(file.cstr)
    actual override fun close(): Unit = Unit
}

