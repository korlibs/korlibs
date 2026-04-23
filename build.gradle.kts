import org.apache.tools.ant.taskdefs.condition.*
import java.text.*
import java.util.*

val templateFolder = file("korlibs-library-template")

fun syncFiles(srcDir: File, dstDir: File, names: List<String>) {
    for (name in names) {
        val src = File(srcDir, name)
        val dst = File(dstDir, name)
        dst.deleteRecursively()
        src.copyRecursively(dst, overwrite = true)
    }
}

fun badgesForLibrary(lib: String): String {
    return """
        <!-- BADGES -->
        [![test](https://github.com/korlibs/$lib/actions/workflows/TEST.yml/badge.svg)](https://github.com/korlibs/$lib/actions/workflows/TEST.yml)
        [![codecov](https://codecov.io/gh/korlibs/$lib/graph/badge.svg)](https://codecov.io/gh/korlibs/$lib)
        [![Maven Central Version](https://img.shields.io/maven-central/v/org.korge/$lib)](https://central.sonatype.com/artifact/org.korge/$lib)
        [![Discord](https://img.shields.io/discord/728582275884908604?logo=discord&label=Discord)](https://discord.korge.org/)
        [![KDoc](https://img.shields.io/badge/docs-kdoc-blue)](https://korlibs.github.io/$lib/)
        [![Documentation](https://img.shields.io/badge/docs-documentation-purple)](https://docs.korge.org/${lib.removePrefix("korlibs-")}/)
        <!-- /BADGES -->
    """.trimIndent()
}

val libsIncludingTemplate = (rootDir.listFiles() ?: arrayOf())
    .filter { File(it, ".git").isFile }

val libs = libsIncludingTemplate
    .filter { it.name != templateFolder.name }

fun File.execSimple(vararg args: String) {
    exec {
        workingDir = this@execSimple
        commandLine(*args)
    }
}

class ProcessResult(val stdout: ByteArray, val stderr: ByteArray, val exitCode: Int) {
    val stdoutString by lazy { stdout.decodeToString() }
    val stderrString by lazy { stderr.decodeToString() }

    override fun toString(): String = "ProcessResult(exitCode=$exitCode, stdout=$stdoutString, stderr=$stderrString)"
}
fun File.execCapture(vararg args: String): ProcessResult {
    val p = ProcessBuilder(*args).directory(this.absoluteFile).start()
    return ProcessResult(
        p.inputStream.readBytes(),
        p.errorStream.readBytes(),
        p.exitValue()
    )
}

fun expectFail(message: String, block: () -> Unit) {
    var failed: Boolean
    try {
        block()
        failed = false
    } catch (e: Throwable) {
        failed = true
    }
    if (!failed) error("Expected to fail but didn't : $message")
}

