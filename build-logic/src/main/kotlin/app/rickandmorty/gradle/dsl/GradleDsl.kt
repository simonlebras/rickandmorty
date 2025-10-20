package app.rickandmorty.gradle.dsl

import org.gradle.api.DomainObjectCollection
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Provider
import org.gradle.api.reflect.TypeOf
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.plugin.use.PluginDependency

internal fun Project.apply(vararg plugins: Provider<PluginDependency>) {
  plugins.forEach { plugin -> pluginManager.apply(plugin.get().pluginId) }
}

internal fun Project.apply(vararg plugins: String) {
  plugins.forEach(pluginManager::apply)
}

internal inline fun <reified T : Any> Project.configure(
  noinline configuration: T.() -> Unit
): Unit = extensions.configure(typeOf<T>(), configuration)

internal fun Project.dependencies(configuration: DependencyHandler.() -> Unit) {
  dependencies.configuration()
}

internal inline fun <reified T : Any> ExtensionAware.the(): T = extensions.getByType(typeOf<T>())

internal inline fun <reified S : Any> DomainObjectCollection<in S>.withType():
  DomainObjectCollection<S> = withType(S::class.java)

internal inline fun <reified T : Task> TaskContainer.register(
  name: String,
  noinline configuration: T.() -> Unit,
): TaskProvider<T> = register(name, T::class.java, configuration)

internal inline fun <reified S : Task> TaskCollection<in S>.withType(): TaskCollection<S> =
  withType(S::class.java)

private inline fun <reified T> typeOf(): TypeOf<T> = object : TypeOf<T>() {}
