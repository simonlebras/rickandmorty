package app.rickandmorty.gradle.util

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless(libs: LibrariesForLibs) {
    val ktlintVersion = libs.versions.ktlint.get()

    configure<SpotlessExtension> {
        kotlin {
            ktlint(ktlintVersion)
            target("src/**/*.kt")
        }
        kotlinGradle {
            ktlint(ktlintVersion)
            target("*.kts")
        }

        json {
            gson().indentWithSpaces(2)
            target(
                "assets/**/*.json",
                "src/**/*.json",
                "*.json",
            )
        }

        format("misc") {
            target(
                ".editorconfig",
                ".gitattributes",
                ".gitignore",
                "*.md",
                "*.pro",
                "*.properties",
            )
            trimTrailingWhitespace()
            endWithNewline()
        }

        format("xml") {
            target(
                "src/**/*.xml",
                "*.xml",
            )
            trimTrailingWhitespace()
            endWithNewline()
        }

        if (isRootProject) {
            predeclareDeps()
        }
    }

    if (isRootProject) {
        configure<SpotlessExtensionPredeclare> {
            kotlin { ktlint(ktlintVersion) }
            kotlinGradle { ktlint(ktlintVersion) }
            json { gson() }
        }
    }
}
