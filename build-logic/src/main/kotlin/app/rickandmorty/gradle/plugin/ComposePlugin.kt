package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.assign
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.getBooleanProperty
import app.rickandmorty.gradle.util.withPlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

public class ComposePlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.compose.compiler, libs.plugins.compose.multiplatform)

      configure<ComposeCompilerGradlePluginExtension> {
        val enableComposeCompilerReports =
          providers.getBooleanProperty(name = "ram.enableComposeCompilerReports", default = false)
        if (enableComposeCompilerReports) {
          val composeReportsFolder =
            layout.buildDirectory.map { buildDir -> buildDir.dir("reports").dir("compose") }
          reportsDestination = composeReportsFolder
          metricsDestination = composeReportsFolder
        }
      }

      pluginManager.withPlugin(libs.plugins.android.lint) {
        configurations.getByName("lintChecks").dependencies.add(libs.slack.compose.lints.get())
      }
    }
}
