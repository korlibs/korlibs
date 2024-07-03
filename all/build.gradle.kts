import com.google.gson.*
import com.google.gson.*
import com.google.gson.JsonParser
import groovy.json.*
import groovy.namespace.*
import groovy.util.*
import org.gradle.api.tasks.testing.logging.*
import org.gradle.jvm.tasks.Jar
import org.gradle.plugins.signing.signatory.internal.pgp.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.targets.js.ir.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import java.net.*
import java.util.*
import java.util.concurrent.*

plugins {
    kotlin("multiplatform") version "2.0.20-Beta1"
    id("com.android.library") version "8.2.2"
    `maven-publish`
    signing
}

var REAL_VERSION = System.getenv("FORCED_VERSION")
    ?.replaceFirst(Regex("^refs/tags/"), "")
    ?.replaceFirst(Regex("^v"), "")
    ?.replaceFirst(Regex("^w"), "")
    ?.replaceFirst(Regex("^z"), "")
//?: rootProject.findProperty("version")
    ?: "999.0.0.999"

//val REAL_VERSION = System.getenv("FORCED_VERSION") ?: "999.0.0.999"

val JVM_TARGET = JvmTarget.JVM_1_8
val GROUP = "com.soywiz"

kotlin {
    jvm()
    androidTarget()
}

allprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        //maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
        //maven("https://www.jetbrains.com/intellij-repository/releases")
        //maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
    version = REAL_VERSION
    group = GROUP

    project.apply(plugin = "kotlin-multiplatform")
    project.apply(plugin = "android-library")

    android {
        //signingConfigs {
        //    debug {
        //        […]
        //    }
        //    release {
        //        […]
        //    }
        //}
        compileSdk = 33
        namespace = "com.soywiz.${project.name.replace("-", ".")}"
        defaultConfig {
            minSdk = 20
        }
        //    defaultConfig {
        //        applicationId "[…]"
        //        minSdk 25
        //        targetSdk 33
        //        compileSdk 33
        //        versionCode 33
        //        versionName '33'
        //        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        //        signingConfig signingConfigs.release
        //    }
        //buildTypes {
        //    release {
        //        […]
        //    }
        //}
    }
}

fun getKotlinBasePlatform(platform: String): String = platform.removeSuffix("X64").removeSuffix("X86").removeSuffix("Arm64").removeSuffix("Arm32").removeSuffix("Simulator").removeSuffix("Device").also {
    check(it.all { it.isLowerCase() && !it.isDigit() })
}

data class SourceSetPair(val main: KotlinSourceSet, val test: KotlinSourceSet? = null) {
    fun dependsOn(other: SourceSetPair) {
        main.dependsOn(other.main)
        if (test != null && other.test != null) test.dependsOn(other.test)
    }
}

val sourceSetPairs = LinkedHashMap<String, SourceSetPair>()

//fun SourceDirectorySet.srcDirIfExists(path: String) {
//    if (path in projectFiles) setSrcDirs(listOf(path)) //else println("file doesn't exist $path")
//    //srcDir(path)
//}


val upFiles = (File(rootDir, "../").listFiles() ?: emptyArray()).toList()
val korlibsFolders = upFiles
    .filter { it.name.startsWith("korlibs-") }
    .map { it.canonicalFile }
    //.filter { 
    //    false
    //        || it.name.contains("korlibs-annotations") 
    //        //|| it.name.contains("korlibs-platform") 
    //        //|| it.name.contains("korlibs-number") 
    //        //|| it.name.contains("korlibs-logger") 
    //        //|| it.name.contains("korlibs-bignumber") 
    //        //|| it.name.contains("korlibs-jseval") 
    //        //|| it.name.contains("korlibs-string") 
    //        //|| it.name.contains("korlibs-math-core") 
    //        //|| it.name.contains("korlibs-math-vector") 
    //        //|| it.name.contains("korlibs-time-core") 
    //        //|| it.name.contains("korlibs-datastructure-core") 
    //        //|| it.name.contains("korlibs-serialization") 
    //        //|| it.name.contains("korlibs-template") 
    //        //|| it.name.contains("korlibs-memory") 
    //}


fun NamedDomainObjectContainer<KotlinSourceSet>.ssPair(name: String): SourceSetPair {
    return sourceSetPairs.getOrPut(name) {
        val atName = if (name == "common") "" else "@$name"
        SourceSetPair(
            main = maybeCreate("${name}Main").also {
                for (folder in korlibsFolders) {
                    it.kotlin.srcDir(File(folder, "src$atName"))
                    //println("ADD SRC[${name}]: ${File(folder, "src$atName")}")
                    //println(File(folder, "src$atName"))
                    it.resources.srcDir(File(folder, "resources$atName"))
                }
            },
            /*
            test = maybeCreate("${name}Test").also {
                for (folder in korlibsFolders) {
                    it.kotlin.srcDir(File(folder, "test$atName"))
                    it.resources.srcDir(File(folder, "testResources$atName"))
                }
            }
            */
        )
    }
}

fun NamedDomainObjectContainer<KotlinSourceSet>.ssDependsOn(base: String, other: String) {
    if (base == other) return
    //println("$base dependsOn $other")
    ssPair(base).dependsOn(ssPair(other))
}

val kotlinPlatforms = listOf(
    "jvm",
    "js",
    "wasm",
    "android",
    "linuxX64",
    "linuxArm64",
    "tvosArm64",
    "tvosX64",
    "tvosSimulatorArm64",
    "macosX64",
    "macosArm64",
    "iosArm64",
    "iosSimulatorArm64",
    "iosX64",
    "watchosArm64",
    "watchosArm32",
    "watchosDeviceArm64",
    "watchosSimulatorArm64",
    "mingwX64",
)

