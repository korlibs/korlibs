@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.serialization.json

import korlibs.datastructure.DoubleArrayList
import korlibs.datastructure.FastArrayList
import korlibs.datastructure.iterators.fastForEach
import korlibs.io.util.NumberParser
import korlibs.util.*
import kotlin.collections.set

object Json {
    fun parse(s: String, context: Context = Context.DEFAULT): Any? = parse(SimpleStrReader(s), context)
    fun parseFast(s: String): Any? = parse(SimpleStrReader(s), Context.FAST)

    fun stringify(obj: Any?, pretty: Boolean = false): String = when {
        pretty -> SimpleIndenter().apply { stringifyPretty(obj, this) }.toString()
        else -> StringBuilder().apply { stringify(obj, this) }.toString()
    }

    interface CustomSerializer {
        fun encodeToJson(b: StringBuilder)
    }

    data class Context(val optimizedNumericLists: Boolean, val useFastArrayList: Boolean = false) {
        companion object {
            val DEFAULT = Context(optimizedNumericLists = false, useFastArrayList = false)
            val FAST = Context(optimizedNumericLists = true, useFastArrayList = true)
        }

        fun <T> createArrayList(): MutableList<T> {
            return if (useFastArrayList) FastArrayList() else ArrayList()
        }
    }

