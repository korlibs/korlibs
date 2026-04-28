import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.TestExecutable

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
        namespace = "org.korge.korlibs.io"
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
            api(projects.korlibsChecksum)
            api(projects.korlibsCompression)
            api(projects.korlibsJseval)
            api(projects.korlibsIoVfs)
            api(projects.korlibsIoStream)
            api(projects.korlibsIoNetworkCore)
            api(projects.korlibsMathCore)
            api(projects.korlibsMemory)
            api(projects.korlibsFfiLegacy)
            api(projects.korlibsCrypto)
            api(projects.korlibsEncoding)
            api(projects.korlibsPlatform)
            api(projects.korlibsDatastructure)
            api(projects.korlibsNumber)
            api(projects.korlibsTime)
            api(projects.korlibsLogger)
            api(projects.korlibsDyn)
            api(projects.korlibsString)
            api(projects.korlibsSerialization)
            api(projects.korlibsIoFs)
            api(libs.kotlinx.atomicfu)
            implementation(libs.jna.jna)
        }

        val concurrentMain by creating {
            dependsOn(commonMain.get())
        }

        val posixMain by creating {
            dependsOn(nativeMain.get())
        }

        val jvmAndAndroidMain by creating {
            dependsOn(commonMain.get())
        }

        nativeMain {
            dependsOn(concurrentMain)
        }

        linuxMain {
            dependsOn(posixMain)
        }

        appleMain {
            dependsOn(posixMain)
        }

        jvmMain {
            dependsOn(jvmAndAndroidMain)
            dependsOn(concurrentMain)
        }

        androidMain {
            dependsOn(jvmAndAndroidMain)
            dependsOn(concurrentMain)
        }

        commonTest.dependencies {
            implementation(projects.korlibsTime)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        val jvmAndAndroidTest by creating {
            dependsOn(commonTest.get())
        }

        jvmTest {
            dependsOn(jvmAndAndroidTest)
        }

        getByName("androidHostTest") {
            dependsOn(jvmAndAndroidTest)
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
