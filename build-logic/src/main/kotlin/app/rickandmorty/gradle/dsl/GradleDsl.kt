package app.rickandmorty.gradle.dsl

import org.gradle.api.DomainObjectCollection
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.HasMultipleValues
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.reflect.TypeOf
import org.gradle.api.tasks.TaskCollection
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.plugin.use.PluginDependency

internal fun Project.apply(vararg plugins: Provider<PluginDependency>) {
  plugins.forEach { plugin -> pluginManager.apply(plugin.get().pluginId) }
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

@Suppress("extension_shadowed_by_member", "unchecked_cast")
internal inline fun <reified T : Task> TaskCollection<out Task>.named(
  name: String
): TaskProvider<T> = (this as TaskCollection<T>).named(name, T::class.java)

internal inline fun <reified S : Task> TaskCollection<in S>.withType(): TaskCollection<S> =
  withType(S::class.java)

internal fun <T : Any> Property<T>.assign(value: T?): Unit = set(value)

internal fun <T : Any> Property<T>.assign(value: Provider<out T>): Unit = set(value)

internal fun <T : Any> HasMultipleValues<T>.assign(elements: Iterable<T>?): Unit = set(elements)

internal fun <T : Any> HasMultipleValues<T>.assign(provider: Provider<out Iterable<T>>): Unit =
  set(provider)

internal fun <K : Any, V : Any> MapProperty<K, V>.assign(entries: Map<out K, V>?): Unit =
  set(entries)

internal fun <K : Any, V : Any> MapProperty<K, V>.assign(
  provider: Provider<out Map<out K, V>>
): Unit = set(provider)

private inline fun <reified T> typeOf(): TypeOf<T> = object : TypeOf<T>() {}
