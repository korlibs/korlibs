package korlibs.io.lang

import korlibs.platform.*
import kotlin.collections.set

private var customEnvironments: LinkedHashMap<String, String>? = null

/**
 * Interface for [Environment] variables, that can be mocked for testing purposes.
 *
 * The [Companion] object provides the default implementation actually using the system environment variables.
 */
interface Environment {
    /** Returns the String value or null of the environment provided by [key] (it is case insensitive) */
    operator fun get(key: String): String?
    /** Sets the [value] for the environment [key] */
    operator fun set(key: String, value: String)
    /** Gets the full list of environment variables, keeping its case-sensitivity. */
    fun getAll(): Map<String, String>

    companion object : Environment {
        /** The directory separator for the current platform. Either '\\' (windows) or '/' (the rest of the platforms) */
        val DIR_SEPARATOR: Char get() = if (Platform.isWindows) '\\' else '/'
        /** The path separator for the current platform. Either ';' (windows) or ':' (the rest of the platforms) */
        val PATH_SEPARATOR: Char get() = if (Platform.isWindows) ';' else ':'

        // Uses querystring on JS/Browser, and proper env vars in the rest
        override operator fun get(key: String): String? = customEnvironments?.get(key.uppercase()) ?: Platform.envsUC[key.uppercase()]
        override operator fun set(key: String, value: String) {
            if (customEnvironments != null) {
                customEnvironments = LinkedHashMap()
            }
            customEnvironments?.set(key.uppercase(), value)
        }

        override fun getAll(): Map<String, String> = (customEnvironments ?: mapOf()) + Platform.envs
    }
}

@Deprecated("", ReplaceWith("tempPath"))
val Environment.TEMP get() = tempPath

/** Returns the TEMP folder of the system based on this [Environment] variables (that could be mocked) */
val Environment.tempPath get() = this["TMPDIR"] ?: this["TEMP"] ?: this["TMP"] ?: "/tmp"

// @TODO: System.getProperty("user.home")
/** Returns the HOME path for the user executing this program based on the [Environment] variables (that could be mocked) */
val Environment.userHome get() = when {
    this["HOMEDRIVE"] != null && this["HOMEPATH"] != null -> "${this["HOMEDRIVE"]}${this["HOMEPATH"]}"
    else -> this["HOMEPATH"] ?: this["HOME"] ?: this.tempPath
}

/**
 * A custom implementation of [Environment] that allows to set custom environment variables.
 */
open class EnvironmentCustom(customEnvironments: Map<String, String> = LinkedHashMap()) : Environment {
    var customEnvironments = when (customEnvironments) {
        is MutableMap<*, *> -> customEnvironments as MutableMap<String, String>
        else -> customEnvironments.toMutableMap()
    }
    private val customEnvironmentsNormalized = customEnvironments.map { it.key.uppercase() to it.value }.toLinkedMap()
    fun String.normalized() = this.uppercase().trim()
    override operator fun get(key: String): String? = customEnvironmentsNormalized[key.normalized()]
    override operator fun set(key: String, value: String) {
        customEnvironments[key] = value
        customEnvironmentsNormalized[key.normalized()] = value
    }
    override fun getAll(): Map<String, String> = customEnvironments
}

/** Creates a new [Environment] with the provided [envs] */
fun Environment(envs: Map<String, String> = mapOf()): Environment = EnvironmentCustom(envs)
/** Creates a new [Environment] with the provided [envs] */
fun Environment(vararg envs: Pair<String, String>): Environment = EnvironmentCustom(envs.toMap())

/** Expands the [str] replacing `~` with the user home */
fun Environment.expand(str: String): String {
    return str.replace(Regex("(~|%(\\w+)%)")) {
        val key = it.value.trim('%')
        when (key) {
            "~" -> this.userHome
            else -> this[key]
        } ?: ""
    }
}

private fun <K, V> Iterable<Pair<K, V>>.toLinkedMap(): LinkedHashMap<K, V> = LinkedHashMap<K, V>().also { for ((key, value) in this) it[key] = value }
