package korlibs.serialization

import korlibs.io.dynamic.*
import korlibs.io.serialization.json.*
import korlibs.io.serialization.json.Json.Context

fun Json.parseDyn(s: String, context: Context = Context.DEFAULT): Dyn = parse(s, context).dyn
fun Json.parseFastDyn(s: String): Dyn = parseFast(s).dyn

fun Json.stringify(obj: Dyn, pretty: Boolean = false): String = stringify(obj.value, pretty)

