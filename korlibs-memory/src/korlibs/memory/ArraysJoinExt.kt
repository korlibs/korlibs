@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.util

import korlibs.memory.*

private inline fun <TArray> _join(items: List<TArray>, build: (Int) -> TArray, size: (TArray) -> Int, arraycopy: (TArray, Int, TArray, Int, Int) -> Unit): TArray {
    val out = build(items.sumOf { size(it) })
    var pos = 0
    items.fastForEach { c ->
        arraycopy(c, 0, out, pos, size(c))
        pos += size(c)
    }
    return out
}

fun List<BooleanArray>.join(): BooleanArray = _join(this, { BooleanArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })
fun List<ByteArray>.join(): ByteArray = _join(this, { ByteArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })
fun List<ShortArray>.join(): ShortArray = _join(this, { ShortArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })
//fun List<CharArray>.join(): CharArray = _join(this, { CharArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })
fun List<IntArray>.join(): IntArray = _join(this, { IntArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })
fun List<LongArray>.join(): LongArray = _join(this, { LongArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })
fun List<FloatArray>.join(): FloatArray = _join(this, { FloatArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })
fun List<DoubleArray>.join(): DoubleArray = _join(this, { DoubleArray(it) }, { it.size }, { a, b, c, d, e -> arraycopy(a, b, c, d, e) })

private inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
    var n = 0
    while (n < size) callback(this[n++])
}