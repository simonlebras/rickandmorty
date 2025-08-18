package app.rickandmorty.gradle.util

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
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

internal inline fun <reified T : Task> Project.getOrCreateTask(
  name: String,
  noinline block: T.() -> Unit,
): TaskProvider<T> {
  return if (tasks.names.contains(name)) {
    tasks.named<T>(name).apply { configure(block) }
  } else {
    tasks.register<T>(name, block)
  }
}
