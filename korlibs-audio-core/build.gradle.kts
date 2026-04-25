import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.multiplatform.library)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()

    android {
        namespace = "org.korge.korlibs.audio.core"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        androidResources.enable = true
    }
    js {
        browser {
            compilerOptions {
                target.set("es2015")
            }
        }
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            compilerOptions {
                target.set("es2015")
            }
        }
    }

    iosArm64()
    iosSimulatorArm64()
    iosX64()
    tvosArm64()
    tvosSimulatorArm64()
    watchosArm64()
    watchosArm32()
    watchosDeviceArm64()
    watchosSimulatorArm64()
    mingwX64()
    linuxX64()
    linuxArm64()
    macosArm64()
    // TODO Add android native targets as well

    sourceSets {
        commonMain.dependencies {
            implementation(projects.korlibsMathCore)
            implementation(projects.korlibsConcurrent)
            api(projects.korlibsMathVector)
            implementation(projects.korlibsFfiLegacy)
            implementation(projects.korlibsPlatform)
            implementation(projects.korlibsIoFs)
            implementation(projects.korlibsTime)
            implementation(projects.korlibsDatastructure)
            implementation(libs.jna.jna)
        }

        val appleIosTvosMain by creating {
            dependsOn(appleMain.get())
        }

        val appleIosTvosWatchosMain by creating {
            dependsOn(appleMain.get())
        }

        val appleNonWatchosMain by creating {
            dependsOn(appleMain.get())
        }

        macosMain.get().dependsOn(appleNonWatchosMain)

        iosMain {
            dependsOn(appleIosTvosMain)
            dependsOn(appleIosTvosWatchosMain)
            dependsOn(appleNonWatchosMain)
        }

        tvosMain {
            dependsOn(appleIosTvosMain)
            dependsOn(appleIosTvosWatchosMain)
            dependsOn(appleNonWatchosMain)
        }

        watchosMain.get().dependsOn(appleIosTvosWatchosMain)
    }
}
