plugins {
    //kotlin("multiplatform") version "1.9.23"
    kotlin("multiplatform") //apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
    afterEvaluate {
        group = "com.soywiz"
    }
}

kotlin {
    jvm()
}

subprojects {
    //apply<KotlinMultiplatformPlugin>()
    apply(plugin = "kotlin-multiplatform")

    kotlin {
        js() {
            nodejs()
        }
    }
}
