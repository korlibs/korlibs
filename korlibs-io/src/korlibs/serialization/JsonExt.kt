package korlibs.serialization

import korlibs.datastructure.*
import korlibs.io.dynamic.*
import korlibs.io.serialization.json.*

val Json.Context: JsonContext get() = JsonContext
val Json.Companion.Context: JsonContext get() = JsonContext

object JsonContext {
    val DEFAULT: Json = Json
    val FAST: Json = object : Json() {
        override val optimizeNumbers: Boolean = true
        override fun <T> createArrayList(capacity: Int): MutableList<T> = FastArrayList<T>()
        override fun createDoubleArrayList(doubles: MiniNumberArrayList): Any = DoubleArrayList(*doubles.toDoubleArray())
    }
}

fun Json.parse(s: String, context: Json = Context.DEFAULT): Any? = context.parse(s)
fun Json.parseFast(s: String): Any? = parse(s, JsonContext.FAST)

// Dyn variants
fun Json.parseDyn(s: String, context: Json = Context.DEFAULT): Dyn = parse(s, context).dyn
fun Json.parseFastDyn(s: String): Dyn = parseFast(s).dyn
fun Json.stringify(obj: Dyn, pretty: Boolean = false): String = stringify(obj.value, pretty)
