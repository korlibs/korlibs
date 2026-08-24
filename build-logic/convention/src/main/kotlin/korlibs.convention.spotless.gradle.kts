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
        // Uses automatically the .editorconfig from root project
        // TODO Enable ktlint and include rules from .editorconfig
        // ktlint()
    }
    kotlinGradle {
        leadingTabsToSpaces(4)

        target("*.gradle.kts")
        // TODO Enable ktlint and include rules from .editorconfig
        // ktlint()
    }
}
