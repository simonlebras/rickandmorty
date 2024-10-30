package app.rickandmorty.gradle.util

import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget

internal fun KotlinMultiplatformExtension.kspDependencies(
    block: KspDependencies.() -> Unit,
) {
    targets.configureEach {
        if (targetName != "metadata") {
            kspDependencies(block)
        }
    }
}

private fun KotlinTarget.kspDependencies(block: KspDependencies.() -> Unit) {
    val configurationName = "ksp${targetName.capitalize()}"
    project.dependencies {
        KspDependencies { dependencyNotation ->
            add(configurationName, dependencyNotation)
        }.block()
    }
}

internal fun interface KspDependencies {
    fun ksp(dependencyNotation: Any)
}
