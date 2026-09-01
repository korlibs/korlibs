import com.diffplug.spotless.LineEnding

plugins {
    id("com.diffplug.spotless")
}

spotless {
    // Pin line endings explicitly and align with .editorconfig,
    // don't rely on GIT_ATTRIBUTES autodetection
    lineEndings = LineEnding.UNIX

    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**", "**/generated/**")

        leadingTabsToSpaces(4)
        ktlint()
    }
    kotlinGradle {
        leadingTabsToSpaces(4)

        target("*.gradle.kts")
        ktlint()
    }
}