tasks {
    val sync by registering { ->
        doLast {
            for (folder in libsIncludingTemplate) {
                println("!!!!!!!!! $folder")
                folder.execSimple("git", "reset", "--hard")
                folder.execSimple("git", "checkout", "main")
                folder.execSimple("git", "pull")
            }
        }
    }
    val copyKarmaFolder by registering { ->
        doLast {
            for (folder in libsIncludingTemplate) {
                println("!!!!!!!!! $folder")
                syncFiles(
                    rootDir, folder, listOf(
                        "karma.config.d"
                    )
                )
                folder.execSimple("git", "add", "karma.config.d")
                folder.execSimple("git", "commit", "-m", "Add karma.config.d folder")
                folder.execSimple("git", "push")
            }
        }
    }
    val updateBadges by registering {
        doLast {
            for (folder in libsIncludingTemplate) {
                println("!!!!!!!!! $folder")
                folder.execSimple("git", "reset", "--hard")
                folder.execSimple("git", "checkout", "main")
                //folder.execSimple("git", "pull")
                val readmeMdFile = File(folder, "README.md")
                val readmeText = readmeMdFile.readText()
                val newReadmeText = if (readmeText.contains("<!-- BADGES -->")) {
                    readmeText.replace(
                        Regex("<!-- BADGES -->(.*)<!-- /BADGES -->", RegexOption.DOT_MATCHES_ALL),
                        badgesForLibrary(folder.name)
                    )
                } else {
                    // Add badges after the first heading line or at the end of the file
                    if (readmeText.startsWith("#")) {
                        val lines = readmeText.lines()
                        "" + lines.first() + "\n\n" + badgesForLibrary(folder.name) + "\n\n" + lines.drop(1).joinToString("\n")
                    } else {
                        readmeText + "\n\n" + badgesForLibrary(folder.name)
                    }.trimEnd() + "\n"
                }
                readmeMdFile.writeText(newReadmeText)
                folder.execSimple("git", "add", "README.md")
                folder.execSimple("git", "commit", "-m", "Update badges")
                folder.execSimple("git", "push")
                //println(newReadmeText)
            }
        }
    }
    val updateSpecificLibs by registering {
        doLast {
            val branchName = "jobe/update-kotlin-version-and-move-publishing-to-org.korge"

            // Uncomment libs names to include them in the update process
            val specificLibs = (rootDir.listFiles() ?: arrayOf())
                .filter {
                    // Level 1
                    //it.name == "korlibs-annotations"
                    //it.name == "korlibs-bignumber"
                    //it.name == "korlibs-checksum"
                    //it.name == "korlibs-datastructure-core"
                    //it.name == "korlibs-encoding"
                    //it.name == "korlibs-inject"
                    //it.name == "korlibs-number"
                    //it.name == "korlibs-platform"

                    // Level 2
                    //it.name == "korlibs-crypto"
                    //it.name == "korlibs-dyn"
                    //it.name == "korlibs-logger"
                    //it.name == "korlibs-math-core"
                    //it.name == "korlibs-memory"
                    //it.name == "korlibs-string"
                    //it.name == "korlibs-time"

                    // Level 3
                    //it.name == "korlibs-concurrent"
                    //it.name == "korlibs-io-stream"
                    //it.name == "korlibs-math-vector"
                    //it.name == "korlibs-serialization"

                    // Level 4
                    //it.name == "korlibs-compression"
                    //it.name == "korlibs-datastructure"
                    //it.name == "korlibs-io-fs"
                    //it.name == "korlibs-jseval"
                    //it.name == "korlibs-template"

                    // Level 5
                    //it.name == "korlibs-ffi"
                    //it.name == "korlibs-io-network-core"
                    //it.name == "korlibs-io-vfs"
                    //it.name == "korlibs-math"

                    // Level 6
                    //it.name == "korlibs-audio-core"
                    //it.name == "korlibs-io"

                    // Level 7
                    //it.name == "korlibs-audio"
                    //it.name == "korlibs-wasm"

                    // Level 8
                    //it.name == "korlibs-image-core"

                    // Level 9
                    it.name == "korlibs-image"

                }

            for (folder in specificLibs) {
                println("!!!!!!!!! $folder")

                try {
                    val remoteBranch = folder.execCapture("git", "ls-remote", "--heads", "origin", "refs/heads/$branchName").stdoutString.trim()
//*
                    if (remoteBranch.isNotEmpty()) {
                        println(" --> remote branch already created. Skipping: $remoteBranch")
                        continue
                    }

                    folder.execSimple("git", "reset", "--hard")
                    folder.execSimple("git", "checkout", "main")
                    folder.execSimple("git", "pull")

                    kotlin.runCatching { folder.execSimple("git", "branch", "--delete", branchName) }
                    kotlin.runCatching { folder.execSimple("git", "checkout", "-b", branchName) }
                    kotlin.runCatching { folder.execSimple("git", "checkout", branchName) }

                    syncFiles(
                        templateFolder, folder, listOf(
                            "build.gradle.kts",
                            "settings.gradle.kts",
                            "gradle.properties",
                            "gradlew",
                            "gradlew.bat",
                            "gradle",
                            "karma.config.d",
                            ".gitignore",
                            ".github",
                        )
                    )

                    // Sync specific files in lib module folder
                    val templateLibModuleFolder = templateFolder.resolve("korlibs-simple")
                    val libModuleFolder = folder.resolve(folder.name)
                    syncFiles(
                        templateLibModuleFolder, libModuleFolder, listOf(
                            "build.gradle",
                        )
                    )

                    // Patch DOCS workflow with the correct module name
                    val docsFile = File(folder, ".github/workflows/DOCS.yml")
                    val docsText = docsFile.readText()
                    val newDocsText = docsText.replace("<KORLIBS-MODULENAME>", folder.name)
                    docsFile.writeText(newDocsText)

                    // Update badges in README.md
                    val readmeMdFile = File(folder, "README.md")
                    val readmeText = readmeMdFile.readText()
                    val newReadmeText = readmeText.replace(
                        Regex("<!-- BADGES -->(.*)<!-- /BADGES -->", RegexOption.DOT_MATCHES_ALL),
                        badgesForLibrary(folder.name)
                    )
                    readmeMdFile.writeText(newReadmeText)

                    // Update com.soywiz to org.korge in dependency files
                    val moduleFile = File(folder, "${folder.name}/module.yaml")
                    val moduleText = moduleFile.readText()
                    var newModuleText = moduleText.replace("com.soywiz", "org.korge")  // Update namespace for all dependencies
                    newModuleText = newModuleText.replace("6.0.0", "6.1.0")  // Update version for all dependencies
                    moduleFile.writeText(newModuleText)

                    folder.execSimple(if (Os.isFamily(Os.FAMILY_WINDOWS)) "gradlew.bat" else "./gradlew", "apiDump")
// */

                    try {
                        folder.execSimple("git", "add", "-A")
                        folder.execSimple("git", "commit", "-m",
                            """
                            Move publishing to org.korge and update Kotlin dependencies to 2.3.20
                            
                            - Toolchain was updated: Kotlin 2.3.20, AGP 8.13.1, Dokka 2.1.0, Gradle 8.14.4
                            - Custom Sonatype staging/release logic was replaced with
                              com.vanniktech.maven.publish for signing + Central Portal publishing
                            - Namespace on maven central was changed to "org.korge" and Maven Central
                              badge in README.md was updated
                            - GitHub Actions workflows were refreshed (Java 21, updated actions versions,
                              new publish and dokka tasks, optional yarn lock upgrade)
                            - API check was updated
                            - Update korlibs dependencies:
                            """.trimIndent()
/*
                                org.korge:korlibs-annotations:6.1.0
                                org.korge:korlibs-audio-core:6.1.0
                                org.korge:korlibs-checksum:6.1.0
                                org.korge:korlibs-compression:6.1.0
                                org.korge:korlibs-concurrent:6.1.0
                                org.korge:korlibs-crypto:6.1.0
                                org.korge:korlibs-datastructure-core:6.1.0
                                org.korge:korlibs-datastructure:6.1.0
                                org.korge:korlibs-dyn:6.1.0
                                org.korge:korlibs-encoding:6.1.0
                                org.korge:korlibs-ffi-legacy:6.1.1
                                org.korge:korlibs-io-fs:6.1.0
                                org.korge:korlibs-io-network-core:6.1.0
                                org.korge:korlibs-io-stream:6.1.0
                                org.korge:korlibs-io-vfs:6.1.0
                                org.korge:korlibs-io:6.1.0
                                org.korge:korlibs-jseval:6.1.0
                                org.korge:korlibs-logger:6.1.0
                                org.korge:korlibs-math-core:6.1.0
                                org.korge:korlibs-math-vector:6.1.0
                                org.korge:korlibs-memory:6.1.0
                                org.korge:korlibs-number:6.1.0
                                org.korge:korlibs-platform:6.1.0
                                org.korge:korlibs-serialization:6.1.0
                                org.korge:korlibs-string:6.1.0
                                org.korge:korlibs-time-core:6.1.0
                                org.korge:korlibs-time:6.1.0
                                org.korge:korlibs-wasm:6.1.0
                                org.jetbrains.kotlinx:atomicfu:0.32.1
                                org.jetbrains.kotlinx:kotlinx-browser-wasm-js:0.5.0
                                org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2
                                org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2
                                org.mozilla:rhino-engine:1.9.1
                                org.ow2.asm:asm-util:9.9.1
                                org.ow2.asm:asm:9.9.1
                                net.java.dev.jna:jna:5.18.1
                                net.java.dev.jna:jna-platform:5.18.1
// */
                        )
                        folder.execSimple("git", "push", "--force", "--set-upstream", "origin", branchName)
//                        folder.execSimple("gh", "pr", "create", "--fill")
                    } catch (e: Throwable) {
                        System.err.println("FAILED to push changes: ${e.message}")
                    }

                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    val updateFromTemplateFolder by registering {
        doLast {
            val branchName = "jobe/update-from-template.${SimpleDateFormat("YYYY-MM-dd").format(Date())}"

            for (folder in libs) {
                println("!!!!!!!!! $folder")
                val remoteBranch = folder.execCapture("git", "ls-remote", "--heads", "origin", "refs/heads/$branchName").stdoutString.trim()

                if (remoteBranch.isNotEmpty()) {
                    println(" --> remote branch already created. Skipping: $remoteBranch")
                    continue
                }

                try {
                    folder.execSimple("git", "reset", "--hard")
                    folder.execSimple("git", "checkout", "main")
                    folder.execSimple("git", "pull")

                    //expectFail("Branch should not exist") { folder.execSimple("git", "checkout", branchName) }

                    kotlin.runCatching { folder.execSimple("git", "branch", "--delete", branchName) }
                    kotlin.runCatching { folder.execSimple("git", "checkout", "-b", branchName) }
                    kotlin.runCatching { folder.execSimple("git", "checkout", branchName) }
                    syncFiles(
                        templateFolder, folder, listOf(
                            "build.gradle.kts",
                            "settings.gradle.kts",
                            "gradle.properties",
                            "gradlew",
                            "gradlew.bat",
                            "gradle",
                            "karma.config.d",
                            ".gitignore",
                            ".github",
                        )
                    )

                    folder.execSimple(if (Os.isFamily(Os.FAMILY_WINDOWS)) "gradlew.bat" else "./gradlew", "apiDump")
                    try {
                        folder.execSimple("git", "add", "-A")
                        folder.execSimple("git", "commit", "-m", "Update template")
                        folder.execSimple("git", "push", "--force", "--set-upstream", "origin", branchName)
                        folder.execSimple("gh", "pr", "create", "--fill")
                    } catch (e: Throwable) {
                        System.err.println("FAILED to push changes: ${e.message}")
                    }
                    folder.execSimple("git", "reset", "--hard")
                    folder.execSimple("git", "checkout", "main")
                    kotlin.runCatching { folder.execSimple("git", "branch", "--delete", branchName) }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }
}
