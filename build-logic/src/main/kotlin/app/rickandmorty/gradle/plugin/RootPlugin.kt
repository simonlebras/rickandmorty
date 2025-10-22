package app.rickandmorty.gradle.plugin

import app.rickandmorty.gradle.dsl.apply
import app.rickandmorty.gradle.dsl.configure
import app.rickandmorty.gradle.dsl.the
import app.rickandmorty.gradle.util.isRootProject
import com.autonomousapps.DependencyAnalysisExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.language.base.plugins.LifecycleBasePlugin

public class RootPlugin : Plugin<Project> {
  override fun apply(target: Project): Unit =
    with(target) {
      require(isRootProject) { "Root plugin should only be applied on the root project." }

      val libs = the<LibrariesForLibs>()

      apply(libs.plugins.rickandmorty.codehealth)
      apply("base")

      configureDependencyAnalysis()

      tasks.named(LifecycleBasePlugin.CHECK_TASK_NAME).configure {
        dependsOn(
          provider {
            gradle.includedBuilds.map { build ->
              build.task(":${LifecycleBasePlugin.CHECK_TASK_NAME}")
            }
          }
        )
      }
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
