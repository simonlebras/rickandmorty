package app.rickandmorty.gradle.util

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project

context(Project)
internal fun SpotlessExtension.configureSpotless() {
    kotlin {
        if (isRootProject) {
            target("build-logic/src/**/*.kt")
        } else {
            target("src/**/*.kt")
        }
        ktlint()
    }

    kotlinGradle {
        if (isRootProject) {
            target(
                "*.kts",
                "build-logic/*.kts",
            )
        } else {
            target("*.kts")
        }
        ktlint()
    }

    format("misc") {
        if (isRootProject) {
            target(
                ".editorconfig",
                ".gitattributes",
                ".gitignore",
                "*.md",
                "*.properties",
                "gradle/libs.versions.toml",
            )
        } else {
            target(
                ".editorconfig",
                ".gitattributes",
                ".gitignore",
                "*.md",
                "*.pro",
                "*.properties",
            )
        }
        trimTrailingWhitespace()
        endWithNewline()
    }

    format("styling") {
        if (isRootProject) {
            target(
                "*.json",
                ".github/**/*.yml",
            )
        } else {
            target(
                "*.json",
                "assets/**/*.json",
                "src/**/*.json",
                "src/**/*.graphql",
            )
        }
        prettier().npmInstallCache("${project.rootProject.rootDir}/.gradle/spotless-npm-cache")
        trimTrailingWhitespace()
        endWithNewline()
    }

    format("xml") {
        if (isRootProject) {
            target("*.xml")
        } else {
            target(
                "*.xml",
                "src/**/*.xml",
            )
        }
        trimTrailingWhitespace()
        endWithNewline()
    }
}
