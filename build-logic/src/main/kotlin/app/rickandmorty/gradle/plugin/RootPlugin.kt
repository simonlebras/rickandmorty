package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.assign
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.getOrCreateTask
import app.rickandmorty.gradle.util.isRootProject
import com.autonomousapps.DependencyAnalysisExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class RootPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      require(isRootProject) { "Root plugin should only be applied on the root project." }

      val libs = the<LibrariesForLibs>()

      apply(
        libs.plugins.rickandmorty.spotless,
        libs.plugins.dependencyanalysis,
        libs.plugins.gradledoctor,
        libs.plugins.sortdependencies,
      )

      getOrCreateTask<Task>("check") {
        dependsOn(gradle.includedBuilds.map { build -> build.task(":check") })
      }

      configureDependencyAnalysis()
    }
}

private fun Project.configureDependencyAnalysis() {
  configure<DependencyAnalysisExtension> {
    issues {
      all {
        onAny { severity("fail") }
        onUsedTransitiveDependencies { severity("ignore") }
      }
    }

    abi { exclusions { ignoreGeneratedCode() } }
  }
}