project.kotlin.sourceSets {
    ssDependsOn("nonJs", "common")
    ssDependsOn("concurrent", "nonJs")
    ssDependsOn("jvmAndAndroid", "concurrent")
    ssDependsOn("native", "concurrent")
    ssDependsOn("posix", "native")
    ssDependsOn("apple", "posix")
    ssDependsOn("appleNonWatchos", "apple")
    ssDependsOn("appleIosTvos", "apple")

    for (platform in kotlinPlatforms) {
        println("PLATFORM: $platform")
        val isMacos = platform.startsWith("macos")
        val isJs = platform.startsWith("js")
        val isJvm = platform.startsWith("jvm")
        val isAndroid = platform.startsWith("android")
        val isWasm = platform.startsWith("wasm")
        val isIos = platform.startsWith("ios")
        val isTvos = platform.startsWith("tvos")
        val isWatchos = platform.startsWith("watchos")
        val isNative = platform.contains("X86") || platform.contains("X64") || platform.contains("Arm")
        val isApple = isMacos || isIos || isTvos || isWatchos
        val isLinux = platform.startsWith("linux")
        val isWindows = platform.startsWith("mingw")
        val isPosix = isLinux || isApple
        val isConcurrent = !isJs && !isWasm
        val basePlatform = getKotlinBasePlatform(platform)
        if (isIos || isTvos) ssDependsOn(basePlatform, "appleIosTvos")
        if (isApple && !isWatchos) ssDependsOn(basePlatform, "appleNonWatchos")
        if (isConcurrent) ssDependsOn(basePlatform, "concurrent")
        if (isPosix) ssDependsOn(basePlatform, "posix")
        if (isApple) ssDependsOn(basePlatform, "apple")
        if (isNative) ssDependsOn(basePlatform, "native")
        if (isWasm) ssDependsOn(basePlatform, "nonJs")
        if (isJs) ssPair("js")
        if (isJvm || isAndroid) ssDependsOn(basePlatform, "jvmAndAndroid")
        if (platform != basePlatform) ssDependsOn(platform, basePlatform)
    }

    for (platform in kotlinPlatforms) {
        when (platform) {
            "jvm" -> kotlin.jvm {
                compilerOptions {
                    this.jvmTarget.set(JVM_TARGET)
                }
            }
            "js" -> kotlin.js {
                browser()
            }
            "wasm" -> {
                kotlin.wasmJs {
                }
                kotlin.sourceSets {
                    ssDependsOn("wasmJs", "wasm")
                }
            }
            "android" -> kotlin.androidTarget {}
            "linuxX64" -> kotlin.linuxX64()
            "linuxArm64" -> kotlin.linuxArm64()
            "tvosArm64" -> kotlin.tvosArm64()
            "tvosX64" -> kotlin.tvosX64()
            "tvosSimulatorArm64" -> kotlin.tvosSimulatorArm64()
            "macosX64" -> kotlin.macosX64()
            "macosArm64" -> kotlin.macosArm64()
            "iosArm64" -> kotlin.iosArm64()
            "iosSimulatorArm64" -> kotlin.iosSimulatorArm64()
            "iosX64" -> kotlin.iosX64()
            "watchosArm64" -> kotlin.watchosArm64()
            "watchosArm32" -> kotlin.watchosArm32()
            "watchosDeviceArm64" -> kotlin.watchosDeviceArm64()
            "watchosSimulatorArm64" -> kotlin.watchosSimulatorArm64()
            "mingwX64" -> kotlin.mingwX64()
        }
    }
}

dependencies {
    add("jvmMainApi", "net.java.dev.jna:jna:5.14.0")
    add("jvmMainApi", "net.java.dev.jna:jna-platform:5.14.0")
    add("jvmMainApi", "org.ow2.asm:asm:9.5")
    add("jvmMainApi", "org.ow2.asm:asm-util:9.5")
    commonMainApi("org.jetbrains.kotlinx:atomicfu:0.24.0")
    commonMainApi("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    commonTestApi("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0-RC")
}

tasks.withType(ProcessResources::class) {
    this.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType(Jar::class) {
    this.entryCompression = org.gradle.api.tasks.bundling.ZipEntryCompression.STORED
}

/*
afterEvaluate {
    kotlin.targets.filter { it.platformType == KotlinPlatformType.native }.forEach { target ->
        target.compilations.getByName("main") {
            (this as KotlinNativeCompilation).cinterops {
                val stb_image by creating {
                    defFile(project.file("../korlibs-image/nativeInterop/cinterop/stb_image.def"))
                }
            }
        }
    }
}
afterEvaluate {
    kotlin.targets.filter { it.platformType == KotlinPlatformType.native }.forEach { target ->
        target.compilations.getByName("main") {
            (this as KotlinNativeCompilation).cinterops {
                val miniffi by creating {
                    defFile(project.file("../korlibs-ffi/nativeInterop/cinterop/miniffi.def"))
                }
            }
        }
    }
}
*/

afterEvaluate {
    kotlin.targets.filter { it.platformType == KotlinPlatformType.native && it.name == "mingwX64" }.forEach { target ->
        target.compilations.getByName("main") {
            (this as KotlinNativeCompilation).cinterops {
                val win32ssl by creating {
                    defFile(project.file("../korlibs-io-network-core/nativeInterop/cinterop/win32ssl.def"))
                }
            }
        }
    }
}