@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.util

private inline fun <TArray, T> _indexOf(array: TArray, access: (TArray, Int) -> T, v: T, startOffset: Int, endOffset: Int, default: Int): Int {
    for (n in startOffset until endOffset) if (access(array, n) == v) return n
    return default
}

// IndexOf in range
fun BooleanArray.indexOf(v: Boolean, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
fun ByteArray.indexOf(v: Byte, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
fun ShortArray.indexOf(v: Short, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
//fun CharArray.indexOf(v: Char, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
fun IntArray.indexOf(v: Int, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
fun LongArray.indexOf(v: Long, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
fun FloatArray.indexOf(v: Float, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
fun DoubleArray.indexOf(v: Double, startOffset: Int = 0, endOffset: Int = this.size, default: Int = -1): Int = _indexOf(this, { a, b -> a[b] }, v, startOffset, endOffset, default)
