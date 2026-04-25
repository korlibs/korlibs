pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        //maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
        //maven("https://www.jetbrains.com/intellij-repository/releases")
        //maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }

    plugins {
        id("com.google.devtools.ksp") version "2.0.0-1.0.23"
        //id("org.jetbrains.amper.settings.plugin").version("0.2.1-dev-470")
        //id("org.jetbrains.amper.settings.plugin").version("0.2.2")
        //id("org.jetbrains.amper.settings.plugin").version("0.2.3-dev-473")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

for (file in rootDir.listFiles()) {
    if (file.isDirectory() && File(file, "module.yaml").exists()) {
        include(":${file.name}")
        project(":${file.name}").projectDir = file
    }
}

rootProject.name = "${rootDir.name}-root"

File(rootDir, "local.properties").also { localProperties ->
    if (localProperties.exists()) return@also

    fun detectAndroidHome(): File? = listOf(
        System.getenv("ANDROID_HOME")?.let { File(it) },
        File(System.getProperty("user.home"), "/Library/Android/sdk"),
        File(System.getProperty("user.home"), "/Android/Sdk"),
        File(System.getProperty("user.home"), "/AppData/Local/Android/Sdk"),
    ).firstNotNullOfOrNull { it?.takeIf { it.isDirectory } }

    detectAndroidHome()?.let {
        localProperties.writeText("sdk.dir=${it.absolutePath.replace("\\", "\\\\").replace(":", "\\:")}")
    }
}
