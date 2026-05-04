package korlibs.ffi.api

import com.sun.jna.Callback
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.Pointer.createConstant
import com.sun.jna.Pointer.nativeValue

internal actual class TestMathFFIImpl actual constructor() : TestMathFFI {
  actual override fun cosf(v: Float): Float = TestMathFFIImplFunctions.cosf(v)

  actual override fun malloc(size: Int): FFIPointer = TestMathFFIImplFunctions.malloc(size).toFFIPointer()

  actual override fun free(ptr: FFIPointer): Unit = TestMathFFIImplFunctions.free(ptr.toPointer())

  actual override fun qsort(
    base: FFIPointer,
    number: Int,
    width: Int,
    compare: FFIFunctionRef<Function2<FFIPointer, FFIPointer, Int>>,
  ) = TestMathFFIImplFunctions.qsort(
      base = base.toPointer(),
      number = number,
      width = width,
      compare = { p0, p1 -> compare.func(p0.toFFIPointer(), p1.toFFIPointer()) },
  )

  actual override fun fopen(file: String, mode: String): FFIPointer = TestMathFFIImplFunctions.fopen(file, mode).toFFIPointer()

  actual override fun fclose(ptr: FFIPointer): Unit = TestMathFFIImplFunctions.fclose(ptr.toPointer())

  actual override fun remove(file: String): Unit = TestMathFFIImplFunctions.remove(file)

  actual override fun close() = Unit
}

private inline fun com.sun.jna.Pointer.toFFIPointer() = FFIPointer(nativeValue(this))

private inline fun FFIPointer.toPointer() = createConstant(this.address)

private object TestMathFFIImplFunctions : Library {
  external fun cosf(v: Float): Float

  external fun malloc(size: Int): com.sun.jna.Pointer

  external fun free(ptr: com.sun.jna.Pointer)

  external fun qsort(base: com.sun.jna.Pointer, number: Int, width: Int, compare: CallbackQsortCompare)

  external fun fopen(file: String, mode: String): com.sun.jna.Pointer

  external fun fclose(ptr: com.sun.jna.Pointer)

  external fun remove(file: String)

  init {
    when {
      Platform.isWindows() -> Native.register("msvcrt")
      Platform.isMac() -> Native.register("/usr/lib/libSystem.dylib")
      else -> Native.register("libm.so.6")
    }
  }
}

private fun interface CallbackQsortCompare : Callback {
  fun callback(__p0: com.sun.jna.Pointer, __p1: com.sun.jna.Pointer): Int
}
