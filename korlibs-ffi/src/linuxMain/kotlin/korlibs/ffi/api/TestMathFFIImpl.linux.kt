@file:OptIn(ExperimentalForeignApi::class)

package korlibs.ffi.api

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValues
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.rawValue
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toCPointer

internal actual class TestMathFFIImpl actual constructor() : TestMathFFI {
    actual override fun cosf(v: Float): Float = TestMathFFIImplFunctions.cosf(v)

    actual override fun malloc(size: Int): FFIPointer = TestMathFFIImplFunctions.malloc(size).toFFIPointer()

    actual override fun free(ptr: FFIPointer): Unit = TestMathFFIImplFunctions.free(ptr.toPointer())

    actual override fun qsort(
        base: FFIPointer,
        number: Int,
        width: Int,
        compare: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>,
    ): Unit = TestMathFFIImplFunctions.qsort(
        p1 = base.toPointer(),
        p2 = number,
        p3 = width,
        p4 = allocCallbackQsortCompare(compare),
    )

    actual override fun fopen(file: String, mode: String): FFIPointer =
        TestMathFFIImplFunctions.fopen(file.cstr, mode.cstr).toFFIPointer()

    actual override fun fclose(ptr: FFIPointer): Unit = TestMathFFIImplFunctions.fclose(ptr.toPointer())

    actual override fun remove(file: String): Unit = TestMathFFIImplFunctions.remove(file.cstr)

    actual override fun close() = Unit
}

fun COpaquePointer?.toFFIPointer(): FFIPointer = FFIPointer(this.rawValue.toLong())

fun FFIPointer.toPointer(): COpaquePointer? = address.toCPointer()

private object TestMathFFIImplFunctions {
    val library = FFIDLOpen("libm.so.6")

    val cosf by lazy {
        FFIDLSym(library, "cosf")?.reinterpret<CFunction<(Float) -> Float>>() ?: error("Can't find cosf")
    }

    val malloc by lazy {
        FFIDLSym(library, "malloc")?.reinterpret<CFunction<(Int) -> COpaquePointer?>>() ?: error("Can't find malloc")
    }

    val free by lazy {
        FFIDLSym(library, "free")?.reinterpret<CFunction<(COpaquePointer?) -> Unit>>() ?: error("Can't find free")
    }

    val qsort by lazy {
        FFIDLSym(
            library,
            "qsort"
        )?.reinterpret<CFunction<(COpaquePointer?, Int, Int, CPointer<CFunction<(COpaquePointer?, COpaquePointer?) -> Int>>) -> Unit>>()
            ?: error("Can't find qsort")
    }

    val fopen by lazy {
        FFIDLSym(library, "fopen")?.reinterpret<CFunction<(CValues<ByteVar>, CValues<ByteVar>) -> COpaquePointer?>>()
            ?: error("Can't find fopen")
    }

    val fclose by lazy {
        FFIDLSym(library, "fclose")?.reinterpret<CFunction<(COpaquePointer?) -> Unit>>() ?: error("Can't find fclose")
    }

    val remove by lazy {
        FFIDLSym(library, "remove")?.reinterpret<CFunction<(CValues<ByteVar>) -> Unit>>() ?: error("Can't find remove")
    }
}

private fun callbackQsortCompare0(p0: COpaquePointer?, p1: COpaquePointer?): Int =
    funCallbackQsortCompare[0]!!.func(p0.toFFIPointer(), p1.toFFIPointer())

private fun callbackQsortCompare1(p0: COpaquePointer?, p1: COpaquePointer?): Int =
    funCallbackQsortCompare[1]!!.func(p0.toFFIPointer(), p1.toFFIPointer())

private fun callbackQsortCompare2(p0: COpaquePointer?, p1: COpaquePointer?): Int =
    funCallbackQsortCompare[2]!!.func(p0.toFFIPointer(), p1.toFFIPointer())

private fun callbackQsortCompare3(p0: COpaquePointer?, p1: COpaquePointer?): Int =
    funCallbackQsortCompare[3]!!.func(p0.toFFIPointer(), p1.toFFIPointer())

private val funCallbackQsortCompare = arrayOfNulls<FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>>(4)

private val refCallbackQsortCompare = listOf(
    staticCFunction(::callbackQsortCompare0),
    staticCFunction(::callbackQsortCompare1),
    staticCFunction(::callbackQsortCompare2),
    staticCFunction(::callbackQsortCompare3),
)

private fun allocCallbackQsortCompare(cfunc: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>) =
    refCallbackQsortCompare[cfunc.allocIn(funCallbackQsortCompare)]
