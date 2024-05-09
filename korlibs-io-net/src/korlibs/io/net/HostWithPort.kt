package korlibs.io.net

data class HostWithPort(val host: String, val port: Int) {
	val address: String get() = host

	companion object {
		fun parse(str: String, defaultPort: Int): HostWithPort {
			val parts = str.split(':', limit = 2)
			return HostWithPort(parts[0], parts.getOrElse(1) { "$defaultPort" }.toInt())
		}
	}
}

typealias AsyncAddress = HostWithPort

fun AsyncAddress(address: String = "0.0.0.0", port: Int = 0, unit: Unit = Unit): HostWithPort = HostWithPort(address, port)
