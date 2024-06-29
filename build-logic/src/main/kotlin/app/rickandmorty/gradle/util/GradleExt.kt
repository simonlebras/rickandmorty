package app.rickandmorty.gradle.util

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.api.variant.TestAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.findByType
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

internal fun DependencyHandler.screenshotTestImplementation(
    vararg dependencyNotations: Any,
) {
    dependencyNotations.forEach {
        add("screenshotTestImplementation", it)
    }
}

internal val Project.isRootProject: Boolean
    get() = rootProject === this

internal val Project.androidExtension: AndroidComponentsExtension<*, *, *>
    get() = extensions.findByType<LibraryAndroidComponentsExtension>()
        ?: extensions.findByType<ApplicationAndroidComponentsExtension>()
        ?: extensions.findByType<TestAndroidComponentsExtension>()
        ?: throw IllegalArgumentException("Failed to find any registered Android extension")

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
