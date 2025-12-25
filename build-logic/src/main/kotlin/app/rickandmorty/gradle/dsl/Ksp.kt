package app.rickandmorty.gradle.dsl

import app.rickandmorty.gradle.util.capitalize
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

public fun interface KspDependencies {
  public fun ksp(dependencyNotation: Any)
}

public fun KotlinTarget.kspDependencies(block: KspDependencies.() -> Unit) {
  val configurationName = "ksp${targetName.capitalize()}"
  project.dependencies {
    KspDependencies { dependencyNotation -> add(configurationName, dependencyNotation) }.block()
  }
}

public fun KotlinMultiplatformExtension.kspDependenciesForAllTargets(
  block: KspDependencies.() -> Unit
) {
  targets.configureEach {
    if (targetName != "metadata") {
      kspDependencies(block)
    }
  }
}

public fun Project.commonMainKspDependencies(block: KspDependencies.() -> Unit) {
  dependencies {
    KspDependencies { dependencyNotation -> add("kspCommonMainMetadata", dependencyNotation) }
      .block()
  }

  tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
      dependsOn("kspCommonMainKotlinMetadata")
    }
  }
}
