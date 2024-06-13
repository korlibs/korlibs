@file:Suppress("PackageDirectoryMismatch")

package korlibs.io.serialization.xml

import korlibs.io.serialization.xml.internal.*
import korlibs.io.stream.*
import korlibs.io.util.*
import korlibs.util.*
import korlibs.util.CharReaderStrReader
import kotlin.collections.set

data class Xml(
    val type: Type,
    val name: String,
    val attributes: Map<String, String>,
    val allChildren: List<Xml>,
    val content: String
) {
    fun withExtraChild(node: Xml) = copy(allChildren = allChildren + node)

    val attributesLC: Map<String, String> = attributes.toCaseInsensitiveMap()
    val nameLC: String = name.lowercase().trim()
    val descendants: Sequence<Xml> get() = allChildren.asSequence().flatMap { it.descendants + it }
    val allChildrenNoComments get() = allChildren.filter { !it.isComment }
    val allNodeChildren get() = allChildren.filter { it.isNode }

    companion object {
        private const val NAME_RAW = "_raw_"
        private const val NAME_TEXT = "_text_"
        private const val NAME_CDATA = "_cdata_"
        private const val NAME_COMMENT = "_comment_"

        fun Tag(tagName: String, attributes: Map<String, Any?>, children: List<Xml>): Xml =
            Xml(Xml.Type.NODE, tagName, attributes.filter { it.value != null }.map { it.key to it.value.toString() }.toMap(), children, "")
        fun Raw(text: String): Xml = Xml(Xml.Type.TEXT, NAME_RAW, LinkedHashMap(), listOf(), text)
        fun Text(text: String): Xml = Xml(Xml.Type.TEXT, NAME_TEXT, LinkedHashMap(), listOf(), text)
        fun CData(text: String): Xml = Xml(Xml.Type.TEXT, NAME_CDATA, LinkedHashMap(), listOf(), text)
        fun Comment(text: String): Xml = Xml(Xml.Type.COMMENT, NAME_COMMENT, LinkedHashMap(), listOf(), text)

        //operator fun invoke(@Language("xml") str: String): Xml = parse(str)

        fun parse(str: String, collapseSpaces: Boolean = true, processNamespaces: Boolean = false): Xml {
            try {
                val stream = Xml.Stream.xmlSequence(str, collapseSpaces, processNamespaces).iterator()

                data class Level(val children: List<Xml>, val close: Xml.Stream.Element.CloseTag?)

                fun level(): Level {
                    val children = arrayListOf<Xml>()

                    var textNodes = 0
                    var endTag: Xml.Stream.Element.CloseTag? = null
                    loop@while (stream.hasNext()) {
                        when (val tag = stream.next()) {
                            is Xml.Stream.Element.ProcessingInstructionTag -> Unit
                            is Xml.Stream.Element.CommentTag -> children.add(Xml.Comment(tag.text))
                            is Xml.Stream.Element.Text -> children.add((if (tag.cdata) Xml.CData(tag.text) else Xml.Text(tag.text))).also { textNodes++ }
                            is Xml.Stream.Element.OpenCloseTag -> children.add(Xml.Tag(tag.name, tag.attributes, listOf()))
                            is Xml.Stream.Element.OpenTag -> {
                                val out = level()
                                if (out.close?.name?.equals(tag.name, ignoreCase = true) != true) {
                                    throw IllegalArgumentException("Expected '${tag.name}' but was ${out.close?.name}")
                                }
                                children.add(Xml(Xml.Type.NODE, tag.name, tag.attributes, out.children, ""))
                            }
                            is Xml.Stream.Element.CloseTag -> {
                                endTag = tag
                                break@loop
                            }
                        }
                    }

                    return Level(children.mapIndexed { i, it ->
                        when {
                            it.type == Xml.Type.TEXT -> {
                                val firstText = i == 0
                                val lastText = i == children.size - 1
                                when{
                                    firstText && lastText -> it.copy(content = it.content.trim())
                                    firstText -> it.copy(content = it.content.trimStart())
                                    lastText -> it.copy(content = it.content.trimEnd())
                                    else -> it
                                }
                            }
                            else -> it
                        }
                    }, endTag)
                }

                val children = level().children
                return children.firstOrNull { it.type == Xml.Type.NODE }
                    ?: children.firstOrNull()
                    ?: Xml.Text("")
            } catch (t: NoSuchElementException) {
                println("ERROR: XML: $str thrown a NoSuchElementException")
                return Xml.Text("!!ERRORED!!")
            }
        }
    }

    val text: String get() = when (type) {
        Type.NODE -> allChildren.joinToString("") { it.text }
        Type.TEXT -> content
        Type.COMMENT -> ""
    }

    fun toOuterXmlIndentedString(indenter: SimpleIndenter = SimpleIndenter(trailingLine = true)): String = toOuterXmlIndented(indenter).toString() + "\n"

    fun toOuterXmlIndented(indenter: SimpleIndenter = SimpleIndenter(trailingLine = true)): SimpleIndenter = indenter.apply {
        when (type) {
            Type.NODE -> {
                if (allChildren.isEmpty()) {
                    line("<$name$attributesStr/>")
                } else if (allChildren.size == 1 && allChildren[0].type == Type.TEXT) {
                    inline("<$name$attributesStr>")
                    inline(allChildren[0].content)
                    line("</$name>")
                } else {
                    line("<$name$attributesStr>")
                    indent {
                        allChildren.fastForEach { child ->
                            child.toOuterXmlIndented(indenter)
                        }
                    }
                    line("</$name>")
                }
            }
            else -> line(outerXml)
        }
    }

    val attributesStr: String get() = attributes.toList().map { " ${it.first}=\"${it.second}\"" }.joinToString("")

    val outerXml: String
        get() = when (type) {
            Type.NODE -> when {
                allChildren.isEmpty() -> "<$name$attributesStr/>"
                else -> {
                    val children = this.allChildren.joinToString("") { it.outerXml }
                    "<$name$attributesStr>$children</$name>"
                }
            }
            Type.TEXT -> when (name) {
                NAME_TEXT -> Entities.encode(content)
                NAME_CDATA -> "<![CDATA[$content]]>"
                NAME_RAW -> content
                else -> content
            }
            Type.COMMENT -> "<!--$content-->"
        }

    val innerXml: String get() = when (type) {
        Type.NODE -> this.allChildren.joinToString("") { it.outerXml }
        else -> outerXml
    }

    operator fun get(name: String): Iterable<Xml> = children(name)

    fun children(name: String): Iterable<Xml> = allChildren.filter { it.name.equals(name, ignoreCase = true) }
    fun child(name: String): Xml? = children(name).firstOrNull()
    fun childText(name: String): String? = child(name)?.text

    fun hasAttribute(key: String): Boolean = this.attributesLC.containsKey(key)
    fun attribute(name: String): String? = this.attributesLC[name]

    fun getString(name: String): String? = this.attributesLC[name]
    fun getInt(name: String): Int? = this.attributesLC[name]?.toInt()
    fun getLong(name: String): Long? = this.attributesLC[name]?.toLong()
    fun getDouble(name: String): Double? = this.attributesLC[name]?.toDouble()
    fun getFloat(name: String): Float? = this.attributesLC[name]?.toFloat()

    fun double(name: String, defaultValue: Double = 0.0): Double =
        this.attributesLC[name]?.toDoubleOrNull() ?: defaultValue

    fun boolean(name: String, defaultValue: Boolean = false): Boolean = booleanOrNull(name) ?: defaultValue

    fun booleanOrNull(name: String): Boolean? =
        when (str(name).toLowerCase()) {
            "true", "1" -> true
            "false", "0" -> false
            else -> null
        }

    fun float(name: String, defaultValue: Float = 0f): Float = this.attributesLC[name]?.toFloatOrNull() ?: defaultValue
    fun int(name: String, defaultValue: Int = 0): Int = this.attributesLC[name]?.toIntOrNull() ?: defaultValue
    fun long(name: String, defaultValue: Long = 0): Long = this.attributesLC[name]?.toLongOrNull() ?: defaultValue
    fun str(name: String, defaultValue: String = ""): String = this.attributesLC[name] ?: defaultValue
    fun uint(name: String, defaultValue: UInt = 0u): UInt = this.attributesLC[name]?.toUIntOrNull() ?: defaultValue

    fun doubleNull(name: String): Double? = this.attributesLC[name]?.toDoubleOrNull()
    fun floatNull(name: String): Float? = this.attributesLC[name]?.toFloatOrNull()
    fun intNull(name: String): Int? = this.attributesLC[name]?.toIntOrNull()
    fun longNull(name: String): Long? = this.attributesLC[name]?.toLongOrNull()
    fun strNull(name: String): String? = this.attributesLC[name]

    //override fun toString(): String = innerXml
    override fun toString(): String = outerXml

    enum class Type { NODE, TEXT, COMMENT }

    object Encode {
        fun encodeOpenTag(name: String, map: Map<String, Any?>, selfClose: Boolean = false): String = buildString {
            append("<")
            append(name)
            if (map.isNotEmpty()) {
                append(" ")
                append(quoteParams(map))
            }
            if (selfClose) {
                append("/")
            }
            append(">")
        }
        fun encodeCloseTag(name: String): String = "</$name>"
        fun quoteParams(map: Map<String, Any?>): String = map.entries.joinToString(" ") { it.key + "=" + quote(it.value) }

        fun quote(value: Any?): String = when (value) {
            is Number, is Boolean -> value.toString()
            else -> value?.toString()?.let { quote(it) } ?: "\"\""
        }
        fun quote(str: String): String = "\"${Entities.encode(str)}\""
    }

    class Literals(
        private val lits: Array<String>,
        private val map: MutableMap<String, Boolean>,
        val lengths: Array<Int>
    ) {
        companion object {
            fun invoke(vararg lits: String): Literals =
                fromList(lits.toCollection(arrayListOf<String>()).toTypedArray())

            //fun invoke(lits:Array<String>): Literals = fromList(lits)
            fun fromList(lits: Array<String>): Literals {
                val lengths = lits.map { it.length }.sorted().reversed().distinct().toTypedArray()
                val map = linkedMapOf<String, Boolean>()
                lits.fastForEach { lit ->
                    map[lit] = true
                }
                return Literals(lits, map, lengths)
            }
        }

        fun contains(lit: String) = map.containsKey(lit)

        fun matchAt(str: String, offset: Int): String? {
            lengths.fastForEach { len ->
                val id = str.substr(offset, len)
                if (contains(id)) return id
            }
            return null
        }

        private fun String.substr(start: Int, length: Int): String {
            val low = (if (start >= 0) start else this.length + start).coerceIn(0, this.length)
            val high = (if (length >= 0) low + length else this.length + length).coerceIn(0, this.length)
            return if (high >= low) this.substring(low, high) else ""
        }

        override fun toString() = "Literals(${lits.joinToString(" ")})"
    }

    object Entities {
        // Predefined entities in XML 1.0
        private val charToEntity = linkedMapOf('"' to "&quot;", '\'' to "&apos;", '<' to "&lt;", '>' to "&gt;", '&' to "&amp;")
        private val entities = Literals.fromList(charToEntity.values.toTypedArray())
        private val entityToChar = charToEntity.flip()

        fun encode(str: String): String = str.eachBuilder {
            val entry = charToEntity[it]
            when {
                entry != null -> append(entry)
                else -> append(it)
            }
        }
        fun decode(str: String): String = decode(SimpleStrReader(str))
        fun decode(r: SimpleStrReader): String = buildString {
            val sb = StringBuilder()
            while (!r.eof) {
                @Suppress("LiftReturnOrAssignment") // Performance?
                append(r.readUntilBuilder('&', sb.clear()))
                if (r.eof) break

                r.skipExpect('&')
                val value = r.readUntilBuilder(';', sb.clear(), included = true)
                val full = "&$value"
                when {
                    value.startsWith('#') -> {
                        var base = 10
                        var str = value.substring(1, value.length - 1)
                        if(str.startsWith("x")) {
                            base = 16
                            str = str.substring(1)
                        }
                        append(str.toIntOrNull(base)?.toChar())
                    }
                    entityToChar.contains(full) -> append(entityToChar[full])
                    else -> append(full)
                }
            }
        }
    }

    object Stream {
        // https://wiki.tei-c.org/index.php/XML_Whitespace
        fun parse(str: String, collapseSpaces: Boolean = true): Iterable<Element> = parse(SimpleStrReader(str), collapseSpaces)
        fun parse(r: SimpleStrReader, collapseSpaces: Boolean = true): Iterable<Element> = Xml2Iterable(r, collapseSpaces)
        fun parse(r: CharReader, collapseSpaces: Boolean = true): Iterable<Element> = Xml2Iterable(CharReaderStrReader(r), collapseSpaces)

        private fun SimpleStrReader.matchStringOrId(out: StringBuilder): StringBuilder? = matchSingleOrDoubleQuoteString(out) ?: matchIdentifier(out)

        private val SPACES = Regex("\\s+")

        fun xmlSequence(str: String, collapseSpaces: Boolean = true, processNamespaces: Boolean = false): Sequence<Element> = xmlSequence(SimpleStrReader(str), collapseSpaces, processNamespaces)
        fun xmlSequence(r: SimpleStrReader, collapseSpaces: Boolean = true, processNamespaces: Boolean = false): Sequence<Element> = sequence<Element> {
            val sb = StringBuilder(128)

            loop@while (!r.eof) {
                val str = r.readUntilBuilder('<', sb.clear(), included = false)
                if (str.isNotEmpty()) {
                    val text = str.toString()
                    val textNs = when {
                        collapseSpaces -> text.replace(SPACES, " ").let { if (it.all { it.isWhitespaceFast() }) "" else it }
                        else -> text
                    }
                    if (textNs.isNotEmpty()) {
                        yield(Element.Text(Xml.Entities.decode(textNs)))
                    }
                }
                if (r.eof) break

                r.skipExpect('<')
                r.skipSpaces()
                sb.clear()
                val processingInstruction = r.tryExpect('?')
                val processingEntityOrDocType = r.tryExpect('!')
                if (processingEntityOrDocType) {
                    sb.append('!')
                    while (!r.eof) {
                        val c = r.peekChar()
                        if (c == '>' || c.isWhitespaceFast() || c == '/') break

                        sb.append(r.readChar())

                        if (sb.startsWith("!--")) {
                            sb.clear()
                            while (!r.eof) {
                                sb.append(r.readChar())
                                if (sb.endsWith("-->")) {
                                    sb.deleteRange(sb.length - 3, sb.length)
                                    break
                                }
                            }
                            yield(Element.CommentTag(sb.toString()))
                            continue@loop
                        }
                        if (sb.startsWith("![CDATA[")) {
                            sb.clear()
                            while (!r.eof) {
                                sb.append(r.readChar())
                                if (sb.endsWith("]]>")) {
                                    sb.deleteRange(sb.length - 3, sb.length)
                                    break
                                }
                            }
                            yield(Element.Text(sb.toString()).also { it.cdata = true })
                            continue@loop
                        }
                    }
                    sb.deleteAt(0)
                }
                val close = r.tryExpect('/') || processingEntityOrDocType
                r.skipSpaces()
                val name = sb.takeIf { it.isNotEmpty() }?.toString()
                    ?: r.matchIdentifier(sb.clear())?.toString()
                    ?: error("Couldn't match identifier after '<', offset=${r.pos}, near='${r.toStringContext()}'")
                r.skipSpaces()
                val attributes = linkedMapOf<String, String>()
                while (r.peekChar() != '?' && r.peekChar() != '/' && r.peekChar() != '>') {
                    val key = r.matchStringOrId(sb.clear())?.toString() ?: throw IllegalArgumentException(
                        "Malformed document or unsupported xml construct around ~${r.toStringContext()}~ for name '$name'"
                    )
                    r.skipSpaces()
                    if (r.tryExpect('=')) {
                        r.skipSpaces()
                        val argsQuote = r.matchStringOrId(sb.clear())?.toString()
                        attributes[key] = when {
                            argsQuote != null && !(argsQuote.startsWith("'") || argsQuote.startsWith("\"")) -> argsQuote
                            argsQuote != null -> Xml.Entities.decode(argsQuote.substring(1, argsQuote.length - 1))
                            else -> Xml.Entities.decode(r.matchIdentifier(sb.clear())!!.toString())
                        }
                    } else {
                        attributes[key] = key
                    }
                    r.skipSpaces()
                }
                val openclose = r.tryExpect('/')
                val processingInstructionEnd = r.tryExpect('?')
                r.skipExpect('>')

                // Handle namespace processing based on the processNamespaces flag
                val elementName = if (!processNamespaces && name.contains(':')) {
                    name.substringAfter(':')
                } else {
                    name
                }

                yield(when {
                    processingInstruction || processingEntityOrDocType -> Element.ProcessingInstructionTag(elementName, attributes)
                    openclose -> Element.OpenCloseTag(elementName, attributes)
                    close -> Element.CloseTag(elementName)
                    else -> Element.OpenTag(elementName, attributes)
                })
            }
        }

        class Xml2Iterable(val reader2: SimpleStrReader, val skipSpaces: Boolean) : Iterable<Element> {
            val reader = reader2.clone()
            override fun iterator(): Iterator<Element> = xmlSequence(reader, skipSpaces).iterator()
        }

        sealed class Element {
            data class ProcessingInstructionTag(val name: String, val attributes: Map<String, String>) : Element()
            data class OpenCloseTag(val name: String, val attributes: Map<String, String>) : Element()
            data class OpenTag(val name: String, val attributes: Map<String, String>) : Element()
            data class CommentTag(val text: String) : Element()
            data class CloseTag(val name: String) : Element()
            data class Text(val text: String, var cdata: Boolean = false) : Element()
        }
    }
}

