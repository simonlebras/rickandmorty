package app.rickandmorty.gradle.utils

import com.diffplug.gradle.spotless.FormatExtension
import com.diffplug.gradle.spotless.KotlinExtension
import com.diffplug.gradle.spotless.KotlinGradleExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.spotless.LineEnding
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotless(libs: LibrariesForLibs) {
    require(!isRootProject) {
        "Spotless configuration should only be applied on subprojects."
    }

    pluginManager.apply(
        libs.plugins.nodeGradle,
        libs.plugins.spotless,
    )

    configure<SpotlessExtension> {
        // https://github.com/diffplug/spotless/issues/1644
        lineEndings = LineEnding.PLATFORM_NATIVE

        ktlint {
            target("src/**/*.kt")
        }

        ktlintGradle {
            target("*.kts")
        }

        misc {
            target(
                ".editorconfig",
                ".gitattributes",
                ".gitignore",
                "*.md",
                "*.pro",
                "*.properties",
            )
        }

        prettier {
            target(
                "*.json",
                "assets/**/*.json",
                "src/**/*.json",
                "src/**/*.graphql",
            )
        }

        xml {
            target(
                "*.xml",
                "src/**/*.xml",
            )
        }
    }
}

internal fun SpotlessExtension.ktlint(block: KotlinExtension.() -> Unit = {}) {
    kotlin {
        block()
        ktlint()
    }
}

internal fun SpotlessExtension.ktlintGradle(block: KotlinGradleExtension.() -> Unit = {}) {
    kotlinGradle {
        block()
        ktlint()
    }
}

internal fun SpotlessExtension.misc(block: FormatExtension.() -> Unit = {}) {
    format("misc") {
        block()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

context(Project)
internal fun SpotlessExtension.prettier(block: FormatExtension.() -> Unit = {}) {
    format("styling") {
        block()
        prettier().npmInstallCache("${project.rootProject.rootDir}/.gradle/spotless-npm-cache")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

internal fun SpotlessExtension.xml(block: FormatExtension.() -> Unit = {}) {
    format("xml") {
        block()
        trimTrailingWhitespace()
        endWithNewline()
    }
}
