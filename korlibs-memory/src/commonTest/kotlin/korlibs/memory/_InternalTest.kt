package korlibs.memory

/** Reverses the bytes of [this] [Short]: AABB -> BBAA */
public fun Short.reverseBytes(): Short {
    val low = ((this.toInt() ushr 0) and 0xFF)
    val high = ((this.toInt() ushr 8) and 0xFF)
    return ((high and 0xFF) or (low shl 8)).toShort()
}

/** Reverses the bytes of [this] [Int]: AABBCCDD -> DDCCBBAA */
public fun Int.reverseBytes(): Int {
    val v0 = ((this ushr 0) and 0xFF)
    val v1 = ((this ushr 8) and 0xFF)
    val v2 = ((this ushr 16) and 0xFF)
    val v3 = ((this ushr 24) and 0xFF)
    return (v0 shl 24) or (v1 shl 16) or (v2 shl 8) or (v3 shl 0)
}

/** Reverses the bytes of [this] [Long]: AABBCCDDEEFFGGHH -> HHGGFFEEDDCCBBAA */
public fun Long.reverseBytes(): Long {
    val v0 = (this ushr 0).toInt().reverseBytes().toLong() and 0xFFFFFFFFL
    val v1 = (this ushr 32).toInt().reverseBytes().toLong() and 0xFFFFFFFFL
    return (v0 shl 32) or (v1 shl 0)
}


private val formatRegex = Regex("%([-]?\\d+)?(\\w)")

fun String.format(vararg params: Any): String {
    var paramIndex = 0
    return formatRegex.replace(this) { mr ->
        val param = params[paramIndex++]
        //println("param: $param")
        val size = mr.groupValues[1]
        val type = mr.groupValues[2]
        val str = when (type) {
            "d" -> (param as Number).toLong().toString()
            "X", "x" -> {
                val res = when (param) {
                    is Int -> param.toUInt().toString(16)
                    else -> (param as Number).toLong().toULong().toString(16)
                }
                if (type == "X") res.uppercase() else res.lowercase()
            }
            else -> "$param"
        }
        val prefix = if (size.startsWith('0')) '0' else ' '
        val asize = size.toIntOrNull()
        var str2 = str
        if (asize != null) {
            while (str2.length < asize) {
                str2 = prefix + str2
            }
        }
        str2
    }
}
