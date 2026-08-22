plugins {
    `kotlin-dsl`
}

group = "org.korge.korlibs.buildlogic"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.diffplug.spotless.gradlePlugin)
}
