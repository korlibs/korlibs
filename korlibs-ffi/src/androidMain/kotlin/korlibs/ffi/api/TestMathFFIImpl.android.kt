package korlibs.ffi.api

internal actual class TestMathFFIImpl actual constructor() : TestMathFFI {

  actual override fun cosf(v: Float): Float = TODO()

  actual override fun malloc(size: Int): FFIPointer = TODO()

  actual override fun free(ptr: FFIPointer): Unit = TODO()

  actual override fun qsort(
    base: FFIPointer,
    number: Int,
    width: Int,
    compare: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>,
  ): Unit = TODO()

  actual override fun fopen(file: String, mode: String): FFIPointer = TODO()

  actual override fun fclose(ptr: FFIPointer): Unit = TODO()

  actual override fun remove(file: String): Unit = TODO()

  actual override fun close() = Unit
}
