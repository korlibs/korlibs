import com.google.devtools.ksp.gradle.KspAATask
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.multiplatform.library)
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

    android {
        namespace = "org.korge.korlibs.ffi.ksp"
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
        commonMain {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain {
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
            dependencies {
                api(libs.jna.platform)
                api(libs.jna.jna)

            }
        }

        val posixMain by creating {
            dependsOn(nativeMain.get())
        }

        linuxMain {
            dependsOn(posixMain)
        }

        appleMain {
            dependsOn(posixMain)
        }

        linuxX64Main {
            kotlin.srcDir("build/generated/ksp/linuxX64/linuxX64Main/kotlin")
        }

        linuxArm64Main {
            kotlin.srcDir("build/generated/ksp/linuxArm64/linuxArm64Main/kotlin")
        }

        wasmJsMain {
            kotlin.srcDir("build/generated/ksp/wasmJs/wasmJsMain/kotlin")
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.korlibsFfiKsp)

    add("kspJvm", projects.korlibsFfiKsp)
    add("kspJvmTest", projects.korlibsFfiKsp)

    add("kspAndroid", projects.korlibsFfiKsp)

    add("kspLinuxX64", projects.korlibsFfiKsp)
    add("kspLinuxArm64", projects.korlibsFfiKsp)
    add("kspMingwX64", projects.korlibsFfiKsp)
    add("kspJs", projects.korlibsFfiKsp)
    add("kspWasmJs", projects.korlibsFfiKsp)
}

tasks.named("sourcesJar") {
    dependsOn("kspCommonMainKotlinMetadata")
}

// Task dependencies — use kspKotlin<Target> (capital K, no Main suffix)
tasks.withType(KspAATask::class).configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

afterEvaluate {
    kotlin.targets.filterIsInstance<KotlinNativeTarget>().forEach { target ->
        val targetName = target.name
        val capitalized = targetName.replaceFirstChar { it.uppercase() }
        // Correct task name is kspKotlin<Target>
        tasks.findByName("compileKotlin$capitalized")
            ?.dependsOn("kspKotlin$capitalized")
    }
}
