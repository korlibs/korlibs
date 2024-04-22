package korlibs.wasm

fun JsAny.toMap(): Map<String, JsAny?> = this.keys().associateWith { this[it] }
fun JsAny.keys(): List<String> = jsObjectKeys(this).toList().map { it.toString() }
operator fun JsAny.get(key: String): JsAny? = jsObjectGet(this, key.toJsString())
//operator fun JsAny.get(key: Int): JsAny? = jsObjectGet(this, key.toJsNumber())
operator fun JsAny.set(key: String, value: JsAny?) = jsObjectSet(this, key.toJsString(), value)
//operator fun JsAny.set(key: Int, value: JsAny?) = jsObjectSet(this, key.toJsNumber(), value)

fun <T : JsAny> JsArray<T>.toList(): List<T> = List<T>(this.length) { this[it]!! }

@JsFun("(obj, key) => { return obj ? obj[key] : null; }") @Suppress("UNUSED_PARAMETER")
private external fun jsObjectGet(obj: JsAny, key: JsAny?): JsAny?

@JsFun("(obj, key, value) => { if (obj) obj[key] = value }") @Suppress("UNUSED_PARAMETER")
private external fun jsObjectSet(obj: JsAny, key: JsAny?, value: JsAny?): JsAny?

@JsFun("(obj) => { return Object.keys(obj); }") @Suppress("UNUSED_PARAMETER")
private external fun jsObjectKeys(obj: JsAny?): JsArray<JsString>
