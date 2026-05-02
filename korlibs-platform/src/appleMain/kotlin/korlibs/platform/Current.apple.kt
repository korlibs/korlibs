package korlibs.platform

internal actual val envs: Map<String, String> by lazy { kotlinx.cinterop.autoreleasepool { platform.Foundation.NSProcessInfo.processInfo.environment.map { it.key.toString() to it.value.toString() }.toMap() } }
