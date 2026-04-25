package korlibs.js

fun jsEmptyObj(): dynamic = js("({})")

fun jsObjectOf(vararg pairs: Pair<String, Any?>): dynamic {
    val out = jsEmptyObj()
    for (pair in pairs) out[pair.first] = pair.second
    return out
}

fun jsObject(vararg pairs: Pair<String, Any?>): dynamic {
    val out = jsEmptyObj()
    for (pair in pairs) out[pair.first] = pair.second
    return out
}

fun Map<String, Any?>.toJsObject() = jsObject(*this.entries.map { it.key to it.value }.toTypedArray())

fun jsToObjectMap(obj: dynamic): Map<String, Any?>? {
    if (obj == null) return null
    val out = linkedMapOf<String, Any?>()
    val keys = jsObjectKeys(obj)
    for (n in 0 until keys.length) {
        val key = keys[n]
        out["$key"] = obj[key]
    }
    return out
}

fun jsNew(clazz: dynamic): dynamic = js("(new (clazz))()")
fun jsNew(clazz: dynamic, a0: dynamic): dynamic = js("(new (clazz))(a0)")
fun jsNew(clazz: dynamic, a0: dynamic, a1: dynamic): dynamic = js("(new (clazz))(a0, a1)")
fun jsNew(clazz: dynamic, a0: dynamic, a1: dynamic, a2: dynamic): dynamic = js("(new (clazz))(a0, a1, a2)")
fun jsEnsureNumber(v: dynamic): Number = js("(+v)")
fun jsEnsureInt(v: dynamic): Int = js("(v|0)")
fun jsEnsureString(v: dynamic): String = js("(String(v))")
fun jsObjectKeys(obj: dynamic): dynamic = js("(Object.keys(obj))")
fun jsObjectKeysArray(obj: dynamic): Array<String> = jsToArray(jsObjectKeys(obj)) as Array<String>
fun jsObjectToMap(obj: dynamic): Map<String, dynamic> = jsObjectKeysArray(obj).associate { it to obj[it] }
