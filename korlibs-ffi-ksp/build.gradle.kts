import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    applyDefaultHierarchyTemplate()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        enabled.set(true)
    }

    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(libs.ksp.symbolProcessingApi)
        }
    }
}
