package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.dependencies
import app.rickandmorty.gradle.dsl.kspDependenciesForAllTargets
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.api
import app.rickandmorty.gradle.util.ksp
import app.rickandmorty.gradle.util.withPlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

public class NavigationSerializationPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.kotlin.serialization, libs.plugins.ksp, libs.plugins.metro)

      pluginManager.withPlugin(libs.plugins.kotlin.multiplatform) { configureKotlinMultiplatform() }

      pluginManager.withPlugin(libs.plugins.android.application) { configureKotlinAndroid() }
      pluginManager.withPlugin(libs.plugins.android.library) { configureKotlinAndroid() }
    }
  }

  private fun Project.configureKotlinMultiplatform() {
    configure<KotlinMultiplatformExtension> {
      sourceSets.commonMain {
        dependencies { api(project(":core:navigation-serialization-runtime")) }
      }

      kspDependenciesForAllTargets { ksp(project(":core:navigation-serialization-processor")) }
    }
  }

  private fun Project.configureKotlinAndroid() {
    dependencies {
      api(project(":core:navigation-serialization-runtime"))

      ksp(":core:navigation-serialization-processor")
    }
  }
}
