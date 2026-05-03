import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin

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

// Relocated modules: old project name -> new artifact ID
// This mapping can be removed once first release is published
val relocatedModules = mapOf(
    "korlibs-image-core" to "korlibs-image",
    // add more mappings here as needed
)

subprojects {
    // Apply maven publishing configuration to subprojects
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

                // Inject relocation block if this module has been moved
                val newArtifactId = relocatedModules[project.name]
                if (newArtifactId != null) {
                    distributionManagement {
                        relocation {
                            groupId.set(group.toString())
                            artifactId.set(newArtifactId)
                            version.set(project.version.toString())
                            message.set("${project.name} has been merged into $newArtifactId")
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

// TODO Evaluate and apply previous configurations
//afterEvaluate {
//    kotlin.targets.filter { it.platformType == KotlinPlatformType.native }.forEach { target ->
//        if (target.name.contains("linux") || target.name.contains("mingw")) {
//            target.compilations.getByName("main") {
//                (this as KotlinNativeCompilation).cinterops {
//                    val stb_image by creating {
//                        defFile(project.file("../korlibs-image-core/nativeInterop/cinterop/stb_image.def"))
//                    }
//                }
//            }
//        }
//    }
//}

//afterEvaluate {
//    kotlin.targets.filter { it.platformType == KotlinPlatformType.native && it.name == "mingwX64" }.forEach { target ->
//        target.compilations.getByName("main") {
//            (this as KotlinNativeCompilation).cinterops {
//                val win32ssl by creating {
//                    defFile(project.file("../korlibs-io-network-core/nativeInterop/cinterop/win32ssl.def"))
//                }
//            }
//        }
//    }
//}
