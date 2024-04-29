import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

afterEvaluate {
    kotlin.targets.filter { it.platformType == KotlinPlatformType.native }.forEach { target ->
        target.compilations.getByName("main") {
            (this as KotlinNativeCompilation).cinterops {
                val miniffi by creating {
                    defFile(project.file("nativeInterop/cinterop/miniffi.def"))
                }
            }
        }
    }
}