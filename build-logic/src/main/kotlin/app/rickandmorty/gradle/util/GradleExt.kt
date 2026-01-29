package app.rickandmorty.gradle.util

import app.rickandmorty.gradle.dsl.configure
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.plugins.PluginManager
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun DependencyHandler.api(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("api", it) }
}

internal fun DependencyHandler.implementation(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("implementation", it) }
}

internal fun DependencyHandler.coreLibraryDesugaring(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("coreLibraryDesugaring", it) }
}

internal fun DependencyHandler.ksp(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("ksp", it) }
}

internal fun DependencyHandler.lintChecks(vararg dependencyNotations: Any) {
  dependencyNotations.forEach { add("lintChecks", it) }
}

@Suppress("GradleProjectIsolation")
internal val Project.isRootProject: Boolean
  get() = rootProject === this

internal fun Project.kotlinAndroid(configuration: KotlinAndroidProjectExtension.() -> Unit) {
  configure(configuration = configuration)
}

internal fun Project.kotlinJvm(configuration: KotlinJvmProjectExtension.() -> Unit) {
  configure(configuration = configuration)
}

internal fun Project.kotlinMultiplatform(configuration: KotlinMultiplatformExtension.() -> Unit) {
  configure(configuration = configuration)
}

internal fun PluginManager.withPlugin(
  plugin: Provider<PluginDependency>,
  action: AppliedPlugin.() -> Unit,
) {
  withPlugin(plugin.get().pluginId, action)
}

internal fun ProviderFactory.getBooleanProperty(name: String, default: Boolean): Boolean =
  gradleProperty(name).orNull?.toBooleanStrict() ?: default
