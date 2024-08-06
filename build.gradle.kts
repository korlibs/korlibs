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
            for (folder in listOf(file("korlibs-inject"))) {
                if (!File(folder, ".git").isFile) continue
                if (folder.name == "korlibs-template") continue

                fun execSimple(vararg args: String) {
                    exec {
                        workingDir = folder
                        commandLine(*args)
                    }
                }

                println("!!!!!!!!! $folder")
                execSimple("git", "reset", "--hard")
                execSimple("git", "checkout", "main")

                try {
                    execSimple("git", "checkout", "-b", "soywiz/codecov.kover.binary.compatibility")
                } catch (e: Throwable) {

                }
                execSimple("git", "checkout", "soywiz/codecov.kover.binary.compatibility")
                syncFiles(
                    file("korlibs-template"), folder, listOf(
                        "build.gradle.kts",
                        "settings.gradle.kts",
                        ".github",
                    )
                )
                execSimple("./gradlew", "apiDump")
                try {
                    execSimple("git", "add", "-A")
                    execSimple("git", "commit", "-m", "Update template: codecov + kover + binary compatibility")
                    execSimple("git", "push", "--set-upstream", "origin", "soywiz/codecov.kover.binary.compatibility")
                } catch (e: Throwable) {
                    System.err.println("FAILED to push changes: ${e.message}")
                }
                execSimple("git", "reset", "--hard")
                execSimple("git", "checkout", "main")
            }
        }
    }
}