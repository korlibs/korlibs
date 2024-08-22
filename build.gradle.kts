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
        [![Maven Central Version](https://img.shields.io/maven-central/v/com.soywiz/$lib)](https://central.sonatype.com/artifact/com.soywiz/$lib)
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
    val sync by creating {
        doLast {
            for (folder in libsIncludingTemplate) {
                println("!!!!!!!!! $folder")
                folder.execSimple("git", "reset", "--hard")
                folder.execSimple("git", "checkout", "main")
                folder.execSimple("git", "pull")
            }
        }
    }
    val copyKarmaFolder by creating {
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
    val updateBadges by creating {
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
                        "" + lines.first + "\n\n" + badgesForLibrary(folder.name) + "\n\n" + lines.drop(1).joinToString("\n")
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
    val update by creating {
        doLast {
            val branchName = "soywiz/update.template.${SimpleDateFormat("YYYY-MM-dd").format(Date())}"

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
