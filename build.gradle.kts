import org.gradle.api.tasks.testing.logging.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.targets.js.ir.*

plugins {
    //kotlin("multiplatform") version "1.9.23"
    kotlin("multiplatform") //apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
    afterEvaluate {
        group = "com.soywiz"
    }
}

kotlin {
    jvm()
}

subprojects {
    //apply<KotlinMultiplatformPlugin>()
    apply(plugin = "kotlin-multiplatform")


    kotlin {
        js {
            nodejs()
            browser()
        }
    }

    afterEvaluate {
        tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class) {
            compilerOptions.suppressWarnings.set(true)
            //println(this::class.java)
        }

        tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink::class) {
            // /Users/soywiz/projects/korge-korlibs/korlibs-io/build/bin/iosSimulatorArm64/debugTest
            //println(this.target)
            //val target = Regex("^link(.*?)Test.*$").find(this.name)?.groupValues?.getOrNull(1)?.replaceFirstChar { it.lowercaseChar() }
            //println(target)
            //val compileTaskName = this.name.replace(Regex("^link(.*?)Test.*$")) { "compileTestKotlin${it.groupValues[1]}" }
            //val compileTask = tasks.findByName(compileTaskName) as? KotlinNativeCompile?

            doLast {
                val folder = this.outputs.files.toList().firstOrNull()
                if (folder != null) {
                    copy {
                        //from(compileTask.defaultSourceSet.resources)
                        from(File(project.projectDir, "testresources"))
                        //from(File(project.rootDir, "build/bin/$target/debugTest"))
                        into(folder)
                    }
                }
            }
        }

        tasks.withType(org.gradle.api.tasks.testing.AbstractTestTask::class) {
            testLogging {
                events = mutableSetOf(
                    TestLogEvent.SKIPPED,
                    TestLogEvent.FAILED,
                    TestLogEvent.STANDARD_OUT, TestLogEvent.STANDARD_ERROR
                )
                exceptionFormat = TestExceptionFormat.FULL
                showStandardStreams = true
                showStackTraces = true
            }
        }
        kotlin.targets.withType(KotlinJsIrTarget::class) {
            //println("TARGET: $this")
            browser {
                //testTask { useKarma { useChromeHeadless() } }
                testRuns.getByName(KotlinTargetWithTests.DEFAULT_TEST_RUN_NAME).executionTask.configure {
                    useKarma {
                        useChromeHeadless()
                        File(project.rootProject.rootDir, "karma.config.d").takeIf { it.exists() }?.let {
                            useConfigDirectory(it)
                        }
                    }
                }
            }
        }
    }
}
