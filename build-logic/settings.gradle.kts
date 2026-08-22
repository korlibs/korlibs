dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"

// Add further convention plugin modules here as they are created,
// e.g. include(":android-library"), include(":kotlin-jvm"), ...
include(":convention")
