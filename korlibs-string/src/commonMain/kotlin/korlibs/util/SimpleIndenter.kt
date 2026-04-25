package korlibs.util

import kotlinx.atomicfu.locks.*

interface SimpleIndenter {
    fun clear()
    fun inline(s: String): SimpleIndenter
    fun line(s: String): SimpleIndenter
    @ExperimentalStdlibApi
    fun indent()
    @ExperimentalStdlibApi
    fun unindent()
    override fun toString(): String

    object INDENTS {
        private val lock = SynchronizedObject()
        private val lastIndent = StringBuilder(1024)
        private val INDENTS = ArrayList<String>(1024)

        operator fun get(index: Int): String {
            if (INDENTS.size <= index) {
                synchronized(lock) {
                    while (INDENTS.size <= index) {
                        INDENTS.add(lastIndent.toString())
                        lastIndent.append('\t')
                    }
                }
            }
            return INDENTS[index]
        }
    }

    companion object {
        operator fun invoke(trailingLine: Boolean = false): SimpleIndenter = Impl(trailingLine)

        @OptIn(ExperimentalStdlibApi::class)
        class Impl(val trailingLine: Boolean) : SimpleIndenter {
            var indentation = 0
            val lines = arrayListOf<String>()
            var currentLine = StringBuilder()

            override fun clear() {
                indentation = 0
                lines.clear()
                currentLine.clear()
            }

            fun flush() {
                if (currentLine.isNotEmpty()) {
                    lines += INDENTS[indentation] + currentLine.toString()
                    currentLine.clear()
                }
            }

            override fun inline(s: String): SimpleIndenter = this.apply {
                currentLine.append(s)
            }

            override fun line(s: String): SimpleIndenter = this.apply {
                currentLine.append(s)
                flush()
            }

            override fun indent() {
                flush()
                indentation++
            }

            override fun unindent() {
                flush()
                indentation--
            }

            override fun toString(): String {
                flush()
                val end = lines.joinToString("\n")
                return if (trailingLine) "$end\n" else end
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
inline fun <T> SimpleIndenter.indent(function: () -> T): T {
    indent()
    try {
        return function()
    } finally {
        unindent()
    }
}
