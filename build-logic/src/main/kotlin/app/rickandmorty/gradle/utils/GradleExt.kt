package app.rickandmorty.gradle.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal fun DependencyHandler.implementation(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("implementation", it)
    }
}

internal fun DependencyHandler.androidTestImplementation(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("androidTestImplementation", it)
    }
}

internal fun DependencyHandler.kapt(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("kapt", it)
    }
}

internal fun DependencyHandler.ksp(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("ksp", it)
    }
}

internal fun DependencyHandler.coreLibraryDesugaring(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("coreLibraryDesugaring", it)
    }
}

internal fun DependencyHandler.lintChecks(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("lintChecks", it)
    }
}

internal val Project.isRootProject: Boolean
    get() = rootProject === this
