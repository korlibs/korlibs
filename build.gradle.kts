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

// Relocated modules: old project name -> new base artifact ID.
// Each platform artifact is derived automatically using the suffixes below.
val relocatedModules = mapOf(
    "korlibs-image-core" to "korlibs-image",
    "korlibs-time-core" to "korlibs-time",
    "korlibs-io-fs" to "korlibs-io",
    "korlibs-io-network-core" to "korlibs-io",
    "korlibs-io-nodejs" to "korlibs-io",
    "korlibs-io-stream" to "korlibs-io",
    "korlibs-io-vfs" to "korlibs-io",
    "korlibs-compression" to "korlibs-io",
    // add more mappings here as needed
)

// Suffix for each KMP platform. Empty string = root/kotlinMultiplatform POM.
val platformSuffixes = listOf(
    "",
    "-android",
    "-jvm",
    "-js",
    "-wasm-js",
    "-iosarm64",
    "-iossimulatorarm64",
    "-iosx64",
    "-tvosarm64",
    "-tvossimulatorarm64",
    "-watchosarm32",
    "-watchosarm64",
    "-watchosdevicearm64",
    "-watchossimulatorarm64",
    "-linuxarm64",
    "-linuxx64",
    "-macosarm64",
    "-mingwx64",
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

// For each relocated module, generate a stub subproject that publishes
// one redirect-only POM per platform — no sources, no compilation.
relocatedModules.forEach { (oldName, newBaseName) ->
    project(":$oldName").run {
        apply(plugin = "com.vanniktech.maven.publish")

        configure<PublishingExtension> {
            publications {
                platformSuffixes.forEach { suffix ->
                    val oldArtifactId = "$oldName$suffix"
                    val newArtifactId = "$newBaseName$suffix"
                    // Derive a valid Gradle publication name from the suffix
                    val publicationName = suffix
                        .removePrefix("-")
                        .replace("-", "")
                        .ifEmpty { "root" }

                    create<MavenPublication>(publicationName) {
                        groupId = group.toString()
                        artifactId = oldArtifactId
                        version = version.toString()

                        pom {
                            val projectUrl = "https://github.com/korlibs/korlibs"
                            name.set(oldArtifactId)
                            description.set("Relocated to $newArtifactId")
                            url.set(projectUrl)
                            inceptionYear.set("2020")
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
                            scm { url.set(projectUrl) }
                            distributionManagement {
                                relocation {
                                    groupId.set(rootProject.group.toString())
                                    artifactId.set(newArtifactId)
                                    version.set(rootProject.version.toString())
                                    message.set("$oldArtifactId has been relocated to $newArtifactId")
                                }
                            }
                        }
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
