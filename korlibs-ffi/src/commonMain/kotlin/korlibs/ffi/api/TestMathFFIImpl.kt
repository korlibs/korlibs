package korlibs.ffi.api

internal expect class TestMathFFIImpl() : TestMathFFI {
    override fun cosf(v: Float): Float

    override fun malloc(size: Int): FFIPointer

    override fun free(ptr: FFIPointer)

    override fun qsort(
        base: FFIPointer,
        number: Int,
        width: Int,
        compare: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>,
    )

    override fun fopen(file: String, mode: String): FFIPointer

    override fun fclose(ptr: FFIPointer)

    override fun remove(file: String)

    override fun close()
}
