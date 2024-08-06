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
}