package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.withPlugin
import com.autonomousapps.DependencyAnalysisSubExtension
import dev.zacsweers.metro.gradle.ExperimentalMetroGradleApi
import dev.zacsweers.metro.gradle.MetroPluginExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project

public class MetroPlugin : Plugin<Project> {
  @OptIn(ExperimentalMetroGradleApi::class)
  override fun apply(target: Project): Unit =
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.metro)

      configure<MetroPluginExtension> { generateContributionProviders.set(true) }

      pluginManager.withPlugin(libs.plugins.dependencyanalysis) {
        configure<DependencyAnalysisSubExtension> {
          issues { onIncorrectConfiguration { exclude(libs.metro.runtime) } }
        }
      }
    }
}
