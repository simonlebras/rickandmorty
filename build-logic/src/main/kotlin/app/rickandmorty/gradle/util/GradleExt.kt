package app.rickandmorty.gradle.util

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

internal fun DependencyHandler.api(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("api", it) }
}

internal fun DependencyHandler.implementation(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("implementation", it) }
}

internal fun DependencyHandler.coreLibraryDesugaring(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("coreLibraryDesugaring", it) }
}

internal fun DependencyHandler.lintChecks(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("lintChecks", it) }
}

@Suppress("GradleProjectIsolation")
internal val Project.isRootProject: Boolean
  get() = rootProject === this

internal typealias AndroidCommonExtension = CommonExtension<*, *, *, *, *, *>

internal fun PluginManager.apply(vararg plugins: Provider<PluginDependency>) {
  plugins.forEach { plugin -> apply(plugin.get().pluginId) }
}

internal fun PluginManager.withPlugin(
  plugin: Provider<PluginDependency>,
  action: AppliedPlugin.() -> Unit,
) {
  withPlugin(plugin.get().pluginId, action)
}
