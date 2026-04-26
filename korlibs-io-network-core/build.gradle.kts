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
        namespace = "org.korge.korlibs.io.network.core"
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
    mingwX64 {
        compilations.getByName("main") {
            cinterops {
                val win32ssl by creating {
                    defFile(project.file("nativeInterop/cinterop/win32ssl.def"))
                }
            }
        }
    }
    linuxX64()
    linuxArm64()
    macosArm64()
    // TODO Add android native targets as well

    sourceSets {
        commonMain.dependencies {
            api(projects.korlibsAnnotations)
            api(projects.korlibsIoStream)
            implementation(projects.korlibsDatastructure)
            api(projects.korlibsPlatform)
            api(projects.korlibsIoStream)
            implementation(projects.korlibsLogger)
            api(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
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

        linuxMain {
            dependsOn(posixMain)
        }

        appleMain {
            dependsOn(posixMain)
        }

        jvmMain {
            dependsOn(concurrentMain)
            dependsOn(jvmAndAndroidMain)
        }

        androidMain {
            dependsOn(concurrentMain)
            dependsOn(jvmAndAndroidMain)
        }

        nativeMain {
            dependsOn(concurrentMain)
        }
    }
}
