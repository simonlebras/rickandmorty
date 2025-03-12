package app.rickandmorty.gradle.util

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless() {
    val spotlessFormatters: SpotlessExtension.() -> Unit = {
        val ktlintVersion = libs.versions.ktlint.core.get()

        kotlin {
            ktlint(ktlintVersion)
                .customRuleSets(
                    listOf(
                        libs.ktlint.composerules.get().toString(),
                    ),
                )
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
    }

    configure<SpotlessExtension> {
        spotlessFormatters()
        if (isRootProject) {
            predeclareDeps()
        }
    }
    if (isRootProject) {
        configure<SpotlessExtensionPredeclare> { spotlessFormatters() }
    }
}
