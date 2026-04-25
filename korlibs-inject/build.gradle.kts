import com.vanniktech.maven.publish.*
import org.gradle.api.internal.tasks.testing.*
import org.gradle.api.tasks.testing.logging.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.targets.js.ir.*
import java.util.*

plugins {
    kotlin("multiplatform") version "2.3.20"
    id("com.android.library") version "8.13.1"
    id("org.jetbrains.kotlinx.kover") version "0.9.3" apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.18.1"
    id("org.jetbrains.dokka") version "2.1.0"
    id("com.vanniktech.maven.publish") version "0.36.0" apply false
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
// JDK_VERSION controls Android compileOptions source/target compat only.
// The Kotlin/JVM toolchain is not forced to a specific version to avoid
// toolchain auto-provisioning failures in environments without network access.
// Bytecode output for Kotlin targets is controlled by JVM_TARGET (1.8) above.
val JDK_VERSION = JavaVersion.VERSION_1_8
val GROUP = "org.korge"

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
        compileOptions {
            // Keep Android bytecode at Java 8 regardless of toolchain JDK version
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        //signingConfigs {
        //    debug {
        //        […]
        //    }
        //    release {
        //        […]
        //    }
        //}
        compileSdk = 33
        namespace = "org.korge.${project.name.replace("-", ".")}"
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
    MicroAmper(this).configure()
}

open class DenoTestTask : AbstractTestTask() {
//open class DenoTestTask : KotlinTest() {

    //var isDryRun by org.jetbrains.kotlin.gradle.utils.property { false }

    @get:Internal
    val projectPathName: String = project.path.trim(':').replace(':', '-')

    @get:Internal
    val rootDir: File = project.rootProject.rootDir

    @get:Internal
    val projectDir: File = project.projectDir

    init {
        this.group = "verification"
        this.dependsOn("compileTestDevelopmentExecutableKotlinJs")
    }

    //@Option(option = "tests", description = "Specify tests to execute as a filter")
    //@Input
    //var tests: String = ""

    init {
        this.reports {
            junitXml.outputLocation.set(project.file("build/test-results/jsDenoTest/"))
            html.outputLocation.set(project.file("build/reports/tests/jsDenoTest/"))
        }
        binaryResultsDirectory.set(project.file("build/test-results/jsDenoTest/binary"))
        //reports.enabledReports["junitXml"]!!.optional
        //reports.junitXml.outputLocation.opt
        //reports.enabledReports.clear()
        //reports.junitXml.outputLocation.set(project.file("build/deno-test-results"))
    }

    override fun createTestExecuter(): TestExecuter<out TestExecutionSpec> {
        return DenoTestExecuter(projectPathName, rootDir, projectDir, this.filter)
    }
    //override fun createTestExecuter(): TestExecuter<out TestExecutionSpec> = TODO()
    override fun createTestExecutionSpec(): TestExecutionSpec = DenoTestExecutionSpec()

    init {
        outputs.upToDateWhen { false }
    }

    class DenoTestExecuter(val projectPathName: String, val rootDir: File, val projectDir: File, val filter: TestFilter) : TestExecuter<DenoTestExecutionSpec> {
        override fun execute(testExecutionSpec: DenoTestExecutionSpec, testResultProcessor: TestResultProcessor) {
            val baseTestFileNameBase = "$projectPathName-test"
            val baseTestFileName = "$baseTestFileNameBase.mjs"
            val runFile = File(rootDir, "build/js/packages/$baseTestFileNameBase/kotlin/$baseTestFileName.deno.mjs")

            runFile.parentFile.mkdirs()
            runFile.writeText(
                //language=js
                """
                    var describeStack = []
                    globalThis.describe = (name, callback) => { describeStack.push(name); try { callback() } finally { describeStack.pop() } }
                    globalThis.it = (name, callback) => { return Deno.test({ name: describeStack.join(".") + "." + name, fn: callback}) }
                    globalThis.xit = (name, callback) => { return Deno.test({ name: describeStack.join(".") + "." + name, ignore: true, fn: callback}) }
                    function exists(path) { try { Deno.statSync(path); return true } catch (e) { return false } }
                    // Polyfill required for kotlinx-coroutines that detects window 
                    window.postMessage = (message, targetOrigin) => { const ev = new Event('message'); ev.source = window; ev.data = message; window.dispatchEvent(ev); }
                    const file = './${baseTestFileName}';
                    if (exists(file)) await import(file)
                """.trimIndent())

            //testResultProcessor.started()
            val process = ProcessBuilder(buildList<String> {
                add("deno")
                add("test")
                add("--unstable-ffi")
                add("--unstable-webgpu")
                add("-A")
                if (filter.includePatterns.isEmpty()) {
                    add("--filter=${filter.includePatterns.joinToString(",")}")
                }
                add("--junit-path=${File(projectDir, "build/test-results/jsDenoTest/junit.xml").absolutePath}")
                add(runFile.absolutePath)
            }).directory(runFile.parentFile)
                .start()
            var id = 0
            val buffered = process.inputStream.bufferedReader()
            var capturingOutput = false
            var currentTestId: String? = null
            var currentTestExtra: String = "ok"
            var failedCount = 0

            fun flush() {
                if (currentTestId != null) {
                    try {
                        val type = when {
                            currentTestExtra.contains("skip", ignoreCase = true) || currentTestExtra.contains("ignored", ignoreCase = true) -> TestResult.ResultType.SKIPPED
                            currentTestExtra.contains("error", ignoreCase = true) || currentTestExtra.contains("failed", ignoreCase = true) -> TestResult.ResultType.FAILURE
                            currentTestExtra.contains("ok", ignoreCase = true) -> TestResult.ResultType.SUCCESS
                            else -> TestResult.ResultType.SUCCESS
                        }
                        if (type == TestResult.ResultType.FAILURE) {
                            testResultProcessor.failure(currentTestId, DefaultTestFailure.fromTestFrameworkFailure(Exception("FAILED").also { it.stackTrace = arrayOf() }, null))
                            failedCount++
                        }
                        testResultProcessor.completed(currentTestId, TestCompleteEvent(System.currentTimeMillis(), type))
                    } catch (e: Throwable) {
                        //System.err.println("COMPLETED_ERROR: ${e}")
                        e.printStackTrace()
                    }
                    currentTestId = null
                }
            }

            testResultProcessor.started(DefaultTestSuiteDescriptor("deno", "deno"), TestStartEvent(System.currentTimeMillis()))

            for (line in buffered.lines()) {
                println("::: $line")
                when {
                    line == "------- output -------" -> {
                        capturingOutput = true
                    }
                    line == "----- output end -----" -> {
                        capturingOutput = false
                    }
                    capturingOutput -> {
                        // Avoid deprecated DefaultTestOutputEvent constructor usage.
                    }
                    line.contains("...") -> {
                        //DefaultNestedTestSuiteDescriptor()
                        flush()
                        val (name, extra) = line.split("...").map { it.trim() }
                        //currentTestId = "$name${id++}"
                        currentTestId = "deno.myid${id++}"
                        //val demo = CompositeId("Unit", "Name${id++}")
                        //val descriptor = DefaultTestMethodDescriptor(currentTestId, name.substringBeforeLast('.'), name.substringAfterLast('.'))

                        val descriptor = DefaultTestMethodDescriptor(currentTestId, name.substringBeforeLast('.'), name)
                        currentTestExtra = extra
                        testResultProcessor.started(
                            descriptor,
                            TestStartEvent(System.currentTimeMillis())
                        )
                    }
                }
            }
            flush()

            testResultProcessor.completed("deno", TestCompleteEvent(System.currentTimeMillis(), if (failedCount == 0) TestResult.ResultType.SUCCESS else TestResult.ResultType.FAILURE))

            process.waitFor()
            System.err.print(process.errorStream.readBytes().decodeToString())
        }

        override fun stopNow() {
        }
    }

    class DenoTestExecutionSpec : TestExecutionSpec
}

private fun Project.configureCentralPortalCompatibilityProps() {
    // Map legacy env/property names to the exact names the Vanniktech plugin expects.
    // Vanniktech reads: signingInMemoryKey, signingInMemoryKeyPassword, mavenCentralUsername, mavenCentralPassword
    val extras = extensions.extraProperties

    fun mapIfAbsent(targetKey: String, vararg sources: () -> String?) {
        // Skip if already set to a non-blank value via extras or project properties
        if (extras.has(targetKey) && extras[targetKey]?.toString().isNullOrBlank().not()) return
        if (findProperty(targetKey)?.toString().isNullOrBlank().not()) return
        // Find the first non-blank value among the sources
        val value = sources.firstNotNullOfOrNull { it()?.takeIf { v -> v.isNotBlank() } } ?: return
        extras[targetKey] = value
    }

    mapIfAbsent("signingInMemoryKey",
        { System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKey") },
        { System.getenv("ORG_GRADLE_PROJECT_signingKey") },
        { findProperty("signing.signingKey")?.toString() }
    )
    mapIfAbsent("signingInMemoryKeyPassword",
        { System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKeyPassword") },
        { System.getenv("ORG_GRADLE_PROJECT_signingPassword") },
        { findProperty("signing.password")?.toString() }
    )
    mapIfAbsent("mavenCentralUsername",
        { System.getenv("ORG_GRADLE_PROJECT_mavenCentralUsername") },
        { System.getenv("SONATYPE_USERNAME") },
        { rootProject.findProperty("SONATYPE_USERNAME")?.toString() },
        { findProperty("sonatypeUsername")?.toString() }
    )
    mapIfAbsent("mavenCentralPassword",
        { System.getenv("ORG_GRADLE_PROJECT_mavenCentralPassword") },
        { System.getenv("SONATYPE_PASSWORD") },
        { rootProject.findProperty("SONATYPE_PASSWORD")?.toString() },
        { findProperty("sonatypePassword")?.toString() }
    )
}

private fun Project.hasSigningCredentials(): Boolean {
    // A credential is only considered present when it resolves to a non-blank string.
    fun String?.isPresent() = !isNullOrBlank()
    return System.getenv("ORG_GRADLE_PROJECT_signingInMemoryKey").isPresent() ||
            System.getenv("ORG_GRADLE_PROJECT_signingKey").isPresent() ||
            findProperty("signingInMemoryKey")?.toString().isPresent() ||
            findProperty("signing.secretKeyRingFile")?.toString().isPresent() ||
            extensions.extraProperties.runCatching { get("signingInMemoryKey")?.toString() }.getOrNull().isPresent()
}

subprojects {
    //apply<KotlinMultiplatformPlugin>()
    apply(plugin = "kotlin-multiplatform")
    apply(plugin = "com.vanniktech.maven.publish")

    configureCentralPortalCompatibilityProps()

    kotlin {
        js {
            //nodejs()
            browser {
                compilerOptions {
                    target.set("es2015")
                }
            }
        }
    }

    kotlin {
        //if (targets.any { it.name.contains("android") }) {
        androidTarget {
            this.compilerOptions.jvmTarget.set(JVM_TARGET)
            publishLibraryVariants("release")
            //publishLibraryVariants("release", "debug")
        }
        //}
    }

    tasks {
        //println(this.findByName("compileTestKotlinJs")!!::class)
        //println(this.findByName("compileTestKotlinJs")!!.dependsOn?.toList())
        //println(this.findByName("compileTestKotlinJs")?.outputs?.files?.toList())

        val jsDenoTest by registering(DenoTestTask::class)
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class) {
        compilerOptions.suppressWarnings.set(true)
        // @TODO: We should actually, convert warnings to errors and start removing warnings
        compilerOptions.freeCompilerArgs.add("-nowarn")
        //println("${project.name} ${this::class.java} : ${this.name}")
    }

    //class KotlinNativeLinkDoLast : Copy() {
    //}

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink::class) {
        // /Users/korge/projects/korge-korlibs/korlibs-io/build/bin/iosSimulatorArm64/debugTest
        //println(this.target)
        //val target = Regex("^link(.*?)Test.*$").find(this.name)?.groupValues?.getOrNull(1)?.replaceFirstChar { it.lowercaseChar() }
        //println(target)
        //val compileTaskName = this.name.replace(Regex("^link(.*?)Test.*$")) { "compileTestKotlin${it.groupValues[1]}" }
        //val compileTask = tasks.findByName(compileTaskName) as? KotlinNativeCompile?

        val folder = this.outputs.files.toList().firstOrNull()
        val fromFolder = File(project.projectDir, "testresources")

        if (folder != null) {
            val copyAfterLink = tasks.register("${this.name}CopyResources", Copy::class.java) {
                from(fromFolder)
                into(folder)
            }
            this.dependsOn(copyAfterLink)
        }
    }

    tasks.withType(org.gradle.api.tasks.testing.AbstractTestTask::class) {
        testLogging {
            events = mutableSetOf(
                TestLogEvent.SKIPPED,
                TestLogEvent.FAILED,
                TestLogEvent.STANDARD_OUT, TestLogEvent.STANDARD_ERROR
            )
            exceptionFormat = TestExceptionFormat.FULL
            showStandardStreams = true
            showStackTraces = true
        }
    }

    kotlin.targets.withType(KotlinJsIrTarget::class) {
        //println("TARGET: $this")
        //nodejs()
        browser {
            //testTask { useKarma { useChromeHeadless() } }
            testRuns.getByName(KotlinTargetWithTests.DEFAULT_TEST_RUN_NAME).executionTask.configure {
                useKarma {
                    useChromeHeadless()
                    File(project.rootProject.rootDir, "karma.config.d").takeIf { it.exists() }?.let {
                        useConfigDirectory(it)
                    }
                }
            }
        }
    }

    fun String.escape(): String = buildString(length) {
        for (c in this@escape) when (c) { '\n' -> append("\\n"); '\r' -> append("\\r"); '\t' -> append("\\t"); '\\' -> append("\\\\"); else -> append(c)  }
    }
    fun String.quote(): String = "\"${escape()}\""

    open class TestProcessResourcesLast : DefaultTask() {
        @Input
        lateinit var dirs: List<File>

        @TaskAction
        fun action() {
            for (dir in dirs) {
                for (file in dir.walkTopDown()) {
                    if (file.isDirectory) {
                        //println("file=$file")
                        File(file, "\$catalog.json").writeText(generateCatalog(file))
                    }
                }
            }
        }

        fun generateCatalog(folder: File): String = buildString {
            appendLine("{")
            for (file in folder.listFiles() ?: arrayOf()) {
                val fileName = if (file.isDirectory) "${file.name}/" else file.name
                appendLine(" ${fileName.quote()} : [${file.length()}, ${file.lastModified()}],")
            }
            appendLine("}")
        }
    }

    for (taskName in listOf("jsTestProcessResources", "wasmTestProcessResources")) {
        tasks.findByName(taskName)?.apply {
            val copyResourcesTask = tasks.register("${taskName}CopyResources", TestProcessResourcesLast::class.java) {
                dirs = this@apply.outputs.files.toList().filter { it.isDirectory }
            }
            this.dependsOn(copyResourcesTask)
        }
    }

    // This is required on linux because testResources / testresources mismatch (that doesn't happen on Windows or Mac)
    // See https://github.com/korlibs/korge-korlibs/issues/6
    tasks.withType(ProcessResources::class) {
        if (this.name.contains("js", ignoreCase = true) || this.name.contains("wasm", ignoreCase = true)) {
            if (this.name.contains("Test")) {
                from("testresources")
            }
            from("resources")
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }

    //println(tasks.findByName("jsProcessResources")!!::class)

    // Publishing
    extensions.configure<MavenPublishBaseExtension> {
        if (project.hasSigningCredentials()) {
            publishToMavenCentral()
            signAllPublications()
        }

        coordinates(project.group.toString(), project.name, project.version.toString())

        pom {
            val defaultGitUrl = "https://github.com/korlibs/korlibs"
            name.set(project.name)
            description.set(project.description ?: project.name)
            url.set(defaultGitUrl)
            licenses {
                license {
                    name.set("MIT")
                    url.set("https://raw.githubusercontent.com/korlibs/korge/refs/heads/main/LICENSE")
                }
            }
            developers {
                developer {
                    id.set("korge")
                    name.set("Korge Team")
                    email.set("info@korge.org")
                }
            }
            scm {
                url.set(defaultGitUrl)
            }
        }
    }

    //println(KotlinCompilerVersion.VERSION)
}

/*
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().apply{
        this.nodeVersion = "20.12.2"
        //download = false
    }
}
*/


// Tiny, coupled and limited variant of amper compatible with the current structure, so we can bump to Kotlin 2.0.0 in the meantime, while amper is discarded or evolved.
class MicroAmper(val project: Project) {
    private var kotlinPlatforms = mutableListOf<String>()
    private var kotlinAliases = LinkedHashMap<String, List<String>>()
    private var deps = mutableListOf<Dep>()
    //val kotlinBasePlatforms by lazy { kotlinPlatforms.groupBy { getKotlinBasePlatform(it) }.filter { it.value != listOf(it.key) } }
    val kotlinBasePlatforms by lazy { kotlinPlatforms.groupBy { getKotlinBasePlatform(it) } }

    fun getKotlinBasePlatform(platform: String): String = platform.removeSuffix("X64").removeSuffix("X86").removeSuffix("Arm64").removeSuffix("Arm32").removeSuffix("Simulator").removeSuffix("Device").also {
        check(it.all { it.isLowerCase() && !it.isDigit() })
    }

    data class Dep(val path: String, val exported: Boolean, val test: Boolean, val platform: String) {
        val rplatform = platform.takeIf { it.isNotEmpty() } ?: "common"
        val configuration = "$rplatform${if (test) "Test" else "Main"}${if (exported) "Api" else "Implementation"}"
    }

    fun parseFile(file: File, lines: List<String> = file.readLines()) {
        var mode = ""

        for (line in lines) {
            val tline = line.substringBeforeLast('#').trim().takeIf { it.isNotEmpty() } ?: continue

            if (line.startsWith(" ") || line.startsWith("\t") || line.startsWith("-")) {
                when {
                    mode == "product" -> {
                        //println("product=$tline")
                        when {
                            tline.startsWith("platforms:") -> {
                                val platforms = tline.substringAfter('[').substringBeforeLast(']').split(',').map { it.trim() }
                                kotlinPlatforms.addAll(platforms)
                            }
                        }
                    }
                    mode == "aliases" -> {
                        //println("aliases=$tline")
                        if (tline.startsWith("-")) {
                            val (alias2, platforms2) = tline.split(":", limit = 2)
                            val alias = alias2.trim('-', ' ')
                            val platforms = platforms2.trim('[', ']', ' ').split(',').map { it.trim() }
                            //println(" -> alias=$alias, platforms=$platforms")
                            kotlinAliases[alias] = platforms
                        }
                    }
                    mode.contains("dependencies") -> {
                        val platform = mode.substringAfterLast('@', "")
                        val test = mode.startsWith("test")
                        val exported = line.contains(Regex(":\\s*exported"))
                        val path = tline.removePrefix("-").removeSuffix(": exported").removeSuffix(":exported").trim()
                        deps += Dep(path = path, exported = exported, test = test, platform = platform)
                    }
                }
            } else {
                if (tline.endsWith(":")) {
                    mode = tline.trimEnd(':').trim()
                }
                if (tline.startsWith("apply:")) {
                    val paths = tline.substringAfter(':').trim('[', ',', ' ', ']').split(",")
                    for (path in paths) {
                        parseFile(file.parentFile.resolve(path))
                    }
                    //parseFile(File(project.rootDir, "common.module-template.yaml"))
                }
            }
        }
    }

    data class SourceSetPair(val main: KotlinSourceSet, val test: KotlinSourceSet) {
        fun dependsOn(other: SourceSetPair) {
            main.dependsOn(other.main)
            test.dependsOn(other.test)
        }
    }

    val sourceSetPairs = LinkedHashMap<String, SourceSetPair>()

    // specific depends on more generic
    fun NamedDomainObjectContainer<KotlinSourceSet>.ssDependsOn(base: String, other: String) {
        if (base == other) return
        //println("$base dependsOn $other")
        ssPair(base).dependsOn(ssPair(other))
    }

    val projectFiles: Set<String> = (project.projectDir.list() ?: emptyArray()).toSet()

    fun SourceDirectorySet.srcDirIfExists(path: String) {
        //if (path in projectFiles) setSrcDirs(listOf(path)) //else println("file doesn't exist $path")
        //srcDir(path)
        setSrcDirs(listOf(path))
    }

    fun NamedDomainObjectContainer<KotlinSourceSet>.ssPair(name: String): SourceSetPair {
        return sourceSetPairs.getOrPut(name) {
            val atName = if (name == "common") "" else "@$name"
            SourceSetPair(
                main = maybeCreate("${name}Main").also {
                    it.kotlin.srcDirIfExists("src$atName")
                    it.resources.srcDirIfExists("resources$atName")
                    it.kotlin.srcDir("build/generated/ksp/$name/${name}Main/kotlin")
                },
                test = maybeCreate("${name}Test").also {
                    it.kotlin.srcDirIfExists("test$atName")
                    it.resources.srcDirIfExists("testResources$atName")
                    it.kotlin.srcDir("build/generated/ksp/$name/${name}Test/kotlin")
                }
            )
        }
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    fun applyTo() = with(project) {
        project.kotlin.sourceSets {
            ssDependsOn("native", "common")
            ssDependsOn("posix", "native")
            ssDependsOn("apple", "posix")
            ssDependsOn("appleNonWatchos", "apple")
            ssDependsOn("appleIosTvos", "apple")

            maybeCreate("commonMain").kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            for (platform in kotlinPlatforms) {
                val isMacos = platform.startsWith("macos")
                val isIos = platform.startsWith("ios")
                val isTvos = platform.startsWith("tvos")
                val isWatchos = platform.startsWith("watchos")
                val isNative = platform.contains("X86") || platform.contains("X64") || platform.contains("Arm")
                val isApple = isMacos || isIos || isTvos || isWatchos
                val isLinux = platform.startsWith("linux")
                val isPosix = isLinux || isApple
                val basePlatform = getKotlinBasePlatform(platform)
                if (isIos || isTvos) ssDependsOn(basePlatform, "appleIosTvos")
                if (isApple && !isWatchos) ssDependsOn(basePlatform, "appleNonWatchos")
                if (isPosix) ssDependsOn(basePlatform, "posix")
                if (isApple) ssDependsOn(basePlatform, "apple")
                if (isNative) ssDependsOn(basePlatform, "native")
                if (platform != basePlatform) ssDependsOn(platform, basePlatform)
            }
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
                    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
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

        //kotlin.applyDefaultHierarchyTemplate()

        kotlin.targets.forEach {
            it.compilations.forEach {
                it.compileTaskProvider.configure {
                    compilerOptions {
                        // apiVersion: Allow to use declarations only from the specified version of bundled libraries
                        // languageVersion: Provide source compatibility with specified language version
                        //this.apiVersion.set(KotlinVersion.KOTLIN_2_0)
                        //this.languageVersion.set(KotlinVersion.KOTLIN_2_0)
                    }
                }
            }
        }

        kotlin.sourceSets {
            // jvm, js, wasm, android, linuxX64, linuxArm64, tvosArm64, tvosX64, tvosSimulatorArm64, macosX64, macosArm64, iosArm64, iosSimulatorArm64, iosX64, watchosArm64, watchosArm32, watchosDeviceArm64, watchosSimulatorArm64, mingwX64

            for ((alias, platforms) in (kotlinAliases + kotlinBasePlatforms)) {
                //for ((alias, platforms) in kotlinAliases) {
                ssDependsOn(alias, "common")
                for (platform in platforms) ssDependsOn(platform, alias)
            }
        }
        //println(" -> $platforms")

        dependencies {
            for (dep in deps) {
                add(dep.configuration, when {
                    dep.path.contains('/') -> project(":${File(dep.path).name}")
                    dep.path.startsWith("\$") -> {
                        when (dep.path) {
                            "\$kotlin-test" -> "org.jetbrains.kotlin:kotlin-test"
                            else -> TODO("Unknown ${dep.path}")
                        }
                    }
                    else -> dep.path
                })
            }
        }

        for (target in kotlin.targets) {
            target.compilations.all {
                compileTaskProvider.configure {
                    compilerOptions {
                        suppressWarnings.set(true)
                    }
                }
            }
        }
    }

    fun configure() {
        val amperFile = File(project.projectDir, "module.yaml").takeIf { it.exists() } ?: return
        parseFile(amperFile)
        applyTo()
    }
}

allprojects {
    tasks.withType(Test::class).configureEach {
        jvmArgs(
            "-XX:+IgnoreUnrecognizedVMOptions",
            "--add-opens", "java.base/java.nio=ALL-UNNAMED",
            "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED",
        )
    }
}

subprojects {
    plugins.apply("org.jetbrains.dokka")
    plugins.apply("org.jetbrains.kotlinx.kover")
}

apiValidation {
    ignoredProjects.addAll(listOf(rootProject.name))
}
