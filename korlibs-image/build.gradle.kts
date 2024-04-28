import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*

kotlin {
    /*
    macosArm64 {
        compilations.getByName("main") {
            cinterops {
                val stb_image by creating {
                    defFile(project.file("nativeInterop/cinterop/stb_image.def"))
                }
            }
        }
    }
     */
}

afterEvaluate {
    kotlin.targets.filter { it.platformType == KotlinPlatformType.native }.forEach { target ->
        target.compilations.getByName("main") {
            (this as KotlinNativeCompilation).cinterops {
                val stb_image by creating {
                    defFile(project.file("nativeInterop/cinterop/stb_image.def"))
                }
            }
        }
    }
}