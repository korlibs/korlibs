import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.TestExecutable
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeTest

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
        namespace = "org.korge.korlibs.wasm"
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
            implementation(projects.korlibsPlatform)
            api(projects.korlibsMemory)
            api(projects.korlibsLogger)
            api(projects.korlibsMathCore)
            api(projects.korlibsDatastructure)
            implementation(projects.korlibsCompression)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(projects.korlibsIo)
        }

        androidMain.dependencies {
            implementation(projects.korlibsSerialization)
        }

        jvmMain.dependencies {
            implementation(libs.asm.asm)
            implementation(libs.asm.utils)
        }

        val appleIosTvosMacosMain by creating {
            dependsOn(appleMain.get())
        }

        iosMain {
            dependsOn(appleIosTvosMacosMain)
        }

        tvosMain {
            dependsOn(appleIosTvosMacosMain)
        }

        macosMain {
            dependsOn(appleIosTvosMacosMain)
        }
    }
}

// Configuration that links resources for native targets manually
kotlin.targets
    .withType<KotlinNativeTarget>()
    .configureEach {
        binaries.withType<TestExecutable>()
            .configureEach {
                val copyResources = tasks.register<Copy>("copy${target.name.capitalized()}TestResources") {
                    from("src/commonTest/resources")
                    into(outputDirectory)
                }
                linkTaskProvider.dependsOn(copyResources)
            }
    }
