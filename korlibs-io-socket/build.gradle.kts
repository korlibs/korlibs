kotlin {
    mingwX64 {
        compilations.getByName("main") {
            cinterops {
                val win32ssl by creating {
                    defFile(project.file("nativeInterop/cinterop/win32ssl.def"))
                }
            }
        }
    }
}