val Xml.isText get() = this.type == Xml.Type.TEXT
val Xml.isComment get() = this.type == Xml.Type.COMMENT
val Xml.isNode get() = this.type == Xml.Type.NODE

fun Iterable<Xml>.str(name: String, defaultValue: String = ""): String = this.first().attributes[name] ?: defaultValue
fun Iterable<Xml>.children(name: String): Iterable<Xml> = this.flatMap { it.children(name) }
val Iterable<Xml>.allChildren: Iterable<Xml> get() = this.flatMap(Xml::allChildren)
val Iterable<Xml>.allNodeChildren: Iterable<Xml> get() = this.flatMap(Xml::allNodeChildren)
val Iterable<Xml>.firstText: String? get() = this.firstOrNull()?.text
val Iterable<Xml>.text: String get() = this.joinToString("") { it.text }
operator fun Iterable<Xml>.get(name: String): Iterable<Xml> = this.children(name)

fun Sequence<Xml>.str(name: String, defaultValue: String = ""): String = this.first().attributes[name] ?: defaultValue
fun Sequence<Xml>.children(name: String): Sequence<Xml> = this.flatMap { it.children(name) }
val Sequence<Xml>.allChildren: Sequence<Xml> get() = this.flatMap(Xml::allChildren)
val Sequence<Xml>.allNodeChildren: Sequence<Xml> get() = this.flatMap(Xml::allNodeChildren)
val Sequence<Xml>.firstText: String? get() = this.firstOrNull()?.text
val Sequence<Xml>.text: String get() = this.joinToString("") { it.text }
operator fun Sequence<Xml>.get(name: String): Sequence<Xml> = this.children(name)

