import com.google.gson.*
import com.google.gson.JsonParser
import groovy.json.*
import groovy.namespace.*
import groovy.util.*
import org.gradle.api.internal.file.archive.*
import org.gradle.jvm.tasks.Jar
import org.gradle.plugins.signing.signatory.internal.pgp.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*
import java.io.*
import java.net.*
import java.util.*
import java.util.concurrent.*
import java.util.jar.*
import kotlin.collections.LinkedHashMap

plugins {
    //kotlin("multiplatform") version "2.0.20-Beta1"
    kotlin("multiplatform") version "2.0.0"
    id("com.android.library") version "8.2.2"
    `maven-publish`
    signing
}

val ONLY_KORGE = true
//val ONLY_KORGE = false

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

val kotlinPlatforms = buildList {
    add("jvm")
    add("js")
    add("wasm")
    add("android")
    add("iosArm64")
    add("iosSimulatorArm64")
    add("iosX64")
    add("tvosArm64")
    add("tvosX64")
    add("tvosSimulatorArm64")
    if (!ONLY_KORGE) {
        add("watchosArm64")
        add("watchosArm32")
        add("watchosDeviceArm64")
        add("watchosSimulatorArm64")
        add("mingwX64")
        add("linuxX64")
        add("linuxArm64")
        add("macosX64")
        add("macosArm64")
    }
}

