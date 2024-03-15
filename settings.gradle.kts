pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
}

plugins {
    id("org.jetbrains.amper.settings.plugin").version("0.2.1-dev-470")
}

File(rootDir, "local.properties").also { localProperties ->
    if (localProperties.exists()) return@also

    fun detectAndroidHome(): File? = listOf(
        System.getenv("ANDROID_HOME")?.let { File(it) },
        File(System.getProperty("user.home"), "/Library/Android/sdk"),
        File(System.getProperty("user.home"), "/Android/Sdk"),
        File(System.getProperty("user.home"), "/AppData/Local/Android/Sdk"),
    ).firstNotNullOfOrNull { it?.takeIf { it.isDirectory } }

    detectAndroidHome().let {
        localProperties.writeText("sdk.dir=$it")
    }
}

