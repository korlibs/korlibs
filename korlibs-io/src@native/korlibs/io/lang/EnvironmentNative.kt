package korlibs.io.lang

internal actual object EnvironmentInternal {
	val allEnvs: Map<String, String> get() = korlibs.platform.Platform.envs
	val allEnvsUpper: Map<String, String> by lazy { allEnvs.map { it.key.toUpperCase() to it.value }.toMap() }

	//actual operator fun get(key: String): String? = platform.posix.getenv(key)?.toKString()
	actual operator fun get(key: String): String? = allEnvsUpper[key.toUpperCase()]
	actual fun getAll() = allEnvs
}
