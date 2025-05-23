package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

public class ComposePlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      pluginManager.apply(libs.plugins.compose.compiler, libs.plugins.compose.multiplatform)

      configure<ComposeCompilerGradlePluginExtension> {
        featureFlags =
          setOf(
            ComposeFeatureFlag.OptimizeNonSkippingGroups,
            ComposeFeatureFlag.PausableComposition,
          )

        val enableComposeCompilerReports =
          providers.gradleProperty("ram.enableComposeCompilerReports").orNull == "true"
        if (enableComposeCompilerReports) {
          val composeReportsFolder =
            layout.buildDirectory.map { buildDir -> buildDir.dir("reports").dir("compose") }
          reportsDestination = composeReportsFolder
          metricsDestination = composeReportsFolder
        }
      }
    }
}
