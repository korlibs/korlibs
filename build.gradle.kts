import com.google.gson.*
import com.google.gson.JsonParser
import groovy.json.*
import groovy.util.*
import org.gradle.api.internal.tasks.testing.*
import org.gradle.api.tasks.testing.logging.*
import org.gradle.jvm.tasks.Jar
import org.gradle.plugins.signing.signatory.internal.pgp.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.targets.js.ir.*
import java.net.*
import java.util.*
import java.util.concurrent.*

plugins {
    kotlin("multiplatform") version "2.0.0"
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
    MicroAmper(this).configure()
}

fun Project.doOnce(uniqueName: String, block: () -> Unit) {
    val key = "doOnce-$uniqueName"
    if (!rootProject.extra.has(key)) {
        rootProject.extra.set(key, true)
        block()
    }
}


open class DenoTestTask : AbstractTestTask() {
//open class DenoTestTask : KotlinTest() {

    //var isDryRun by org.jetbrains.kotlin.gradle.utils.property { false }

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
        return DenoTestExecuter(this.project, this.filter)
    }
    //override fun createTestExecuter(): TestExecuter<out TestExecutionSpec> = TODO()
    override fun createTestExecutionSpec(): TestExecutionSpec = DenoTestExecutionSpec()

    init {
        outputs.upToDateWhen { false }
    }

    class DenoTestExecuter(val project: Project, val filter: TestFilter) : TestExecuter<DenoTestExecutionSpec> {
        private fun Project.fullPathName(): String {
            //KotlinTest
            if (this.parent == null) return this.name
            return this.parent!!.fullPathName() + ":" + this.name
        }

        override fun execute(testExecutionSpec: DenoTestExecutionSpec, testResultProcessor: TestResultProcessor) {
            val baseTestFileNameBase = this.project.fullPathName().trim(':').replace(':', '-') + "-test"
            val baseTestFileName = "$baseTestFileNameBase.mjs"
            val runFile = File(this.project.rootProject.rootDir, "build/js/packages/$baseTestFileNameBase/kotlin/$baseTestFileName.deno.mjs")

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
                add("--junit-path=${project.file("build/test-results/jsDenoTest/junit.xml").absolutePath}")
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
                            testResultProcessor.output(currentTestId, DefaultTestOutputEvent(TestOutputEvent.Destination.StdErr, "FAILED\n"))
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
                        testResultProcessor.output(currentTestId, DefaultTestOutputEvent(TestOutputEvent.Destination.StdOut, "$line\n"))
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

    open class StartReleasingMavenCentral : DefaultTask() {
        @Input
        @Optional
        var sonatype: Sonatype? = null

        @TaskAction
        fun action() {
            val profileId = sonatype!!.findProfileIdByGroupId("com.soywiz")
            val stagedRepositoryId = sonatype!!.startStagedRepository(profileId)
            println("profileId=$profileId")
            println("stagedRepositoryId=$stagedRepositoryId")
            GithubCI.setOutput("stagedRepositoryId", stagedRepositoryId)
            File("stagedRepositoryId").writeText(stagedRepositoryId)
        }
    }

    open class ReleaseMavenCentralTask : DefaultTask() {
        //val groupId = "com.soywiz.korge" ?: rootProject.group.toString()
        @Input
        var groupId = "com.soywiz.korge"

        @Input
        @Optional
        var sonatype: Sonatype? = null

        @TaskAction
        fun action() {
            //if (!sonatype.releaseGroupId(rootProject.group.toString())) {
            try {
                if (!sonatype!!.releaseGroupId(groupId)) {
                    error("Can't promote artifacts. Check log for details")
                }
            } finally {
                File("stagedRepositoryId").delete()
            }

        }
    }

    fun createTasks(project: Project) = with(project) {
        if (sonatype != null) {
            tasks.create("startReleasingMavenCentral", StartReleasingMavenCentral::class) {
                this.sonatype = this@SonatypeProps.sonatype
            }
            rootProject.tasks.create<ReleaseMavenCentralTask>("releaseMavenCentral") {
                this.sonatype = this@SonatypeProps.sonatype
            }
        }

        if (stagedRepositoryId != null) {
            println("stagedRepositoryId=$stagedRepositoryId")
        }
    }
}

val sonatypeProps = SonatypeProps(rootProject)

sonatypeProps.createTasks(rootProject)

subprojects {
    //apply<KotlinMultiplatformPlugin>()
    apply(plugin = "kotlin-multiplatform")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    //val JDK_VERSION = 8
    //java.toolchain.languageVersion = JavaLanguageVersion.of(JDK_VERSION)
    //kotlin.jvmToolchain(JDK_VERSION)
    //afterEvaluate {
    //    tasks.withType(Test::class) {
    //        //this.javaLauncher.set()
    //        this.javaLauncher.set(javaToolchains.launcherFor {
    //            // 17 is latest at the current moment
    //            languageVersion.set(JavaLanguageVersion.of(JDK_VERSION))
    //        })
    //    }
    //}

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
            publishAllLibraryVariants()
            //publishLibraryVariants("release", "debug")
        }
        //}
    }

    tasks {
        //println(this.findByName("compileTestKotlinJs")!!::class)
        //println(this.findByName("compileTestKotlinJs")!!.dependsOn?.toList())
        //println(this.findByName("compileTestKotlinJs")?.outputs?.files?.toList())

        val jsDenoTest by creating(DenoTestTask::class) {
        }
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
        // /Users/soywiz/projects/korge-korlibs/korlibs-io/build/bin/iosSimulatorArm64/debugTest
        //println(this.target)
        //val target = Regex("^link(.*?)Test.*$").find(this.name)?.groupValues?.getOrNull(1)?.replaceFirstChar { it.lowercaseChar() }
        //println(target)
        //val compileTaskName = this.name.replace(Regex("^link(.*?)Test.*$")) { "compileTestKotlin${it.groupValues[1]}" }
        //val compileTask = tasks.findByName(compileTaskName) as? KotlinNativeCompile?

        val folder = this.outputs.files.toList().firstOrNull()
        val fromFolder = File(project.projectDir, "testresources")

        if (folder != null) {
            val copyAfterLink = tasks.create("${this.name}CopyResources", Copy::class)
            copyAfterLink.from(fromFolder)
            copyAfterLink.into(folder)
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
            this.dependsOn(tasks.create("${taskName}CopyResources", TestProcessResourcesLast::class).also {
                it.dirs = this.outputs.files.toList().filter { it.isDirectory }
            })
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
    run {
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
                        val root = NodeList(listOf(this@withXml.asNode()))
                        //println("baseProjectName=$baseProjectName")
                        val packaging = root.getAt("packaging").text()
                        //println("---------------")
                        //println("root=$root")
                        //println("packaging=" + (root.getAt("packaging")))
                        //println("packaging=" + root.getAt("packaging"))
                        //println("packaging.text=" + root.getAt("packaging").text())
                        if (packaging == "aar") {
                            val nodes: NodeList = root.getAt("dependencies").getAt("dependency").getAt("scope")
                            for (node in nodes as List<Node>) {
                                node.setValue("compile")
                                //println("node=$node setValue=compile")
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

    //println(KotlinCompilerVersion.VERSION)
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

    private val client get() = SimpleHttpClient(user, pass)

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

    operator fun JsonElement.get(key: String): JsonElement = asJsonObject.get(key)
    val JsonElement.list: JsonArray get() = asJsonArray
    fun JsonElement.toStringPretty() = GsonBuilder().setPrettyPrinting().create().toJson(this)
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
                },
                test = maybeCreate("${name}Test").also {
                    it.kotlin.srcDirIfExists("test$atName")
                    it.resources.srcDirIfExists("testResources$atName")
                }
            )
        }
    }

    fun applyTo() = with(project) {
        project.kotlin.sourceSets {
            ssDependsOn("native", "common")
            ssDependsOn("posix", "native")
            ssDependsOn("apple", "posix")
            ssDependsOn("appleNonWatchos", "apple")
            ssDependsOn("appleIosTvos", "apple")

            for (platform in kotlinPlatforms) {
                val isMacos = platform.startsWith("macos")
                val isIos = platform.startsWith("ios")
                val isTvos = platform.startsWith("tvos")
                val isWatchos = platform.startsWith("watchos")
                val isNative = platform.contains("X86") || platform.contains("X64") || platform.contains("Arm")
                val isApple = isMacos || isIos || isTvos || isWatchos
                val isLinux = platform.startsWith("linux")
                val isWindows = platform.startsWith("mingw")
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

tasks {
    subprojects {
        afterEvaluate {
            val copyArtifactsToDirectory = tasks.create("copyArtifactsToDirectory", Task::class) {
                //dependsOn("publishToMavenLocal")
            }

            val base = rootProject.layout.buildDirectory.dir("artifacts")
            for ((index, pub) in publishing.publications.filterIsInstance<MavenPublication>().withIndex()) {
                //println(pub.artifacts.toList())
                val basePath = pub.groupId.replace(".", "/") + "/" + pub.artifactId + "/" + pub.version
                val baseDir = File(base.get().asFile, basePath)
                val m2Dir = File(File(System.getProperty("user.home"), ".m2/repository"), basePath)

                val afterTask = tasks.create("copyArtifactsToDirectory$index", Copy::class).also {
                    it.from(m2Dir)
                    it.into(baseDir)
                }
                afterTask.dependsOn("publishToMavenLocal")
                copyArtifactsToDirectory.dependsOn(afterTask)
                //println("TASK: afterTask=$afterTask")
            }
        }
    }

    val generateArtifactsZip by registering(Zip::class) {
        subprojects {
            dependsOn("${this.path}:copyArtifactsToDirectory")
        }
        from(rootProject.layout.buildDirectory.dir("artifacts"))
        archiveFileName = "korlibs-${REAL_VERSION}.zip"
        destinationDirectory = rootProject.layout.buildDirectory
    }

    val generateArtifactsTar by registering(Tar::class) {
        subprojects {
            dependsOn("${this.path}:copyArtifactsToDirectory")
        }
        from(rootProject.layout.buildDirectory.dir("artifacts"))
        //compression = Compression.GZIP
        //into(rootProject.layout.buildDirectory)
        archiveFileName = "korlibs-${REAL_VERSION}.tar"
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
            "-f", File(rootFile, "korlibs-${REAL_VERSION}.tar").absolutePath,
            "-o", File(rootFile, "korlibs-${REAL_VERSION}.tar.zstd").absolutePath
        )
    }
}

allprojects {
    afterEvaluate {
        afterEvaluate {
            afterEvaluate {
                tasks.withType(org.gradle.api.tasks.testing.Test::class) {
                    //println("TEST-TASK: $this")
                    jvmArgs(
                        "--add-opens", "java.base/java.nio=ALL-UNNAMED",
                        //"--add-opens", "java.base/jdk.incubator.foreign=ALL-UNNAMED",
                        "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED",
                    )
                }
            }
        }
    }
}
