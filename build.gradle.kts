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
            for (folder in libs) {
                println("!!!!!!!!! $folder")
                folder.execSimple("git", "reset", "--hard")
                folder.execSimple("git", "checkout", "main")
                folder.execSimple("git", "pull")

                val branchName = "soywiz/update.template"

                kotlin.runCatching { folder.execSimple("git", "branch", "--delete", branchName) }
                kotlin.runCatching { folder.execSimple("git", "checkout", "-b", branchName) }
                folder.execSimple("git", "checkout", branchName)
                syncFiles(
                    templateFolder, folder, listOf(
                        "build.gradle.kts",
                        "settings.gradle.kts",
                        ".github",
                    )
                )
                folder.execSimple("./gradlew", "apiDump")
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
            }
        }
    }
}