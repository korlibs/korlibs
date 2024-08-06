fun syncFiles(srcDir: File, dstDir: File, names: List<String>) {
    for (name in names) {
        File(srcDir, name).copyRecursively(File(dstDir, name), overwrite = true)
    }
}

tasks {
    val sync by creating {
        doLast {
            for (file in rootDir.listFiles() ?: arrayOf()) {
                if (File(file, ".git").isFile) {
                    // Submodule
                    println(file)
                    exec {
                        workingDir = file
                        commandLine("git", "checkout", "main")
                    }
                    exec {
                        workingDir = file
                        commandLine("git", "pull")
                    }
                }
            }
        }
    }
    val update by creating {
        doLast {
            //for (folder in rootDir.listFiles() ?: arrayOf()) {
            for (folder in listOf(file("korlibs-time"))) {
                if (!File(folder, ".git").isFile) continue
                if (folder.name == "korlibs-template") continue

                println("!!!!!!!!! $folder")

                try {
                    exec {
                        workingDir = folder
                        commandLine("git", "checkout", "-b", "soywiz/codecov.kover.binary.compatibility")
                    }
                } catch (e: Throwable) {

                }
                exec {
                    workingDir = folder
                    commandLine("git", "checkout", "soywiz/codecov.kover.binary.compatibility")
                }
                syncFiles(
                    file("korlibs-template"), folder, listOf(
                        "build.gradle.kts",
                        "settings.gradle.kts",
                        ".github",
                    )
                )
                exec {
                    workingDir = folder
                    commandLine("./gradlew", "apiDump")
                }
                try {
                    exec {
                        workingDir = folder
                        commandLine("git", "add", "-A")
                    }
                    exec {
                        workingDir = folder
                        commandLine("git", "commit", "-m", "Update template: codecov + kover + binary compatibility")
                    }
                    exec {
                        workingDir = folder
                        commandLine("git", "push", "--set-upstream", "origin", "soywiz/codecov.kover.binary.compatibility")
                    }
                } catch (e: Throwable) {
                    System.err.println("FAILED to push changes: ${e.message}")
                }
                exec {
                    workingDir = folder
                    commandLine("git", "reset", "--hard")
                }
                exec {
                    workingDir = folder
                    commandLine("git", "checkout", "main")
                }
            }
        }
    }
}