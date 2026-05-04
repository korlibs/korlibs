package korlibs.ffi.api

internal actual class TestMathFFIImpl actual constructor() : TestMathFFI {
    private val allTestMathFFIImplFunctions = loadTestMathFFIImplFunctions()

    private val testMathFFIImplSymbols = allTestMathFFIImplFunctions.symbols

    actual override fun cosf(v: Float): Float = testMathFFIImplSymbols.cosf(v)

    actual override fun malloc(size: Int): FFIPointer = denoPointerToFFIPointer(testMathFFIImplSymbols.malloc(size))

    actual override fun free(ptr: FFIPointer): Unit = testMathFFIImplSymbols.free(ffiPointerToDenoPointer(ptr))

    actual override fun qsort(
        base: FFIPointer,
        number: Int,
        width: Int,
        compare: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>,
    ): Unit =
        testMathFFIImplSymbols.qsort(ffiPointerToDenoPointer(base), number, width, run {
            val func = fun(__p0: dynamic, __p1: dynamic): dynamic {
                return compare.func(
                    denoPointerToFFIPointer(__p0),
                    denoPointerToFFIPointer(__p1)
                )
            }
            val callback = js("(new Deno.UnsafeCallback({ parameters: ['pointer', 'pointer'], result: 'i32' }, func))")
            compare.closer = { callback.close() }
            callback.pointer
        })

    actual override fun fopen(file: String, mode: String): FFIPointer =
        denoPointerToFFIPointer(testMathFFIImplSymbols.fopen(stringToDenoPointer(file), stringToDenoPointer(mode)))

    actual override fun fclose(ptr: FFIPointer): Unit = testMathFFIImplSymbols.fclose(ffiPointerToDenoPointer(ptr))

    actual override fun remove(file: String): Unit = testMathFFIImplSymbols.remove(stringToDenoPointer(file))

    actual override fun close() {
        allTestMathFFIImplFunctions.close()
    }
}

private fun stringToDenoPointer(str: String): dynamic = js("(Deno.UnsafePointer.of(new TextEncoder().encode(str)))")

private fun denoPointerToFFIPointer(v: dynamic): FFIPointer =
    FFIPointer(js("Deno.UnsafePointer.value(v)").toString().toLong())

private fun ffiPointerToDenoPointer(v: FFIPointer): dynamic {
    val vv = v.address.toString(); return js("Deno.UnsafePointer.create(BigInt(vv))")
}

private fun loadTestMathFFIImplFunctions() = js(
    """
  (typeof Deno === 'undefined') ? {} : Deno.dlopen(Deno.build.os === 'windows' ? 'msvcrt' : Deno.build.os === 'darwin' ? '/usr/lib/libSystem.dylib' : 'libm.so.6', {
    "cosf": { parameters: ['f32'], result: 'f32' },
    "malloc": { parameters: ['i32'], result: 'pointer' },
    "free": { parameters: ['pointer'], result: 'void' },
    "qsort": { parameters: ['pointer', 'i32', 'i32', 'function'], result: 'void' },
    "fopen": { parameters: ['pointer', 'pointer'], result: 'pointer' },
    "fclose": { parameters: ['pointer'], result: 'void' },
    "remove": { parameters: ['pointer'], result: 'void' },
  })
"""
)
