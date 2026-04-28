import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.multiplatform.library)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    applyDefaultHierarchyTemplate()
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation {
        enabled.set(true)
    }

    jvm()

    android {
        namespace = "org.korge.korlibs.datastructure"
        compileSdk = libs.versions.compileSdk.get().toInt()
        minSdk = libs.versions.minSdk.get().toInt()

        androidResources.enable = true
        withHostTest {}
        withDeviceTest {}
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
            api(projects.korlibsConcurrent)
            api(projects.korlibsTimeCore)
            api(projects.korlibsMathVector)
            api(projects.korlibsDatastructureCore)
        }
        commonTest.dependencies {
            implementation(projects.korlibsPlatform)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        val concurrentTest by creating {
            dependsOn(commonTest.get())
        }

        jvmTest {
            dependsOn(concurrentTest)
        }
        getByName("androidHostTest") {
            dependsOn(concurrentTest)
        }
        linuxTest {
            dependsOn(concurrentTest)
        }
        tvosTest {
            dependsOn(concurrentTest)
        }
        macosTest {
            dependsOn(concurrentTest)
        }
        iosTest {
            dependsOn(concurrentTest)
        }
        watchosTest {
            dependsOn(concurrentTest)
        }
        mingwTest {
            dependsOn(concurrentTest)
        }
    }
}
