package korlibs.io.lang

import korlibs.io.util.*
import kotlin.jvm.*
import kotlin.text.isDigit

@Deprecated("Use StringCase instead")
typealias TextCase = StringCase

val String.case: StringCase get() = StringCase.autodetect(this)

@Deprecated("this.case", ReplaceWith("this.case"))
fun String.textCase(): StringCase = this.case
@Deprecated("this.case", ReplaceWith("this.case"))
fun String.stringCase(): StringCase = this.case

@JvmInline
value class StringCase(val words: List<String>) {
    constructor(vararg words: String) : this(words.toList())

    companion object {
        //@Deprecated("", ReplaceWith("autodetect(str, lower)", "korlibs.io.lang.StringCase.Companion.autodetect"))
        //operator fun invoke(str: String, lower: Boolean = true): StringCase = autodetect(str, lower)

        fun autodetect(str: String, lower: Boolean = true): StringCase {
            val words = mutableListOf<String>()
            val buffer = StringBuilder()
            fun flush() {
                if (buffer.isEmpty()) return
                words.add(buffer.toString())
                buffer.clear()
            }

            val DIGIT = 0
            val UPPER = 1
            val LOWER = 2

            var prevCase = LOWER
            for (c in str) {
                val currCase = when {
                    c.isDigit() -> DIGIT
                    c.isUpperCase() -> UPPER
                    else -> LOWER
                }
                if (currCase != LOWER && currCase != prevCase) flush()
                when {
                    c == '_' || c == '-' || c == '.' || c == ',' || c.isWhitespaceFast() -> flush()
                    else -> {
                        buffer.append(if (lower) c.lowercaseChar() else c)
                        prevCase = currCase
                    }
                }
            }
            flush()
            return StringCase(words)
        }
    }

    val spaceCase: String get() = words.joinToString(" ") { it.lowercase() }
    val snakeCase: String get() = words.joinToString("_") { it.lowercase() }
    val kebabCase: String get() = words.joinToString("-") { it.lowercase() }
    val screamingSnakeCase: String get() = words.joinToString("_") { it.uppercase() }
    val pascalCase: String get() = words.joinToString("") { it.lowercase().replaceFirstChar { it.uppercaseChar() } }
    val camelCase: String get() = pascalCase.replaceFirstChar { it.lowercaseChar() }
}
