import org.gradle.api.tasks.testing.logging.*
import org.jetbrains.kotlin.gradle.plugin.*

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
        js() {
            nodejs()
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

    afterEvaluate {
        tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
            kotlinOptions {
                suppressWarnings = true
                //freeCompilerArgs += "-Xjavac-arguments=-Xlint:-deprecation"
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
    }
}
