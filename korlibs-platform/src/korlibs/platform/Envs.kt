package korlibs.platform

object Envs {
    val ALL: Map<String, String> by lazy { korlibs.platform.envs }
    val ALL_UC: Map<String, String> by lazy { ALL.mapKeys { it.key.uppercase() } }
    operator fun get(key: String): String? = ALL_UC[key.uppercase()]
}