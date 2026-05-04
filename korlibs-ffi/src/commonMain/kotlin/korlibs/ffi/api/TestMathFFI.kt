package korlibs.ffi.api

internal interface TestMathFFI : AutoCloseable {
    fun cosf(v: Float): Float

    fun malloc(size: Int): FFIPointer

    fun free(ptr: FFIPointer)

    fun qsort(
        base: FFIPointer,
        number: Int,
        width: Int,
        compare: FFIFunctionRef<(FFIPointer, FFIPointer) -> Int>,
    )

    fun fopen(file: String, mode: String): FFIPointer

    fun fclose(ptr: FFIPointer)

    fun remove(file: String)

    companion object {
        operator fun invoke(): TestMathFFI = TestMathFFIImpl()
    }
}
