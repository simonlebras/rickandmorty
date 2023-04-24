package app.rickandmorty.gradle.util

import com.diffplug.gradle.spotless.FormatExtension
import com.diffplug.gradle.spotless.KotlinExtension
import com.diffplug.gradle.spotless.KotlinGradleExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project

internal fun SpotlessExtension.ktlint(block: KotlinExtension.() -> Unit) {
    kotlin {
        block()
        ktlint()
    }
}

internal fun SpotlessExtension.ktlintGradle(block: KotlinGradleExtension.() -> Unit) {
    kotlinGradle {
        block()
        ktlint()
    }
}

internal fun SpotlessExtension.misc(block: FormatExtension.() -> Unit) {
    format("misc") {
        block()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

context(Project)
internal fun SpotlessExtension.prettier(block: FormatExtension.() -> Unit) {
    format("styling") {
        block()
        prettier().npmInstallCache("${project.rootProject.rootDir}/.gradle/spotless-npm-cache")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

internal fun SpotlessExtension.xml(block: FormatExtension.() -> Unit) {
    format("xml") {
        block()
        trimTrailingWhitespace()
        endWithNewline()
    }
}
