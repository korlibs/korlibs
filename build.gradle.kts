import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

group = "org.korge.korlibs"
version = libs.versions.korlibs.get()

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }

    group = "org.korge.korlibs"
    version = rootProject.libs.versions.korlibs.get()
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.multiplatform.library) apply false
    alias(libs.plugins.kotlinx.kover) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktech.mavenPublish)
}

// Relocated modules: old project name -> new artifact ID (root/kotlinMultiplatform artifact).
// Each platform artifact is derived automatically, e.g. korlibs-image-jvm, korlibs-image-android, etc.
val relocatedModules = mapOf(
    "korlibs-image-core" to "korlibs-image",
    // add more mappings here as needed
)

// Maps KMP publication name -> artifact ID suffix used by vanniktech.
// kotlinMultiplatform is the root POM and gets no suffix.
val publicationSuffixes = mapOf(
    "kotlinMultiplatform"    to "",
    "android"                to "-android",
    "jvm"                    to "-jvm",
    "js"                     to "-js",
    "wasmJs"                 to "-wasm-js",
    "iosArm64"               to "-iosarm64",
    "iosSimulatorArm64"      to "-iossimulatorarm64",
    "iosX64"                 to "-iosx64",
    "tvosArm64"              to "-tvosarm64",
    "tvosSimulatorArm64"     to "-tvossimulatorarm64",
    "watchosArm32"           to "-watchosarm32",
    "watchosArm64"           to "-watchosarm64",
    "watchosDeviceArm64"     to "-watchosdevicearm64",
    "watchosSimulatorArm64"  to "-watchossimulatorarm64",
    "linuxArm64"             to "-linuxarm64",
    "linuxX64"               to "-linuxx64",
    "macosArm64"             to "-macosarm64",
    "mingwX64"               to "-mingwx64",
)

subprojects {
    plugins.withType<MavenPublishPlugin> {
        extensions.configure<MavenPublishBaseExtension> {
            publishToMavenCentral()
            signAllPublications()

            coordinates(
                groupId = group.toString(),
                artifactId = project.name,
                version = version.toString(),
            )

            pom {
                val projectUrl = "https://github.com/korlibs/korlibs"
                name.set(project.name)
                description.set(project.description ?: project.name)
                inceptionYear.set("2020")
                url.set(projectUrl)
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://raw.githubusercontent.com/korlibs/korlibs/refs/heads/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("korge")
                        name.set("Korge Team")
                        email.set("info@korge.org")
                    }
                }
                scm {
                    url.set(projectUrl)
                    // TODO Consider below fields as well
                    // connection.set("scm:git:git://github.com/username/mylibrary.git")
                    // developerConnection.set("scm:git:ssh://git@github.com/username/mylibrary.git")
                }
            }
        }
    }

    // TODO Re-enable warnings once we address most of the reported ones
    val isCi = System.getenv("CI") != null
    plugins.withType<KotlinMultiplatformPluginWrapper> {
        extensions.configure<KotlinMultiplatformExtension> {
            compilerOptions {
                allWarningsAsErrors.set(false)
                suppressWarnings.set(isCi)
            }
        }
    }
}

// Inject relocation POMs after all subprojects have fully configured,
// so vanniktech + KMP have registered every MavenPublication by this point.
gradle.projectsEvaluated {
    subprojects {
        val newBaseArtifactId = relocatedModules[name] ?: return@subprojects
        val publishingExt = extensions.findByType<PublishingExtension>() ?: return@subprojects

        publishingExt.publications.withType<MavenPublication>().configureEach {
            val suffix = publicationSuffixes[name] ?: run {
                logger.warn("WARNING: Unknown publication '${name}' in project '${project.name}' — skipping relocation POM")
                return@configureEach
            }
            val targetArtifactId = newBaseArtifactId + suffix
            logger.lifecycle("Relocating ${project.name} publication '$name': ${artifactId} -> $targetArtifactId")
            pom {
                distributionManagement {
                    relocation {
                        groupId.set(project.group.toString())
                        artifactId.set(targetArtifactId)
                        version.set(project.version.toString())
                        message.set("${project.name} has been merged into $newBaseArtifactId")
                    }
                }
            }
        }
    }
}

dependencies {
    // Aggregate all korlibs-* prefixed subprojects to root dokka documentation
    subprojects.forEach { project ->
        if (project.name.startsWith("korlibs-")) {
            dokka(project)
        }
    }
}
