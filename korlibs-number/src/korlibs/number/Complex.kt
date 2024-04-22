package korlibs.number

/**
 * Complex numbers: a + bi
 *
 * <https://en.wikipedia.org/wiki/Complex_number>
 */
data class Complex(val a: Float, val b: Float) {
    companion object {
        val I = Complex(0f, 1f)
    }
}

class ComplexArray(internal val raw: FloatArray) {
    companion object {
        operator fun invoke(size: Int, gen: (Int) -> Complex = { Complex(0f, 0f) }): ComplexArray {
            return ComplexArray(FloatArray(size)).also {
                for (n in 0 until size) it[n] = gen(n)
            }
        }
    }

    val size: Int get() = raw.size / 2

    private fun getAIndex(index: Int): Int = index * 2 + 0
    private fun getBIndex(index: Int): Int = index * 2 + 1

    fun getA(index: Int): Float = raw[getAIndex(index)]
    fun getB(index: Int): Float = raw[getBIndex(index)]

    fun setA(index: Int, value: Float) { raw[getAIndex(index)] = value }
    fun setB(index: Int, value: Float) { raw[getBIndex(index)] = value }

    operator fun get(index: Int): Complex = Complex(getA(index), getB(index))
    operator fun set(index: Int, value: Complex) {
        setA(index, value.a)
        setB(index, value.b)
    }

    fun asFloatArray(): FloatArray = raw
}

fun arraycopy(src: ComplexArray, srcPos: Int, dst: ComplexArray, dstPos: Int, size: Int) {
    src.raw.copyInto(dst.raw, dstPos * 2, srcPos * 2, srcPos * 2 + size * 2)
}
