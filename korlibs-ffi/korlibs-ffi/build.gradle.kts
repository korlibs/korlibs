plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    // Metadata pass generates common expect declarations.
    add("kspCommonMainMetadata", project(":korlibs-ffi-ksp"))
    // JVM-based targets: user-facing configs are required to enable the KSP task.
    add("kspJvm", project(":korlibs-ffi-ksp"))
    add("kspAndroid", project(":korlibs-ffi-ksp"))
    // JS / WasmJs
    add("kspJs", project(":korlibs-ffi-ksp"))
    add("kspWasmJs", project(":korlibs-ffi-ksp"))
}

// Native targets use a checked-in src@native actual (TestMathFFI_FFIImpl.Native.kt) instead of KSP
// generation, because native KSP tasks behave inconsistently across platforms and CI runners.

tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }.configureEach {
    dependsOn("kspCommonMainKotlinMetadata")
}

// sourcesJar packages generated metadata sources, so it must run after KSP metadata generation.
tasks.named("sourcesJar").configure {
    dependsOn("kspCommonMainKotlinMetadata")
}
