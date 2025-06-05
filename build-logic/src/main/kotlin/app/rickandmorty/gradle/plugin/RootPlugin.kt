package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.util.apply
import app.rickandmorty.gradle.util.isRootProject
import com.autonomousapps.DependencyAnalysisExtension
import com.osacky.doctor.DoctorExtension
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure

public class RootPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      require(isRootProject) { "Root plugin should only be applied on the root project." }

      pluginManager.apply(
        libs.plugins.rickandmorty.spotless,
        libs.plugins.dependencyanalysis,
        libs.plugins.gradledoctor,
        libs.plugins.sortdependencies,
      )

      tasks.named("check") {
        dependsOn(gradle.includedBuilds.map { build -> build.task(":check") })
      }

      configureDependencyAnalysis()

      configureGradleDoctor()
    }
}

private fun Project.configureDependencyAnalysis() {
  configure<DependencyAnalysisExtension> {
    issues {
      all {
        onAny { severity("fail") }
        onIncorrectConfiguration { exclude("org.jetbrains.kotlin:kotlin-stdlib") }
        onModuleStructure { exclude("android") }
        onUsedTransitiveDependencies { severity("ignore") }
      }
    }

    abi { exclusions { ignoreGeneratedCode() } }
  }
}

private fun Project.configureGradleDoctor() {
  configure<DoctorExtension> {
    @Suppress("DEPRECATION")
    warnWhenNotUsingParallelGC = false
  }
}
