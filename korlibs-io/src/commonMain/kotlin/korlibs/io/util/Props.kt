package korlibs.io.util

import korlibs.datastructure.iterators.fastForEach
import korlibs.io.file.VfsFile
import korlibs.io.lang.Charset
import korlibs.io.lang.UTF8

class Props(private val props: LinkedHashMap<String, String> = LinkedHashMap<String, String>()) : MutableMap<String, String> by props {
	companion object {
		fun load(str: String) = Props().apply { deserializeNew(str) }
	}

	fun deserializeAdd(str: String) {
		str.split("\n").fastForEach { line ->
			if (line.startsWith('#')) return@fastForEach
			if (line.isBlank()) return@fastForEach
			val parts = line.split('=', limit = 2)
			val key = parts[0].trim()
			val value = parts.getOrElse(1) { " " }.trim()
			props[key] = value
		}
	}

	fun deserializeNew(str: String) {
		clear()
		deserializeAdd(str)
	}

	fun serialize(): String = props.map { "${it.key}=${it.value}" }.joinToString("\n")
}

suspend fun VfsFile.loadProperties(charset: Charset = UTF8) = Props.load(this.readString(charset))
suspend fun VfsFile.saveProperties(props: Props, charset: Charset = UTF8) = this.writeString(props.serialize(), charset = charset)
