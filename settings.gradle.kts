pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

rootProject.name = "korlibs"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(
    ":korlibs-annotations",
    ":korlibs-audio",
    ":korlibs-audio-core",
    ":korlibs-bignumber",
    ":korlibs-checksum",
    ":korlibs-compression",
    ":korlibs-concurrent",
    ":korlibs-crypto",
    ":korlibs-datastructure",
    ":korlibs-datastructure-core",
    ":korlibs-dyn",
    ":korlibs-encoding",
    ":korlibs-ffi",
    ":korlibs-ffi-ksp",
    ":korlibs-ffi-legacy",
    ":korlibs-image",
    ":korlibs-image-core",
    ":korlibs-inject",
    ":korlibs-io",
    ":korlibs-io-nodejs",
    ":korlibs-io-fs",
    ":korlibs-io-network-core",
    ":korlibs-io-stream",
    ":korlibs-io-vfs",
    ":korlibs-jseval",
    ":korlibs-logger",
    ":korlibs-math",
    ":korlibs-math-core",
    ":korlibs-math-vector",
    ":korlibs-memory",
    ":korlibs-number",
    ":korlibs-platform",
    ":korlibs-serialization",
    ":korlibs-string",
    ":korlibs-template",
    ":korlibs-time",
    ":korlibs-time-core",
    ":korlibs-wasm",
)