project.kotlin.sourceSets {
    ssDependsOn("nonJs", "common")
    ssDependsOn("concurrent", "nonJs")
    ssDependsOn("jvmAndAndroid", "concurrent")
    ssDependsOn("native", "concurrent")
    ssDependsOn("posix", "native")
    ssDependsOn("apple", "posix")
    ssDependsOn("appleNonWatchos", "apple")
    ssDependsOn("appleIosTvos", "apple")
    ssDependsOn("appleIosTvosMacos", "apple")

    for (platform in kotlinPlatforms) {
        //println("PLATFORM: $platform")
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
        if (isIos || isTvos || isMacos) ssDependsOn(basePlatform, "appleIosTvosMacos")
        if (isApple && !isWatchos) ssDependsOn(basePlatform, "appleNonWatchos")
        if (isConcurrent) ssDependsOn(basePlatform, "concurrent")
        if (isPosix) ssDependsOn(basePlatform, "posix")
        if (isApple) ssDependsOn(basePlatform, "apple")
        if (isNative) ssDependsOn(basePlatform, "native")
        if (isWasm) ssDependsOn(basePlatform, "nonJs")
        if (isJs) ssPair("js")
        if (isJvm || isAndroid) ssDependsOn(basePlatform, "jvmAndAndroid")
        if (isAndroid) ssDependsOn(basePlatform, "android")
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
                browser {
                }
                compilerOptions {
                    target.set("es2015")
                }
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

//tasks.withType(Jar::class) {
//    //this.entryCompression = org.gradle.api.tasks.bundling.ZipEntryCompression.STORED
//    this.entryCompression = org.gradle.api.tasks.bundling.ZipEntryCompression.DEFLATED
//}

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

fun recompressJarWithoutCompression(inputJarPath: File, outputJarFile: File) {
    JarFile(inputJarPath).use { inputJarFile ->
        JarOutputStream(FileOutputStream(outputJarFile)).use { outputStream ->
            // Set compression level to 0 (no compression)
            outputStream.setLevel(0)

            inputJarFile.entries().asSequence().forEach { entry ->
                val newEntry = JarEntry(entry.name)
                newEntry.time = entry.time // Preserve the modification time
                outputStream.putNextEntry(newEntry)
                inputJarFile.getInputStream(entry).use { input ->
                    input.copyTo(outputStream)
                }
                outputStream.closeEntry()
            }
        }
    }
}


tasks {

    allprojects {
        val copyArtifactsToDirectory by tasks.registering(Task::class) {
            dependsOn("publishToMavenLocal")

            doLast {
                val base = rootProject.layout.buildDirectory.dir("artifacts")
                for (pub in publishing.publications.filterIsInstance<MavenPublication>()) {
                    //println(pub.artifacts.toList())
                    val basePath = pub.groupId.replace(".", "/") + "/" + pub.artifactId + "/" + pub.version
                    val baseDir = File(base.get().asFile, basePath)

                    val m2Dir = File(File(System.getProperty("user.home"), ".m2/repository"), basePath)

                    //println("m2Dir=$m2Dir")
                    // .module
                    copy {
                        from(m2Dir)
                        into(baseDir)
                    }
                }
            }
        }
    }

    val copyAllArtifactsToDirectory by registering(Task::class) {
        allprojects {
            dependsOn("${this.path}:copyArtifactsToDirectory")
        }
        doLast {
            // Recompress .klib files
            val base = rootProject.layout.buildDirectory.dir("artifacts").get().asFile
            val baseUncompressed = rootProject.layout.buildDirectory.dir("artifacts-uncompressed").get().asFile
            for (file in base.walkTopDown()) {
                val relative = file.relativeTo(base)
                val uncomp = File(baseUncompressed, relative.path)
                if (file.isDirectory) {
                    uncomp.mkdirs()
                } else {
                    if (file.name.endsWith(".klib") || file.name.endsWith(".jar")) {
                        recompressJarWithoutCompression(file, uncomp)
                    } else {
                        file.copyTo(uncomp, overwrite = true)
                    }
                }
                println("file=$relative")
                //val tmpFile = File(file.absolutePath + ".tmp")
                //file.copyTo(tmpFile, overwrite = true)
                //file.delete()
                //tmpFile.copyTo(file, overwrite = true)
                //tmpFile.delete()
            }
        }
    }

    val copyKorgeArtifactsToDirectory by registering(Sync::class) {
        dependsOn(copyAllArtifactsToDirectory)
        from(rootProject.layout.buildDirectory.dir("artifacts-uncompressed"))
        into(rootProject.layout.buildDirectory.dir("artifacts-korge"))
        exclude("**/*linux*/**")
        exclude("**/*mingw*/**")
        exclude("**/*macos*/**")
        exclude("**/*watch*/**")
    }

    val generateArtifactsZip by registering(Zip::class) {
        dependsOn(copyAllArtifactsToDirectory)
        from(rootProject.layout.buildDirectory.dir("artifacts-uncompressed"))
        archiveFileName = "korlibs-all-${REAL_VERSION}.zip"
        destinationDirectory = rootProject.layout.buildDirectory
    }

    val generateArtifactsTar by registering(Tar::class) {
        dependsOn(copyAllArtifactsToDirectory)
        from(rootProject.layout.buildDirectory.dir("artifacts-uncompressed"))
        //compression = Compression.GZIP
        //into(rootProject.layout.buildDirectory)
        archiveFileName = "korlibs-all-${REAL_VERSION}.tar"
        destinationDirectory = rootProject.layout.buildDirectory
    }

    val generateArtifactsKorgeTar by registering(Tar::class) {
        dependsOn(copyKorgeArtifactsToDirectory)
        from(rootProject.layout.buildDirectory.dir("artifacts-korge"))
        //compression = Compression.GZIP
        //into(rootProject.layout.buildDirectory)
        archiveFileName = "korlibs-all-korge-${REAL_VERSION}.tar"
        destinationDirectory = rootProject.layout.buildDirectory
    }

    // winget install zstd
    val generateArtifactsTarZstd by registering(Exec::class) {
        val rootFile = rootProject.layout.buildDirectory.asFile.get()
        dependsOn(generateArtifactsTar)
        commandLine(
            "zstd", "-z",
            //"--ultra", "-22",
            "-17",
            "-f", File(rootFile, "korlibs-all-${REAL_VERSION}.tar").absolutePath,
            "-o", File(rootFile, "korlibs-all-${REAL_VERSION}.tar.zstd").absolutePath
        )
    }

    // winget install zstd
    val generateArtifactsKorgeTarZstd by registering(Exec::class) {
        val rootFile = rootProject.layout.buildDirectory.asFile.get()
        dependsOn(generateArtifactsKorgeTar)
        commandLine(
            "zstd", "-z",
            //"--ultra", "-22",
            "-17",
            "-f", File(rootFile, "korlibs-all-korge-${REAL_VERSION}.tar").absolutePath,
            "-o", File(rootFile, "korlibs-all-korge-${REAL_VERSION}.tar.zstd").absolutePath
        )
    }

    val generateArtifactsTarXz by registering(Exec::class) {
        dependsOn(copyAllArtifactsToDirectory)
        val rootFile = rootProject.layout.buildDirectory.asFile.get()
        doFirst {
            rootFile.mkdirs()
        }
        commandLine(
            "tar", "-cJf",
            File(rootFile, "korlibs-all-${REAL_VERSION}.tar.xz").absolutePath,
            File(rootFile, "artifacts-uncompressed").absolutePath,
        )
    }
}


//val compileKotlinIosArm64 = tasks.findByName("compileKotlinIosArm64") as KotlinNativeCompile
//println("!!!!!!!!!!!!!!!!!!!!!")
//println(compileKotlinIosArm64.dependsOn.toList())
//println(compileKotlinIosArm64.finalizedBy.getDependencies(null).toList())
//println(compileKotlinIosArm64.outputFile.get())


/////////////////////////////////////////////////////////////////
// PUBLISHING
/////////////////////////////////////////////////////////////////

fun Project.doOnce(uniqueName: String, block: () -> Unit) {
    val key = "doOnce-$uniqueName"
    if (!rootProject.extra.has(key)) {
        rootProject.extra.set(key, true)
        block()
    }
}

class SonatypeProps(val project: Project) {
    // Signing
    val signingKey: String? = System.getenv("ORG_GRADLE_PROJECT_signingKey") ?: project.findProperty("signing.signingKey")?.toString()
    val signingPassword: String? = System.getenv("ORG_GRADLE_PROJECT_signingPassword") ?: project.findProperty("signing.password")?.toString()
    val globalSignatories: CachedInMemoryPgpSignatoryProvider? = when {
        signingKey != null && signingPassword != null -> CachedInMemoryPgpSignatoryProvider(signingKey, signingPassword)
        else -> null
    }

    val sonatypePublishUserNull: String? =
        (System.getenv("SONATYPE_USERNAME") ?: rootProject.findProperty("SONATYPE_USERNAME")?.toString() ?: project.findProperty("sonatypeUsername")
            ?.toString())
    val sonatypePublishPasswordNull: String? =
        (System.getenv("SONATYPE_PASSWORD") ?: rootProject.findProperty("SONATYPE_PASSWORD")?.toString() ?: project.findProperty("sonatypePassword")
            ?.toString())
    val sonatype: Sonatype? = when {
        sonatypePublishUserNull != null && sonatypePublishPasswordNull != null -> Sonatype(sonatypePublishUserNull, sonatypePublishPasswordNull)
        else -> null
    }

    val stagedRepositoryId: String? by lazy {
        System.getenv("stagedRepositoryId")
            ?: findProperty("stagedRepositoryId")?.toString()
            ?: File("stagedRepositoryId").takeIf { it.exists() }?.readText()?.trim()
    }

    fun createTasks(project: Project) = with(project) {
        if (sonatype != null) {
            tasks.create("startReleasingMavenCentral", Task::class) {
                doLast {
                    val profileId = sonatype.findProfileIdByGroupId("com.soywiz")
                    val stagedRepositoryId = sonatype.startStagedRepository(profileId)
                    println("profileId=$profileId")
                    println("stagedRepositoryId=$stagedRepositoryId")
                    GithubCI.setOutput("stagedRepositoryId", stagedRepositoryId)
                    File("stagedRepositoryId").writeText(stagedRepositoryId)
                }
            }
            rootProject.tasks.create<Task>("releaseMavenCentral") {
                //val groupId = "com.soywiz.korge" ?: rootProject.group.toString()
                val groupId = "com.soywiz.korge"
                doLast {
                    //if (!sonatype.releaseGroupId(rootProject.group.toString())) {
                    try {
                        if (!sonatype.releaseGroupId(groupId)) {
                            error("Can't promote artifacts. Check log for details")
                        }
                    } finally {
                        File("stagedRepositoryId").delete()
                    }
                }
            }
        }

        if (stagedRepositoryId != null) {
            println("stagedRepositoryId=$stagedRepositoryId")
        }
    }
}

val sonatypeProps = SonatypeProps(rootProject)

sonatypeProps.createTasks(rootProject)


publishing {
    repositories {
        if (sonatypeProps.sonatype != null) {
            maven {
                credentials {
                    username = sonatypeProps.sonatype.user
                    password = sonatypeProps.sonatype.pass
                }
                url = when {
                    version.toString().contains("-SNAPSHOT") -> uri("https://oss.sonatype.org/content/repositories/snapshots/")
                    sonatypeProps.stagedRepositoryId != null -> uri("https://oss.sonatype.org/service/local/staging/deployByRepositoryId/${sonatypeProps.stagedRepositoryId}/")
                    else -> uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                }
                doOnce("showDeployTo") { logger.info("DEPLOY mavenRepository: $url") }
            }
        }
    }

    publications.withType(MavenPublication::class) {
        //println(this.artifacts.stream().map { it.file })
        //copyArtifactsToDirectory.get().from(this.artifacts.stream().map { it.file })

        val publication = this
        val jarTaskName = "${publication.name}JavadocJar"
        //println(jarTaskName)
        val javadocJar = tasks.create<Jar>(jarTaskName) {
            archiveClassifier.set("javadoc")
            archiveBaseName.set(jarTaskName)
        }
        publication.artifact(javadocJar)

        //println("PUBLICATION: ${publication.name}")

        fun getCustomProp(key: String, defaultValue: String): String {
            // @TODO: Actually be able to override it
            return defaultValue
        }

        //if (multiplatform) {
        //if (!isGradlePluginMarker) {
        run {
            val defaultGitUrl = "https://github.com/korlibs/korge-korlibs"
            publication.pom.also { pom ->
                pom.name.set(project.name)
                pom.description.set(project.description ?: getCustomProp("project.description", project.description ?: project.name))
                pom.url.set(getCustomProp("project.scm.url", defaultGitUrl))
                pom.licenses {
                    license {
                        name.set(getCustomProp("project.license.name", "MIT"))
                        url.set(getCustomProp("project.license.url", "https://raw.githubusercontent.com/korlibs/korge-korlibs/main/LICENSE"))
                    }
                }
                pom.developers {
                    developer {
                        id.set(getCustomProp("project.author.id", "soywiz"))
                        name.set(getCustomProp("project.author.name", "Carlos Ballesteros Velasco"))
                        email.set(getCustomProp("project.author.email", "soywiz@gmail.com"))
                    }
                }
                pom.scm {
                    url.set(getCustomProp("project.scm.url", defaultGitUrl))
                }
            }
            publication.pom.withXml {
                if (publication.pom.packaging == "aar") {
                    //println("baseProjectName=$baseProjectName")
                    asNode().apply {
                        val nodes: NodeList = this.getAt(QName("dependencies")).getAt("dependency").getAt("scope")
                        for (node in nodes as List<Node>) {
                            node.setValue("compile")
                        }
                    }
                }
            }
        }
    }
}

// Signing
if (sonatypeProps.globalSignatories != null) {
    signing {
        sign(publishing.publications)
        this.signatories = sonatypeProps.globalSignatories
    }
}

open class CachedInMemoryPgpSignatoryProvider(signingKey: String?, signingPassword: String?) : InMemoryPgpSignatoryProvider(signingKey, signingPassword) {
    var cachedPhpSignatory: PgpSignatory? = null
    override fun getDefaultSignatory(project: Project): PgpSignatory? {
        //project.rootProject
        //println("getDefaultSignatory:$project")
        if (cachedPhpSignatory == null) {
            cachedPhpSignatory = super.getDefaultSignatory(project)
        }
        return cachedPhpSignatory
    }
}


open class Sonatype(
    val user: String,
    val pass: String,
    val BASE: String = DEFAULT_BASE
) {
    companion object {
        val DEFAULT_BASE = "https://oss.sonatype.org/service/local/staging"
        private val BASE = DEFAULT_BASE

        //fun fromGlobalConfig(): Sonatype {
        //    val props = Properties().also { it.load(File(System.getProperty("user.home") + "/.gradle/gradle.properties").readText().reader()) }
        //    return Sonatype(props["sonatypeUsername"].toString(), props["sonatypePassword"].toString(), DEFAULT_BASE)
        //}

        //fun fromProject(project: Project): Sonatype {
        //    return Sonatype(project.sonatypePublishUser, project.sonatypePublishPassword)
        //}

        //@JvmStatic
        //fun main(args: Array<String>) {
        //    val sonatype = fromGlobalConfig()
        //    sonatype.releaseGroupId("korlibs")
        //}
    }

    fun releaseGroupId(groupId: String = "korlibs"): Boolean {
        println("Trying to release groupId=$groupId")
        val profileId = findProfileIdByGroupId(groupId)
        println("Determined profileId=$profileId")
        val repositoryIds = findProfileRepositories(profileId).toMutableList()
        if (repositoryIds.isEmpty()) {
            println("Can't find any repositories for profileId=$profileId for groupId=$groupId. Artifacts weren't upload?")
            return false
        }
        val totalRepositories = repositoryIds.size
        var promoted = 0
        var stepCount = 0
        var retryCount = 0
        process@while (true) {
            stepCount++
            if (stepCount > 200) {
                error("Too much steps. stepCount=$stepCount")
            }
            repo@for (repositoryId in repositoryIds.toList()) {
                val state = try {
                    getRepositoryState(repositoryId)
                } catch (e: SimpleHttpException) {
                    when (e.responseCode) {
                        404 -> {
                            println("Can't find $repositoryId anymore. Probably released. Stopping")
                            repositoryIds.remove(repositoryId)
                            continue@repo
                        }
                        // Server error
                        // @TODO: We should handle retrying on other operations too
                        in 500..599 -> { // Sometimes  HTTP Error 502 Bad Gateway
                            e.printStackTrace()
                            println("Retrying...")
                            Thread.sleep(15_000L)
                            retryCount++
                            continue@repo
                        }
                        else -> {
                            throw e
                        }
                    }
                }
                when {
                    state.transitioning -> {
                        println("Waiting transition $state")
                    }
                    // Even if open, if there are notifications we should drop it
                    state.notifications > 0 -> {
                        println("Dropping release because of error state.notifications=$state")
                        println(" - activity: " + getRepositoryActivity(repositoryId))
                        repositoryDrop(repositoryId)
                        repositoryIds.remove(repositoryId)
                    }
                    state.isOpen -> {
                        println("Closing open repository $state")
                        println(" - activity: " + getRepositoryActivity(repositoryId))
                        repositoryClose(repositoryId)
                    }
                    else -> {
                        println("Promoting repository $state")
                        println(" - activity: " + getRepositoryActivity(repositoryId))
                        repositoryPromote(repositoryId)
                        promoted++
                    }
                }
            }
            if (repositoryIds.isEmpty()) {
                println("Completed promoted=$promoted, totalRepositories=$totalRepositories, retryCount=$retryCount")
                break@process
            }
            Thread.sleep(30_000L)
        }

        return promoted == totalRepositories
    }

    open val client = SimpleHttpClient(user, pass)

    fun getRepositoryState(repositoryId: String): RepoState {
        val info = client.request("${BASE}/repository/$repositoryId")
        //println("info: ${info.toStringPretty()}")
        return RepoState(
            repositoryId = repositoryId,
            type = info["type"].asString,
            notifications = info["notifications"].asInt,
            transitioning = info["transitioning"].asBoolean,
        )
    }

    fun getRepositoryActivity(repositoryId: String): String {
        val info = client.request("${BASE}/repository/$repositoryId/activity")
        //println("info: ${info.toStringPretty()}")
        return info.toStringPretty()
    }

    data class RepoState(
        val repositoryId: String,
        // "open" or "closed"
        val type: String,
        val notifications: Int,
        val transitioning: Boolean
    ) {
        val isOpen get() = type == "open"
    }

    private fun getDataMapForRepository(repositoryId: String): Map<String, Map<*, *>> {
        return mapOf(
            "data" to mapOf(
                "stagedRepositoryIds" to listOf(repositoryId),
                "description" to "",
                "autoDropAfterRelease" to true,
            )
        )
    }

    fun repositoryClose(repositoryId: String) {
        client.request("${BASE}/bulk/close", getDataMapForRepository(repositoryId))
    }

    fun repositoryPromote(repositoryId: String) {
        client.request("${BASE}/bulk/promote", getDataMapForRepository(repositoryId))
    }

    fun repositoryDrop(repositoryId: String) {
        client.request("${BASE}/bulk/drop", getDataMapForRepository(repositoryId))
    }

    fun findProfileRepositories(profileId: String): List<String> {
        return client.request("${BASE}/profile_repositories")["data"].list
            .filter { it["profileId"].asString == profileId }
            .map { it["repositoryId"].asString }
    }

    fun findProfileIdByGroupId(groupId: String): String {
        val profiles = client.request("$BASE/profiles")["data"].list
        return profiles
            .filter { groupId.startsWith(it["name"].asString) }
            .map { it["id"].asString }
            .firstOrNull() ?: error("Can't find profile with group id '$groupId'")
    }

    fun startStagedRepository(profileId: String): String {
        return client.request("${BASE}/profiles/$profileId/start", mapOf(
            "data" to mapOf("description" to "Explicitly created by easy-kotlin-mpp-gradle-plugin")
        ))["data"]["stagedRepositoryId"].asString
    }
}

open class SimpleHttpClient(
    val user: String? = null,
    val pass: String? = null
) {
    open fun request(url: String, body: Any? = null): JsonElement {
        val post = (URL(url).openConnection()) as HttpURLConnection
        post.connectTimeout = 300 * 1000 // 300 seconds // 5 minutes
        post.readTimeout = 300 * 1000 // 300 seconds // 5 minutes
        post.requestMethod = (if (body != null) "POST" else "GET")
        if (user != null && pass != null) {
            val authBasic = Base64.getEncoder().encodeToString("${user}:${pass}".toByteArray(Charsets.UTF_8))
            post.setRequestProperty("Authorization", "Basic $authBasic")
        }
        post.setRequestProperty("Accept", "application/json")
        if (body != null) {
            post.doOutput = true
            post.setRequestProperty("Content-Type", "application/json")
            val bodyText = if (body is String) body.toString() else JsonOutput.toJson(body)
            //println(bodyText)
            post.outputStream.write(bodyText.toByteArray(Charsets.UTF_8))
        }
        val postRC = post.responseCode
        val postMessage = post.responseMessage
        //println(postRC)
        if (postRC < 400) {
            return JsonParser.parseString(post.inputStream.reader(Charsets.UTF_8).readText())
        } else {
            val errorString = try {
                post.errorStream?.reader(Charsets.UTF_8)?.readText()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                null
            }
            throw SimpleHttpException(postRC, postMessage, url, errorString)
        }
    }
}

class SimpleHttpException(val responseCode: Int, val responseMessage: String, val url: String, val errorString: String?) :
    RuntimeException("HTTP Error $responseCode $responseMessage - $url - $errorString")

operator fun JsonElement.get(key: String): JsonElement = asJsonObject.get(key)
val JsonElement.list: JsonArray get() = asJsonArray

private val prettyGson by lazy { GsonBuilder().setPrettyPrinting().create() }
fun JsonElement.toStringPretty() = prettyGson.toJson(this)

object GithubCI {
    fun setOutput(name: String, value: String) {
        val GITHUB_OUTPUT = System.getenv("GITHUB_OUTPUT")
        if (GITHUB_OUTPUT != null) {
            File(GITHUB_OUTPUT).appendText("$name=$value\n")
        } else {
            println("::set-output name=$name::$value")
        }
    }
}
