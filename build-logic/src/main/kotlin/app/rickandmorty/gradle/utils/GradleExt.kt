package app.rickandmorty.gradle.utils

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.FileCollectionDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.SelfResolvingDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

internal fun DependencyHandler.api(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("api", it)
    }
}

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

public fun Dependency.toIdentifier(): String? = when (this) {
    is ProjectDependency -> dependencyProject.path.intern()
    // Flat JAR/AAR files have no group
    is ModuleDependency -> if (group != null) "$group:$name" else name
    is FileCollectionDependency -> {
        // Note that this only gets the first file in the collection, ignoring the rest
        when (val files = files) {
            is ConfigurableFileCollection -> {
                files.from.firstOrNull()?.let { first ->
                    // https://github.com/gradle/gradle/pull/26317
                    val firstFile = if (first is Array<*>) {
                        first.firstOrNull()
                    } else {
                        first
                    }
                    firstFile?.toString()?.substringAfterLast("/")
                }?.intern()
            }

            is ConfigurableFileTree -> files.firstOrNull()?.name?.intern()
            else -> null
        }
    }
    // Not enough information, ignore it
    is SelfResolvingDependency -> null
    else -> throw GradleException("Unknown Dependency subtype: \n$this\n${javaClass.name}")
}

internal val Project.isRootProject: Boolean
    get() = rootProject === this

internal fun PluginManager.apply(vararg plugins: Provider<PluginDependency>) {
    plugins.forEach { plugin ->
        apply(plugin.get().pluginId)
    }
}

internal fun PluginManager.withPlugin(
    plugin: Provider<PluginDependency>,
    action: AppliedPlugin.() -> Unit,
) {
    withPlugin(plugin.get().pluginId, action)
}

internal fun PluginManager.withPlugins(
    first: Provider<PluginDependency>,
    vararg others: Provider<PluginDependency>,
    action: (AppliedPlugin) -> Unit,
) {
    withPlugin(first, action)
    others.forEach { plugin ->
        withPlugin(plugin, action)
    }
}