fun String.toXml(collapseSpaces: Boolean = true): Xml = Xml.parse(this, collapseSpaces)

// language=html
fun Xml(
    // language=html
    str: String, collapseSpaces: Boolean = true, processNamespaces: Boolean = false
): Xml = Xml.parse(str, collapseSpaces, processNamespaces)

fun Xml.descendants(name: String) = descendants.filter { it.name.equals(name, ignoreCase = true) }
fun Xml.firstDescendant(name: String) = descendants(name).firstOrNull()

class XmlBuilder @PublishedApi internal constructor() {
    @PublishedApi
    internal val nodes = arrayListOf<Xml>()
    fun node(node: Xml) = node.also { nodes += node }
    inline fun node(tag: String, vararg props: Pair<String, Any?>, block: XmlBuilder.() -> Unit = {}): Xml =
        Xml.Tag(tag, props.filter { it.second != null }.toMap(), XmlBuilder().apply(block).nodes).also { nodes += it }
    fun comment(comment: String): Xml = Xml.Comment(comment).also { nodes += it }
    fun text(text: String): Xml = Xml.Text(text).also { nodes += it }
    fun cdata(text: String): Xml = Xml.CData(text).also { nodes += it }.also { if (text.contains("]]>")) error("A cdata node cannot contain the ]]> literal") }
    fun raw(text: String): Xml = Xml.Raw(text).also { nodes += it }
}

