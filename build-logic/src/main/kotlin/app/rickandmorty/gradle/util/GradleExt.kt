package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.named
import app.rickandmorty.gradle.dsl.register
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.tasks.TaskProvider
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

internal fun PluginManager.withPlugin(
  plugin: Provider<PluginDependency>,
  action: AppliedPlugin.() -> Unit,
) {
  withPlugin(plugin.get().pluginId, action)
}

internal inline fun <reified T : Task> Project.getOrCreateTask(
  name: String,
  noinline configuration: T.() -> Unit,
): TaskProvider<T> {
  return if (tasks.names.contains(name)) {
    tasks.named<T>(name).apply { configure(configuration) }
  } else {
    tasks.register<T>(name, configuration)
  }
}

internal fun ProviderFactory.getBooleanProperty(name: String, default: Boolean): Boolean =
  gradleProperty(name).orNull?.toBooleanStrict() ?: default

internal val Project.isAndroidTestEnabled
  get() = providers.getBooleanProperty(name = "ram.enableAndroidTest", default = false)