    fun parse(s: SimpleStrReader, context: Context = Context.DEFAULT): Any? = when (val ic = s.skipSpaces().peekChar()) {
        '{' -> parseObject(s, context)
        '[' -> parseArray(s, context)
        //'-', '+', in '0'..'9' -> { // @TODO: Kotlin native doesn't optimize char ranges
        '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
            val dres = parseNumber(s)
            if (dres.toInt().toDouble() == dres) dres.toInt() else dres
        }
        't' -> true.also { s.expect("true") }
        'f' -> false.also { s.expect("false") }
        'n' -> null.also { s.expect("null") }
        'u' -> null.also { s.expect("undefined") }
        '"' -> s.readStringLit().toString()
        else -> invalidJson("Not expected '$ic' in $s")
    }

    private fun parseObject(s: SimpleStrReader, context: Context = Context.DEFAULT): Map<String, Any?> {
        s.skipExpect('{')
        return LinkedHashMap<String, Any?>().apply {
            obj@ while (true) {
                when (s.skipSpaces().peekChar()) {
                    '}' -> { s.readChar(); break@obj }
                    ',' -> { s.readChar(); continue@obj }
                    else -> Unit
                }
                val key = parse(s, context) as String
                s.skipSpaces().skipExpect(':')
                val value = parse(s, context)
                this[key] = value
            }
        }
    }

    private fun parseArray(s: SimpleStrReader, context: Context = Context.DEFAULT): Any {
        var out: MutableList<Any?>? = null
        var outNumber: DoubleArrayList? = null
        s.skipExpect('[')
        array@ while (true) {
            when (s.skipSpaces().peekChar()) {
                ']' -> { s.readChar(); break@array }
                ',' -> { s.readChar(); continue@array }
                else -> Unit
            }
            val v = s.peekChar()
            if (out == null && context.optimizedNumericLists && (v in '0'..'9' || v == '-')) {
                if (outNumber == null) {
                    outNumber = DoubleArrayList()
                }
                outNumber.add(parseNumber(s))
            } else {
                if (out == null) out = context.createArrayList()
                if (outNumber != null) {
                    outNumber.fastForEach { out.add(it) }
                    outNumber = null
                }
                out.add(parse(s, context))
            }
        }
        return outNumber ?: out ?: context.createArrayList<Any?>()
    }

    private fun Char.isNumberStart() = when (this) {
        '-', '+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true
        else -> false
    }

    private fun parseNumber(s: SimpleStrReader): Double = NumberParser.parseDouble {
        val c = s.peekChar()
        val isC = ((c >= '0') && (c <= '9')) || c == '.' || c == 'e' || c == 'E' || c == '-' || c == '+'
        if (isC) s.readChar()
        if (isC) c else '\u0000'
    }

    fun stringify(obj: Any?, b: StringBuilder) {
        when (obj) {
            null -> b.append("null")
            is Boolean -> b.append(if (obj) "true" else "false")
            is Map<*, *> -> {
                b.append('{')
                for ((i, v) in obj.entries.withIndex()) {
                    if (i != 0) b.append(',')
                    stringify(v.key, b)
                    b.append(':')
                    stringify(v.value, b)
                }
                b.append('}')
            }
            is Iterable<*> -> {
                b.append('[')
                for ((i, v) in obj.withIndex()) {
                    if (i != 0) b.append(',')
                    stringify(v, b)
                }
                b.append(']')
            }
            is Enum<*> -> encodeString(obj.name, b)
            is String -> encodeString(obj, b)
            is Number -> b.append("$obj")
            is CustomSerializer -> obj.encodeToJson(b)
            else -> throw IllegalArgumentException("Don't know how to serialize $obj") //encode(ClassFactory(obj::class).toMap(obj), b)
        }
    }

    fun stringifyPretty(obj: Any?, b: SimpleIndenter) {
        when (obj) {
            null -> b.inline("null")
            is Boolean -> b.inline(if (obj) "true" else "false")
            is Map<*, *> -> {
                b.line("{")
                b.indent {
                    val entries = obj.entries
                    for ((i, v) in entries.withIndex()) {
                        if (i != 0) b.line(",")
                        b.inline(encodeString("" + v.key))
                        b.inline(": ")
                        stringifyPretty(v.value, b)
                        if (i == entries.size - 1) b.line("")
                    }
                }
                b.inline("}")
            }
            is Iterable<*> -> {
                b.line("[")
                b.indent {
                    val entries = obj.toList()
                    for ((i, v) in entries.withIndex()) {
                        if (i != 0) b.line(",")
                        stringifyPretty(v, b)
                        if (i == entries.size - 1) b.line("")
                    }
                }
                b.inline("]")
            }
            is String -> b.inline(encodeString(obj))
            is Number -> b.inline("$obj")
            is CustomSerializer -> b.inline(StringBuilder().apply { obj.encodeToJson(this) }.toString())
            else -> {
                throw IllegalArgumentException("Don't know how to serialize $obj")
                //encode(ClassFactory(obj::class).toMap(obj), b)
            }
        }
    }

    private fun encodeString(str: String) = StringBuilder().apply { encodeString(str, this) }.toString()

    private fun encodeString(str: String, b: StringBuilder) {
        b.append('"')
        for (c in str) {
            when (c) {
                '\\' -> b.append("\\\\"); '/' -> b.append("\\/"); '\'' -> b.append("\\'")
                '"' -> b.append("\\\""); '\b' -> b.append("\\b"); '\u000c' -> b.append("\\f")
                '\n' -> b.append("\\n"); '\r' -> b.append("\\r"); '\t' -> b.append("\\t")
                else -> b.append(c)
            }
        }
        b.append('"')
    }

    private fun invalidJson(msg: String = "Invalid JSON"): Nothing = throw IllegalArgumentException(msg)

    fun SimpleStrReader.readStringLit(reportErrors: Boolean = true, out: StringBuilder = StringBuilder()): StringBuilder {
        val quotec = readChar()
        when (quotec) {
            '"', '\'' -> Unit
            else -> throw IllegalArgumentException("Invalid string literal")
        }
        var closed = false
        loop@ while (hasMore) {
            when (val c = readChar()) {
                '\\' -> {
                    val cc = readChar()
                    val c: Char = when (cc) {
                        '\\' -> '\\'; '/' -> '/'; '\'' -> '\''; '"' -> '"'
                        'b' -> '\b'; 'f' -> '\u000c'; 'n' -> '\n'; 'r' -> '\r'; 't' -> '\t'
                        'u' -> NumberParser.parseInt(radix = 16) { if (it >= 4) NumberParser.END else readChar() }.toChar()
                        else -> throw IllegalArgumentException("Invalid char '$cc'")
                    }
                    out.append(c)
                }
                quotec -> {
                    closed = true
                    break@loop
                }
                else -> out.append(c)
            }
        }
        if (!closed && reportErrors) {
            throw RuntimeException("String literal not closed! '${this}'")
        }
        return out
    }

    private fun SimpleStrReader.expect(str: String) {
        for (n in str.indices) {
            val c = readChar()
            if (c != str[n]) throw IllegalStateException("Expected '$str' but found '$c' at $n")
        }
    }

    private fun SimpleStrReader.skipSpaces(): SimpleStrReader {
        this.skipWhile { it.isWhitespaceFast() }
        return this
    }

    private inline fun SimpleStrReader.skipWhile(filter: (Char) -> Boolean) {
        while (hasMore && filter(this.peekChar())) {
            this.readChar()
        }
    }

    private fun SimpleStrReader.skipExpect(expected: Char) {
        val readed = this.readChar()
        if (readed != expected) throw IllegalArgumentException("Expected '$expected' but found '$readed' at $pos")
    }

    private fun Char.isWhitespaceFast(): Boolean = this == ' ' || this == '\t' || this == '\r' || this == '\n'
}

fun String.fromJson(): Any? = Json.parse(this)
fun Map<*, *>.toJson(pretty: Boolean = false): String = Json.stringify(this, pretty)