inline fun buildXml(rootTag: String, vararg props: Pair<String, Any?>, crossinline block: XmlBuilder.() -> Unit = {}): Xml =
    XmlBuilder().node(rootTag, *props, block = block)

inline fun Xml(rootTag: String, vararg props: Pair<String, Any?>, block: XmlBuilder.() -> Unit = {}): Xml =
    XmlBuilder().node(rootTag, *props, block = block)
inline fun Xml(rootTag: String, props: Map<String, Any?>?, block: XmlBuilder.() -> Unit = {}): Xml =
    XmlBuilder().node(rootTag, *(props ?: emptyMap()).map { it.key to it.value }.toTypedArray(), block = block)



/**
 * [Map] with [String] keys that are treated in a insensitive manner.
 */
private class CaseInsensitiveStringMap<T> private constructor(
    private val mapOrig: MutableMap<String, T>,
    private val lcToOrig: MutableMap<String, String>,
    private val mapLC: MutableMap<String, T>
) : MutableMap<String, T> by mapOrig {
    constructor() : this(LinkedHashMap(), LinkedHashMap(), LinkedHashMap())
    constructor(data: Map<String, T>) : this() { putAll(data) }
    constructor(vararg items: Pair<String, T>) : this() { putAll(items.toList()) }

    override fun containsKey(key: String): Boolean = mapLC.containsKey(key.toLowerCase())

    override fun clear() {
        mapOrig.clear()
        mapLC.clear()
        lcToOrig.clear()
    }

    override fun get(key: String): T? = mapLC[key.toLowerCase()]

    override fun put(key: String, value: T): T? {
        remove(key)
        mapOrig[key] = value
        lcToOrig[key.toLowerCase()] = key
        return mapLC.put(key.toLowerCase(), value)
    }

    override fun putAll(from: Map<out String, T>) {
        for (v in from) put(v.key, v.value)
    }

    override fun remove(key: String): T? {
        val lkey = key.toLowerCase()
        val okey = lcToOrig[lkey]
        mapOrig.remove(okey)
        val res = mapLC.remove(lkey)
        lcToOrig.remove(lkey)
        return res
    }

    override fun equals(other: Any?): Boolean = (other is CaseInsensitiveStringMap<*>) && this.mapLC == other.mapLC
    override fun hashCode(): Int = mapLC.hashCode()
}

private fun <T> Map<String, T>.toCaseInsensitiveMap(): Map<String, T> =
    CaseInsensitiveStringMap<T>().also { it.putAll(this) }

private fun <K, V> Map<K, V>.flip(): Map<V, K> = this.map { Pair(it.value, it.key) }.toMap()

private inline fun String.eachBuilder(transform: StringBuilder.(Char) -> Unit): String = buildString {
    @Suppress("ReplaceManualRangeWithIndicesCalls") // Performance reasons? Check that plain for doesn't allocate
    for (n in 0 until this@eachBuilder.length) transform(this, this@eachBuilder[n])
}
