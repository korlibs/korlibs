import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

afterEvaluate {
    kotlin.targets.filter { it.platformType == KotlinPlatformType.native }.forEach { target ->
        if (target.name.contains("linux") || target.name.contains("mingw")) {
            target.compilations.getByName("main") {
                (this as KotlinNativeCompilation).cinterops {
                    val stb_image by creating {
                        defFile(project.file("nativeInterop/cinterop/stb_image.def"))
                    }
                }
            }
        }
    }
}
